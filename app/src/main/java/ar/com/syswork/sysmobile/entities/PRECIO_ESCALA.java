package ar.com.syswork.sysmobile.entities;

public class PRECIO_ESCALA {
    int id	;
    String CodigosSKU;
    String SKU;
    int UNIDADESXCAJA;
    String UNIDADES;
    double PRECIO_UNITARIO;
    int UNIDADES_VALIDAR;
    double PRECIO2;

    public PRECIO_ESCALA() {
    }

    public PRECIO_ESCALA(int id, String codigosSKU, String SKU, int UNIDADESXCAJA, String UNIDADES, double PRECIO_UNITARIO, int UNIDADES_VALIDAR, double PRECIO2) {
        this.id = id;
        CodigosSKU = codigosSKU;
        this.SKU = SKU;
        this.UNIDADESXCAJA = UNIDADESXCAJA;
        this.UNIDADES = UNIDADES;
        this.PRECIO_UNITARIO = PRECIO_UNITARIO;
        this.UNIDADES_VALIDAR = UNIDADES_VALIDAR;
        this.PRECIO2 = PRECIO2;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCodigosSKU() {
        return CodigosSKU;
    }

    public void setCodigosSKU(String codigosSKU) {
        CodigosSKU = codigosSKU;
    }

    public String getSKU() {
        return SKU;
    }

    public void setSKU(String SKU) {
        this.SKU = SKU;
    }

    public int getUNIDADESXCAJA() {
        return UNIDADESXCAJA;
    }

    public void setUNIDADESXCAJA(int UNIDADESXCAJA) {
        this.UNIDADESXCAJA = UNIDADESXCAJA;
    }

    public String getUNIDADES() {
        return UNIDADES;
    }

    public void setUNIDADES(String UNIDADES) {
        this.UNIDADES = UNIDADES;
    }

    public double getPRECIO_UNITARIO() {
        return PRECIO_UNITARIO;
    }

    public void setPRECIO_UNITARIO(double PRECIO_UNITARIO) {
        this.PRECIO_UNITARIO = PRECIO_UNITARIO;
    }

    public int getUNIDADES_VALIDAR() {
        return UNIDADES_VALIDAR;
    }

    public void setUNIDADES_VALIDAR(int UNIDADES_VALIDAR) {
        this.UNIDADES_VALIDAR = UNIDADES_VALIDAR;
    }

    public double getPRECIO2() {
        return PRECIO2;
    }

    public void setPRECIO2(double PRECIO2) {
        this.PRECIO2 = PRECIO2;
    }
}
