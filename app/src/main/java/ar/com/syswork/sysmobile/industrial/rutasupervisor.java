package ar.com.syswork.sysmobile.industrial;

public class rutasupervisor
{
    public String CodeBranch;
        public String NameBranch;
        public String StreetBranch;
        public String StatusBranch;
        public String RouteBranch;
        public String IdDevice;
        public String campaign;
        public double GeoLength;
        public double Geolatitude;
        double _timetaks ;
        String _dateexec;
        public String startDate;

        public String getStartDate() {
            return startDate;
        }

        public void setStartDate(String startDate) {
            this.startDate = startDate;
        }

        public double get_timetaks() {
            return _timetaks;
        }

        public void set_timetaks(double _timetaks) {
            this._timetaks = _timetaks;
        }

        public String get_dateexec() {
            return _dateexec;
        }

        public void set_dateexec(String _dateexec) {
            this._dateexec = _dateexec;
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
}
