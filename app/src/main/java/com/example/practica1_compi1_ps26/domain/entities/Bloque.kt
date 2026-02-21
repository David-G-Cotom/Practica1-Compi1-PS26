package com.example.practica1_compi1_ps26.domain.entities

class Bloque(
    generalId: Int,
    specificId: Int,
    content: String,
    textColor: String = "000",
    figureColor: String = "ffc107",
    figureName: FigureType = FigureType.PARALELOGRAMO,
    font: FontType = FontType.TIMES_NEW_ROMAN,
    fontSize: Double = 14.0
) : Component(
    generalId,
    specificId,
    content,
    textColor,
    figureColor,
    figureName,
    font,
    fontSize
) {

    override fun setDefaultValues() {
        this.textColor = "000"
        this.figureColor = "ffc107"
        this.figureName = FigureType.PARALELOGRAMO
        this.font = FontType.TIMES_NEW_ROMAN
        this.fontSize = 14.0
    }

}