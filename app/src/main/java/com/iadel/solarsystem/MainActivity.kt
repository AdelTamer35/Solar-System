package com.iadel.solarsystem

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.iadel.solarsystem.ui.theme.SolarSystemTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
//            SolarSystemTheme {
//                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
//
//                }
//            }
        }
    }
}

@Composable
fun EarthIntroScreen(modifier: Modifier = Modifier) {

}

@Composable
private fun EarthHeader() {
    Column(
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = "Earth",
            style = TextStyle(
                fontSize = 64.sp,
                fontFamily = FontFamily(Font(R.font.rubik_bold)),
                fontWeight = FontWeight(700),
                color = Color(0xE0FFFFFF),
                textAlign = TextAlign.Center,
                letterSpacing = 0.25.sp,
            )
        )

        Text(
            text = "A tiny blue world drifting through the endless dark.",
            style = TextStyle(
                fontSize = 16.sp,
                fontFamily = FontFamily(Font(R.font.lily_script_one_regular)),
                fontWeight = FontWeight(400),
                color = Color(0xCCFFFFFF),
                textAlign = TextAlign.Center,
                letterSpacing = 0.25.sp,
            )
        )
    }
}

@Composable
private fun SwipeUpTip() {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        repeat(3) {
            Icon(
                painter = painterResource(id = R.drawable.ic_arrow_up),
                contentDescription = "Swipe Up",
                modifier = Modifier.size(24.dp),
                tint = Color.Unspecified
            )
        }
        Text(
            text = "Swipe up to explore",
            style = TextStyle(
                fontSize = 16.sp,
                fontFamily = FontFamily(Font(R.font.rubik_medium)),
                fontWeight = FontWeight(500),
                color = Color(0xFFFFFFFF),
                textAlign = TextAlign.Center,
                letterSpacing = 0.25.sp,
            ),
            modifier = Modifier.padding(top = 10.dp)
        )
    }
}

@Composable
private fun SolarSystemHeader(modifier: Modifier = Modifier) {
    Column(
        verticalArrangement = Arrangement.spacedBy(4.dp, Alignment.Top),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "Our Solar System",
            style = TextStyle(
                fontSize = 24.sp,
                fontFamily = FontFamily(Font(R.font.rubik_bold)),
                fontWeight = FontWeight(700),
                color = Color(0xE0FFFFFF),
                textAlign = TextAlign.Center,
                letterSpacing = 0.25.sp,
            )
        )
        Text(
            text = "Earth is only one small part of a much larger story.",
            style = TextStyle(
                fontSize = 16.sp,
                fontFamily = FontFamily(Font(R.font.lily_script_one_regular)),
                fontWeight = FontWeight(400),
                color = Color(0xCCFFFFFF),
                textAlign = TextAlign.Center,
                letterSpacing = 0.25.sp,
            )
        )
    }
}

@Composable
private fun PlanetInfoItem(
    planetInfo: PlanetInfo,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.height(258.dp)
    ) {
        Column(
            Modifier
                .padding(top = 16.dp)
                .fillMaxHeight()
                .border(
                    width = 0.5.dp,
                    color = Color(0xFF2F2E2E),
                    shape = RoundedCornerShape(size = 20.dp)
                )
                .fillMaxWidth()
                .background(color = Color(0xCC0B1223), shape = RoundedCornerShape(size = 20.dp)),
        ) {
            PlanetInfoSection(
                weight = planetInfo.weight,
                dayLength = planetInfo.dayLength,
                temperature = planetInfo.temperature,
                additionalInfo = planetInfo.additionalInfo,
                modifier = Modifier.padding(
                    top = 112.dp,
                    start = 16.dp,
                    end = 16.dp,
                )
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = planetInfo.planetImageRes),
                contentDescription = "Planet",
                modifier = Modifier
                    .padding(start = 16.dp)
                    .size(width = 112.dp, height = 104.dp)
            )

            PlanetHeader(
                name = planetInfo.name,
                tagline = planetInfo.tagline
            )
        }
    }
}

