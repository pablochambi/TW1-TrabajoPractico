package com.tallerwebi.dominio;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    @Temporal(TemporalType.DATE)
    private Date fechaCreacion;
    @Enumerated(EnumType.STRING)
    private Estado estado = Estado.EN_ESPERA;
    private Boolean conCalandrado = false;
    private Integer tiempoEstimadoEntrega;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "maquina_id")
    private Maquina maquina;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Archivo> archivos = new ArrayList<>();


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

    public String getNombre() { return nombre; }

    public void setNombre(String nombre) { this.nombre = nombre; }

    public Usuario getUsuario() { return usuario; }

    public void setUsuario(Usuario usuario) { this.usuario = usuario; }

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

    public String obtenerFechaHoy(){
        LocalDate date = LocalDate.now();
        return date.toString();
    }
}
