package ar.com.syswork.sysmobile.entities;

public class reportecabecera {
    private String fecha;
    private String codcliente;
    private  int _id;
    private int idpedido;
   private  String idpedidouri;

    public String getIdpedidouri() {
        return idpedidouri;
    }

    public void setIdpedidouri(String idpedidouri) {
        this.idpedidouri = idpedidouri;
    }

    public int getIdpedido() {
        return idpedido;
    }

    public void setIdpedido(int idpedido) {
        this.idpedido = idpedido;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public reportecabecera() {
    }

    public reportecabecera(String fecha, String codcliente, int _id, int idpedido) {
        this.fecha = fecha;
        this.codcliente = codcliente;
        this._id = _id;
        this.idpedido = idpedido;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getCodcliente() {
        return codcliente;
    }

    public void setCodcliente(String codcliente) {
        this.codcliente = codcliente;
    }
}
