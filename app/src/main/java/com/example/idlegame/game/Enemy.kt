import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.idlegame.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import android.graphics.BitmapFactory
import android.graphics.Bitmap

enum class LifeState {
    ALIVE,
    DEAD,
    RESPAWNING
}

data class Enemy(
    val name: String,
    var health: Int,
    val icon: @Composable (Enemy) -> Unit,
    val imageResource: MutableState<Int>,
    val lifeState: MutableState<LifeState>,
    var isRespawning: MutableState<Boolean> = mutableStateOf(false)
)

val slimeEnemy = Enemy(
    name = "Slime",
    health = 1,
    icon = { enemy: Enemy ->
        val imageResource by enemy.imageResource
        Image(painterResource(id = imageResource), contentDescription = "Slime picture", modifier = Modifier.size(96.dp))
    },
    imageResource = mutableStateOf(R.drawable.slimeidle1), // Initial drawable
    lifeState = mutableStateOf(LifeState.ALIVE)
)

@Composable
fun ProgressBar(currentHealth: Int, maxHealth: Int) {
    val progress = currentHealth.toFloat() / maxHealth.toFloat()
    Box(modifier = Modifier.fillMaxWidth().padding(4.dp)) {
        LinearProgressIndicator(
            progress = progress,
            modifier = Modifier.fillMaxWidth().height(24.dp)
        )
        Text(
            text = "$currentHealth/$maxHealth",
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Composable
fun CustomProgressBar(currentHealth: Int, maxHealth: Int) {
    val progress = currentHealth.toFloat() / maxHealth.toFloat()

    Box(modifier = Modifier.width(100.dp).height(30.dp).padding(4.dp)) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            drawRoundRect(
                color = Color.LightGray,
                cornerRadius = CornerRadius(12f, 12f)
            )
            drawRoundRect(
                color = Color.Red,
                size = Size(size.width * progress, size.height),
                cornerRadius = CornerRadius(12f, 12f)
            )
        }
        Text(
            text = "$currentHealth",
            modifier = Modifier.align(Alignment.Center),
            color = Color.White
        )
    }
}

@Composable
fun Enemy(enemy: Enemy) {
    val state = remember { mutableStateOf(enemy.health) }
    val scope = rememberCoroutineScope()

    // Array of drawable resources
    val drawableResources = listOf(
        R.drawable.slimeidle1,
        R.drawable.slimeidle2,
        R.drawable.slimeidle3,
        R.drawable.slimeidle0
    )
    val deathDrawableResources = listOf(
        R.drawable.slimedeath1,
        R.drawable.slimedeath2,
        R.drawable.slimedeath3,
        R.drawable.slimedeath4,
    )

    val infiniteTransition = rememberInfiniteTransition()
    val frameIndex by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = (drawableResources.size - 1).toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(500, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )

    var deathFrameIndex by remember { mutableStateOf(0f) }
    LaunchedEffect(enemy.lifeState.value) {
        if (enemy.lifeState.value == LifeState.DEAD) {
            deathFrameIndex = 0f
            animate(
                initialValue = 0f,
                targetValue = (deathDrawableResources.size - 1).toFloat(),
                animationSpec = tween(500, easing = LinearEasing)
            ) { value, _ ->
                deathFrameIndex = value
            }
        }
    }

    // Update image resource based on animation frame
    if (enemy.lifeState.value == LifeState.ALIVE) {
        enemy.imageResource.value = drawableResources[frameIndex.toInt()]
    } else if (enemy.lifeState.value == LifeState.DEAD) {
        enemy.imageResource.value = deathDrawableResources[deathFrameIndex.toInt()]
    }

    DisposableEffect(Unit) {
        val listener: (EnemyHitEvent) -> Unit = { event ->
            if (event.enemy == enemy) {
                state.value = Math.max(0, state.value - event.damage)
                if (state.value == 0 && enemy.lifeState.value == LifeState.ALIVE) {
                    enemy.lifeState.value = LifeState.DEAD
                    enemy.imageResource.value = R.drawable.slimedeath1
                }
            }
        }
        EventBus.addListener(listener)
        onDispose { EventBus.removeListener(listener) }
    }

    Column(
        modifier = Modifier.padding(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = enemy.name, style = MaterialTheme.typography.bodyLarge)
        CustomProgressBar(currentHealth = state.value, maxHealth = enemy.health)
        Box(
            modifier = Modifier
                .clickable {
                    if (enemy.lifeState.value == LifeState.ALIVE) {
                        damageEnemy(enemy, 1)
                        scope.launch {
                            if (enemy.lifeState.value == LifeState.DEAD) {
                                if (!enemy.isRespawning.value) {
                                    enemy.isRespawning.value = true
                                    delay(2500)
                                    enemy.lifeState.value = LifeState.RESPAWNING
                                    enemy.imageResource.value = R.drawable.slimedeath4 // death animation
                                    if (enemy.lifeState.value == LifeState.RESPAWNING) {
                                        state.value = enemy.health + 2
                                        enemy.health = state.value
                                        enemy.imageResource.value = drawableResources[frameIndex.toInt()] // back to idle animation
                                        enemy.lifeState.value = LifeState.ALIVE
                                        enemy.isRespawning.value = false
                                    }
                                }
                            }
                        }
                    }
                }
                .wrapContentSize()
        ) {
            enemy.icon(enemy)
        }
    }
}
@Composable
fun Enemies(enemies: MutableList<Enemy>) {
    Column {
        enemies.forEach { enemy ->
            Enemy(enemy = enemy)
        }
    }
}

val enemyList = mutableStateOf(mutableListOf(slimeEnemy))

data class EnemyHitEvent(val enemy: Enemy, val damage: Int)

object EventBus {
    private val listeners = mutableListOf<(EnemyHitEvent) -> Unit>()

    fun addListener(listener: (EnemyHitEvent) -> Unit) {
        listeners.add(listener)
    }

    fun removeListener(listener: (EnemyHitEvent) -> Unit) {
        listeners.remove(listener)
    }

    fun triggerEvent(event: EnemyHitEvent) {
        listeners.forEach { it(event) }
    }
}

fun damageEnemy(enemy: Enemy, damage: Int) {
    if (enemy.lifeState.value == LifeState.DEAD || enemy.lifeState.value == LifeState.RESPAWNING) {
        return
    }
    val event = EnemyHitEvent(enemy, damage)
    EventBus.triggerEvent(event)
}



fun decodeSampledBitmapFromPath(path: String, reqWidth: Int, reqHeight: Int): Bitmap {
    // First decode with inJustDecodeBounds=true to check dimensions
    val options = BitmapFactory.Options().apply {
        inJustDecodeBounds = true
    }
    BitmapFactory.decodeFile(path, options)

    // Calculate inSampleSize
    options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight)

    // Decode bitmap with inSampleSize set
    options.inJustDecodeBounds = false
    return BitmapFactory.decodeFile(path, options)
}

fun calculateInSampleSize(options: BitmapFactory.Options, reqWidth: Int, reqHeight: Int): Int {
    // Raw height and width of image
    val (height: Int, width: Int) = options.run { outHeight to outWidth }
    var inSampleSize = 1

    if (height > reqHeight || width > reqWidth) {

        val halfHeight: Int = height / 2
        val halfWidth: Int = width / 2

        // Calculate the largest inSampleSize value that is a power of 2 and keeps both
        // height and width larger than the requested height and width.
        while (halfHeight / inSampleSize >= reqHeight && halfWidth / inSampleSize >= reqWidth) {
            inSampleSize *= 2
        }
    }

    return inSampleSize
}

