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

data class Enemy(val name: String, var health: Int, val icon: @Composable (Enemy) -> Unit, val imageResource: MutableState<Int>, val isDead: MutableState<Boolean>)

val slimeEnemy = Enemy(
    name = "Slime",
    health = 1,
    icon = { enemy: Enemy ->
        val imageResource by enemy.imageResource
        Image(painterResource(id = imageResource), contentDescription = "Slime Icon", Modifier.size(64.dp))
    },
    imageResource = mutableStateOf(R.drawable.slime),
    isDead = mutableStateOf(false)

)
val slimeEnemy2 = Enemy(
    name = "Slime 2",
    health = 30,
    icon = { enemy: Enemy ->
        val imageResource by enemy.imageResource
        Image(painterResource(id = imageResource), contentDescription = "Slime Icon", Modifier.size(64.dp))
    },
    imageResource = mutableStateOf(R.drawable.slime),
    isDead = mutableStateOf(false)

)

@Composable
fun Enemy(enemy: Enemy) {
    val state = remember { mutableStateOf(enemy.health) }  // Local state for enemy health
    val scope = rememberCoroutineScope()

    DisposableEffect(Unit) {
        val listener: (EnemyHitEvent) -> Unit = { event ->
            if (event.enemy == enemy) {
                state.value = Math.max(0, state.value - event.damage)
                if (state.value == 0) {
                    enemy.isDead.value = true
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
        Box(modifier = Modifier.clickable {
            if (!enemy.isDead.value) {
                damageEnemy(enemy, 1)  // Add clickable modifier to the icon
                scope.launch {
                    enemy.imageResource.value = R.drawable.slime_hit
                    delay(500)  // wait for 500ms
                    if (!enemy.isDead.value) {
                        enemy.imageResource.value = R.drawable.slime
                    }
                    else {
                        enemy.imageResource.value = R.drawable.slime_dead
                        delay(2000)
                        enemy.imageResource.value = R.drawable.slime
                        state.value = enemy.health + 10
                        enemy.health += 10
                        enemy.isDead.value = false
                    }
                }
            }
        }.wrapContentSize()) {
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
    val event = EnemyHitEvent(enemy, damage)
    EventBus.triggerEvent(event)
    }


