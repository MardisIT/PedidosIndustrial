package ar.com.syswork.sysmobile.entities;

public class Inventario {
    private Long id;
    private String codcliente;
    private String codvendedor;
    private String fechainventario;
    private Cliente cliente;

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
