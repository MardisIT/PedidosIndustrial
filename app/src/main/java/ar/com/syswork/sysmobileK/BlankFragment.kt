package ar.com.syswork.sysmobileK

import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import ar.com.syswork.sysmobile.R
import androidx.activity.compose.setContent
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ar.com.syswork.sysmobile.daos.DaoCodigosNuevos
import ar.com.syswork.sysmobile.daos.DataManager
import ar.com.syswork.sysmobile.shared.AppSysMobile
import ar.com.syswork.sysmobileK.ui.theme.MyApplicationTheme
import ar.com.syswork.sysmobileK.ui.theme.Purple200
import java.util.*
import ar.com.syswork.sysmobileK.SplashIcon as SplashIcon

private val timer: Timer? = null
private var dm: DataManager? = null
private var app: AppSysMobile? = null
var daoNewCodes: DaoCodigosNuevos? = null
private var locationManager : LocationManager? = null

private enum class BoxState {
    Small, Large
}


private fun getIsLocationEnabled(): Boolean {
    return  locationManager?.isProviderEnabled(LocationManager.GPS_PROVIDER)!!
            || locationManager?.isProviderEnabled(LocationManager.NETWORK_PROVIDER)!!
}

@Composable
private fun ShowAlertDialog(onOptionClicked: (result: Boolean) -> Unit ) {
    AlertDialog(
        onDismissRequest = {
            onOptionClicked(false)
        },
        title = {
            Text(text = "Dialog Title")
        },
        text = {
            Text("Here is a text ")
        },
        confirmButton = {
            Button(

                onClick = {
                    onOptionClicked(false)
                }) {
                Text("This is the Confirm Button")
            }
        },
        dismissButton = {
            Button(

                onClick = {
                    onOptionClicked(false)
                }) {
                Text("This is the dismiss Button")
            }
        }
    )
}



class SplashPage : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        locationManager = getSystemService(LOCATION_SERVICE) as LocationManager?
        val isLocationEnabled  = getIsLocationEnabled()

        setContent {
            MyApplicationTheme {
                val showDialogLocationEnabled  = remember {
                    mutableStateOf(isLocationEnabled)
                }
                Body()

                if(showDialogLocationEnabled.value){
                    ShowAlertDialog(
                        onOptionClicked = { result ->
                            showDialogLocationEnabled.value = result
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun Body() {
    Scaffold(
        backgroundColor = Color.White,
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
                    color = Purple200,
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 50.dp)

                )

            }
        }
    )
}

@Composable
fun SplashIcon() {

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
            BoxState.Small -> 80.dp
            BoxState.Large -> 150.dp
        }
    }

    Image(
        painter = image,
        modifier  = Modifier.size(size= size) ,
        contentDescription = "description of the image"
    )

    boxState.value = BoxState.Large
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MyApplicationTheme {
        Body()
    }
}