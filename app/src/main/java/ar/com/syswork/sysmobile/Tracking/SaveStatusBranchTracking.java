package ar.com.syswork.sysmobile.Tracking;

public class SaveStatusBranchTracking {

    private String IdDevice;
    private String campaign;
    private String Code;
    private String AggregateUri;
    private String Status ;
    private Double TimeTask ;
        private String End ;
    private String Start ;

    public  SaveStatusBranchTracking(    String IdDevice,
                                         String campaign,
                                         String Code,
                                         String AggregateUri,
                                         String Status ,
                                         Double TimeTask ,
                                         String _Start,
                                         String _End ){


        this.IdDevice=IdDevice;
        this.campaign=campaign;
        this.Code=Code;
        this.AggregateUri=AggregateUri;
        this.Status =Status;
        this.TimeTask =TimeTask;
        this.Start =_Start;
        this.End =_End;
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

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }

    public String getAggregateUri() {
        return AggregateUri;
    }

    public void setAggregateUri(String aggregateUri) {
        AggregateUri = aggregateUri;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public Double getTimeTask() {
        return TimeTask;
    }

    public void setTimeTask(Double timeTask) {
        TimeTask = timeTask;
    }

    public String getEnd() {
        return End;
    }

    public void setEnd(String end) {
        End = end;
    }

    public String getStart() {
        return Start;
    }

    public void setStart(String start) {
        Start = start;
    }
}
