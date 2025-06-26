package com.example.spendtrack.Screen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.spendtrack.data.SpendViewModel
import com.example.spendtrack.data.SpendModel
import java.util.Date


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(spendViewModel: SpendViewModel) {

    var showDialog = remember { mutableStateOf(false) }//for dialog box
    var description = remember { mutableStateOf("") }//for description
    var cost = remember { mutableStateOf("") }//for cost

    val spendList = spendViewModel.getAllSpend.collectAsState(listOf())

    val totalCost = spendViewModel.totalCost.collectAsState(initial = 0L)



    if (showDialog.value) {
        Dialog(
            onDismissRequest = {
                showDialog.value = false
                description.value = ""
                cost.value = ""
            }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(1f)
                    .height(250.dp)
                    .background(color = Color.White, shape = RoundedCornerShape(8.dp))

            ) {
                OutlinedTextField(
                    value = description.value,
                    onValueChange = { description.value = it },
                    label = { Text(text = "Description") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp, top = 32.dp, start = 8.dp, end = 8.dp),
                    singleLine = true
                )
                OutlinedTextField(
                    value = cost.value,
                    onValueChange = { cost.value = it },
                    label = { Text(text = "Cost") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 8.dp, end = 8.dp),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 26.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Button(
                        onClick = {
                            showDialog.value = false
                            description.value = ""
                            cost.value = ""
                        },
                        modifier = Modifier
                            .padding(start = 8.dp)
                            .width(100.dp)
                            .height(40.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Red,
                            contentColor = Color.White
                        )

                    ) {
                        Text(text = "Cancel")
                    }
                    Button(
                        onClick = {
                            try {
                                val costDouble = cost.value.toLong()
                                spendViewModel.addToList(
                                    SpendModel(0,description.value, costDouble,Date())
                                )
//                                spendViewModel.calTotalCost()
                                showDialog.value = false
                                description.value = ""
                                cost.value = ""

                            } catch (e: NumberFormatException) {
                                Log.e("MainScreen", "Invalid cost format", e)
                            }
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Red,
                            contentColor = Color.White
                        ),
                        modifier = Modifier
                            .padding(end = 8.dp)
                            .width(100.dp)
                            .height(40.dp)
                    ) {
                        Text(text = "Add")
                    }
                }
            }
        }

    }
    Scaffold(topBar = {
        TopAppBar(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 75.dp, max = 75.dp), title = {
                    Row(modifier = Modifier.wrapContentSize()){
                Text(
                    text = "SpendTrack",
                    modifier = Modifier.padding(start = 4.dp),
                    color = Color.White,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    overflow = TextOverflow.Ellipsis

                )
                        Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = "₹ ${totalCost.value.toString()}",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 30.dp),
                    textAlign = TextAlign.Right,
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    overflow = TextOverflow.Ellipsis
                )
            }}, colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color.Red,
            )
        )
    }, floatingActionButton = {
        FloatingActionButton(
            onClick = {
                showDialog.value = true
            },
            contentColor = Color.Red,
            containerColor = Color.White
        ) {
            Icon(imageVector = Icons.Default.Add, contentDescription = "Add")
        }
    }) {

        Box(modifier = Modifier.fillMaxSize().padding(it)) {
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(spendList.value.size) { itx ->
                    Item(spendViewModel, spendList.value[itx])
                }
            }

            FloatingActionButton(
                onClick = {
                   //to clear the list
                    spendViewModel.deleteAll()
                },
                contentColor = Color.Red,
                containerColor = Color.White,
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(start = 14.dp, bottom = 18.dp)
            ) {
                Icon(imageVector = Icons.Default.Done, contentDescription = "Add")
            }

        }
    }
}


@Composable
fun Item(spendViewModel: SpendViewModel, spend: SpendModel) {
    // var totalCost = remember { mutableStateOf(spendViewModel.calTotalCost()) }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = {
                //Toast.makeText(context, "Clicked", Toast.LENGTH_SHORT).show()
            })
            .height(110.dp)
            .padding(4.dp)
            .border(width = 1.dp, color = Color.Black, shape = RoundedCornerShape(8.dp)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column (modifier = Modifier.weight(1f)){
                Text(
                    text = "Description::${spend.description}",
                    modifier = Modifier.padding(top = 8.dp, start = 20.dp),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = "cost::${spend.cost}",
                    modifier = Modifier.padding(top = 8.dp, start = 20.dp),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = spend.date.toString(),//for date
                    modifier = Modifier.padding(top = 8.dp, start = 20.dp),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = "Delete",
                modifier = Modifier
                    .padding(end = 10.dp)
                    .clickable(onClick = {
                        spendViewModel.deleteFromList(spend)
                    })
            )
        }
    }
}




