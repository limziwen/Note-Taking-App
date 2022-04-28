package Primitives

import javafx.scene.paint.Color
import javafx.scene.text.Font
import javafx.scene.text.FontPosture
import javafx.scene.text.FontWeight

enum class Styling {
    H1, H2, H3, H4, H5, P, SMALL
}

class Sizes() {
    val paragraph = 14.0
    val h1 = 50.0
    val h2 = 38.0
    val h3 = 28.0
    val h4 = 21.0
    val h5 = 14.0
    val small = 12.0
}

class TextStyles(styling: Styling) {
    var name = "Roboto"
    var font = Font(name, Sizes().paragraph)

    init{
        when(styling){
            Styling.H1 -> font = Font.font("Playfair Display", FontWeight.MEDIUM, FontPosture.REGULAR, Sizes().h1)
            Styling.H2 -> font = Font.font("IBM Plex Sans", FontWeight.MEDIUM, FontPosture.REGULAR, Sizes().h2)
            Styling.H3 -> font = Font.font("IBM Plex Sans", FontWeight.NORMAL, FontPosture.REGULAR, Sizes().h3)
            Styling.H4 -> font = Font.font("IBM Plex Sans", FontWeight.NORMAL, FontPosture.REGULAR, Sizes().h4)
            Styling.H5 -> font = Font.font("IBM Plex Mono", FontWeight.MEDIUM, FontPosture.REGULAR, Sizes().h5)
            Styling.P -> font = Font.font("IBM Plex Sans", FontWeight.NORMAL, FontPosture.REGULAR, Sizes().paragraph)
            Styling.SMALL -> font = Font.font("IBM Plex Mono", FontWeight.NORMAL, FontPosture.REGULAR, Sizes().small)
        }
    }
}
