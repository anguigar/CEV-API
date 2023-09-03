package com.example.cursos.services;

import com.example.cursos.models.CursoModel;
import com.example.cursos.models.CursoUsuarioLikeModel;
import com.example.cursos.repositories.CursoRepository;
import com.example.cursos.repositories.CursoUsuarioLikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class CursoService {
    @Autowired
    CursoRepository cursoRepository;
    private final CursoUsuarioLikeRepository cursoUsuarioLikeRepository;

    @Autowired
    public CursoService(CursoUsuarioLikeRepository cursoUsuarioLikeRepository) {
        this.cursoUsuarioLikeRepository = cursoUsuarioLikeRepository;
    }

    @Autowired

    public ArrayList<CursoModel> obtenerCursos() {
        return (ArrayList<CursoModel>) cursoRepository.findAll();
    }

    public CursoModel guardarCurso(CursoModel curso) {
        return cursoRepository.save(curso);
    }

    public CursoUsuarioLikeModel guardarCursoUsuarioLike(CursoUsuarioLikeModel cursoUsuarioLike) {
        return cursoUsuarioLikeRepository.save(cursoUsuarioLike);
    }

    public Optional<CursoModel> obtenerPorId(Long id) {
        return cursoRepository.findById(id);
    }

    public boolean eliminarCurso(Long id) {
        try {
            cursoRepository.deleteById(id);
            return true;
        } catch (Exception err) {
            return false;
        }
    }
}
