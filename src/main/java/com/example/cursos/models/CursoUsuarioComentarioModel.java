package com.example.cursos.models;

import jakarta.persistence.*;

@Entity
@Table(name = "curso_usuario_comentario")
public class CursoUsuarioComentarioModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private UsuarioModel usuario;

    @ManyToOne
    @JoinColumn(name = "curso_id")
    private CursoModel curso;

    @Column(columnDefinition = "TEXT")
    private String comentario;

    public void setId(Long id) {
        this.id = id;
    }

    public UsuarioModel getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioModel usuario) {
        this.usuario = usuario;
    }

    public CursoModel getCurso() {
        return curso;
    }

    public void setCurso(CursoModel curso) {
        this.curso = curso;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }
}
