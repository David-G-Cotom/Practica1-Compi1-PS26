package com.example.practica1_compi1_ps26.domain

import com.example.practica1_compi1_ps26.domain.entities.Bloque
import com.example.practica1_compi1_ps26.domain.entities.CodeType
import com.example.practica1_compi1_ps26.domain.entities.Component
import com.example.practica1_compi1_ps26.domain.entities.FigureType
import com.example.practica1_compi1_ps26.domain.entities.FontType
import com.example.practica1_compi1_ps26.domain.entities.Mientras
import com.example.practica1_compi1_ps26.domain.entities.Si

class Interpreter(var components: ArrayList<Component>) {

    fun setDefault(index: Int) {
        for (component in this.components) {
            if (component.generalId == index) {
                component.setDefaultValues()
                break
            }
        }
    }

    fun setColorTexto(index: Int, color: String, code: CodeType) {
        var newColor: String = color
        if (color.contains(",")) {
            newColor = convertToHexadecimal(color)
        }
        for (component in this.components) {
            if (code == CodeType.SI && component is Si) {
                if (component.specificId == index) {
                    component.textColor = newColor
                    break
                }
            } else if (code == CodeType.MIENTRAS && component is Mientras) {
                if (component.specificId == index) {
                    component.textColor = newColor
                    break
                }
            } else if (code == CodeType.BLOQUE && component is Bloque) {
                if (component.specificId == index) {
                    component.textColor = newColor
                    break
                }
            }
        }
    }

    fun setColor(index: Int, color: String, code: CodeType) {
        var newColor: String = color
        if (color.contains(",")) {
            newColor = convertToHexadecimal(color)
        }
        for (component in this.components) {
            if (code == CodeType.SI && component is Si) {
                if (component.specificId == index) {
                    component.figureColor = newColor
                    break
                }
            } else if (code == CodeType.MIENTRAS && component is Mientras) {
                if (component.specificId == index) {
                    component.figureColor = newColor
                    break
                }
            } else if (code == CodeType.BLOQUE && component is Bloque) {
                if (component.specificId == index) {
                    component.figureColor = newColor
                    break
                }
            }
        }
    }

    fun convertToHexadecimal(colorRGB: String): String {
        val colorRGBArray = colorRGB.split(",")

        var red = colorRGBArray[0].toInt()
        if (red > 255) {
            red = 255
        } else if (red < 0) {
            red = 0
        }
        var green = colorRGBArray[1].toInt()
        if (green > 255) {
            green = 255
        } else if (green < 0) {
            green = 0
        }
        var blue = colorRGBArray[2].toInt()
        if (blue > 255) {
            blue = 255
        } else if (blue < 0) {
            blue = 0
        }

        val redHex = Integer.toHexString(red)
        val greenHex = Integer.toHexString(green)
        val blueHex = Integer.toHexString(blue)

        return redHex + greenHex + blueHex
    }

    fun setFigura(index: Int, figure: FigureType, code: CodeType) {
        for (component in this.components) {
            if (code == CodeType.SI && component is Si) {
                if (component.specificId == index) {
                    component.figureName = figure
                    break
                }
            } else if (code == CodeType.MIENTRAS && component is Mientras) {
                if (component.specificId == index) {
                    component.figureName = figure
                    break
                }
            } else if (code == CodeType.BLOQUE && component is Bloque) {
                if (component.specificId == index) {
                    component.figureName = figure
                    break
                }
            }
        }
    }

    fun setLetra(index: Int, font: FontType, code: CodeType) {
        for (component in this.components) {
            if (code == CodeType.SI && component is Si) {
                if (component.specificId == index) {
                    component.font = font
                    break
                }
            } else if (code == CodeType.MIENTRAS && component is Mientras) {
                if (component.specificId == index) {
                    component.font = font
                    break
                }
            } else if (code == CodeType.BLOQUE && component is Bloque) {
                if (component.specificId == index) {
                    component.font = font
                    break
                }
            }
        }
    }

    fun setLetraSize(index: Int, size: Double, code: CodeType) {
        for (component in this.components) {
            if (code == CodeType.SI && component is Si) {
                if (component.specificId == index) {
                    component.fontSize = size
                    break
                }
            } else if (code == CodeType.MIENTRAS && component is Mientras) {
                if (component.specificId == index) {
                    component.fontSize = size
                    break
                }
            } else if (code == CodeType.BLOQUE && component is Bloque) {
                if (component.specificId == index) {
                    component.fontSize = size
                    break
                }
            }
        }
    }

    fun generateDOT(): String {
        var dot = "digraph G {\nnode [style=filled]\n"
        var previus2ID = ""
        var previousID = ""
        var currentID: String
        var withinSiFlag = false
        var assembleSiFlag = false
        var withinMientrasFlag = false
        var assembleMientrasFlag = false
        for (component in this.components) {
            val id: String = component::class.simpleName + component.generalId.toString()
            dot += id + " " +
                    "[shape=" + component.figureName.value + ", " +
                    "label=\"" + component.content + "\", color=\"#" + component.figureColor + "\", " +
                    "fontcolor=\"#" + component.textColor + "\", fontname=\"" + component.font.value + "\", " +
                    "fontsize=" + component.fontSize.toString() + "];\n"
            if (previousID == "") {
                previousID = id
                continue
            }

            currentID = id

            if (withinSiFlag) {
                dot += previousID + " -> " + currentID + " [label=\"Si\"];\n"
                previousID = currentID
                withinSiFlag = false
                assembleSiFlag = true
                continue
            }
            if (withinMientrasFlag) {
                dot += previousID + " -> " + currentID + " [label=\"Mientras Si\"];\n"
                previousID = currentID
                withinMientrasFlag = false
                assembleMientrasFlag = true
                continue
            }
            if (assembleMientrasFlag) {
                dot += previus2ID + " -> " + currentID + " [label=\"No\"];\n"
                dot += previousID + " -> " + previus2ID + ";\n"
                assembleMientrasFlag = false
                previousID = currentID
                if (component is Si) {
                    withinSiFlag = true
                    previus2ID = currentID
                    continue
                }
                continue
            }

            dot += previousID + " -> " + currentID + ";\n"

            if (assembleSiFlag) {
                dot += previus2ID + " -> " + currentID + " [label=\"No\"];\n"
                assembleSiFlag = false
            }

            previousID = currentID

            if (component is Si) {
                withinSiFlag = true
                previus2ID = currentID
                continue
            }
            if (component is Mientras) {
                withinMientrasFlag = true
                previus2ID = currentID
                continue
            }
        }
        dot += "}"
        return dot
    }

}