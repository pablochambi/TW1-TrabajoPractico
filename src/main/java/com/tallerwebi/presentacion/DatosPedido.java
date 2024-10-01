package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Archivo;

public class DatosPedido {
    private String nombreCliente;
    private String tipoServicio;
    private Archivo archivo1;
    private Archivo archivo2;
    private Archivo archivo3;
    private Double metros;

    public DatosPedido() {
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public String getTipoServicio() {
        return tipoServicio;
    }

    public void setTipoServicio(String tipoServicio) {
        this.tipoServicio = tipoServicio;
    }

    public Archivo getArchivo1() {
        return archivo1;
    }

    public void setArchivo1(Archivo archivo1) {
        this.archivo1 = archivo1;
    }

    public Archivo getArchivo2() {
        return archivo2;
    }

    public void setArchivo2(Archivo archivo2) {
        this.archivo2 = archivo2;
    }

    public Archivo getArchivo3() {
        return archivo3;
    }

    public void setArchivo3(Archivo archivo3) {
        this.archivo3 = archivo3;
    }

    public Double getMetros() {
        return metros;
    }

    public void setMetros(Double metros) {
        this.metros = metros;
    }
}
