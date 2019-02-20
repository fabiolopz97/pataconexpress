package com.pataconexpress.fastfood.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Pedido implements Serializable {
    @Expose
    @SerializedName("idpedido")
    private Integer idpedido;
    private Date fechaPedido;
    @Expose
    @SerializedName("nombreCliente")
    private String nombreCliente;
    @Expose
    @SerializedName("idCliente")
    private long idCliente;
    @Expose
    @SerializedName("direccionCliente")
    private String direccionCliente;
    @Expose
    @SerializedName("contacto")
    private String contacto;
    @Expose
    @SerializedName("precioTotal")
    private double precioTotal;
    @Expose
    @SerializedName("detallePedidoList")
    private List<DetallePedido> detallePedidoList;

    public Pedido() {
    }

    public Pedido(Integer idpedido) {
        this.idpedido = idpedido;
    }

    public Pedido(Integer idpedido, Date fechaPedido, String nombreCliente, long idCliente, String direccionCliente, String contacto, double precioTotal) {
        this.idpedido = idpedido;
        this.fechaPedido = fechaPedido;
        this.nombreCliente = nombreCliente;
        this.idCliente = idCliente;
        this.direccionCliente = direccionCliente;
        this.contacto = contacto;
        this.precioTotal = precioTotal;
    }
    public Pedido( Date fechaPedido, String nombreCliente, long idCliente, String direccionCliente, String contacto, double precioTotal) {
        this.fechaPedido = fechaPedido;
        this.nombreCliente = nombreCliente;
        this.idCliente = idCliente;
        this.direccionCliente = direccionCliente;
        this.contacto = contacto;
        this.precioTotal = precioTotal;
    }
    public Integer getIdpedido() {
        return idpedido;
    }

    public void setIdpedido(Integer idpedido) {
        this.idpedido = idpedido;
    }

    public Date getFechaPedido() {
        return fechaPedido;
    }

    public void setFechaPedido(Date fechaPedido) {
        this.fechaPedido = fechaPedido;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public long getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(long idCliente) {
        this.idCliente = idCliente;
    }

    public String getDireccionCliente() {
        return direccionCliente;
    }

    public void setDireccionCliente(String direccionCliente) {
        this.direccionCliente = direccionCliente;
    }

    public String getContacto() {
        return contacto;
    }

    public void setContacto(String contacto) {
        this.contacto = contacto;
    }

    public double getPrecioTotal() {
        return precioTotal;
    }

    public void setPrecioTotal(double precioTotal) {
        this.precioTotal = precioTotal;
    }

    public List<DetallePedido> getDetallePedidoList() {
        return detallePedidoList;
    }

    public void setDetallePedidoList(List<DetallePedido> detallePedidoList) {
        this.detallePedidoList = detallePedidoList;
    }

}
