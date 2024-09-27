package com.example.myshoppinglistapp

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

data class ShoppingItem(val id:Int,
                        var name: String,
                        var quantity: Int,//miktar
    //düzenleyip düzenlemediğimize dair detaylar
                        var isEditing: Boolean = false
)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShoppingListApp() {
    var sItems by remember { mutableStateOf(listOf<ShoppingItem>()) } //ShoppingItem'ın nesnelerini kullansın istiyorum.
    var showDialog by remember { mutableStateOf(false) }
    var itemName by remember { mutableStateOf("") }
    var itemQuantity by remember { mutableStateOf("") } //edit textfield olduğu için böyle yaptım
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {
        Button(
            onClick = {showDialog=true},
            modifier = Modifier.align(Alignment.CenterHorizontally)

        ) {
            Text("Add Item")
        }
        LazyColumn(
            modifier = Modifier
                .fillMaxSize() //alanın tümünü kapladığı için buton en üste çıktı
                .padding(16.dp)
        ) {//Lazy Column içeriği
            //lazycolumn içindeki sItems ögeleri listeleyebileceğimiz bir yapı
            items(sItems) {
           // ShoppingListItem(it, {},{})
                    item ->
                if (item.isEditing) {
                    ShoppingItemEditor(
                        item = item,
                        onEditComplete = { editedName, editedQuantity ->
                            sItems = sItems.map { it.copy(isEditing = false) }
                            val editedItem = sItems.find { it.id == item.id }
                            editedItem?.let {
                                it.name = editedName
                                it.quantity = editedQuantity
                            }
                        }
                    )
                } else {
                    ShoppingListItem(
                        item = item,
                        onEditClick = { sItems = sItems.map { it.copy(isEditing = it.id == item.id) } },
                        onDeleteClick = { sItems = sItems - item }
                    )
                }
            }
        }
    }
//  Column altındayım
    if (showDialog){
        AlertDialog(
            onDismissRequest = {showDialog=false},
            confirmButton = {
                            Row(modifier= Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                              horizontalArrangement = Arrangement.SpaceBetween //Aralarında eşit mesafe olacak şekilde
                            ) {

                                Button(onClick = {
                                    if (itemName.isNotBlank()){
                                        val newItem= ShoppingItem(
                                            id= sItems.size+1,
                                            name= itemName,
                                            quantity= itemQuantity.toInt()
                                            //editing eklememe gerek yok zaten false olarak tanımlı eğer gerekirse değiştirim
                                        )
                                        sItems=sItems+newItem
                                        showDialog=false
                                        itemName=""//bir sonrakinde yine aynı ad çıkmasın diye
                                        itemQuantity=""
                                    }

                            }) {//Button için
                                Text("Add")
                            }

                                Button(onClick = { showDialog=false }) {
                                 Text("Cancel")
                                }
                            }


                            },


            title = {Text ("Add shopping item")},

            text = {
                Column {
                    OutlinedTextField(value =itemName ,
                        onValueChange = {itemName=it},//değişikliği girilene göre yapması
                        singleLine= true,//Tekli satır
                        modifier= Modifier
                            .fillMaxWidth()
                            .padding(8.dp)//tüm satırı kaplasın
                    )

                    OutlinedTextField(value =itemQuantity ,
                        onValueChange = {itemQuantity=it},//değişikliği girilene göre yapması
                        singleLine= true,//Tekli satır
                        modifier= Modifier
                            .fillMaxWidth()
                            .padding(8.dp)//tüm satırı kaplasın
                    )


                }
            })
    }
}

@Composable
fun ShoppingItemEditor(item:ShoppingItem,
                       onEditComplete:(String, Int)-> Unit ){ //düzenleme kısmı için
    var editedName by remember { mutableStateOf(item.name) }
    var editedQuantity by remember { mutableStateOf(item.quantity.toString()) }
    var isEditing by remember { mutableStateOf(item.isEditing) }

    Row(modifier= Modifier
        .fillMaxWidth()
        .background(Color.White)
        .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    )
    {
        Column {
            BasicTextField(
                value= editedName,
                onValueChange = {editedName=it},
                singleLine= true,
                modifier= Modifier
                    .wrapContentSize()
                    .padding(8.dp)//sadece ihtiyacı olanı alır.
            )
            BasicTextField(
                    value= editedQuantity,
                onValueChange = {editedQuantity=it},
                singleLine= true,
                modifier= Modifier
                    .wrapContentSize()
                    .padding(8.dp)//sadece ihtiyacı olanı alır.
            )
        }
        Button(onClick = {
            isEditing=false
            onEditComplete(editedName,editedQuantity.toIntOrNull()?:1)
        }) {
            Text(text = "Save")
        }

    }
}


@Composable //farklı bir composable oluşturuyorum-> Bu görünecek kart yapıları için ?
fun ShoppingListItem(
    item:ShoppingItem,
    onEditClick: ()-> Unit, //fonksiyonu direkt olduğu yerde çağırır
    onDeleteClick: ()-> Unit,
){
    Row(modifier= Modifier
        .fillMaxWidth()
        .padding(8.dp)
        .border(
            border = BorderStroke(2.dp, Color(0XFF018786)),
            shape = RoundedCornerShape(20),

        ),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = item.name, modifier= Modifier.padding(8.dp))
        Text(text = "Gty : ${item.quantity}", modifier= Modifier.padding(8.dp))
        Row(modifier= Modifier.padding(8.dp)) {
            IconButton(onClick=onEditClick){
                Icon(imageVector= Icons.Default.Edit, contentDescription = null)
            }
            IconButton(onClick=onDeleteClick){
                Icon(imageVector= Icons.Default.Delete, contentDescription = null)
            }

        }


    }

}