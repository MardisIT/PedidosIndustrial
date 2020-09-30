package ar.com.syswork.sysmobile.industrial;



/*import com.evernote.android.job.Job;
import com.evernote.android.job.JobManager;
import com.evernote.android.job.JobRequest;*/

public class ServerPollingJob {}/*extends Job {

    private static final long FIFTEEN_MINUTES_PERIOD = 900000;
    private static final long ONE_HOUR_PERIOD = 3600000;
    private static final long SIX_HOURS_PERIOD = 21600000;
    private static final long ONE_DAY_PERIOD = 86400000;

    public static final String TAG = "serverPollingJob";
    LocationManager locationManager;
    private AppSysMobile app;
    private Activity a;
    private DataManager dm;
    private AppSysMobile appSysmobile;
    private DataManager dataManager;
    private DaoCliente daoCliente;
    private DaoConfiguracion daoConfiguracion;
    private DaoToken daoToken;

    public static void ServerPollingJob() {
        schedulePeriodicJob("");
    }

    @androidx.annotation.RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
    @Override
    @NonNull
    protected Result onRunJob(@NonNull Params params) {
        if (!isDeviceOnline()) {
            return Result.FAILURE;
        }
        this.a=AppSysMobile.getActividad();

        app = (AppSysMobile) this.a.getApplication();
        dataManager = app.getDataManager();
        daoCliente = dataManager.getDaoCliente();
        daoConfiguracion=dataManager.getDaoConfiguracion();


        if (AppSysMobile.getInstance().getString(R.string.never_value_tracking).equals("true")) {
            try{
                JavaRestClient tarea = new JavaRestClient();
                ValidToken();
                String deviceId = obterImeid(AppSysMobile.getActividad());
                GPSTracker _track =new  GPSTracker(AppSysMobile.getInstance().getApplicationContext());
                String campaing= daoConfiguracion.getByKey("1").getCampaniaNombre();
                Tracking _traTracking=new Tracking(_track.getLongitude(),_track.getLatitude(),_track.getAccurency(),campaing,campaing,deviceId,LeveLBatery());
                tarea.SetTracking(_traTracking);
                // informAboutTracking();}
            }catch (Exception e){


            }
        }

        return Result.SUCCESS;


    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
    @SuppressLint("MissingPermission")
    public String  obterImeid(Activity a) {
        final String androidIdName = Settings.Secure.ANDROID_ID;

        TelephonyManager tm = (TelephonyManager) a.getSystemService(Context.TELEPHONY_SERVICE);
        TelephonyManager mTelephony = (TelephonyManager) a.getSystemService(Context.TELEPHONY_SERVICE);
        String simSerialNo="";
        String  myIMEI = mTelephony.getDeviceId();

        if (myIMEI == null) {
            SubscriptionManager subsManager = (SubscriptionManager) a.getSystemService(Context.TELEPHONY_SUBSCRIPTION_SERVICE);

            List<SubscriptionInfo> subsList = subsManager.getActiveSubscriptionInfoList();

            if (subsList!=null) {
                for (SubscriptionInfo subsInfo : subsList) {
                    if (subsInfo != null) {
                        simSerialNo  = subsInfo.getIccId();
                    }
                }

            }

            myIMEI=simSerialNo;
        }



        return myIMEI;

    }
    private boolean isDeviceOnline() {
        ConnectivityManager connMgr =
                (ConnectivityManager) AppSysMobile.getInstance().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    private int LeveLBatery()
    {
        IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatus = AppSysMobile.getInstance().getApplicationContext().registerReceiver(null, ifilter);

        int level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        int scale = batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1);

        int batteryPct = level * 100 / scale;
        return  batteryPct;

    }
    private void ValidToken(){
        this.a=AppSysMobile.getActividad();
        app = (AppSysMobile) a.getApplication();
        dm = app.getDataManager();
        daoToken=dm.getDaoToken();
        JavaRestClient tarea = new JavaRestClient();
        String date="";
        for (Token a:daoToken.getAll("")
        ) {
            date= a.getFecha();
        }

        try {
            Date datetoken=new SimpleDateFormat("dd-MM-yyyy").parse(date);
            Date dateNow = new Date();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(datetoken); // Configuramos la fecha que se recibe
            calendar.add(Calendar.DAY_OF_YEAR, 5);  // numero de días a añadir, o restar en caso de días<0
            Date ExpToke=calendar.getTime();
            if(dateNow.compareTo(ExpToke)>0 ){
                User _user =new User();
                tarea.getToken2(_user,a.getApplicationContext());
            }

        }catch (Exception e){
            User _user =new User();
            tarea.getToken2(_user,a.getApplicationContext());

        }
    }
    public static void schedulePeriodicJob(String selectedOption) {
        if (AppSysMobile.getInstance().getString(R.string.never_value).equals("never")) {
            JobManager.instance().cancelAllForTag(TAG);
        } else {
            long period = FIFTEEN_MINUTES_PERIOD;


            new JobRequest.Builder(TAG)
                    .setPeriodic(period, 300000)
                    .setUpdateCurrent(true)
                    .setRequiredNetworkType(JobRequest.NetworkType.CONNECTED)
                    .build()
                    .schedule();
        }
    }



}*/