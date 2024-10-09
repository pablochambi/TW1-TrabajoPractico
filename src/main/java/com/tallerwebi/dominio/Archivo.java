package com.tallerwebi.dominio;

import javax.persistence.*;

@Entity
public class Archivo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String tipo;
    private Double peso;
    private String direccion;
    private Integer metros;
    private Boolean impreso = false;
    private Integer precio;

    @Lob  // ESTO ES PARA DECIR QUE EL MYSQL PERMITA ARCHIVOS MAS PESADOS
    @Column(name = "datos", columnDefinition = "LONGBLOB")
    private byte[] datos;

    @ManyToOne
    @JoinColumn(name = "pedido_id")
    private Pedido pedido;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Double getPeso() {
        return peso;
    }

    public void setPeso(Double peso) {
        this.peso = peso;
    }

    public byte[] getData() { return datos; }

    public void setData(byte[] data) { this.datos = data; }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Integer getMetros() { return metros; }

    public void setMetros(Integer metros) { this.metros = metros; }

    public Boolean getImpreso() { return impreso; }

    public void setImpreso(Boolean impreso) { this.impreso = impreso; }

    public Integer getPrecio() { return precio; }

    public void setPrecio(Integer precio) { this.precio = precio; }
}
