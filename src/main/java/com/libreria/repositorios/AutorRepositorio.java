
package com.libreria.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.libreria.entidades.Autor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;



@Repository
public interface AutorRepositorio extends JpaRepository <Autor, String> {
    
      @Query ("SELECT a FROM Autor a WHERE a.nombre = :nombre")
public Autor buscarPorNombre (@Param("nombre") String nombre);
    
    
}
