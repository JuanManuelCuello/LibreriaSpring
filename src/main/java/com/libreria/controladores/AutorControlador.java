package com.libreria.controladores;

import com.libreria.errores.ErrorServicio;
import com.libreria.servicios.AutorServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/autor")
public class AutorControlador {

    @Autowired
    private AutorServicio autorServicio;
    
    
    @PostMapping("/crearAutor")
    public String crearAutor(RedirectAttributes attr, @RequestParam String nombre) {

        try {

            autorServicio.crearAutor(nombre);

        } catch (ErrorServicio e) {
            attr.addFlashAttribute("error", e.getMessage());
             
        }

        return "redirect:/libros";
    }

}
