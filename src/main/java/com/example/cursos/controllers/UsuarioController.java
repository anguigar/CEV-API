package com.example.cursos.controllers;

import com.example.cursos.models.UsuarioModel;
import com.example.cursos.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {
    @Autowired
    UsuarioService usuarioService;

    // Método POST para guardar usuario
    @PostMapping()
    public UsuarioModel guardarUsuario(@RequestBody UsuarioModel usuario) {
        return this.usuarioService.guardarUsuario(usuario);
    }

    // Método GET para mostrar usuario por Id
    @GetMapping(path = "/{id}")
    public Optional<UsuarioModel> obtenerUsuarioPorId(@PathVariable("id") Long id) {
        return this.usuarioService.obtenerPorId(id);
    }

    // Método DELETE para eliminar usuario por Id
    @DeleteMapping(path = "/{id}")
    public String eliminarPorId(@PathVariable("id") Long id) {
        boolean ok = this.usuarioService.eliminarUsuario(id);
        if (ok) {
            return "Se eliminó el usuario con id " + id;
        } else {
            return "No pudo eliminar el usuario con id" + id;
        }
    }
}
