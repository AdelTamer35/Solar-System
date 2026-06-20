package com.iadel.solarsystem

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.DrawableRes
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed // Updated to itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin // Added
import androidx.compose.ui.graphics.graphicsLayer // Added
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
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
            SolarSystemTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    EarthIntroScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun EarthIntroScreen(modifier: Modifier = Modifier) {
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp

    var isExploring by remember { mutableStateOf(false) }
    val listState = rememberLazyListState()

    // 1. Nested Scroll to detect swipe down when the planet list is at the very top
    val nestedScrollConnection = remember(isExploring) {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                if (isExploring && available.y > 0f) {
                    if (listState.firstVisibleItemIndex == 0 && listState.firstVisibleItemScrollOffset == 0) {
                        isExploring = false
                        return Offset(0f, available.y) // Consume gesture to animate back
                    }
                }
                return Offset.Zero
            }
        }
    }

    val transition = updateTransition(targetState = isExploring, label = "explore_transition")

    // --- EARTH CONFIGURATION (Matched perfectly to Figma dimensions) ---
    val earthSize by transition.animateDp(
        transitionSpec = { tween(durationMillis = 700, easing = FastOutSlowInEasing) },
        label = "earth_size"
    ) { exploring ->
        if (exploring) 260.dp else 644.dp
    }

    val startEarthY = screenHeight - 644.dp + 58.dp
    val earthOffsetY by transition.animateDp(
        transitionSpec = { tween(durationMillis = 700, easing = FastOutSlowInEasing) },
        label = "earth_y"
    ) { exploring ->
        if (exploring) 32.dp else startEarthY
    }

    // --- INTRO SCREEN ANIMATIONS ---
    val introAlpha by transition.animateFloat(
        transitionSpec = { tween(durationMillis = 350) },
        label = "intro_alpha"
    ) { exploring ->
        if (exploring) 0f else 1f
    }

    val introHeaderOffsetY by transition.animateDp(
        transitionSpec = { tween(durationMillis = 400) },
        label = "intro_header_y"
    ) { exploring ->
        if (exploring) (-60).dp else 56.dp
    }

    val tipOffsetY by transition.animateDp(
        transitionSpec = { tween(durationMillis = 400) },
        label = "tip_y"
    ) { exploring ->
        if (exploring) 60.dp else 0.dp
    }

    // --- EXPLORE/FIGMA SCREEN ANIMATIONS ---
    val exploreAlpha by transition.animateFloat(
        transitionSpec = { tween(durationMillis = 450, delayMillis = 200) },
        label = "explore_alpha"
    ) { exploring ->
        if (exploring) 1f else 0f
    }

    val exploreHeaderOffsetY by transition.animateDp(
        transitionSpec = { tween(durationMillis = 450, delayMillis = 200) },
        label = "explore_header_y"
    ) { exploring ->
        if (exploring) 64.dp else 20.dp
    }

    val listOffsetY by transition.animateDp(
        transitionSpec = {
            tween(
                durationMillis = 550,
                delayMillis = 200,
                easing = FastOutSlowInEasing
            )
        },
        label = "list_y"
    ) { exploring ->
        if (exploring) 0.dp else 300.dp
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        Color(0xFF020D3C),
                        Color(0xFF0F172A),
                        Color(0xFF060816),
                        Color(0xFF000000),
                    ),
                )
            )
            .windowInsetsPadding(WindowInsets.statusBars)
            .navigationBarsPadding()
            .systemBarsPadding()
            .nestedScroll(nestedScrollConnection)
            .pointerInput(isExploring) {
                detectVerticalDragGestures { _, dragAmount ->
                    if (!isExploring && dragAmount < -15) {
                        isExploring = true
                    }
                }
            },
    ) {
        // Space Background
        Image(
            painter = painterResource(R.drawable.img_stars),
            contentDescription = "Stars",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize(),
        )

        // Earth: Rendered FIRST so it sits layout-wise BEHIND the text elements
        Image(
            painter = painterResource(R.drawable.img_earth),
            contentDescription = "Earth",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .offset(y = earthOffsetY)
                .size(earthSize)
        )

        // STATE 1: Intro Text Content
        if (introAlpha > 0f) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                EarthHeader(
                    modifier = Modifier
                        .offset(y = introHeaderOffsetY)
                        .alpha(introAlpha)
                )
                Spacer(modifier = Modifier.weight(1f))
                SwipeUpTip(
                    modifier = Modifier
                        .padding(bottom = 20.dp)
                        .offset(y = tipOffsetY)
                        .alpha(introAlpha)
                )
            }
        }

        // STATE 2: Explore Content (Matches Figma perfectly)
        if (exploreAlpha > 0f) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Header overlays seamlessly on top of the background Earth sphere
                SolarSystemHeader(
                    modifier = Modifier
                        .padding(top = 96.dp)
                        .alpha(exploreAlpha)
                        // Moved pointerInput back here so it doesn't break list scrolling!
                        .pointerInput(Unit) {
                            detectVerticalDragGestures { _, dragAmount ->
                                if (dragAmount > 15) isExploring = false
                            }
                        }
                )

                // Perfect spacing breakdown to let the list cards dynamically overlap the lower edge of the globe
                Spacer(modifier = Modifier.height(174.dp)) // Adjusted for the header spacing

                // Planets Scrollable Section with 3D Wallet Stacking
                LazyColumn(
                    state = listState,
                    modifier = Modifier
                        .offset(y = listOffsetY)
                        .alpha(exploreAlpha)
                        .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    contentPadding = PaddingValues(bottom = 24.dp)
                ) {
                    itemsIndexed(planets) { index, planet ->
                        PlanetInfoItem(
                            planetInfo = planet,
                            modifier = Modifier.graphicsLayer {
                                // Defer layout reading to the draw phase so it doesn't cause recomposition
                                val layoutInfo = listState.layoutInfo
                                val itemInfo =
                                    layoutInfo.visibleItemsInfo.firstOrNull { it.index == index }

                                if (itemInfo != null) {
                                    val delta = itemInfo.offset.toFloat()

                                    // If offset is negative, the item is scrolling off the top boundary
                                    if (delta < 0f) {
                                        val fraction = -delta / itemInfo.size.toFloat()

                                        // Pushes the card slightly UP (30dp) relative to the one covering it so it peeks out
                                        val stagger = 30.dp.toPx()

                                        // Visually pin it to the top + stagger it backward
                                        translationY = -delta - (fraction * stagger)

                                        // Scale it down incrementally the further "deep" it gets stacked
                                        val scale = (1f - (fraction * 0.08f)).coerceAtLeast(0.7f)
                                        scaleX = scale
                                        scaleY = scale

                                        // Add a subtle depth shadow by lowering alpha
                                        alpha = (1f - (fraction * 0.2f)).coerceAtLeast(0.1f)

                                        // Pivot point at Top Center so scaling only shrinks sides and bottom
                                        transformOrigin = TransformOrigin(0.5f, 0f)
                                    } else {
                                        // Normal behavior for unpinned cards
                                        translationY = 0f
                                        scaleX = 1f
                                        scaleY = 1f
                                        alpha = 1f
                                    }
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun EarthHeader(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(4.dp),
        horizontalAlignment = Alignment.CenterHorizontally
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
private fun SwipeUpTip(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
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
        modifier = modifier,
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
        EarthIntroScreen()
    }
}