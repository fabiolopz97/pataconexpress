package com.pataconexpress.fastfood.models;

public class ProductoPedido extends Producto{
    private int cantidad;
    private int totalProducto;
    private int valorTotal;

    public ProductoPedido() {
    }

    public ProductoPedido(String nombre, String descripcion, int valor, int imgBackground, int cantidad, int totalProducto, int valorTotal) {
        super(nombre, descripcion, valor, imgBackground);
        this.cantidad = cantidad;
        this.totalProducto = totalProducto;
        this.valorTotal = valorTotal;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getTotalProducto() {
        return totalProducto;
    }

    public void setTotalProducto(int totalProducto) {
        this.totalProducto = totalProducto;
    }

    public int getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(int valorTotal) {
        this.valorTotal = valorTotal;
    }

    public int totalProducto(int cantidad, int totalProducto){
        int resultado;
        resultado = cantidad * totalProducto;
        return resultado;
    }

    public void cantidadPagar(){

    }
}
