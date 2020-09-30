package ar.com.syswork.sysmobile.entities;

/**
 * Created by Administrador1 on 1/2/2018.
 */

public class ConfiguracionDB {

    public int Id_configuracion;
    public String Id_cuenta;
    public String Id_campania;
    public String FormaBusqueda;
    public String Estado;
    public String FechaCarga;
    public String Formularios;
public String AccountNombre;
public String CampaniaNombre;

    public ConfiguracionDB(int id_configuracion, String id_cuenta, String id_campania, String formaBusqueda, String estado, String fechaCarga, String formularios, String accountNombre, String campaniaNombre) {
        Id_configuracion = id_configuracion;
        Id_cuenta = id_cuenta;
        Id_campania = id_campania;
        FormaBusqueda = formaBusqueda;
        Estado = estado;
        FechaCarga = fechaCarga;
        Formularios = formularios;
        AccountNombre = accountNombre;
        CampaniaNombre = campaniaNombre;
    }

    public ConfiguracionDB() {
    }

    public String getAccountNombre() {
        return AccountNombre;
    }

    public void setAccountNombre(String accountNombre) {
        AccountNombre = accountNombre;
    }

    public String getCampaniaNombre() {
        return CampaniaNombre;
    }

    public void setCampaniaNombre(String campaniaNombre) {
        CampaniaNombre = campaniaNombre;
    }

    public String getFechaCarga() {
        return FechaCarga;
    }

    public void setFechaCarga(String fechaCarga) {
        FechaCarga = fechaCarga;
    }

    public String getFormularios() {
        return Formularios;
    }

    public void setFormularios(String formularios) {
        Formularios = formularios;
    }

    public int getId_configuracion() {
        return Id_configuracion;
    }

    public void setId_configuracion(int id_configuracion) {
        Id_configuracion = id_configuracion;
    }

    public String getId_cuenta() {
        return Id_cuenta;
    }

    public void setId_cuenta(String id_cuenta) {
        Id_cuenta = id_cuenta;
    }

    public String getId_campania() {
        return Id_campania;
    }

    public void setId_campania(String id_campania) {
        Id_campania = id_campania;
    }

    public String getFormaBusqueda() {
        return FormaBusqueda;
    }

    public void setFormaBusqueda(String formaBusqueda) {
        FormaBusqueda = formaBusqueda;
    }

    public String getEstado() {
        return Estado;
    }

    public void setEstado(String estado) {
        Estado = estado;
    }
}
