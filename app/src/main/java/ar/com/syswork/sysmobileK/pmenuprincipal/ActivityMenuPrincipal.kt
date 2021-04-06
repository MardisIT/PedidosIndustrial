package ar.com.syswork.sysmobileK.pmenuprincipal

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ar.com.syswork.sysmobileK.ui.theme.MyApplicationTheme
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import ar.com.syswork.sysmobile.entities.ItemMenuPrincipal
import ar.com.syswork.sysmobile.pmenuprincipal.LogicaMenuPrincipal
import ar.com.syswork.sysmobileK.pmenuprincipal.widgets.ShowForceLoginDialog
import java.util.ArrayList


@SuppressLint("StaticFieldLeak")
private var logicaMenuPrincipal: LogicaMenuPrincipal? = null


class ActivityMenuPrincipal : ComponentActivity() {

    @ExperimentalMaterialApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Creo la Lista
        val opcionesMenu: List<ItemMenuPrincipal> = ArrayList()

        setContent {
            val context = LocalContext.current
            logicaMenuPrincipal = LogicaMenuPrincipal(context as Activity)
            logicaMenuPrincipal?.seteaListaOpciones(opcionesMenu)
            logicaMenuPrincipal?.creaItemsMenuPrincipal()
            val statusForceLogin = logicaMenuPrincipal?.forzarLogueo
            val forceLogin  = remember {
                mutableStateOf(statusForceLogin)
            }
            MyApplicationTheme(
                false
            ) {
                Body(
                    logicaMenuPrincipal!!.listaOpciones
                )
                if (forceLogin.value!!) {
                    ShowForceLoginDialog(
                        onOptionClicked = { result ->
                            forceLogin.value = result
                        },
                    )
                }

            }
        }
    }

}

@ExperimentalMaterialApi
@Composable
private fun Body(lisItemsMenu:List<ItemMenuPrincipal> ) {
    Scaffold(
        topBar = {
           TopAppBar(
               title = {Text(text = "Ventas")},
               elevation = 15.dp,
               actions = {
                   IconButton(onClick = {}) {
                       Icon(imageVector = Icons.Default.Settings, contentDescription = "Settings")
                   }
               }
           )
        },
        content = {
            LazyColumn(
                contentPadding = PaddingValues(horizontal = 10.dp,vertical = 10.dp )
            ) {
                items(lisItemsMenu.size){ i ->
                    run {
                        ListItem(
                            text = { Text(text = lisItemsMenu[i].titulo)},
                            icon = { Icon(painter = painterResource(lisItemsMenu[i].imagen), contentDescription = "icon") },
                            trailing = { Icon(imageVector = Icons.Default.KeyboardArrowRight, contentDescription = "")},
                            modifier = Modifier
                                .clickable {
                                    logicaMenuPrincipal?.lanzarActivity(lisItemsMenu[i].accion)
                                }
                                .clip(RoundedCornerShape(15.dp))
                                .border(1.dp, color = Color.Gray, shape = RoundedCornerShape(15.dp))
                        )
                        Divider()
                        Box(
                            modifier = Modifier.height(5.dp)
                        )
                    }

                }
            }
        }

    )
}

@ExperimentalMaterialApi
@Preview(showBackground = true)
@Composable
private fun DefaultPreview() {
    val opcionesMenu: List<ItemMenuPrincipal> = ArrayList()

    MyApplicationTheme(
        false
    ) {
        Body(
            opcionesMenu
        )
    }
}