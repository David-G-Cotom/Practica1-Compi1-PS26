package com.example.practica1_compi1_ps26.domain.models

class Fin(
    general_id: Number,
    specific_id: Number,
    text_color: String = "fff",
    figure_color: String = "4caf50",
    figure_name: FigureType = FigureType.ELIPSE,
    font: FontType = FontType.TIMES_NEW_ROMAN,
    font_size: Number = 18,
    content: String
): Component(
    general_id,
    specific_id,
    text_color,
    figure_color,
    figure_name,
    font,
    font_size,
    content
) {

    override fun returnFinalValue(): String {
        return "Fin"
    }

    override fun setDefaultValues() {

    }

    override fun setTextColor() {

    }

    override fun setColor() {

    }

    override fun setFigure() {

    }

    override fun setLetter() {

    }

    override fun setFontSize() {

    }

}