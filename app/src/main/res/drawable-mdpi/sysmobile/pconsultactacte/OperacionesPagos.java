package ar.com.syswork.sysmobile.pconsultactacte;

public class OperacionesPagos {
    public static Double valortotal;
    public static  Double valoringresado;
    public static String fotostomadas;

    public static String getFotostomadas() {
        return fotostomadas;
    }

    public static void setFotostomadas(String fotostomadas) {
        OperacionesPagos.fotostomadas = fotostomadas;
    }

    public static Double getValortotal() {
        return valortotal;
    }

    public static void setValortotal(Double valortotal) {
        OperacionesPagos.valortotal = valortotal;
    }

    public static Double getValoringresado() {
        return valoringresado;
    }

    public static void setValoringresado(Double valoringresado) {
        OperacionesPagos.valoringresado = valoringresado;
    }

}
