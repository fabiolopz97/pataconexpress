package com.pataconexpress.fastfood.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public final class CategoriaDTO {


    @SerializedName("idcategoria")
    @Expose
    private Integer idcategoria;
    @SerializedName("nombreCat")
    @Expose
    private String nombreCat;

    public CategoriaDTO() {
    }

    public CategoriaDTO(Integer idcategoria) {
        this.idcategoria = idcategoria;
    }

    public CategoriaDTO(Integer idcategoria, String nombreCat) {
        this.idcategoria = idcategoria;
        this.nombreCat = nombreCat;
    }

    public Integer getIdcategoria() {
        return idcategoria;
    }

    public void setIdcategoria(Integer idcategoria) {
        this.idcategoria = idcategoria;
    }

    public String getNombreCat() {
        return nombreCat;
    }

    public void setNombreCat(String nombreCat) {
        this.nombreCat = nombreCat;
    }
    public String toString(){
        return nombreCat;
    }
}
