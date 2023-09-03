package com.example.cursos.repositories;

import com.example.cursos.models.UsuarioModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends CrudRepository<UsuarioModel, Long> {
    UsuarioModel findByEmail(String email);

    boolean existsByEmail(String email);

}
