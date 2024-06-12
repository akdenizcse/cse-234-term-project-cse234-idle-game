import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.idlegame.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import com.example.idlegame.gembuy.pressStart2P
import androidx.compose.ui.text.TextStyle
import com.example.idlegame.game.PlayerViewModel

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

class EnemyViewModel : ViewModel() {
    val lifeState = mutableStateOf(LifeState.ALIVE)
    val slimeEnemy = Enemy(
        name = "Slime",
        health = 1,
        icon = { enemy: Enemy ->
            val imageResource by enemy.imageResource
            Image(painterResource(id = imageResource), contentDescription = "Slime picture",
                modifier = Modifier.size(96.dp))
        },
        imageResource = mutableStateOf(R.drawable.slimeidle1),
        lifeState = lifeState
    )

    fun resetEnemyState() {
        if (lifeState.value == LifeState.DEAD) {
            lifeState.value = LifeState.ALIVE
            slimeEnemy.health = slimeEnemy.health // Reset health or any other properties as needed
        }
    }
}

@Composable
fun ProgressBar(currentHealth: Int, maxHealth: Int) {
    val progress = currentHealth.toFloat() / maxHealth.toFloat()
    Box(modifier = Modifier
        .fillMaxWidth()
        .padding(4.dp)) {
        LinearProgressIndicator(
            progress = progress,
            modifier = Modifier
                .fillMaxWidth()
                .height(24.dp)
        )
        Text(
            text = "$currentHealth/$maxHealth",
            style = TextStyle(fontFamily = pressStart2P, color = Color.White),
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Composable
fun CustomProgressBar(currentHealth: Int, maxHealth: Int) {
    val progress = currentHealth.toFloat() / maxHealth.toFloat()

    Box(modifier = Modifier
        .width(100.dp)
        .height(30.dp)
        .padding(4.dp)) {
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
            modifier = Modifier.align(Alignment.BottomCenter),
            style = TextStyle(fontFamily = pressStart2P, color = Color.White),
            color = Color.White
        )
    }
}

@Composable
fun EnemyView(enemyViewModel: EnemyViewModel, playerViewModel: PlayerViewModel,health: Int=enemyViewModel.slimeEnemy.health) {
    var state = remember { mutableStateOf(enemyViewModel.slimeEnemy.health) }
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
    LaunchedEffect(enemyViewModel.lifeState.value) {
        if (enemyViewModel.lifeState.value == LifeState.DEAD) {
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
    if (enemyViewModel.lifeState.value == LifeState.ALIVE) {
        enemyViewModel.slimeEnemy.imageResource.value = drawableResources[frameIndex.toInt()]
    } else if (enemyViewModel.lifeState.value == LifeState.DEAD) {
        enemyViewModel.slimeEnemy.imageResource.value = deathDrawableResources[deathFrameIndex.toInt()]
    }

    DisposableEffect(Unit) {
        val listener: (EnemyHitEvent) -> Unit = { event ->
            if (event.enemy == enemyViewModel.slimeEnemy) {
                state.value = Math.max(0, state.value - event.damage)
                if (state.value == 0 && enemyViewModel.lifeState.value == LifeState.ALIVE) {
                    enemyViewModel.lifeState.value = LifeState.DEAD
                    enemyViewModel.slimeEnemy.imageResource.value = R.drawable.slimedeath1
                    // Reward the player with 100 seconds worth of money
                    playerViewModel.earnMoneyForSeconds(100)
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
        Text(text = enemyViewModel.slimeEnemy.name, style = TextStyle(fontFamily = pressStart2P, color = Color.White))
        CustomProgressBar(currentHealth = state.value, maxHealth = enemyViewModel.slimeEnemy.health)
        Box(
            modifier = Modifier
                .clickable {
                    if (enemyViewModel.lifeState.value == LifeState.ALIVE) {
                        damageEnemy(enemyViewModel.slimeEnemy, 1)
                        scope.launch {
                            if (enemyViewModel.lifeState.value == LifeState.DEAD) {
                                if (!enemyViewModel.slimeEnemy.isRespawning.value) {
                                    enemyViewModel.slimeEnemy.isRespawning.value = true
                                    delay(1000)
                                    enemyViewModel.lifeState.value = LifeState.RESPAWNING
                                    enemyViewModel.slimeEnemy.imageResource.value =
                                        R.drawable.slimedeath4 // death animation
                                    if (enemyViewModel.lifeState.value == LifeState.RESPAWNING) {
                                        state.value = enemyViewModel.slimeEnemy.health + 2
                                        enemyViewModel.slimeEnemy.health = state.value
                                        enemyViewModel.slimeEnemy.imageResource.value =
                                            drawableResources[frameIndex.toInt()] // back to idle animation
                                        enemyViewModel.lifeState.value = LifeState.ALIVE
                                        enemyViewModel.slimeEnemy.isRespawning.value = false
                                    }
                                }
                            }
                        }
                    }
                }
                .wrapContentSize()
        ) {
            enemyViewModel.slimeEnemy.icon(enemyViewModel.slimeEnemy)
        }
    }
}


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




