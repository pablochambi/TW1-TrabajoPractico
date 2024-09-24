package com.tallerwebi.dominio;

import javax.persistence.*;

@Entity
public class Maquina {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double capacidadTrabajoPorHora;
    private Boolean activa;
    private Double tiempoReparacionHoras;
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getCapacidadTrabajoPorHora() {
        return capacidadTrabajoPorHora;
    }

    public void setCapacidadTrabajoPorHora(Double capacidadTrabajoPorHora) {
        this.capacidadTrabajoPorHora = capacidadTrabajoPorHora;
    }

    public Boolean getActiva() {
        return activa;
    }

    public void setActiva(Boolean activa) {
        this.activa = activa;
    }

    public Double getTiempoReparacionHoras() {
        return tiempoReparacionHoras;
    }

    public void setTiempoReparacionHoras(Double tiempoReparacionHoras) {
        this.tiempoReparacionHoras = tiempoReparacionHoras;
    }
}
