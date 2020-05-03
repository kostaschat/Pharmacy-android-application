package com.example.farmakeioo


data class Hospital(
    val address: String = " ",
    val image: String = " ",
    var latitude: String = " ",
    var longitude: String = " ",
    var name: String = " ",
    var phone: String = " ",
    var website: String = " ")
{constructor() : this("","","","","","","") }