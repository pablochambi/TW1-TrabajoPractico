package com.tallerwebi.dominio;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
//import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String password;
    private String rol;
    private Boolean activo = false;
    private String username;
    private String nombre;
    private String apellido;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Pedido> misPedidos= new ArrayList<>();

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Archivo> misArchivos = new ArrayList<>();

    @OneToMany(mappedBy = "emisor", cascade = CascadeType.ALL)
    private List<Mensaje> mensajesEnviados;

    @OneToMany(mappedBy = "receptor", cascade = CascadeType.ALL)
    private List<Mensaje> mensajesRecibidos;

    public void addArchivo(Archivo archivo) {
        misArchivos.add(archivo);
        archivo.setUsuario(this);
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getRol() {
        return rol;
    }
    public void setRol(String rol) {
        this.rol = rol;
    }
    public Boolean getActivo() {
        return activo;
    }
    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public boolean activo() {
        return activo;
    }

    public void activar() {
        activo = true;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    public String getUsername() {
        return username;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public List<Archivo> getArchivos() {
        return misArchivos;
    }

    public void setArchivos(List<Archivo> archivos) {
        this.misArchivos = archivos;
    }

    public List<Pedido> getMisPedidos() {
        return misPedidos;
    }

    public void setMisPedidos(List<Pedido> misPedidos) {
        this.misPedidos = misPedidos;
    }
}
