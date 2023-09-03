package com.example.cursos.controllers;

import com.example.cursos.models.CursoUsuarioLikeModel;
import com.example.cursos.services.CursoUsuarioLikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/cursoLike")
public class CursoUsuarioLikeController {
    @Autowired
    CursoUsuarioLikeService cursoUsuarioLikeService;

    // Método POST para guardar like
    @PostMapping()
    public CursoUsuarioLikeModel guardarCursoUsuarioLike(@RequestBody CursoUsuarioLikeModel curso) {
        return this.cursoUsuarioLikeService.guardarCursoUsuarioLike(curso);
    }
}
