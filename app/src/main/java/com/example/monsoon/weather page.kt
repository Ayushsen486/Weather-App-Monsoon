package com.example.monsoon

import android.text.style.BackgroundColorSpan
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.LinearGradient
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.example.monsoon.api.NetworkResponse
import com.example.monsoon.api.WeatherModel
import org.jetbrains.annotations.Async

// code for search box and tap icon
@Composable
fun WeatherPage(viewModel: ViewModel){
   var city by remember {
       mutableStateOf("")
   }
val weatherResult = viewModel.weatherResult.observeAsState()
    val KeyboardController = LocalSoftwareKeyboardController.current
Column(
            modifier = Modifier.fillMaxWidth().padding(8.dp),
    horizontalAlignment = Alignment.CenterHorizontally
            ){
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        OutlinedTextField(
modifier = Modifier.weight(1f),
            value = city,
            onValueChange ={
                city = it
            },
            label = {
                Text(text = "Search for any location",
                    fontSize = 21.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.DarkGray
                )
            }
            )
        IconButton(onClick = {
            viewModel.getData(city)
            KeyboardController?.hide()
        }) {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search for any location"

            )
        }
    }
    when (val result = weatherResult.value){
        is NetworkResponse.Error -> {
            Text(text = result.message)
        }
        NetworkResponse.Loading ->{
            CircularProgressIndicator()
        }
        is NetworkResponse.Success -> {
            WeatherDetails(data = result.data)
        }

        null -> {}
    }
}
}
// given code is for symbols and details with name
@Composable
fun WeatherDetails(data : WeatherModel){
    Column (
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally

    ){
        Row (
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.Bottom

        ){
            Icon(
                imageVector = Icons.Default.LocationOn,
                contentDescription = "Location icon ",
                modifier = Modifier.size(50.dp)
            )
            Text(text = data.location.name, fontSize = 40.sp)
            Spacer(modifier = Modifier.width(10.dp))
            Text(text = data.location.country, fontSize = 25.sp, color = Color.Black)
        }
        Spacer(modifier = Modifier.height(28.dp))
        Text(text =  "${data.current.temp_c} °c",
 fontSize = 56.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center

            )
//        AsyncImage(
//            modifier = Modifier.size(160.dp),
//            model = "https:${data.current.condition.icon}".replace("64x64", "128x128"),         contentDescription = "Weather Icon",
        AsyncImage(
            model = "https:${data.current.condition.icon}".replace("64x64", "128x128"),
            contentDescription = "Weather Icon",
            modifier = Modifier.size(160.dp)
        )



        Icon(
            imageVector = Icons.Default.FavoriteBorder,
            contentDescription = "condition icon ",
            modifier = Modifier.size(60.dp)
        )

        Text(
            text = data.current.condition.text,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            color = Color.DarkGray

        )
         Spacer(modifier = Modifier.height(16.dp))
        @Composable
        fun CardGradient(){

        }
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent,

        )
    )

        {
        Column(
            modifier = Modifier.fillMaxWidth()


        ){
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround,

                    ){
              WeatherKeyval("Humidity", data.current.humidity)
                WeatherKeyval("Wind Speed", data.current.wind_kph+"km/h")

                }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround

            ){
                WeatherKeyval("UV", data.current.uv)
                WeatherKeyval("Precipitation", data.current.precip_mm+"mm")

            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround,

            ){
                WeatherKeyval("local Time", data.location.localtime.split(" ")[1])
                WeatherKeyval("local Date", data.location.localtime.split(" ")[0])


            }
            }
        }
    }

    }

@Composable
fun WeatherKeyval(key: String, value: String){
    Column (
        modifier = Modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text(text = value, fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Text(text = key, fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.DarkGray)
    }
    }

