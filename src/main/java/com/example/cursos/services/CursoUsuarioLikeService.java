package com.example.cursos.services;

import com.example.cursos.models.CursoUsuarioLikeModel;
import com.example.cursos.repositories.CursoUsuarioLikeRepository;

import jakarta.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class CursoUsuarioLikeService {

    @Autowired
    CursoUsuarioLikeRepository cursoUsuarioLikeRepository;

    private final Logger logger = LoggerFactory.getLogger(CursoService.class);

    public ArrayList<CursoUsuarioLikeModel> obtenerCursoUsuarioLike() {
        return (ArrayList<CursoUsuarioLikeModel>) cursoUsuarioLikeRepository.findAll();
    }

    public CursoUsuarioLikeModel guardarCursoUsuarioLike(CursoUsuarioLikeModel usuarioLike) {
        return cursoUsuarioLikeRepository.save(usuarioLike);
    }

    public boolean eliminaCursoUsuarioLike(CursoUsuarioLikeModel usuarioLike) {
        try {
            Long id = usuarioLike.getId();
            CursoUsuarioLikeModel managedLike = cursoUsuarioLikeRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Like no encontrado"));

            cursoUsuarioLikeRepository.delete(managedLike);
            return true;
        } catch (Exception err) {
            logger.error("Error al eliminar el like del curso " + usuarioLike.getId(),
                    err);
            return false;
        }

    }

    public Optional<CursoUsuarioLikeModel> obtenerPorId(Long id) {
        return cursoUsuarioLikeRepository.findById(id);
    }
}
