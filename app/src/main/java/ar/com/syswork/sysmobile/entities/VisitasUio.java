package ar.com.syswork.sysmobile.entities;

public class VisitasUio {
    private Long id;
    private String codcliente;
    private String codvendedor;
    private String fechavisita;
    private double Latitud;
    private double Longitud;
    private String Linkfotoexterior;
    private Cliente cliente;

    private String observaciones;
    private String realizapedido;
    private String estado;

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public VisitasUio() {
    }

    public VisitasUio(Long id, String codcliente, String codvendedor, String fechavisita, double latitud, double longitud, String linkfotoexterior, Cliente cliente, String observaciones, String realizapedido, String estado) {
        this.id = id;
        this.codcliente = codcliente;
        this.codvendedor = codvendedor;
        this.fechavisita = fechavisita;
        Latitud = latitud;
        Longitud = longitud;
        Linkfotoexterior = linkfotoexterior;
        this.cliente = cliente;
        this.observaciones = observaciones;
        this.realizapedido = realizapedido;
        this.estado = estado;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getRealizapedido() {
        return realizapedido;
    }

    public void setRealizapedido(String realizapedido) {
        this.realizapedido = realizapedido;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodcliente() {
        return codcliente;
    }

    public void setCodcliente(String codcliente) {
        this.codcliente = codcliente;
    }

    public String getCodvendedor() {
        return codvendedor;
    }

    public void setCodvendedor(String codvendedor) {
        this.codvendedor = codvendedor;
    }

    public String getFechavisita() {
        return fechavisita;
    }

    public void setFechavisita(String fechavisita) {
        this.fechavisita = fechavisita;
    }

    public double getLatitud() {
        return Latitud;
    }

    public void setLatitud(double latitud) {
        Latitud = latitud;
    }

    public double getLongitud() {
        return Longitud;
    }

    public void setLongitud(double longitud) {
        Longitud = longitud;
    }

    public String getLinkfotoexterior() {
        return Linkfotoexterior;
    }

    public void setLinkfotoexterior(String linkfotoexterior) {
        Linkfotoexterior = linkfotoexterior;
    }
}
