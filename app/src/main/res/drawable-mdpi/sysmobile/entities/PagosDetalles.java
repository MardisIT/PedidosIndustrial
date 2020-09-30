package ar.com.syswork.sysmobile.entities;

public class PagosDetalles {
    private long idpagodetalle;
    private long idpago;
    public String Numfactura;
    public double TotalFactura;
    public  double PagoRegistrado;

    public PagosDetalles() {
    }

    public long getIdpagodetalle() {
        return idpagodetalle;
    }

    public void setIdpagodetalle(long idpagodetalle) {
        this.idpagodetalle = idpagodetalle;
    }

    public long getIdpago() {
        return idpago;
    }

    public void setIdpago(long idpago) {
        this.idpago = idpago;
    }

    public String getNumfactura() {
        return Numfactura;
    }

    public void setNumfactura(String numfactura) {
        Numfactura = numfactura;
    }

    public double getTotalFactura() {
        return TotalFactura;
    }

    public void setTotalFactura(double totalFactura) {
        TotalFactura = totalFactura;
    }

    public double getPagoRegistrado() {
        return PagoRegistrado;
    }

    public void setPagoRegistrado(double pagoRegistrado) {
        PagoRegistrado = pagoRegistrado;
    }
}
