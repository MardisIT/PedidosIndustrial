package ar.com.syswork.sysmobile.entities;

import java.util.List;

public class Pagos {
    private Long idpago;
    public String CodCliente;
    public double ValorTotalPago;
    public String FormaPago;
    public String fecha;
    public String idvendedor;

    public String getIdvendedor() {
        return idvendedor;
    }

    public void setIdvendedor(String idvendedor) {
        this.idvendedor = idvendedor;
    }

    public String getFotos() {
        return fotos;
    }

    public void setFotos(String fotos) {
        this.fotos = fotos;
    }

    public  String fotos;
    private Cliente cliente;
    private List<ChequePagos> chequePagos;
private  List<PagosDetalles> pagosDetalles;

    public List<PagosDetalles> getPagosDetalles() {
        return pagosDetalles;
    }

    public void setPagosDetalles(List<PagosDetalles> pagosDetalles) {
        this.pagosDetalles = pagosDetalles;
    }

    public List<ChequePagos> getChequePagos() {
        return chequePagos;
    }

    public void setChequePagos(List<ChequePagos> chequePagos) {
        this.chequePagos = chequePagos;
    }

    public Long getIdpago() {
        return idpago;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public void setIdpago(Long idpago) {
        this.idpago = idpago;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }



    public Pagos() {
    }

    public String getCodCliente() {
        return CodCliente;
    }

    public void setCodCliente(String codCliente) {
        CodCliente = codCliente;
    }

    public double getValorTotalPago() {
        return ValorTotalPago;
    }

    public void setValorTotalPago(double valorTotalPago) {
        ValorTotalPago = valorTotalPago;
    }

    public String getFormaPago() {
        return FormaPago;
    }

    public void setFormaPago(String formaPago) {
        FormaPago = formaPago;
    }
}
