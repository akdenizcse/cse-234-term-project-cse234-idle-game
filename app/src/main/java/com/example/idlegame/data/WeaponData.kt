package com.example.idlegame.data

import androidx.annotation.DrawableRes

data class WeaponData(val title: String,
                      val level: String,
                      val income: String,
                      val price: String,
                      @DrawableRes val weaponPicture: Int)
