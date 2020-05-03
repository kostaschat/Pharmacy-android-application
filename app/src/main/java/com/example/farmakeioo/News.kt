package com.example.farmakeioo

data class News(
    val author: String = " ",
    val category: String = " ",
    var date: String = " ",
    var description: String = " ",
    var link: String = " ",
    var raw_description: String = " ",
    var title: String = " ")
{constructor() : this("","","","","","","")}