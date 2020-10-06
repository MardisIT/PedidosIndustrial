package ar.com.syswork.sysmobile.entities;

public class reporteitem {
    private String codproducto;
private double  cantidad;
    private double  precio;
    private double  total;
    private String unidad;
private int codcabecera;
private int _id;

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getUnidad() {
        return unidad;
    }

    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public reporteitem(String codproducto, Integer cantidad, int codcabecera, int _id) {
        this.codproducto = codproducto;
        this.cantidad = cantidad;
        this.codcabecera = codcabecera;
        this._id = _id;
    }

    public reporteitem() {
    }

    public int getCodcabecera() {
        return codcabecera;
    }

    public void setCodcabecera(int codcabecera) {
        this.codcabecera = codcabecera;
    }

    public String getCodproducto() {
        return codproducto;
    }

    public void setCodproducto(String codproducto) {
        this.codproducto = codproducto;
    }


    public double getCantidad() {
        return cantidad;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }
}
