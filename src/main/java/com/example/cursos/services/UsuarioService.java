package com.example.cursos.services;

import com.example.cursos.models.UsuarioModel;
import com.example.cursos.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class UsuarioService {
    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public ArrayList<UsuarioModel> obtenerUsuarios() {
        return (ArrayList<UsuarioModel>) usuarioRepository.findAll();
    }

    public UsuarioModel guardarUsuario(UsuarioModel usuario) {
        String hashedPassword = passwordEncoder.encode(usuario.getPassword());
        usuario.setPassword(hashedPassword);
        return usuarioRepository.save(usuario);
    }

    public Optional<UsuarioModel> obtenerPorId(Long id) {
        return usuarioRepository.findById(id);
    }

    public boolean eliminarUsuario(Long id) {
        try {
            usuarioRepository.deleteById(id);
            return true;
        } catch (Exception err) {
            return false;
        }
    }

    public void enviarCorreo(String destinatario, String asunto, String mensaje) {
        SimpleMailMessage correo = new SimpleMailMessage();
        correo.setTo(destinatario);
        correo.setSubject(asunto);
        correo.setText(mensaje);

        javaMailSender.send(correo);
    }
}
