package com.example.cursos.controllers;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.cursos.models.UsuarioModel;
import com.example.cursos.repositories.UsuarioRepository;
import com.example.cursos.services.UsuarioService;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UsuarioService usuarioService;

    // Método POST para hacer login, se envia email y password, y retorna true si es
    // correcto, false si es incorrecto, la url es /auth/login
    @PostMapping("/login")
    public Map<String, Object> iniciarSesion(@RequestBody UsuarioModel usuario) {
        Map<String, Object> response = new HashMap<>();

        if (usuario.getEmail() == "") {
            response.put("response", "Ingrese un email");
            return response;
        }

        if (!isValidEmail(usuario.getEmail())) {
            response.put("response", "Ingrese un email válido");
            return response;
        }

        if (usuario.getPassword() == "") {
            response.put("response", "Ingrese contraseña");
            return response;
        }

        try {
            UsuarioModel usuarioValor = usuarioRepository.findByEmail(usuario.getEmail());

            if (usuarioValor != null && passwordEncoder.matches(usuario.getPassword(), usuarioValor.getPassword())) {
                response.put("response", true);
                response.put("data", usuario);
            } else {
                response.put("response", false);
            }
        } catch (Exception e) {
            response.put("response", false);
        }

        return response;
    }

    // Método POST para registrase, se envia los datos de UsuarioModel y retorna el
    // mensaje, la url es /auth/register
    @PostMapping("/register")
    @ResponseBody
    public Map<String, Object> registrarUsuario(@RequestBody UsuarioModel usuario) {
        Map<String, Object> response = new HashMap<>();

        if (usuario.getNombre() == "") {
            response.put("response", "Ingrese nombre");
            return response;
        }

        if (usuario.getEmail() == "") {
            response.put("response", "Ingrese un email");
            return response;
        }

        if (!isValidEmail(usuario.getEmail())) {
            response.put("response", "Ingrese un email válido");
            return response;
        }

        if (usuario.getPassword() == "") {
            response.put("response", "Ingrese contraseña");
            return response;
        }

        try {
            if (usuarioRepository.existsByEmail(usuario.getEmail())) {
                response.put("response", "Email ya registrado");
            } else {
                String hashedPassword = passwordEncoder.encode(usuario.getPassword());

                try {
                    String destinatario = usuario.getEmail();
                    String asunto = "CEV - Te has registrado correctamente";
                    String mensaje = "Ingrese en la aplicación usando tu email y la siguiente contraseña: "
                            + usuario.getPassword();

                    usuarioService.enviarCorreo(destinatario, asunto, mensaje);
                } catch (Exception e) {
                }

                usuario.setPassword(hashedPassword);
                usuarioRepository.save(usuario);

                response.put("response", true);
            }
        } catch (Exception e) {
            response.put("response", "Error en el registro, intente de nuevo");
        }

        return response;
    }

    // Método POST para devolver información del usuario, la url es /auth/dataUser
    @PostMapping("/dataUser")
    @ResponseBody
    public Map<String, Object> dataUsuario(@RequestBody UsuarioModel usuario) {
        Map<String, Object> response = new HashMap<>();

        try {
            if (usuarioRepository.existsByEmail(usuario.getEmail())) {
                UsuarioModel usuarioValor = usuarioRepository.findByEmail(usuario.getEmail());
                response.put("response", usuarioValor);
                response.put("success", true);
            } else {
                response.put("response", null);
                response.put("success", false);
            }
        } catch (Exception e) {
            response.put("response", null);
            response.put("success", false);
        }

        return response;
    }

    // Método POST para actualizar data del usuario, la url es /auth/editProfile
    @PostMapping("/editProfile")
    @ResponseBody
    public Map<String, Object> editProfile(@RequestBody UsuarioModel usuario) {
        Map<String, Object> response = new HashMap<>();

        Optional<UsuarioModel> usuarioOptional = usuarioRepository.findById(usuario.getId());

        if (usuarioOptional.isPresent()) {

            UsuarioModel usuarioEncontrado = usuarioOptional.get();

            if (usuario.getNombre() == "") {
                response.put("response", "Ingrese nombre");
                response.put("success", false);
                return response;
            }

            if (usuario.getEmail() == "") {
                response.put("response", "Ingrese un email");
                response.put("success", false);
                return response;
            }

            if (!isValidEmail(usuario.getEmail())) {
                response.put("response", "Ingrese un email válido");
                response.put("success", false);
                return response;
            }

            UsuarioModel usuarioEmail = usuarioRepository.findByEmail(usuario.getEmail());
            if (usuarioEmail != null && !usuarioEmail.getId().equals(usuario.getId())) {
                response.put("response", "Email ya registrado");
                response.put("success", false);
                return response;
            }

            usuarioEncontrado.setNombre(usuario.getNombre());
            usuarioEncontrado.setEmail(usuario.getEmail());
            usuarioEncontrado.setTelefono(usuario.getTelefono());

            if (usuario.getPassword() != "") {
                String hashedPassword = passwordEncoder.encode(usuario.getPassword());
                usuarioEncontrado.setPassword(hashedPassword);
            }

            if (usuario.getImagen() != "") {
                usuarioEncontrado.setImagen(usuario.getImagen());
            }

            UsuarioModel usuarioModificado = usuarioRepository.save(usuarioEncontrado);

            response.put("response", true);
            response.put("success", true);
        } else {
            response.put("response", null);
            response.put("success", false);
        }

        return response;
    }

    // Método POST para recuperar contraseña
    @PostMapping("/recover")
    @ResponseBody
    public Map<String, Object> recover(@RequestBody UsuarioModel usuario) {
        Map<String, Object> response = new HashMap<>();

        try {
            if (usuarioRepository.existsByEmail(usuario.getEmail())) {

                try {
                    UsuarioModel usuarioEmail = usuarioRepository.findByEmail(usuario.getEmail());

                    var codigoAletario = generarCodigoNumerico(8);
                    String destinatario = usuario.getEmail();

                    String asunto = "CEV - Recuperación de Contraseña";
                    String mensaje = "Ingrese con esta nueva contraseña: " + codigoAletario;

                    usuarioService.enviarCorreo(destinatario, asunto, mensaje);

                    String hashedPassword = passwordEncoder.encode(codigoAletario);
                    usuarioEmail.setPassword(hashedPassword);
                    usuarioRepository.save(usuarioEmail);

                    response.put("response", true);
                    response.put("success", true);
                } catch (Exception e) {
                    response.put("response", "Correo no enviado, intente más tarde");
                    response.put("success", true);
                }
            } else {
                response.put("response", "Email no se encuentra registrado");
                response.put("success", false);
            }
        } catch (Exception e) {
            response.put("response", null);
            response.put("success", false);
        }

        return response;
    }

    // Funcion para generar un codigo aleatorio
    public static String generarCodigoNumerico(int longitud) {
        StringBuilder codigo = new StringBuilder();

        for (int i = 0; i < longitud; i++) {
            int digito = ThreadLocalRandom.current().nextInt(0, 10);
            codigo.append(digito);
        }

        return codigo.toString();
    }

    // Funcion para validar email
    private boolean isValidEmail(String email) {
        String regex = "^[A-Za-z0-9+_.-]+@(.+)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
