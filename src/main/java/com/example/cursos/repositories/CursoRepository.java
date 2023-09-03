package com.example.cursos.repositories;

import com.example.cursos.models.CursoModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CursoRepository extends CrudRepository<CursoModel, Long> {
}
