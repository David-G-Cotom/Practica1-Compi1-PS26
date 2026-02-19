package com.example.practica1_compi1_ps26.domain.entities

class Si(
    generalId: Number,
    specificId: Number,
    content: String,
    blockContent: Bloque,
    textColor: String = "fff",
    figureColor: String = "f80",
    figureName: FigureType = FigureType.ROMBO,
    font: FontType = FontType.TIMES_NEW_ROMAN,
    fontSize: Number = 14
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
        return "Si"
    }

    override fun setDefaultValues() {
        this.textColor = "fff"
        this.figureColor = "f80"
        this.figureName = FigureType.ROMBO
        this.font = FontType.TIMES_NEW_ROMAN
        this.fontSize = 14
    }

}