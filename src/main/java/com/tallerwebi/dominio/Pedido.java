package com.tallerwebi.dominio;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Temporal(TemporalType.DATE)
    private Date fechaCreacion;
    @Enumerated(EnumType.STRING)
    private Estado estado;
    private Boolean conCalandrado = false;
    private Integer tiempoEstimadoEntrega;
    //Relacion N a 1
    @ManyToOne
    private Maquina maquina;
    //Lista de Archivos
    @OneToMany
    private List<Archivo> archivos;

    public Maquina getMaquina() {
        return maquina;
    }

    public void setMaquina(Maquina maquina) {
        this.maquina = maquina;
    }

    public List<Archivo> getArchivos() {
        return archivos;
    }

    public void setArchivos(List<Archivo> archivos) {
        this.archivos = archivos;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public Boolean getConCalandrado() {
        return conCalandrado;
    }

    public void setConCalandrado(Boolean conCalandrado) {
        this.conCalandrado = conCalandrado;
    }

    public Integer getTiempoEstimadoEntrega() {
        return tiempoEstimadoEntrega;
    }

    public void setTiempoEstimadoEntrega(Integer tiempoEstimadoEntrega) {
        this.tiempoEstimadoEntrega = tiempoEstimadoEntrega;
    }
}
