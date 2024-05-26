package com.example.idlegame.screen

//enum class Screen {
//    WeaponsTab,
//    StoreTab,
//    UpgradesTab,
//}
sealed class Screen(val route: String) {
    object WeaponsTab : Screen("weaponsTab")
    object StoreTab : Screen("storeTab")
    object UpgradesTab : Screen("upgradesTab")
}