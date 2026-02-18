package com.example.practica1_compi1_ps26.domain.entities

abstract class Component(
    val generalId: Number,
    val specificId: Number,
    var textColor: String,
    var figureColor: String,
    var figureName: FigureType,
    var font: FontType,
    var fontSize: Number,
    val content: String
) {

    abstract fun returnFinalValue(): String

    abstract fun setDefaultValues()

    fun setTextColor(newTextColor: String) {
        this.textColor = newTextColor
    }

    fun setFigureColor(newFigureColor: String) {
        this.figureColor = newFigureColor
    }

    fun setFigureName(newFigureName: FigureType) {
        this.figureName = newFigureName
    }

    fun setFont(newFont: FontType) {
        this.font = newFont
    }

    fun setFontSize(newFontSize: Number) {
        this.fontSize = newFontSize
    }

}