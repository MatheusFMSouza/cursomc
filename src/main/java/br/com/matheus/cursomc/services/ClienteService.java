package br.com.matheus.cursomc.services;

import br.com.matheus.cursomc.domain.Cidade;
import br.com.matheus.cursomc.domain.Cliente;
import br.com.matheus.cursomc.domain.Endereco;
import br.com.matheus.cursomc.domain.enums.TipoCliente;
import br.com.matheus.cursomc.dto.ClienteDTO;
import br.com.matheus.cursomc.dto.ClienteNewDTO;
import br.com.matheus.cursomc.repositories.ClienteRepository;
import br.com.matheus.cursomc.repositories.EnderecoRepository;
import br.com.matheus.cursomc.services.exceptions.DataIntegrityException;
import br.com.matheus.cursomc.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository repo;

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private BCryptPasswordEncoder pe;

    public Cliente find(Integer id) {
        Optional<Cliente> obj = repo.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException(
                "Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));
    }


    @Transactional
    public Cliente insert(Cliente obj) {
        obj.setId(null);
        obj = repo.save(obj);
        enderecoRepository.saveAll(obj.getEnderecos());

        return obj;
    }


    public Cliente update(Cliente obj) {
        Cliente newObj = find(obj.getId());
        updateData(newObj, obj);
        return repo.save(newObj);
    }

    public void delete(Integer id) {
        find(id);
        try {
            repo.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException("Não é possivel excluir porque a pedidos relacionados");
        }
    }

    public List<Cliente> findAll() {
        return repo.findAll();
    }

    public Page<Cliente> findPage(Integer page, Integer linesPerPages, String orderBy, String direction) {
        PageRequest pageRequest = PageRequest.of(page, linesPerPages, Sort.Direction.valueOf(direction), orderBy);

        return repo.findAll(pageRequest);
    }

    public Cliente fromDTO(ClienteDTO objDTO) {
        return new Cliente(objDTO.getId(), objDTO.getNome(), objDTO.getEmail(), null, null,null);
    }

    public Cliente fromDTO(ClienteNewDTO objDto) {
        Cliente cli = new Cliente(null,
                objDto.getNome(),
                objDto.getEmail(),
                objDto.getCpfOuCnpj(),
                TipoCliente.toEnum(objDto.getTipo()),
                pe.encode(objDto.getSenha()));

        Cidade cid = new Cidade(objDto.getCidadeId(), null, null);

        Endereco end = new Endereco(null,
                objDto.getLogradouro(),
                objDto.getNumero(),
                objDto.getComplemento(),
                objDto.getBairro(),
                objDto.getCep(),
                null,
                null);
        end.setCidade(cid);
        end.setCliente(cli);

        cli.getEnderecos().add(end);
        cli.getTelefones().add(objDto.getTelefone1());

        if (objDto.getTelefone2() != null) {
            cli.getTelefones().add(objDto.getTelefone2());
        }
        if (objDto.getTelefone3() != null) {
            cli.getTelefones().add(objDto.getTelefone3());
        }

        return cli;
    }


    private void updateData(Cliente newObj, Cliente obj) {
        newObj.setNome(obj.getNome());
        newObj.setEmail(obj.getEmail());
    }


}
