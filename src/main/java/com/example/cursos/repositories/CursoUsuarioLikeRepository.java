package com.example.cursos.repositories;

import com.example.cursos.models.CursoModel;
import com.example.cursos.models.CursoUsuarioLikeModel;
import com.example.cursos.models.UsuarioModel;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CursoUsuarioLikeRepository extends CrudRepository<CursoUsuarioLikeModel, Long> {
    // @Query("SELECT * FROM CursoUsuarioLikeModel c WHERE c.curso = :curso AND
    // c.usuario = :usuario")
    // CursoUsuarioLikeModel findByCursoAndUsuario(@Param("curso") CursoModel curso,
    // @Param("usuario") UsuarioModel usuario);

    CursoUsuarioLikeModel findByCursoAndUsuario(CursoModel curso, UsuarioModel usuario);

}