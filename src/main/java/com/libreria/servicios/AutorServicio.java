package com.libreria.servicios;

import com.libreria.entidades.Autor;
import com.libreria.errores.ErrorServicio;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.libreria.repositorios.AutorRepositorio;
import java.util.List;

@Service
public class AutorServicio {

    @Autowired
    private AutorRepositorio autorRepositorio;

    @Transactional(rollbackFor = {Exception.class})
    public Autor crearAutor(String nombre) throws ErrorServicio {

        validar(nombre);

        Autor autor = new Autor();
        autor.setNombre(nombre);
        autor.setAlta(true);

        return autorRepositorio.save(autor);
    }

    @Transactional(rollbackFor = {Exception.class})
    public void modificarAutor(String id, String nombre, Boolean alta) throws ErrorServicio {

        validar(nombre);

        Optional<Autor> respuesta = autorRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Autor autor = respuesta.get();
            autor.setNombre(nombre);
            autor.setAlta(alta);

            autorRepositorio.save(autor);
        } else {
            throw new ErrorServicio("No se encontró el autor solicitado");
        }
    }

    @Transactional
    public void habilitar(String id) throws ErrorServicio {

        Optional<Autor> respuesta = autorRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Autor autor = respuesta.get();
            autor.setAlta(true);
            autorRepositorio.save(autor);
        } else {
            throw new ErrorServicio("No se encontró el autor solicitado");

        }
    }
    
    
    @Transactional
    public void deshabilitar(String id) throws ErrorServicio {

        Optional<Autor> respuesta = autorRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Autor autor = respuesta.get();
            autor.setAlta(false);
            autorRepositorio.save(autor);
        } else {
            throw new ErrorServicio("No se encontró el autor solicitado");

        }
    }

    @Transactional(readOnly = true)
    public Autor buscarPorId(String id) throws ErrorServicio {
        Optional<Autor> respuesta = autorRepositorio.findById(id);

        if (respuesta.isPresent()) {
            Autor autor = respuesta.get();
            return autor;
        } else {
            throw new ErrorServicio("No existe ese autor");
        }

    }

    
    //////// prueba  //////////////
     @Transactional(readOnly = true)
    public List<Autor> listarTodos() {
        return autorRepositorio.findAll();
    }
    
    
    
    private void validar(String nombre) throws ErrorServicio {

        if (nombre.isEmpty()) {
            throw new ErrorServicio("El Nombre del Autor no puede estar vacio");
        }

    }

}
