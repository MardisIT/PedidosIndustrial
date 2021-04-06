package ar.com.syswork.sysmobileK.psplash.widgets

import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable

@Composable
fun ShowAlertDialog(onOptionClicked: (result: Boolean) -> Unit, onAccept:() -> Unit ) {
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