package com.tallerwebi.dominio;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Notificacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String tituloNotificacion;
    private String descripcionNotificacion;
    private Date fechaNotificacion;
    @Enumerated(EnumType.STRING)
    private EstadoNotificacion estado = EstadoNotificacion.NUEVA;

    @ManyToOne
    @JoinColumn(name = "pedido_id")
    private Pedido pedido;

    public String getTituloNotificacion() {
        return tituloNotificacion;
    }

    public void setTituloNotificacion(String tituloNotificacion) {
        this.tituloNotificacion = tituloNotificacion;
    }

    public String getDescripcionNotificacion() {
        return descripcionNotificacion;
    }

    public void setDescripcionNotificacion(String descripcionNotificacion) {
        this.descripcionNotificacion = descripcionNotificacion;
    }

    public Date getFechaNotificacion() {
        return fechaNotificacion;
    }

    public void setFechaNotificacion(Date fechaNotificacion) {
        this.fechaNotificacion = fechaNotificacion;
    }

    public EstadoNotificacion getEstado() {
        return estado;
    }

    public void setEstado(EstadoNotificacion estado) {
        this.estado = estado;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
