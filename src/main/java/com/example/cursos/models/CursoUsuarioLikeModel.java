package com.example.cursos.models;

import jakarta.persistence.*;

@Entity
@Table(name = "curso_usuario_like", uniqueConstraints = {
        @UniqueConstraint(columnNames = { "curso_id", "usuario_id" })
})

public class CursoUsuarioLikeModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private UsuarioModel usuario;

    @ManyToOne
    @JoinColumn(name = "curso_id")
    private CursoModel curso;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CursoModel getCurso() {
        return curso;
    }

    public void setCurso(CursoModel curso) {
        this.curso = curso;
    }

    public UsuarioModel getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioModel usuario) {
        this.usuario = usuario;
    }
}