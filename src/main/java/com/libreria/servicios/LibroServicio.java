package com.libreria.servicios;

import com.libreria.entidades.Autor;
import com.libreria.entidades.Editorial;
import com.libreria.entidades.Libro;
import com.libreria.errores.ErrorServicio;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.libreria.repositorios.LibroRepositorio;
import java.util.List;

@Service
public class LibroServicio {

    @Autowired
    private LibroRepositorio libroRepositorio;

    @Autowired
    private AutorServicio autorServicio;

    @Autowired
    private EditorialServicio editorialServicio;

    @Transactional(rollbackFor = {Exception.class})
    public Libro crearLibro(Long isbn, String titulo, Integer anio, Integer ejemplares, Integer ejemplaresPrestados,
            Integer ejemplaresRestantes, String idAutor, String idEditorial) throws ErrorServicio {

        validar(isbn, titulo, anio, ejemplares, ejemplaresPrestados, ejemplaresRestantes);

        Libro libro = new Libro();

        libro.setIsbn(isbn);
        libro.setTitulo(titulo);
        libro.setAnio(anio);
        libro.setEjemplares(ejemplares);
        libro.setEjemplaresPrestados(ejemplaresPrestados);
        libro.setEjemplaresRestantes(ejemplaresRestantes);
        libro.setAlta(true);
        Autor autor = autorServicio.buscarPorId(idAutor);
        libro.setAutor(autor);

        Editorial editorial = editorialServicio.buscarPorId(idEditorial);
        libro.setEditorial(editorial);

        return libroRepositorio.save(libro);
    }

    @Transactional(rollbackFor = {Exception.class})
    public void modificarLibro(String id, Long isbn, String titulo, Integer anio, Integer ejemplares, Integer ejemplaresPrestados,
            Integer ejemplaresRestantes) throws ErrorServicio {

        validar(isbn, titulo, anio, ejemplares, ejemplaresPrestados, ejemplaresRestantes);

        Optional<Libro> respuesta = libroRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Libro libro = respuesta.get();

            libro.setIsbn(isbn);
            libro.setTitulo(titulo);
            libro.setAnio(anio);
            libro.setEjemplares(ejemplares);
            libro.setEjemplaresPrestados(ejemplaresPrestados);
            libro.setEjemplaresRestantes(ejemplaresRestantes);
            libroRepositorio.save(libro);
        } else {
            throw new ErrorServicio("No se encontró el libro a modificar");
        }
    }

    @Transactional
    public void habilitar(String id) throws ErrorServicio {

        Optional<Libro> respuesta = libroRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Libro libro = respuesta.get();
            libro.setAlta(true);
            libroRepositorio.save(libro);
        } else {
            throw new ErrorServicio("No se encontró el libro a deshabilitar");

        }
    }
    
    
    
    
    @Transactional
    public void deshabilitar(String id) throws ErrorServicio {

        Optional<Libro> respuesta = libroRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Libro libro = respuesta.get();
            libro.setAlta(false);
            libroRepositorio.save(libro);
        } else {
            throw new ErrorServicio("No se encontró el libro a deshabilitar");

        }
    }

    @Transactional(readOnly = true)
    public Libro buscarPorId(String id) throws ErrorServicio {
        Optional<Libro> respuesta = libroRepositorio.findById(id);

        if (respuesta.isPresent()) {
            Libro libro = respuesta.get();
            return libro;
        } else {
            throw new ErrorServicio("No existe ese libro");
        }

    }

    @Transactional(readOnly = true)
    public List<Libro> listarTodos() {
        return libroRepositorio.findAll();
    }

    @Transactional(rollbackFor = {Exception.class})
    public void eliminarLibro(String id) throws ErrorServicio {
        Optional<Libro> respuesta = libroRepositorio.findById(id);
        if (respuesta.isPresent()) {
            libroRepositorio.deleteById(respuesta.get().getId());
        } else {
            throw new ErrorServicio("No se encuentra el libro a eliminar");
        }

    }

    
    
    
    private void validar(Long isbn, String titulo, Integer anio, Integer ejemplares, Integer ejemplaresPrestados,
            Integer ejemplaresRestantes) throws ErrorServicio {

        if (titulo.isEmpty()) {
            throw new ErrorServicio("El Nombre del Libro no puede estar vacio");
        }

        if (isbn == null) {
            throw new ErrorServicio("El ISBN no puede estar vacio");
        }

        if (anio == null || anio >= 2023 || anio < 0) {
            throw new ErrorServicio("El año ingresado es invalido");
        }

        if (ejemplaresPrestados == null) {
            throw new ErrorServicio("Los Ejemplares prestados no pueden estar vacios");
        }

        if (ejemplares == null) {
            throw new ErrorServicio("Los Ejemplares no pueden estar vacios");

        }

        if (ejemplaresRestantes == null) {
            throw new ErrorServicio("Los Ejemplares Restantes no pueden estar vacios");
        }

    }

}
