package com.example.practica1_compi1_ps26.domain.entities

abstract class Component(
    val generalId: Int,
    val specificId: Int,
    val content: String,
    var textColor: String,
    var figureColor: String,
    var figureName: FigureType,
    var font: FontType,
    var fontSize: Double
) {

    abstract fun returnFinalValue(): String

    abstract fun setDefaultValues()

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