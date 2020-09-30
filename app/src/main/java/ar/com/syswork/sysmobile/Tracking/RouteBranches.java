package ar.com.syswork.sysmobile.Tracking;

public class RouteBranches {

    private String CodeBranch ;
    private String NameBranch ;
    private String StreetBranch ;
    private String StatusBranch ;
    private String RouteBranch ;
    private String IdDevice ;
    private String campaign ;
    private double GeoLength ;
    private double Geolatitude ;
    private String dateexec ;
    private String dateexecini ;
    private double timetaks ;

    public RouteBranches(   String _CodeBranch ,
                       String _NameBranch ,
                       String _StreetBranch ,
                       String _StatusBranch ,
                       String _RouteBranch ,
                       String _IdDevice ,
                       String _campaign ,
                       double _GeoLength ,
                       double _Geolatitude,
                            double _timetaks ,
                            String _dateexec,
                            String _dateexecini
                      )

    {
        this.CodeBranch =_CodeBranch ;
        this.NameBranch =_NameBranch ;
        this.StreetBranch =_StreetBranch ;
        this.StatusBranch =_StatusBranch ;
        this.RouteBranch =_RouteBranch ;
        this.IdDevice =_IdDevice ;
        this.campaign =_campaign ;
        this.GeoLength =_GeoLength ;
        this.Geolatitude =_Geolatitude ;
        this.dateexec=_dateexec;
        this.timetaks=_timetaks;
        this.dateexecini=_dateexecini;


    }

    public String getCodeBranch() {
        return CodeBranch;
    }

    public void setCodeBranch(String codeBranch) {
        CodeBranch = codeBranch;
    }

    public String getNameBranch() {
        return NameBranch;
    }

    public void setNameBranch(String nameBranch) {
        NameBranch = nameBranch;
    }

    public String getStreetBranch() {
        return StreetBranch;
    }

    public void setStreetBranch(String streetBranch) {
        StreetBranch = streetBranch;
    }

    public String getStatusBranch() {
        return StatusBranch;
    }

    public void setStatusBranch(String statusBranch) {
        StatusBranch = statusBranch;
    }

    public String getRouteBranch() {
        return RouteBranch;
    }

    public void setRouteBranch(String routeBranch) {
        RouteBranch = routeBranch;
    }

    public String getIdDevice() {
        return IdDevice;
    }

    public void setIdDevice(String idDevice) {
        IdDevice = idDevice;
    }

    public String getCampaign() {
        return campaign;
    }

    public void setCampaign(String campaign) {
        this.campaign = campaign;
    }

    public double getGeoLength() {
        return GeoLength;
    }

    public void setGeoLength(double geoLength) {
        GeoLength = geoLength;
    }

    public double getGeolatitude() {
        return Geolatitude;
    }

    public void setGeolatitude(double geolatitude) {
        Geolatitude = geolatitude;
    }

    public String getDateexec() {
        return dateexec;
    }

    public void setDateexec(String dateexec) {
        this.dateexec = dateexec;
    }

    public double getTimetaks() {
        return timetaks;
    }

    public void setTimetaks(double timetaks) {
        this.timetaks = timetaks;
    }

    public String getDateexecini() {
        return dateexecini;
    }

    public void setDateexecini(String dateexecini) {
        this.dateexecini = dateexecini;
    }
}
