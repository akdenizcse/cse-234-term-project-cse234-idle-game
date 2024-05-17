package com.example.idlegame.data

import androidx.annotation.DrawableRes

data class UpgradeData(val title: String,
                       val material: String,
                       val description: String,
                       val price: String,
                       @DrawableRes val weaponPicture: Int)
