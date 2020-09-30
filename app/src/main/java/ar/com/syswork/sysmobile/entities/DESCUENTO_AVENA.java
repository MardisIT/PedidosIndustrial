package ar.com.syswork.sysmobile.entities;

public class DESCUENTO_AVENA {
     int ID		;
    String CodigoSKU;
    String SKU_NOMBRE;
    int CANTIDAD	;
    double	DESCUENTO;
    String  CONDICION;

    public DESCUENTO_AVENA() {
    }

    public DESCUENTO_AVENA(int ID, String codigoSKU, String SKU_NOMBRE, int CANTIDAD, double DESCUENTO, String CONDICION) {
        this.ID = ID;
        CodigoSKU = codigoSKU;
        this.SKU_NOMBRE = SKU_NOMBRE;
        this.CANTIDAD = CANTIDAD;
        this.DESCUENTO = DESCUENTO;
        this.CONDICION = CONDICION;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getCodigoSKU() {
        return CodigoSKU;
    }

    public void setCodigoSKU(String codigoSKU) {
        CodigoSKU = codigoSKU;
    }

    public String getSKU_NOMBRE() {
        return SKU_NOMBRE;
    }

    public void setSKU_NOMBRE(String SKU_NOMBRE) {
        this.SKU_NOMBRE = SKU_NOMBRE;
    }

    public int getCANTIDAD() {
        return CANTIDAD;
    }

    public void setCANTIDAD(int CANTIDAD) {
        this.CANTIDAD = CANTIDAD;
    }

    public double getDESCUENTO() {
        return DESCUENTO;
    }

    public void setDESCUENTO(double DESCUENTO) {
        this.DESCUENTO = DESCUENTO;
    }

    public String getCONDICION() {
        return CONDICION;
    }

    public void setCONDICION(String CONDICION) {
        this.CONDICION = CONDICION;
    }
}


