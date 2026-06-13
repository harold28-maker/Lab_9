package org.example.lab_9.beans;

import java.util.Date;

public class Transaccion {
    private int idtransacciones;
    private double monto;
    private String descripcion;
    private String titulo;
    private Date fecha;
    private String tipo;
    private Usuario usuario;

    public Transaccion() {
    }

    public Transaccion(int idtransacciones) {
        this.idtransacciones = idtransacciones;
    }

    public int getIdtransacciones() {
        return idtransacciones;
    }

    public void setIdtransacciones(int idtransacciones) {
        this.idtransacciones = idtransacciones;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
