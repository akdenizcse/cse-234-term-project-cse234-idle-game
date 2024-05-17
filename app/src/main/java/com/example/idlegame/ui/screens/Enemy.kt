package com.example.idlegame.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.idlegame.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch



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
        Image(painterResource(id = imageResource), contentDescription = "Slime Icon", Modifier.size(64.dp))
    },
    imageResource = mutableStateOf(R.drawable.slime),
    lifeState = mutableStateOf(LifeState.ALIVE)
)

@Composable
fun Enemy(enemy: Enemy) {
    val state = remember { mutableStateOf(enemy.health) }
    val scope = rememberCoroutineScope()

    DisposableEffect(Unit) {
        val listener: (EnemyHitEvent) -> Unit = { event ->
            if (event.enemy == enemy) {
                state.value = Math.max(0, state.value - event.damage)
                if (state.value == 0 && enemy.lifeState.value == LifeState.ALIVE) {
                    enemy.lifeState.value = LifeState.DEAD
                    enemy.imageResource.value = R.drawable.slime_dead
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
        Text(text = state.value.toString(), textAlign = TextAlign.Center)
        Box(modifier = Modifier
            .clickable {
                if (enemy.lifeState.value == LifeState.ALIVE) {
                    damageEnemy(enemy, 1)
                    scope.launch {
                        if (enemy.lifeState.value != LifeState.DEAD) {
                            enemy.imageResource.value = R.drawable.slime_hit
                        }
                        delay(200)
                        if (enemy.lifeState.value != LifeState.DEAD) {
                            enemy.imageResource.value = R.drawable.slime
                        } else if (enemy.lifeState.value == LifeState.DEAD) {
                            if (!enemy.isRespawning.value) {
                                enemy.isRespawning.value = true
                                delay(2000)
                                enemy.lifeState.value = LifeState.RESPAWNING
                                enemy.imageResource.value = R.drawable.slime_dead
                                if (enemy.lifeState.value == LifeState.RESPAWNING) {
                                    state.value = enemy.health + 2
                                    enemy.health = state.value
                                    enemy.imageResource.value = R.drawable.slime
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


