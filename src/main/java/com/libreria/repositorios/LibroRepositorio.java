package com.libreria.repositorios;

import com.libreria.entidades.Libro;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LibroRepositorio extends JpaRepository<Libro, String> {

    @Query("SELECT l FROM Libro l WHERE l.id = :id")
    public Libro buscarPorId(@Param("id") String Id);

    @Query("SELECT l FROM Libro l WHERE l.editorial.id like: id")
    public List<Libro> buscarPorIdEditorial (@Param("id") String ID);
    
     @Query("SELECT l FROM Libro l WHERE l.autor.id like: id")
    public List<Libro> buscarPorIdAutor (@Param("id") String ID);
    
}
