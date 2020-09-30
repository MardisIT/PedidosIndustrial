package ar.com.syswork.sysmobile.entities;

public class Cartera {

public  int ID;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String Codcli;
    public String nombre_cliente;
    public String fe_fac;
    public String fe_des;
    public String tp_d;
    public String line;
    public String nro_docm;
    public String ti_desp;
    public String fe_vecto;
    private double corriente;
    private double vcdo1_15;
    private double vcdo16_30;
    private double vcdo31_60;
    private double vcdo61mayor;
    private String vendedor;
    private String codvendedor;

    public Cartera() {
    }

    public Cartera(String codcli, String nombre_cliente, String fe_fac, String fe_des, String tp_d, String line, String nro_docm, String ti_desp, String fe_vecto, double corriente, double vcdo1_15, double vcdo16_30, double vcdo31_60, double vcdo61mayor, String vendedor, String codvendedor) {
        Codcli = codcli;
        this.nombre_cliente = nombre_cliente;
        this.fe_fac = fe_fac;
        this.fe_des = fe_des;
        this.tp_d = tp_d;
        this.line = line;
        this.nro_docm = nro_docm;
        this.ti_desp = ti_desp;
        this.fe_vecto = fe_vecto;
        this.corriente = corriente;
        this.vcdo1_15 = vcdo1_15;
        this.vcdo16_30 = vcdo16_30;
        this.vcdo31_60 = vcdo31_60;
        this.vcdo61mayor = vcdo61mayor;
        this.vendedor = vendedor;
        this.codvendedor = codvendedor;
    }

    public String getCodcli() {
        return Codcli;
    }

    public void setCodcli(String codcli) {
        Codcli = codcli;
    }

    public String getNombre_cliente() {
        return nombre_cliente;
    }

    public void setNombre_cliente(String nombre_cliente) {
        this.nombre_cliente = nombre_cliente;
    }

    public String getFe_fac() {
        return fe_fac;
    }

    public void setFe_fac(String fe_fac) {
        this.fe_fac = fe_fac;
    }

    public String getFe_des() {
        return fe_des;
    }

    public void setFe_des(String fe_des) {
        this.fe_des = fe_des;
    }

    public String getTp_d() {
        return tp_d;
    }

    public void setTp_d(String tp_d) {
        this.tp_d = tp_d;
    }

    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }

    public String getNro_docm() {
        return nro_docm;
    }

    public void setNro_docm(String nro_docm) {
        this.nro_docm = nro_docm;
    }

    public String getTi_desp() {
        return ti_desp;
    }

    public void setTi_desp(String ti_desp) {
        this.ti_desp = ti_desp;
    }

    public String getFe_vecto() {
        return fe_vecto;
    }

    public void setFe_vecto(String fe_vecto) {
        this.fe_vecto = fe_vecto;
    }

    public double getCorriente() {
        return corriente;
    }

    public void setCorriente(double corriente) {
        this.corriente = corriente;
    }

    public double getVcdo1_15() {
        return vcdo1_15;
    }

    public void setVcdo1_15(double vcdo1_15) {
        this.vcdo1_15 = vcdo1_15;
    }

    public double getVcdo16_30() {
        return vcdo16_30;
    }

    public void setVcdo16_30(double vcdo16_30) {
        this.vcdo16_30 = vcdo16_30;
    }

    public double getVcdo31_60() {
        return vcdo31_60;
    }

    public void setVcdo31_60(double vcdo31_60) {
        this.vcdo31_60 = vcdo31_60;
    }

    public double getVcdo61mayor() {
        return vcdo61mayor;
    }

    public void setVcdo61mayor(double vcdo61mayor) {
        this.vcdo61mayor = vcdo61mayor;
    }

    public String getVendedor() {
        return vendedor;
    }

    public void setVendedor(String vendedor) {
        this.vendedor = vendedor;
    }

    public String getCodvendedor() {
        return codvendedor;
    }

    public void setCodvendedor(String codvendedor) {
        this.codvendedor = codvendedor;
    }
}
