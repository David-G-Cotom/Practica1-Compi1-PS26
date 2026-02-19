package com.example.practica1_compi1_ps26.domain.entities

abstract class Component(
    val generalId: Number,
    val specificId: Number,
    val content: String,
    var textColor: String,
    var figureColor: String,
    var figureName: FigureType,
    var font: FontType,
    var fontSize: Number
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

    override fun toString(): String {
        return "ID: " + this.generalId +
                "Figura: " + this.figureName.value +
                "ID Específico: " + this.specificId +
                "Color: " + this.figureColor +
                "Fuente: " + this.font.value +
                "Color Texto: " + this.textColor +
                "Tamaño: " + this.fontSize +
                "Contenido: " + this.content
    }

}