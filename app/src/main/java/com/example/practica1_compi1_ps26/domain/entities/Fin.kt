package com.example.practica1_compi1_ps26.domain.entities

class Fin(
    generalId: Number,
    specificId: Number,
    content: String,
    textColor: String = "fff",
    figureColor: String = "4caf50",
    figureName: FigureType = FigureType.ELIPSE,
    font: FontType = FontType.TIMES_NEW_ROMAN,
    fontSize: Number = 18
): Component(
    generalId,
    specificId,
    content,
    textColor,
    figureColor,
    figureName,
    font,
    fontSize
) {

    override fun returnFinalValue(): String {
        return "Fin"
    }

    override fun setDefaultValues() {
        this.textColor = "fff"
        this.figureColor = "4caf50"
        this.figureName = FigureType.ELIPSE
        this.font = FontType.TIMES_NEW_ROMAN
        this.fontSize = 18
    }

}