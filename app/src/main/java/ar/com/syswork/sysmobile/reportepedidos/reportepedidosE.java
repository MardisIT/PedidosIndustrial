package ar.com.syswork.sysmobile.reportepedidos;

import android.widget.TextView;

public class reportepedidosE {
    public String codProductoD;
    public String  DescripcionProd;
    public double  cantidadpro;
    public double  preciop;
    public double  totalp;
    public String unidades;

    public String getUnidades() {
        return unidades;
    }

    public void setUnidades(String unidades) {
        this.unidades = unidades;
    }

    public String getCodProductoD() {
        return codProductoD;
    }

    public void setCodProductoD(String codProductoD) {
        this.codProductoD = codProductoD;
    }

    public String getDescripcionProd() {
        return DescripcionProd;
    }

    public void setDescripcionProd(String descripcionProd) {
        DescripcionProd = descripcionProd;
    }


    public double getCantidadpro() {
        return cantidadpro;
    }

    public void setCantidadpro(double cantidadpro) {
        this.cantidadpro = cantidadpro;
    }

    public double getPreciop() {
        return preciop;
    }

    public void setPreciop(double preciop) {
        this.preciop = preciop;
    }

    public double getTotalp() {
        return totalp;
    }

    public void setTotalp(double totalp) {
        this.totalp = totalp;
    }
}
