package com.example.idlegame.screen

sealed class Screen(val route: String) {
    object WeaponsTab : Screen("weaponsTab")
    object StoreTab : Screen("storeTab")
    object UpgradesTab : Screen("upgradesTab")
}