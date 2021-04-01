package ar.com.syswork.sysmobileK.psplash

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.runtime.mutableStateOf
import ar.com.syswork.sysmobile.daos.DaoCodigosNuevos
import ar.com.syswork.sysmobile.daos.DataManager
import ar.com.syswork.sysmobile.shared.AppSysMobile
import ar.com.syswork.sysmobile.R
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ar.com.syswork.sysmobile.industrial.DemoJobCreator
import ar.com.syswork.sysmobile.pconsultagenerica.detalle.obtenerCodigos
import ar.com.syswork.sysmobile.plogin.ActivityLogin
import ar.com.syswork.sysmobile.pmenuprincipal.ActivityMenuPrincipal
import ar.com.syswork.sysmobileK.ui.theme.MyApplicationTheme
import ar.com.syswork.sysmobileK.ui.theme.Purple200
import com.evernote.android.job.JobManager
import com.evernote.android.job.JobManagerCreateException
import java.util.*
import androidx.compose.runtime.remember as remember
import ar.com.syswork.sysmobileK.psplash.daoNewCodes as daoNewCode

private enum class BoxState {
    Small, Large
}

private var locationManager : LocationManager? = null
private var timer:Timer? = null
private var dm:DataManager? = null
private var app:AppSysMobile? = null
internal var daoNewCodes:DaoCodigosNuevos? = null
private var showDialogLocationEnabled:MutableState<Boolean>? = null


private fun checkPermission() {
    showDialogLocationEnabled!!.value = getIsLocationEnabled()
}

private fun getIsLocationEnabled(): Boolean {
    return locationManager?.isProviderEnabled(LocationManager.GPS_PROVIDER)!!
            || locationManager?.isProviderEnabled(LocationManager.NETWORK_PROVIDER)!!
}


private fun launchActivity(context: Context,onStartActivity: (result: Intent) -> Unit) {
    val activity = context as Activity
    val daoSeller = dm?.daoVendedor
    val i: Intent
    try {
        JobManager
            .create(context)
            .addJobCreator(DemoJobCreator())
    } catch (e: JobManagerCreateException) {
        Log.d("", e.message.toString())
    }
    daoNewCode = dm?.daoCodigosNuevos
    if (daoNewCode?.getAll("uri=''")?.size == 0) {
        val fetchJsonTask = obtenerCodigos(activity)
        fetchJsonTask.execute("", "")
    }
    if (daoSeller?.count == 0 || daoSeller == null) {
        i = Intent(activity, ActivityMenuPrincipal::class.java)
        app?.vendedorLogueado = ""
    } else {
        i = Intent(activity, ActivityLogin::class.java)
    }
    onStartActivity(i)
}


class ActivitySplash: ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP_MR1)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        locationManager = getSystemService(LOCATION_SERVICE) as LocationManager?
        val isLocationEnabled = getIsLocationEnabled()
        timer = Timer()

        setContent {
            val context = LocalContext.current
            //Instance of DataManager
            dm = DataManager(context)
            //Set the application
            app = application as AppSysMobile
            app?.dataManager = dm

            //Initialize las SharedPreferences
            app?.iniciaConfiguracion()

            showDialogLocationEnabled = remember {
                mutableStateOf(isLocationEnabled)
            }

            val taskTimer: TimerTask = object : TimerTask() {
                override fun run() {
                    launchActivity(
                        context,
                        onStartActivity={ result ->
                            startActivity(result)
                            timer!!.cancel()
                            finish()
                        }
                    )
                }
            }

            MyApplicationTheme {
                Body()
                if (!showDialogLocationEnabled!!.value) {
                    ShowAlertDialog(
                        onOptionClicked = { result ->
                            showDialogLocationEnabled!!.value = result
                        },
                        onAccept= {
                            val myIntent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                            startActivity(myIntent)
                        }
                    )
                } else if (getIsLocationEnabled()) {
                    timer?.schedule(taskTimer, 3000)
                }
            }
        }

    }

    override fun onResume() {
        super.onResume()
        if (showDialogLocationEnabled?.value == true) {
            checkPermission()
        }
    }

}

@Composable
private fun Body() {
    Scaffold(
        content = {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(),
            ) {

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    SplashIcon()
                }
                CircularProgressIndicator(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 50.dp)

                )

            }
        }
    )
}

@Composable
private fun SplashIcon() {

    val image: Painter = painterResource(id = R.drawable.ic_launcher3)
    val boxState = remember{ mutableStateOf(BoxState.Small)}

    val transition = updateTransition(targetState = boxState, label = "")

    val size by transition.animateDp(label = "",
        transitionSpec = {
            spring(
                dampingRatio = 0.2f,
                stiffness = 0.5f
            )
        }
    ) { state ->
        when (state.value) {
            BoxState.Small -> 60.dp
            BoxState.Large -> 120.dp
        }
    }

    Image(
        painter = image,
        modifier  = Modifier.size(size= size) ,
        contentDescription = "description of the image"
    )

    boxState.value = BoxState.Large
}

@Composable
private fun ShowAlertDialog(onOptionClicked: (result: Boolean) -> Unit, onAccept:() -> Unit ) {
    AlertDialog(
        onDismissRequest = {
            onOptionClicked(true)
        },
        title = {
            Text(text = "Habilitar Ubicación")
        },
        text = {
            Text("Su ubicación esta desactivada.\npor favor active su ubicación para continuar.")
        },
        confirmButton = {
            Button(
                onClick = {
                    onOptionClicked(true)
                    onAccept()
                }) {
                Text("Configurar")
            }
        },
    )
}

@Preview(showBackground = true)
@Composable
private fun DefaultPreview() {
    MyApplicationTheme {
        Body()
    }
}

