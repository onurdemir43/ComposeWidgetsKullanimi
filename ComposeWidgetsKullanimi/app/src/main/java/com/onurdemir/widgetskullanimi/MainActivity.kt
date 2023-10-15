package com.onurdemir.widgetskullanimi

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCompositionContext
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.semantics.Role.Companion.Switch
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.onurdemir.widgetskullanimi.ui.theme.WidgetsKullanimiTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WidgetsKullanimiTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    SayfaDropdownMenu()
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    WidgetsKullanimiTheme {
        SayfaDropdownMenu()
    }
}

@Composable
fun SayfaDropdownMenu() {
    val ulkeListe = listOf("Türkiye","İtalya","Almanya","Japonya","Rusya","Çin")
    val menuAcilisKontrol = remember {
        mutableStateOf(false)
    }
    val secilenIndeks = remember {
        mutableStateOf(0)
    }

    Column(modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box{
            
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .size(100.dp, 50.dp)
                    .clickable {
                        menuAcilisKontrol.value = true
                    }
            ) {
                Text(text = ulkeListe[secilenIndeks.value])
                Image(painter = painterResource(id = R.drawable.dropdownmenu_resim),
                    contentDescription = "")
            }
            DropdownMenu(
                expanded = menuAcilisKontrol.value,
                onDismissRequest = { menuAcilisKontrol.value = false }) {

                ulkeListe.forEachIndexed{ indeks, ulke ->

                    DropdownMenuItem(text = { Text(ulke) },
                        onClick = {
                            menuAcilisKontrol.value = false
                            Log.e("Menu","Ülke Seçildi : $ulke")
                            secilenIndeks.value = indeks
                        })

                }



                /*
                DropdownMenuItem(text = { Text("Sil") },
                    onClick = {
                        menuAcilisKontrol.value = false
                        Log.e("Menu","Sil Seçildi")
                    })

                 */


            }
        }
        Button(onClick = {
            Log.e("Menu", "En son seçilen ülke: ${ulkeListe[secilenIndeks.value]}")
        }) {
            Text(text = "GÖSTER")
        }
    }
}

@Composable
fun SayfaImage() {
    Column {

        val activity = (LocalContext.current as Activity)
        Image(bitmap = ImageBitmap.imageResource(id =
        activity.resources.getIdentifier("pisa","drawable",activity.packageName)
        ),
            contentDescription = "")


        Image(painter = painterResource(id = R.drawable.resim), contentDescription = "")
        
    }
}


@SuppressLint("SetJavaScriptEnabled")
@Composable
fun SayfaWebview() {
    val url = "https://gelecegiyazanlar.turkcell.com.tr/"
    AndroidView(factory = {
        WebView(it).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            webViewClient = WebViewClient()
            loadUrl(url)
        }
    }, update = {
        it.loadUrl(url)
    })
}



@Composable
fun SayfaProgressVeSlider() {
    val progressDurum = remember {
        mutableStateOf(false)
    }

    val sliderDeger = remember {
        mutableStateOf(0f)
    }

    Column(modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        if (progressDurum.value) {
            CircularProgressIndicator(color = Color.Red)
        }

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            Button(onClick = {
                progressDurum.value = true
            }) {
                Text(text = "Başla")
            }

            Button(onClick = {
                progressDurum.value = false
            }) {
                Text(text = "Dur")
            }
        }

        Text(text = "Sonuç: ${sliderDeger.value.toInt()}")
        
        Slider(
            value = sliderDeger.value,
            onValueChange = { sliderDeger.value = it },
            valueRange = 0f..100f,
            modifier = Modifier.padding(all = 20.dp),
            colors = SliderDefaults.colors(
                thumbColor = Color.Red,
                activeTrackColor = Color.Black,
                inactiveTrackColor = Color.LightGray
            )

        )


        Button(onClick = {
            Log.e("Slider", sliderDeger.value.toInt().toString())
        }) {
            Text(text = "Göster")
        }




    }
}

@Composable
fun SayfaRadioButton() {
    val secilenIndeks = remember {
        mutableStateOf(0)
    }
    val takimListesi = listOf("Real Madrid","Barcelona")
    Column(modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Column {
            takimListesi.forEachIndexed { indeks, takim ->
                Row(modifier = Modifier.clickable {
                    secilenIndeks.value = indeks
                    Log.e("RadioButton Seçildi", takim)
                }) {
                    RadioButton(
                        selected = takim == takimListesi[secilenIndeks.value],
                        onClick = {
                            secilenIndeks.value = indeks
                            Log.e("RadioButton Seçildi", takim)
                        })
                    Text(text = takim)
                }

            }
        }

        Button(onClick = {
            Log.e("RadioButton En Son Durum", takimListesi[secilenIndeks.value])
        }) {
            Text(text = "Göster")
        }


    }
}

