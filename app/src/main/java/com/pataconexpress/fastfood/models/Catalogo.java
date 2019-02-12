package com.pataconexpress.fastfood.models;

public class Catalogo {
    private String nombre;
    private int imgBackground;

    public Catalogo() {
    }

    public Catalogo(String nombre, int imgBackground) {
        this.nombre = nombre;
        this.imgBackground = imgBackground;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getImgBackground() {
        return imgBackground;
    }

    public void setImgBackground(int imgBackground) {
        this.imgBackground = imgBackground;
    }
}
