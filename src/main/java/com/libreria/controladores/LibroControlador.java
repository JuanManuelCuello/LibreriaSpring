/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.libreria.controladores;

import com.libreria.entidades.Autor;
import com.libreria.entidades.Editorial;
import com.libreria.entidades.Libro;
import com.libreria.errores.ErrorServicio;
import com.libreria.servicios.AutorServicio;
import com.libreria.servicios.EditorialServicio;
import com.libreria.servicios.LibroServicio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/libros")
public class LibroControlador {

    @Autowired
    private LibroServicio libroServicio;

    @Autowired
    private AutorServicio autorServicio;

    @Autowired
    private EditorialServicio editorialServicio;

    @GetMapping
    public String libro(ModelMap modelo) {

        List<Libro> libros = libroServicio.listarTodos();
        modelo.put("libros", libros);

        List<Autor> autores = autorServicio.listarTodos();
        modelo.put("autores", autores);

        List<Editorial> editoriales = editorialServicio.listarTodos();
        modelo.put("editoriales", editoriales);

        return "libros.html";

    }

    @PostMapping("/crearLibro")
    public String crearLibro(RedirectAttributes attr, @RequestParam String isbn, @RequestParam String titulo, @RequestParam String anio, @RequestParam String ejemplares,
            @RequestParam String ejemplaresPrestados, @RequestParam String ejemplaresRestantes, @RequestParam(required = false) String idAutor,
            @RequestParam(required = false) String idEditorial) {

        try {

            libroServicio.crearLibro(Long.parseLong(isbn),
                    titulo, Integer.parseInt(anio), Integer.parseInt(ejemplares),
                    Integer.parseInt(ejemplaresPrestados), Integer.parseInt(ejemplaresRestantes),
                    idAutor, idEditorial);

        } catch (ErrorServicio e) {
            attr.addFlashAttribute("error", e.getMessage());

        }

        return "redirect:/libros";
    }

    @GetMapping("/editar-perfil")
    public String editarLibro(@RequestParam String id, ModelMap modelo) throws ErrorServicio {

        List<Autor> autores = autorServicio.listarTodos();
        modelo.put("autores", autores);

        List<Editorial> editoriales = editorialServicio.listarTodos();
        modelo.put("editoriales", editoriales);

        try {
            Libro libro = libroServicio.buscarPorId(id);
            modelo.addAttribute("perfil", libro);
        } catch (ErrorServicio e) {
            modelo.addAttribute("error", e.getMessage());

        }
        return "redirect:/libros";

    }

    @PostMapping("/actualizar-perfil")
    public String registrar(ModelMap modelo, RedirectAttributes attr, @RequestParam String id, @RequestParam String isbn, @RequestParam String titulo, @RequestParam String anio, @RequestParam String ejemplares,
            @RequestParam String ejemplaresPrestados, @RequestParam String ejemplaresRestantes, @RequestParam(required = false) String idAutor,
            @RequestParam(required = false) String idEditorial) throws ErrorServicio {
        Libro libro = null;

        try {
            libro = libroServicio.buscarPorId(id);

            libroServicio.editarLibro(id, Long.parseLong(isbn),
                    titulo, Integer.parseInt(anio), Integer.parseInt(ejemplares),
                    Integer.parseInt(ejemplaresPrestados), Integer.parseInt(ejemplaresRestantes),
                    idAutor, idEditorial);
            return "redirect:/libros";
        } catch (ErrorServicio e) {
            attr.addFlashAttribute("error", e.getMessage());

            return "redirect:/libros";
        }

    }

    @GetMapping("/eliminarLibro/{id}")
    public String eliminarLibro(@PathVariable ("id") String id, RedirectAttributes attr) throws ErrorServicio {

        try {
            libroServicio.eliminarLibro(id);
            attr.addFlashAttribute("Se ha elimando el libro");
             return "redirect:/libros";
            
        } catch (ErrorServicio e) {
            attr.addFlashAttribute(e.getMessage());
            
        }

         return "redirect:/libros";
    }

}
