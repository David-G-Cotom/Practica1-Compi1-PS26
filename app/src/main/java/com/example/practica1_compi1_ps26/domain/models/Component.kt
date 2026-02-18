package com.example.practica1_compi1_ps26.domain.models

abstract class Component(
    val general_id: Number,
    val specific_id: Number,
    var text_color: String,
    var figure_color: String,
    var figure_name: FigureType,
    var font: FontType,
    var font_size: Number,
    val content: String
) {

    abstract fun returnFinalValue(): String

    abstract fun setDefaultValues()

    abstract fun setTextColor()

    abstract fun setColor()

    abstract fun setFigure()

    abstract fun setLetter()

    abstract fun setFontSize()

}