package ar.com.syswork.sysmobile.industrial;

public class pedidoindustrial {
    private int P_PEDIDO;
    private int P_ORDEN;
    private String P_FECHA;
    private int P_PRODUCTO;
    private double P_PRECIO;
    private double P_CANTIDAD;
    private int P_ESTADO;
    private String P_FILLER;
    private String COD_CLIENTE;

    public pedidoindustrial() {
    }

    public pedidoindustrial(int p_PEDIDO, int p_ORDEN, String p_FECHA, int p_PRODUCTO, double p_PRECIO, double p_CANTIDAD, int p_ESTADO, String p_FILLER, String COD_CLIENTE) {
        P_PEDIDO = p_PEDIDO;
        P_ORDEN = p_ORDEN;
        P_FECHA = p_FECHA;
        P_PRODUCTO = p_PRODUCTO;
        P_PRECIO = p_PRECIO;
        P_CANTIDAD = p_CANTIDAD;
        P_ESTADO = p_ESTADO;
        P_FILLER = p_FILLER;
        this.COD_CLIENTE = COD_CLIENTE;
    }

    public int getP_PEDIDO() {
        return P_PEDIDO;
    }

    public void setP_PEDIDO(int p_PEDIDO) {
        P_PEDIDO = p_PEDIDO;
    }

    public int getP_ORDEN() {
        return P_ORDEN;
    }

    public void setP_ORDEN(int p_ORDEN) {
        P_ORDEN = p_ORDEN;
    }

    public String getP_FECHA() {
        return P_FECHA;
    }

    public void setP_FECHA(String p_FECHA) {
        P_FECHA = p_FECHA;
    }

    public int getP_PRODUCTO() {
        return P_PRODUCTO;
    }

    public void setP_PRODUCTO(int p_PRODUCTO) {
        P_PRODUCTO = p_PRODUCTO;
    }

    public double getP_PRECIO() {
        return P_PRECIO;
    }

    public void setP_PRECIO(double p_PRECIO) {
        P_PRECIO = p_PRECIO;
    }

    public double getP_CANTIDAD() {
        return P_CANTIDAD;
    }

    public void setP_CANTIDAD(double p_CANTIDAD) {
        P_CANTIDAD = p_CANTIDAD;
    }

    public int getP_ESTADO() {
        return P_ESTADO;
    }

    public void setP_ESTADO(int p_ESTADO) {
        P_ESTADO = p_ESTADO;
    }

    public String getP_FILLER() {
        return P_FILLER;
    }

    public void setP_FILLER(String p_FILLER) {
        P_FILLER = p_FILLER;
    }

    public String getCOD_CLIENTE() {
        return COD_CLIENTE;
    }

    public void setCOD_CLIENTE(String COD_CLIENTE) {
        this.COD_CLIENTE = COD_CLIENTE;
    }
}
