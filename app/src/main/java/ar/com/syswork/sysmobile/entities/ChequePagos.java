package ar.com.syswork.sysmobile.entities;

public class ChequePagos {
    Long idcheque;
    Long idpago;
    String numerocheque;
    String banco;
    double valor;
    String fecha;

    public Long getIdcheque() {
        return idcheque;
    }

    public void setIdcheque(Long idcheque) {
        this.idcheque = idcheque;
    }

    public Long getIdpago() {
        return idpago;
    }

    public void setIdpago(Long idpago) {
        this.idpago = idpago;
    }

    public String getNumerocheque() {
        return numerocheque;
    }

    public void setNumerocheque(String numerocheque) {
        this.numerocheque = numerocheque;
    }

    public String getBanco() {
        return banco;
    }

    public void setBanco(String banco) {
        this.banco = banco;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}
