package ar.com.syswork.sysmobile.entities;

public class DESCUENTO_VOLUMEN {
   int ID;
    String CodigosSKU;
    String SKU_NOMBRE;
    int UNIDADES_DESCUENTO;
    String UNIDADES_HASTA;
    double DESCUENTO;

    public DESCUENTO_VOLUMEN() {
    }

    public DESCUENTO_VOLUMEN(int ID, String codigosSKU, String SKU_NOMBRE, int UNIDADES_DESCUENTO, String UNIDADES_HASTA, double DESCUENTO) {
        this.ID = ID;
        CodigosSKU = codigosSKU;
        this.SKU_NOMBRE = SKU_NOMBRE;
        this.UNIDADES_DESCUENTO = UNIDADES_DESCUENTO;
        this.UNIDADES_HASTA = UNIDADES_HASTA;
        this.DESCUENTO = DESCUENTO;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getCodigosSKU() {
        return CodigosSKU;
    }

    public void setCodigosSKU(String codigosSKU) {
        CodigosSKU = codigosSKU;
    }

    public String getSKU_NOMBRE() {
        return SKU_NOMBRE;
    }

    public void setSKU_NOMBRE(String SKU_NOMBRE) {
        this.SKU_NOMBRE = SKU_NOMBRE;
    }

    public int getUNIDADES_DESCUENTO() {
        return UNIDADES_DESCUENTO;
    }

    public void setUNIDADES_DESCUENTO(int UNIDADES_DESCUENTO) {
        this.UNIDADES_DESCUENTO = UNIDADES_DESCUENTO;
    }

    public String getUNIDADES_HASTA() {
        return UNIDADES_HASTA;
    }

    public void setUNIDADES_HASTA(String UNIDADES_HASTA) {
        this.UNIDADES_HASTA = UNIDADES_HASTA;
    }

    public double getDESCUENTO() {
        return DESCUENTO;
    }

    public void setDESCUENTO(double DESCUENTO) {
        this.DESCUENTO = DESCUENTO;
    }
}
