package com.libreria.servicios;

import com.libreria.entidades.Editorial;
import com.libreria.errores.ErrorServicio;
import com.libreria.repositorios.EditorialRepositorio;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EditorialServicio {

    @Autowired
    private EditorialRepositorio editorialRepositorio;

     @Transactional(readOnly = true)
    public List<Editorial> listarTodos() {
        return editorialRepositorio.findAll();
    }
    
    
    @Transactional(rollbackFor = {Exception.class})
    public Editorial crearEditorial(String nombre) throws ErrorServicio {

        validar(nombre);

        Editorial editorial = new Editorial();
        editorial.setNombre(nombre);
        editorial.setAlta(true);

        return editorialRepositorio.save(editorial);
    }

    @Transactional(rollbackFor = {Exception.class})
    public void modificarEditorial(String id, String nombre, Boolean alta) throws ErrorServicio {

        validar(nombre);

        Optional<Editorial> respuesta = editorialRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Editorial editorial = respuesta.get();
            editorial.setNombre(nombre);
            editorial.setAlta(alta);

            editorialRepositorio.save(editorial);
        } else {
            throw new ErrorServicio("No se encontró el editorial solicitado");
        }
    }
    @Transactional
    public void habilitar(String id) throws ErrorServicio {

        Optional<Editorial> respuesta = editorialRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Editorial editorial = respuesta.get();
            editorial.setAlta(true);
            editorialRepositorio.save(editorial);
        } else {
            throw new ErrorServicio("No se encontró el editorial solicitado");

        }
    }
    
    
    @Transactional
    public void deshabilitar(String id) throws ErrorServicio {

        Optional<Editorial> respuesta = editorialRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Editorial editorial = respuesta.get();
            editorial.setAlta(false);
            editorialRepositorio.save(editorial);
        } else {
            throw new ErrorServicio("No se encontró el editorial solicitado");

        }
    }

    @Transactional(readOnly = true)
    public Editorial buscarPorId(String id) throws ErrorServicio {
        Optional<Editorial> respuesta = editorialRepositorio.findById(id);

        if (respuesta.isPresent()) {
            Editorial editorial = respuesta.get();
            return editorial;
        } else {
            throw new ErrorServicio("No existe esta editorial");
        }

    }

    private void validar(String nombre) throws ErrorServicio {

        if (nombre.isEmpty()) {
            throw new ErrorServicio("ERROR: El Nombre de la Editorial no puede estar vacio");
        }

    }

}
