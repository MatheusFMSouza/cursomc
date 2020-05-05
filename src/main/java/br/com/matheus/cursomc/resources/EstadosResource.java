package br.com.matheus.cursomc.resources;

import br.com.matheus.cursomc.domain.Cidade;
import br.com.matheus.cursomc.domain.Estado;
import br.com.matheus.cursomc.dto.CidadeDTO;
import br.com.matheus.cursomc.dto.EstadoDTO;
import br.com.matheus.cursomc.services.CidadeService;
import br.com.matheus.cursomc.services.EstadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value="/estados")
public class EstadosResource {

    @Autowired
    private EstadoService estadoService;

    @Autowired
    private CidadeService cidadeService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<EstadoDTO>> findAll(){
        List<Estado> list = estadoService.findAll();
        List<EstadoDTO> listDTO = list.stream().map(obj -> new EstadoDTO(obj.getId(),obj.getNome())).collect(Collectors.toList());
        return ResponseEntity.ok().body(listDTO);
    }

    @RequestMapping(value ="/{estadoId}/cidades",method = RequestMethod.GET)
    public ResponseEntity<List<CidadeDTO>> findCidades(@PathVariable Integer estadoId){
        List<Cidade> list = cidadeService.findByEstado(estadoId);
        List<CidadeDTO> listDTO = list.stream().map(obj -> new CidadeDTO(obj.getId(),obj.getNome())).collect(Collectors.toList());
        return ResponseEntity.ok().body(listDTO);
    }
}
