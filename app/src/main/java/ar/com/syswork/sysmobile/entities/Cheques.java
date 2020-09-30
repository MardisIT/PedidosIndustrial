package ar.com.syswork.sysmobile.entities;

import java.util.Date;

public class Cheques {
    public String numcheque;
    public String banco;
    public double valor;
    public String fecha;

    public Cheques() {
    }

    public Cheques(String numcheque, String banco, double valor, String fecha) {
        this.numcheque = numcheque;
        this.banco = banco;
        this.valor = valor;
        this.fecha = fecha;
    }

    public String getNumcheque() {
        return numcheque;
    }

    public void setNumcheque(String numcheque) {
        this.numcheque = numcheque;
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
