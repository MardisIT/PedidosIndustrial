package ar.com.syswork.sysmobileK.pmenuprincipal.widgets

import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import ar.com.syswork.sysmobile.R

@Composable
fun ShowForceLoginDialog(onOptionClicked: (result: Boolean) -> Unit) {
    AlertDialog(
        onDismissRequest = {
            onOptionClicked(false)
        },
        title = {
            Text(text = stringResource(id = R.string.avisoAlOperador))
        },
        text = {
            Text(stringResource(id = R.string.avisoVendedorVacio))
        },
        confirmButton = {
            Button(
                onClick = {
                    onOptionClicked(false)
                }) {
                Text("Configurar")
            }
        },
    )
}