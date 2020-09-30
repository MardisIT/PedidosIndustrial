package ar.com.syswork.sysmobile.entities;

public class Reporte {
    private String nombrecliente;
    private String numerofactura;
    private String formapago;
    private double valorpago;

    public Reporte(String nombrecliente, String numerofactura, String formapago, double valorpago) {
        this.nombrecliente = nombrecliente;
        this.numerofactura = numerofactura;
        this.formapago = formapago;
        this.valorpago = valorpago;
    }

    public Reporte() {
    }

    public String getNombrecliente() {
        return nombrecliente;
    }

    public void setNombrecliente(String nombrecliente) {
        this.nombrecliente = nombrecliente;
    }

    public String getNumerofactura() {
        return numerofactura;
    }

    public void setNumerofactura(String numerofactura) {
        this.numerofactura = numerofactura;
    }

    public String getFormapago() {
        return formapago;
    }

    public void setFormapago(String formapago) {
        this.formapago = formapago;
    }

    public double getValorpago() {
        return valorpago;
    }

    public void setValorpago(double valorpago) {
        this.valorpago = valorpago;
    }
}
