package com.example.cursos.controllers;

import com.example.cursos.models.CursoUsuarioComentarioModel;
import com.example.cursos.services.CursoUsuarioComentarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/cursoComentario")
public class CursoUsuarioComentarioController {
    @Autowired
    CursoUsuarioComentarioService cursoUsuarioComentarioService;

    // Método POST para guardar comentario
    @PostMapping()
    public CursoUsuarioComentarioModel guardarCursoUsuarioComentario(@RequestBody CursoUsuarioComentarioModel curso) {
        return this.cursoUsuarioComentarioService.guardarCursoUsuarioComentario(curso);
    }

    // Método POST para listar comentario por curso, enviando el Id del curso
    @PostMapping(path = "/data")
    public ArrayList<CursoUsuarioComentarioModel> obtenerCursoUsuarioComentarioPorId(
            @RequestBody CursoUsuarioComentarioModel curso) {
        Long curso_id = curso.getCurso().getId();
        return this.cursoUsuarioComentarioService.obtenerPorCursoId(curso_id);
    }

}
