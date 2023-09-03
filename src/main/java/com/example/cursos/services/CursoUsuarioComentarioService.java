package com.example.cursos.services;

import com.example.cursos.models.CursoUsuarioComentarioModel;
import com.example.cursos.repositories.CursoUsuarioComentarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class CursoUsuarioComentarioService {

    @Autowired
    CursoUsuarioComentarioRepository cursoUsuarioComentarioRepository;

    public ArrayList<CursoUsuarioComentarioModel> obtenerCursoUsuarioComentario() {
        return (ArrayList<CursoUsuarioComentarioModel>) cursoUsuarioComentarioRepository.findAll();
    }

    public CursoUsuarioComentarioModel guardarCursoUsuarioComentario(CursoUsuarioComentarioModel usuario) {
        return cursoUsuarioComentarioRepository.save(usuario);
    }

    public Optional<CursoUsuarioComentarioModel> obtenerPorId(Long id) {
        return cursoUsuarioComentarioRepository.findById(id);
    }

    public ArrayList<CursoUsuarioComentarioModel> obtenerPorCursoId(Long curso_id) {
        return cursoUsuarioComentarioRepository.findByCursoId(curso_id);
    }

    public boolean eliminarCursoUsuarioComentario(Long id) {
        try {
            cursoUsuarioComentarioRepository.deleteById(id);
            return true;
        } catch (Exception err) {
            return false;
        }
    }
}
