package br.com.matheus.cursomc.resources;

import br.com.matheus.cursomc.domain.Categoria;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.swing.text.Caret;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/categorias")
public class CategoriaResource {

    @RequestMapping(method = RequestMethod.GET)
    public List<Categoria> listar() {
        Categoria c = new Categoria(1, "Informática");
        Categoria c2 = new Categoria(2, "Escritorio");

        List<Categoria> list = new ArrayList<>();

        list.add(c);
        list.add(c2);

        return list;
    }

}
