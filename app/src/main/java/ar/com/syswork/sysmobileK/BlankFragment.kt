package ar.com.syswork.sysmobileK

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ar.com.syswork.sysmobileK.ui.theme.MyApplicationTheme
import androidx.compose.ui.Modifier

class TestActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                Body()
            }
        }
    }
}

@Composable
fun Body() {
    Scaffold(
        backgroundColor = Color.White,
        topBar = {
            Box(modifier = Modifier.fillMaxWidth().height(80.dp).padding(0.dp).background(Color.Cyan) )// Inner padding;)
        },
        content = {
            Greeting("Android")
        }
    )
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MyApplicationTheme {
        Body()
    }
}