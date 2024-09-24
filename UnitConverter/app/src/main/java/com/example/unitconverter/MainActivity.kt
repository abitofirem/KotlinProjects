package com.example.unitconverter

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.unitconverter.ui.theme.UnitConverterTheme
import kotlin.math.roundToInt

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UnitConverterTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    UnitConverter()
                }
            }
        }
    }
}

@Composable
fun UnitConverter(){
     var inputValue by remember { mutableStateOf("") }
     var outputValue by remember { mutableStateOf("") }
     var inputUnit by remember { mutableStateOf("Meters") }
     var outputUnit by remember { mutableStateOf("Meters") }
     var iExpanded by remember { mutableStateOf(false) }
     var oExpanded by remember { mutableStateOf(false) }
     var conversionFactor = remember { mutableStateOf(1.00) } //meter to meter
     var oconversionFactor = remember { mutableStateOf(1.00) } //


    fun convertUnits(){
        val inputValueDouble= inputValue.toDoubleOrNull() ?:0.0
        val result = (inputValueDouble *conversionFactor.value*100.0/oconversionFactor.value).roundToInt()/100.0
        outputValue =result.toString()
    }
    Column( //bunu container olarak düşün ve nasıl görünmesini istediğini gir burada
        modifier = Modifier.fillMaxSize(),
        //Column içinde yukarıdan aşağıya doğru eşit boşluklarla ortalanır.
        verticalArrangement= Arrangement.Center,//verticalAligment->rowda
        horizontalAlignment =Alignment.CenterHorizontally //horizontalArrangement -> Columnda

    ) {

        //Tüm UI'lar birbirinin altına istiflenecek
        Text("Unit Converter",
            style=MaterialTheme.typography.headlineLarge)
        Spacer(modifier=Modifier.height(16.dp))
        OutlinedTextField(value = inputValue,
            onValueChange ={
            inputValue=it // Kullanıcının girdiği yeni değeri inputValue'ya atar. Böylece kullanıcı girdisi aalbilirim.
                convertUnits() //oto değişiklik olsun diye
        //Anonim fonksiyon
            //onu çağıramayız ama döndürebiliriz onValueChange: (String) -> Unit,
        //OutlinedTextField değer değiştiğinde ne olacak?} )

        },
            label= { Text("Enter value")}
            )
        Spacer(modifier=Modifier.height(16.dp))

        Row {     //Tüm UI'lar yan yana istiflenecek
// input box
            Box{
//input button
               Button(onClick = { iExpanded=true }) {
                  Text(text=inputUnit)
                   Icon(Icons.Default.ArrowDropDown,
                       contentDescription = "Arrow Down") //iconun neyi temsil ettiğini gösterir
               }
                DropdownMenu(expanded = iExpanded, onDismissRequest = { iExpanded=false }) {
                 //expanded menünün baştaaçık mı kapalı mı?
                    //expanded menüsü değiştirilbilir olması açısından yapıldı
                    DropdownMenuItem(
                        text = {Text("Centimetres") },
                        onClick = { // tıkladğımda menü kapansın
                            //centimeter seçtiğimden emin olayım

                            iExpanded=false
                            inputUnit="Centimeters"
                            conversionFactor.value=0.01
                            convertUnits()
                        }
                    )

                    DropdownMenuItem(
                        text = {Text("Meters") },
                        onClick = { iExpanded=false
                            inputUnit="Meters"
                            conversionFactor.value=1.0
                            convertUnits() }
                    )
                    DropdownMenuItem(
                        text = {Text("Feet") },
                        onClick = { iExpanded=false
                            inputUnit="Centimeters"
                            conversionFactor.value=0.3048
                            convertUnits()}
                    )
                    DropdownMenuItem(
                        text = {Text("Milimeters") },
                        onClick = { iExpanded=false
                            inputUnit="Milimeters"
                            conversionFactor.value=0.001
                            convertUnits() }
                    )
                }
           }
            Spacer(modifier = Modifier.width(16.dp))
//output box
            Box{
//output button
                Button(onClick = { oExpanded=true }) {
                    Text(text=outputUnit)
                    Icon(Icons.Default.ArrowDropDown,
                        contentDescription = "Arrow Down") //iconun neyi temsil ettiğini gösterir
                }
                DropdownMenu(expanded = oExpanded, onDismissRequest = {oExpanded=false}) {
                    //expanded menünün baştaaçık mı kapalı mı?
                    DropdownMenuItem(
                        text = {Text("Centimetres") },
                        onClick = {
                            oExpanded=false
                            outputUnit="Centimeters"
                            oconversionFactor.value=1.0
                            convertUnits()

                        }
                    )

                    DropdownMenuItem(
                        text = {Text("Meters") },
                        onClick = { oExpanded=false
                            outputUnit="Meters"
                            oconversionFactor.value=1.0
                            convertUnits() }
                    )
                    DropdownMenuItem(
                        text = {Text("Feet") },
                        onClick = { oExpanded=false
                            outputUnit="Feet"
                            oconversionFactor.value=0.3048
                            convertUnits() }
                    )
                    DropdownMenuItem(
                        text = {Text("Milimeters") },
                        onClick = { oExpanded=false
                            outputUnit="Milimeters"
                            oconversionFactor.value=0.001
                            convertUnits() }
                    )
                }

            }
        }
        Spacer(modifier=Modifier.height(16.dp))
        //Result Text
        Text("Result: $outputValue",
            style =MaterialTheme.typography.bodyLarge
        )
    }
}




//Kendi Previewımızı oluşturuyoruz, eskisini silip
//Preview'un otomatik güncellenmesini istiyorum
// Yandaki sekmede görünür olmasını sağladı
@Preview(showBackground =true)// arkaplanın da görünebilir olmasını sağladı
@Composable
fun UnitConverterPreview(){
    UnitConverter()
}

/*@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    UnitConverterTheme {

    }
}*/