package com.example.cursos.controllers;

import com.example.cursos.models.CursoModel;
import com.example.cursos.models.CursoUsuarioLikeModel;
import com.example.cursos.models.UsuarioModel;
import com.example.cursos.repositories.CursoRepository;
import com.example.cursos.repositories.CursoUsuarioLikeRepository;
import com.example.cursos.repositories.UsuarioRepository;
import com.example.cursos.services.CursoService;
import com.example.cursos.services.CursoUsuarioLikeService;

import jakarta.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/curso")
public class CursoController {
    @Autowired
    CursoService cursoService;

    @Autowired
    CursoUsuarioLikeService cursoUsuarioLikeService;

    @Autowired
    CursoRepository cursoRepository;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    CursoUsuarioLikeRepository cursoUsuarioLikeRepository;

    private final Logger logger = LoggerFactory.getLogger(CursoController.class);

    // Método GET para obtener el listado de cursos, la url es /curso
    @GetMapping()

    public Map<String, Object> obtenerCursos() {
        Map<String, Object> response = new HashMap<>();

        response.put("response", cursoService.obtenerCursos());
        response.put("success", true);
        return response;
    }

    // Método GET para mostrar información del curso, la url es /curso/data, donde
    // se envia el id que es el ID de registro de la base de datos
    @PostMapping(path = "data")
    public Optional<CursoModel> obtenerCursoPorId(@RequestBody CursoModel curso) {
        return this.cursoService.obtenerPorId(curso.getId());
    }

    // Método POST para crear un nuevo curso, la url es /curso, se envian los datos
    // establecidos en el CursoModel
    @PostMapping()
    public CursoModel guardarCurso(@RequestBody CursoModel curso) {
        return this.cursoService.guardarCurso(curso);
    }

    // Método GET para mostrar información del curso, la url es /like/data, donde
    // se envia el id de usuario y id de curso
    @PostMapping(path = "/like/data")
    public CursoUsuarioLikeModel obtenerCursoUsuarioLike(@RequestBody CursoUsuarioLikeModel cursoUsuarioLike) {
        Long curso_id = cursoUsuarioLike.getCurso().getId();
        Long usuario_id = cursoUsuarioLike.getUsuario().getId();

        CursoModel curso = cursoRepository.findById(curso_id)
                .orElseThrow(() -> new EntityNotFoundException("Curso no encontrado"));

        UsuarioModel usuario = usuarioRepository.findById(usuario_id)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));

        CursoUsuarioLikeModel cursoUsuarioLikeModel = cursoUsuarioLikeRepository.findByCursoAndUsuario(curso, usuario);
        if (cursoUsuarioLikeModel != null) {
            return cursoUsuarioLikeModel;
        } else {
            return cursoUsuarioLike;
        }
    }

    // Método POST para guardar si se le dio like a un nuevo curso, la url
    // /curso/like, se envian los valores usuario_id, y curso_id
    @PostMapping(path = "/like")
    public CursoUsuarioLikeModel guardarCursoUsuarioLike(@RequestBody CursoUsuarioLikeModel cursoUsuarioLike) {
        Long curso_id = cursoUsuarioLike.getCurso().getId();
        Long usuario_id = cursoUsuarioLike.getUsuario().getId();

        CursoModel curso = cursoRepository.findById(curso_id)
                .orElseThrow(() -> new EntityNotFoundException("Curso no encontrado"));

        UsuarioModel usuario = usuarioRepository.findById(usuario_id)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));

        CursoUsuarioLikeModel cursoUsuarioLikeModel = cursoUsuarioLikeRepository.findByCursoAndUsuario(curso, usuario);
        if (cursoUsuarioLikeModel == null) {
            Integer count = curso.getLikes() + 1;
            curso.setLikes(count);
            this.cursoService.guardarCurso(curso);

            return this.cursoService.guardarCursoUsuarioLike(cursoUsuarioLike);
        } else {
            return cursoUsuarioLike;
        }
    }

    // Método DELETE para eliminar un like de un curso, la url es
    // /curso/deleteLike
    @PostMapping(path = "/deleteLike")
    public Map<String, Object> eliminarPorCursoUsuario(@RequestBody CursoUsuarioLikeModel cursoUsuarioLike) {

        Map<String, Object> response = new HashMap<>();
        boolean success = false;

        Long curso_id = cursoUsuarioLike.getCurso().getId();
        Long usuario_id = cursoUsuarioLike.getUsuario().getId();

        CursoModel curso = cursoRepository.findById(curso_id)
                .orElseThrow(() -> new EntityNotFoundException("Curso no encontrado"));

        UsuarioModel usuario = usuarioRepository.findById(usuario_id)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));

        CursoUsuarioLikeModel cursoUsuarioLikeModel = cursoUsuarioLikeRepository.findByCursoAndUsuario(curso, usuario);
        if (cursoUsuarioLikeModel != null) {
            cursoUsuarioLikeRepository.deleteById(cursoUsuarioLikeModel.getId());

            Integer count = curso.getLikes() - 1;
            curso.setLikes(count);
            this.cursoService.guardarCurso(curso);

            success = true;
        } else {
            success = false;
        }

        response.put("curso_id", curso_id);
        response.put("usuario_id", usuario_id);
        response.put("cursoUsuarioLike", cursoUsuarioLike);
        response.put("success", success);

        return response;
    }
}