@Composable
fun SayfaTıklanma() {
    Column(modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(modifier = Modifier
            .size(100.dp)
            .background(color = Color.Red)
            //.clickable {Log.e("Box", "Tıklandı") }
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = {
                        Log.e("Box", "Tıklandı")
                    },
                    onDoubleTap = {
                        Log.e("Box", "Çift Tıklandı")
                    },
                    onLongPress = {
                        Log.e("Box", "Uzun Basıldı")
                    }
                )
            }
        )
    }
}


@Composable
fun SayfaCheckBox() {
    val checkboxDurum = remember {
        mutableStateOf(false)
    }
    Column(modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Row {
            Checkbox(checked = checkboxDurum.value,
                onCheckedChange = {
                    checkboxDurum.value = it
                    Log.e("Checkbox Seçildi", it.toString())
                },
                colors = CheckboxDefaults.colors(
                    checkedColor = Color.Red
                )
            )
            Text(text = "Jetpack Compose", modifier = Modifier.padding(start = 10.dp))
        }

        Button(onClick = {
            Log.e("Checkbox En Son Durumu", checkboxDurum.value.toString())
        }) {
            Text(text = "Göster")
        }


    }
}


@Composable
fun SayfaSwitch() {
    val switchDurum = remember {
        mutableStateOf(false)
    }
    Column(modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Switch(
            checked = switchDurum.value,
            onCheckedChange = {
            switchDurum.value = it
                Log.e("Switch Seçildi", it.toString())
        },
        colors = SwitchDefaults.colors(
            checkedTrackColor = Color.Red,
            checkedThumbColor = Color.LightGray,
            uncheckedTrackColor = Color.Green,
            uncheckedThumbColor = Color.Black,

        )

            )



        Button(onClick = {
            Log.e("Switch En Son Durumu", switchDurum.value.toString())
        }) {
            Text(text = "Göster")
        }


    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SayfaFab() {

    /*
    Scaffold(
        content = {

        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    Log.e("FAB","Tıklandı")
                },
                containerColor = Color.Red,
                content = {
                    Icon(painter = painterResource(id = R.drawable.ekle_resim), contentDescription = "", tint = Color.White)
                }


            )


        }
    )

     */




    Scaffold(
        content = {

        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = {
                    Log.e("FAB","Tıklandı")
                },
                text = { Text(text = "EKLE", color = Color.White)},
                containerColor = Color.Red,
                icon = {
                    Icon(painter = painterResource(id = R.drawable.ekle_resim), contentDescription = "", tint = Color.White)
                }


            )


        }
    )




}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SayfaButtonTextTextfield() {

    val tf = remember { mutableStateOf("") }
    val tfOutLined = remember { mutableStateOf("") }
    val alinanVeri = remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Gelen Veri: ${alinanVeri.value}",
        color = Color.White,
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            style = TextStyle(
                background = Color.Blue
            )

            )
        
        TextField(
            value = tf.value,
            onValueChange = { tf.value = it },
            label = { Text(text = "Veri Giriniz")},
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.LightGray,
                textColor = Color.Red,
                focusedIndicatorColor = Color.Green,
                focusedLabelColor = Color.Yellow
            ),
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        
        Button(onClick = {
            alinanVeri.value = tf.value
        },
            colors = ButtonDefaults.buttonColors(
            containerColor = Color.Red,
            contentColor = Color.White
        ),
            border = BorderStroke(2.dp, Color.Black),
            shape = RoundedCornerShape(50)


            ) {
            Text(text = "Veriyi Al")
        }

        OutlinedTextField(
            value = tfOutLined.value,
            onValueChange = { tfOutLined.value = it },
            label = { Text(text = "Veri Giriniz")}
        )

        OutlinedButton(onClick = {
            alinanVeri.value = tfOutLined.value
        },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Red,
                contentColor = Color.White
            ),
            border = BorderStroke(2.dp, Color.Black),
            shape = RoundedCornerShape(50)

            ) {
            Text(text = "Veriyi Al Outlined")
        }
    }
}

