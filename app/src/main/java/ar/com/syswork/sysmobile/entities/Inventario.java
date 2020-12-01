package ar.com.syswork.sysmobile.entities;

public class Inventario {
    private Long id;
    private String codcliente;
    private String codvendedor;
    private String fechainventario;
    private Cliente cliente;
    private  String codigomardis;
    public String enviomardis;
    public String envioindustrial;
    public String codigounico;

    public String getEnviomardis() {
        return enviomardis;
    }

    public void setEnviomardis(String enviomardis) {
        this.enviomardis = enviomardis;
    }

    public String getEnvioindustrial() {
        return envioindustrial;
    }

    public void setEnvioindustrial(String envioindustrial) {
        this.envioindustrial = envioindustrial;
    }

    public String getCodigounico() {
        return codigounico;
    }

    public void setCodigounico(String codigounico) {
        this.codigounico = codigounico;
    }

    public String getCodigomardis() {
        return codigomardis;
    }

    public void setCodigomardis(String codigomardis) {
        this.codigomardis = codigomardis;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Inventario() {
    }

    public Inventario(Long id, String codcliente, String codvendedor, String fechainventario) {
        this.id = id;
        this.codcliente = codcliente;
        this.codvendedor = codvendedor;
        this.fechainventario = fechainventario;
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

    public String getFechainventario() {
        return fechainventario;
    }

    public void setFechainventario(String fechainventario) {
        this.fechainventario = fechainventario;
    }
}
