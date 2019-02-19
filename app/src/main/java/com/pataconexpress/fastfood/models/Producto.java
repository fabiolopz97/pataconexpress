package com.pataconexpress.fastfood.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Producto implements Serializable {
    @SerializedName("idprodutos")
    @Expose
    private int idproducto;
    @SerializedName("nombreProducto")
    @Expose
    private String nombre;
    @SerializedName("descripcionProducto")
    @Expose
    private String descripcion;
    @SerializedName("precioProducto")
    @Expose
    private double valor;
    @SerializedName("categoriasIdcategoria")
    @Expose
    private CategoriaDTO categoriasIdcategoria;
    //ignorada
    private int imgBackground;
    private boolean isSelected;

    public Producto() {
    }
    public Producto(int idproducto, String nombre, String descripcion, double valor) {
        this.idproducto = idproducto;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.valor = valor;
        this.isSelected = false;
    }
    public Producto(int idproducto, String nombre, String descripcion, double valor, int imgBackground) {
        this.idproducto = idproducto;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.valor = valor;
        this.imgBackground = imgBackground;
        this.isSelected = false;
    }
    public Producto(String nombre, String descripcion, double valor, int imgBackground) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.valor = valor;
        this.imgBackground = imgBackground;
        this.isSelected = false;
    }

    public int getIdProducto() {
        return idproducto;
    }

    public void setIdfProducto(int id) {
        this.idproducto = id;
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

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public int getImgBackground() {
        return imgBackground;
    }

    public void setImgBackground(int imgBackground) {
        this.imgBackground = imgBackground;
    }

    public CategoriaDTO getCategoriasIdcategoria() {
        return categoriasIdcategoria;
    }

    public void setCategoriasIdcategoria(CategoriaDTO categoriasIdcategoria) {
        this.categoriasIdcategoria = categoriasIdcategoria;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
