package com.example.idlegame.ui.template

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFrom
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.idlegame.R

@Composable
fun Weapon(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .requiredWidth(width = 350.dp)
            .requiredHeight(height = 90.dp)


    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clip(shape = RoundedCornerShape(15.dp))
                .background(color = Color(0xff3a3d45))
                .border(border = BorderStroke(3.dp, Color(0xff242424)),
                    shape = RoundedCornerShape(15.dp)))

        Text(
            text = "titleaaasaaaaaaaa\n",
            modifier = Modifier
                .align(alignment = Alignment.TopStart)
                .offset(x = 80.dp,
                    y = 5.dp)
                .width(260.dp)
                .height(30.dp),
            style = TextStyle(
                fontSize = 15.sp,
                fontWeight = FontWeight(400),
                color = Color(0xFFFFFFFF),

                )
        )
        Text(
            modifier = Modifier
                .align(alignment = Alignment.TopStart)
                .offset(x = 80.dp,
                    y = 35.dp)
                .width(50.dp)
                .height(20.dp),
            text = "LVL",
            style = TextStyle(
                fontSize = 12.sp,
                fontWeight = FontWeight(400),
                color = Color(0xFFFFFFFF),
            )
        )
        Text(
            modifier = Modifier
                .align(alignment = Alignment.TopStart)
                .offset(x = 80.dp,
                    y = 55.dp)
                ,
            text = "/s",
            color = Color.White,
            style = TextStyle(
                fontSize = 12.sp),
        )
        TextButton(
            onClick = { },
            colors = ButtonDefaults.buttonColors(),
            modifier = Modifier
                .align(alignment = Alignment.TopStart)
                .offset(x = 241.dp,
                    y = 45.dp)
                .requiredWidth(width = 100.dp)
                .requiredHeight(height = 35.dp)
        ) {

            Box(
                modifier = Modifier
                    .requiredWidth(width = 100.dp)
                    .requiredHeight(height = 35.dp)
            ) {
                Image(
                painter = painterResource(id = R.drawable.buyrectangle),
                contentDescription = "add",
                modifier = Modifier
                    .requiredWidth(width = 100.dp)
                    .requiredHeight(height = 35.dp)
            )

                Image(
                    painter = painterResource(id = R.drawable.bag_of_cash_2),
                    contentDescription = "bag_of_cash 2",
                    modifier = Modifier
                        .align(alignment = Alignment.TopStart)
                        .offset(x = 72.dp,
                            y = 5.dp)
                        .requiredSize(size = 25.dp))
                Text(
                    modifier = Modifier
                        .align(alignment = Alignment.TopStart)
                        .offset(x = 10.dp,
                            y = 0.dp).width(50.dp)
                        .height(20.dp),
                    text = "Buy 1",
                    style = TextStyle(
                        fontSize = 10.sp,
                        fontWeight = FontWeight(400),
                        color = Color(0xFF000000),
                        )
                )
                Text(modifier = Modifier
                    .align(alignment = Alignment.TopStart)
                    .offset(x = 10.dp,
                        y = 15.dp)
                        .width(70.dp)
                    .height(13.dp),
                    text = "123.31k",
                    style = TextStyle(
                        fontSize = 9.5.sp,
                        fontWeight = FontWeight(400),
                        color = Color(0xFF000000),
                    )

                )
            }
        }
        Box(
            modifier = Modifier
                .align(alignment = Alignment.TopStart)
                .offset(x = 10.dp,
                    y = 13.dp)
        ) {
            Image(
                modifier = Modifier
                    .align(alignment = Alignment.Center)
                    .offset(x = 0.dp,
                        y = 0.dp)
                    .size(64.dp),
                painter = painterResource(id = R.drawable.rectangle_8),
                contentDescription = "image description",
            )
            Image(
                painter = painterResource(id = R.drawable.photo),
                contentDescription = "photo",
                modifier = Modifier
                    .align(alignment = Alignment.Center)
                    .offset(x = 0.dp,
                        y = 0.dp)
                    .requiredSize(size = 50.dp))
        }
    }
}

@Preview(widthDp = 350, heightDp = 90)
@Composable
private fun WeaponPreview() {
    Weapon(Modifier)
}