
package com.libreria.controladores;

import com.libreria.errores.ErrorServicio;
import com.libreria.servicios.EditorialServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/editorial")

public class EditorialControlador {


    @Autowired
    private EditorialServicio editorialServicio;

    @GetMapping
    public String libro() {
        return "libros.html";
    }

    @PostMapping("/crearEditorial")
    public String crearEditorial(RedirectAttributes attr, @RequestParam String nombre) {

        try {

            editorialServicio.crearEditorial(nombre);

        } catch (ErrorServicio e) {
            attr.addFlashAttribute("error", e.getMessage());
            //return "redirect:/libros"; 
        }

        return "redirect:/libros";
    }

}












    

