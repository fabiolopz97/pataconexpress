package com.pataconexpress.fastfood.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public final class DetallePedido implements Serializable {

    @Expose
    @SerializedName("iddetallePedido")
    private int iddetallePedido;
    @Expose
    @SerializedName("cantidad")
    private int cantidad;
    @Expose
    @SerializedName("monto")
    private double monto;
    private Pedido pedidosIdpedido;
    @Expose
    @SerializedName("produtosIdprodutos")
    private Producto produtosIdprodutos;

    public DetallePedido() {
    }

    public DetallePedido(Integer iddetallePedido) {
        this.iddetallePedido = iddetallePedido;
    }
    public DetallePedido(Producto produtosIdprodutos) {
        this.produtosIdprodutos = produtosIdprodutos;
    }

    public DetallePedido(Integer iddetallePedido, int cantidad, double monto) {
        this.iddetallePedido = iddetallePedido;
        this.cantidad = cantidad;
        this.monto = monto;
    }

    public Integer getIddetallePedido() {
        return iddetallePedido;
    }

    public void setIddetallePedido(Integer iddetallePedido) {
        this.iddetallePedido = iddetallePedido;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public Pedido getPedidosIdpedido() {
        return pedidosIdpedido;
    }

    public void setPedidosIdpedido(Pedido pedidosIdpedido) {
        this.pedidosIdpedido = pedidosIdpedido;
    }

    public Producto getProdutosIdprodutos() {
        return produtosIdprodutos;
    }

    public void setProdutosIdprodutos(Producto produtosIdprodutos) {
        this.produtosIdprodutos = produtosIdprodutos;
    }
}
