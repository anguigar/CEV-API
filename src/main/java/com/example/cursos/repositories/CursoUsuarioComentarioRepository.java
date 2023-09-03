package com.example.cursos.repositories;

import com.example.cursos.models.CursoUsuarioComentarioModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface CursoUsuarioComentarioRepository extends CrudRepository<CursoUsuarioComentarioModel, Long> {

    public abstract ArrayList<CursoUsuarioComentarioModel> findByCursoId(Long curso_id);

}
