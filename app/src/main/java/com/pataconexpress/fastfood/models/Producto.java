package com.pataconexpress.fastfood.models;

public class Producto {
    private String nombre;
    private String descripcion;
    private int valor;
    private int imgBackground;

    public Producto() {
    }

    public Producto(String nombre, String descripcion, int valor, int imgBackground) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.valor = valor;
        this.imgBackground = imgBackground;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }

    public int getImgBackground() {
        return imgBackground;
    }

    public void setImgBackground(int imgBackground) {
        this.imgBackground = imgBackground;
    }
}
