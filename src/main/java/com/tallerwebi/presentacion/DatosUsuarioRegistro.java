package com.tallerwebi.presentacion;

public class DatosUsuarioRegistro {

    private String email;
    private String username;
    private String nombre;
    private String apellido;
    private String password;
    private String c_password;

    public DatosUsuarioRegistro(String email,String username, String nombre,String apellido, String password, String c_password){
        this.email=email;
        this.username=username;
        this.nombre=nombre;
        this.apellido=apellido;
        this.password=password;
        this.c_password=c_password;
    }

    public DatosUsuarioRegistro(){}

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getC_password() {
        return c_password;
    }

    public void setC_password(String c_password) {
        this.c_password = c_password;
    }



}