@Composable
private fun PlanetInfoSection(
    weight: String,
    dayLength: String,
    temperature: String,
    additionalInfo: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            PlanetInfo(
                iconRes = R.drawable.ic_weight_scale,
                title = "You Would Weigh",
                info = weight
            )
            VerticalDivider(
                modifier = Modifier.height(30.dp),
                thickness = 1.dp,
                color = Color(0xFF2F2E2E)
            )
            PlanetInfo(
                iconRes = R.drawable.ic_sun,
                title = "One Day",
                info = dayLength
            )
        }

        HorizontalDivider(
            modifier = Modifier.padding(vertical = 16.dp),
            thickness = 1.dp,
            color = Color(0xFF2F2E2E)
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            PlanetInfo(
                iconRes = R.drawable.ic_temperature,
                title = "Temperature",
                info = temperature
            )
            VerticalDivider(
                modifier = Modifier.height(30.dp),
                thickness = 1.dp,
                color = Color(0xFF2F2E2E)
            )
            PlanetInfo(
                iconRes = R.drawable.ic_alert,
                title = "Additional info",
                info = additionalInfo
            )
        }

    }
}

@Composable
private fun PlanetHeader(
    name: String,
    tagline: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(6.dp),
    ) {
        Text(
            text = name,
            style = TextStyle(
                fontSize = 18.sp,
                fontFamily = FontFamily(Font(R.font.rubik_bold)),
                fontWeight = FontWeight(700),
                color = Color(0xE0FFFFFF),
                letterSpacing = 0.25.sp,
            )
        )
        Text(
            text = tagline,
            style = TextStyle(
                fontSize = 14.sp,
                fontFamily = FontFamily(Font(R.font.rubik_regular)),
                fontWeight = FontWeight(400),
                color = Color(0xA8FFFFFF),
                letterSpacing = 0.25.sp,
            )
        )
    }
}

@Composable
private fun PlanetInfo(
    @DrawableRes iconRes: Int,
    title: String,
    info: String,
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = iconRes),
            contentDescription = "Icon",
            modifier = Modifier.size(20.dp),
            tint = Color.Unspecified
        )

        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = title,
                style = TextStyle(
                    fontSize = 12.sp,
                    fontFamily = FontFamily(Font(R.font.rubik_regular)),
                    fontWeight = FontWeight(400),
                    color = Color(0xA8FFFFFF),
                    letterSpacing = 0.25.sp,
                )
            )

            Text(
                text = info,
                style = TextStyle(
                    fontSize = 12.sp,
                    fontFamily = FontFamily(Font(R.font.rubik_medium)),
                    fontWeight = FontWeight(500),
                    color = Color(0xE0FFFFFF),
                    letterSpacing = 0.25.sp,
                )
            )
        }
    }
}

data class PlanetInfo(
    val name: String,
    val tagline: String,
    val weight: String,
    val dayLength: String,
    val temperature: String,
    val additionalInfo: String,
    @DrawableRes val planetImageRes: Int,
)

private val planets = listOf(
    PlanetInfo(
        "Saturn",
        "The Ring Master",
        "70kg → 74kg",
        "10.7 Hours",
        "-178°C, Bring a jacket",
        "Lighter than water",
        R.drawable.img_saturn
    ),
    PlanetInfo(
        "Mars",
        "The next colony",
        "70kg → 27kg",
        "24.6 Hours",
        "-65°C, Bring a jacket",
        "Red Dust Storms",
        R.drawable.img_mars
    ),
    PlanetInfo(
        "Mercury",
        "The Fastest Planet",
        "70kg → 26kg",
        "1,408 Hours",
        "167°C, Very hot!",
        "Birthday every 88 days",
        R.drawable.img_mercury
    ),
    PlanetInfo(
        "Venus",
        "The Toxic Beauty",
        "70kg → 63kg",
        "243 Days",
        "464°C, Run away!",
        "Hotter than Mercury",
        R.drawable.img_venus
    ),
    PlanetInfo(
        "Jupiter",
        "The Gas Giant",
        "70kg → 177kg",
        "9.9 Hours",
        "-110°C, Bring a jacket",
        "Largest planet",
        R.drawable.img_jupiter
    ),
    PlanetInfo(
        "Uranus",
        "The Ice Giant",
        "70kg → 63kg",
        "17.2 Hours",
        "-195°C, Bring 2 jackets",
        "Rotates on its side",
        R.drawable.img_uranus
    ),
    PlanetInfo(
        "Neptune",
        "The Windy World",
        "70kg → 79kg",
        "16 Hours",
        "-214°C, Bring 3 jackets",
        "Wind faster than Sound",
        R.drawable.img_neptune
    ),
)

@Preview
@Composable
fun Preview(modifier: Modifier = Modifier) {
    SolarSystemTheme {
        EarthHeader()
    }
}