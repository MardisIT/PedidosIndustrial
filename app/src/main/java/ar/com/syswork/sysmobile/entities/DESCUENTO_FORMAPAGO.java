package ar.com.syswork.sysmobile.entities;

public class DESCUENTO_FORMAPAGO {
    int id;
    String CodigoSKU;
    Double Porcentaje;

    public DESCUENTO_FORMAPAGO() {
    }

    public DESCUENTO_FORMAPAGO(int id, String codigoSKU, Double porcentaje) {
        this.id = id;
        CodigoSKU = codigoSKU;
        Porcentaje = porcentaje;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCodigoSKU() {
        return CodigoSKU;
    }

    public void setCodigoSKU(String codigoSKU) {
        CodigoSKU = codigoSKU;
    }

    public Double getPorcentaje() {
        return Porcentaje;
    }

    public void setPorcentaje(Double porcentaje) {
        Porcentaje = porcentaje;
    }
}
