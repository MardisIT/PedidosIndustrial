package ar.com.syswork.sysmobile.Tracking;

public class SaveNewBranchTracking {

    private String CodeBranch ;
    private String NameBranch ;
    private String StreetBranch ;
    private String StatusBranch ;
    private String RouteBranch ;
    private String IdDevice ;
    private String campaign ;
    private double GeoLength ;
    private double Geolatitude ;
    private double timetaks ;
    private String Start ;
    private String End ;
    private String AggregateUri ;
  public  SaveNewBranchTracking(String CodeBranch ,
                                String NameBranch ,
                                String StreetBranch ,
                                String StatusBranch ,
                                String RouteBranch ,
                                String IdDevice ,
                                String campaign ,
                                double GeoLength ,
                                double Geolatitude ,
                                double timetaks ,
                                String Start ,
                                String End ,
                                String AggregateUri
                                )
  {
      this.CodeBranch=CodeBranch;
      this.NameBranch=NameBranch;
      this.StreetBranch=StreetBranch;
      this.StatusBranch=StatusBranch;
      this.RouteBranch=RouteBranch;
      this.IdDevice=IdDevice;
      this.campaign=campaign;
      this.GeoLength=GeoLength;
      this.Geolatitude=Geolatitude;
      this.timetaks=timetaks;
      this.Start=Start;
      this.End=End;
      this.AggregateUri=AggregateUri;



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

    public double getTimetaks() {
        return timetaks;
    }

    public void setTimetaks(double timetaks) {
        this.timetaks = timetaks;
    }

    public String getStart() {
        return Start;
    }

    public void setStart(String start) {
        Start = start;
    }

    public String getEnd() {
        return End;
    }

    public void setEnd(String end) {
        End = end;
    }

    public String getAggregateUri() {
        return AggregateUri;
    }

    public void setAggregateUri(String aggregateUri) {
        AggregateUri = aggregateUri;
    }
}
