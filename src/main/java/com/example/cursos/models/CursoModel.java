package com.example.cursos.models;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "cursos")
public class CursoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    @ManyToMany(mappedBy = "cursosLikes")
    private Set<UsuarioModel> usuariosLikes = new HashSet<>();

    @Column(nullable = false)
    private String titulo;

    @Column(nullable = false, columnDefinition = "TEXT", length = 8192)
    private String descripcion;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String imagen;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String imagen_detalle;

    @Column(nullable = false, name = "likes", columnDefinition = "Decimal(10,0) default '0.00'")
    private Integer likes;

    @Column(nullable = false, name = "comentarios", columnDefinition = "Decimal(10,0) default '0.00'")
    private Integer comentarios;

    @Column(nullable = true, columnDefinition = "TEXT")
    private String url;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getImagen_detalle() {
        return imagen_detalle;
    }

    public void setImagen_detalle(String imagen_detalle) {
        this.imagen_detalle = imagen_detalle;
    }

    public Integer getLikes() {
        return likes;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }

    public Integer getComentarios() {
        return comentarios;
    }

    public void setComentarios(Integer comentarios) {
        this.comentarios = comentarios;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
