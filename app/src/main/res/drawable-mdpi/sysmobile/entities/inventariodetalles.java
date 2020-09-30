package ar.com.syswork.sysmobile.entities;

public class inventariodetalles {
    private long id;
    private long  idinventario;
    private String codproducto;
    private  String unidad;
    private long valor;
    private  Articulo articulo;

    public Articulo getArticulo() {
        return articulo;
    }

    public void setArticulo(Articulo articulo) {
        this.articulo = articulo;
    }

    public inventariodetalles() {
    }

    public String getUnidad() {
        return unidad;
    }

    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }

    public inventariodetalles(long id, long idinventario, String codproducto, long valor) {
        this.id = id;
        this.idinventario = idinventario;
        this.codproducto = codproducto;
        this.valor = valor;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getIdinventario() {
        return idinventario;
    }

    public void setIdinventario(long idinventario) {
        this.idinventario = idinventario;
    }

    public String getCodproducto() {
        return codproducto;
    }

    public void setCodproducto(String codproducto) {
        this.codproducto = codproducto;
    }

    public long getValor() {
        return valor;
    }

    public void setValor(long valor) {
        this.valor = valor;
    }
}
