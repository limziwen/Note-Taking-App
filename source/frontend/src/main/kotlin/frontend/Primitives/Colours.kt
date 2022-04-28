package frontend.Primitives

import javafx.scene.paint.Color
import javafx.scene.paint.Paint

class Colours() {
    fun hoverColour(colour: Color): Paint {
        return Color(colour.red, colour.green, colour.blue, 0.1)
    }

    val pastelPurple = Color.rgb(241, 235, 243, 0.95)
    val darkerPastelPurple = Color.rgb(208, 197, 219, 0.86)
    val slightlyDarkerPastelPurple = Color.rgb(219, 197, 218, 0.86)
    val hoverPastelPurple = Color.rgb(236, 224, 240, 1.0)

    val pastelPink = Color.rgb(249, 224, 236, 0.98)

    val darkPurple = Color.rgb(89, 59, 143, 0.56)

    val darkRed = Color.rgb(123, 33, 33)
    val lightRed = Color.rgb(123, 33, 33, 0.1)
/* // Greyscale
     val white = Color.WHITE
     val greyTint2 = Color.color(0.674, 0.71, 0.74)

     val defaultGrey = Color.color(33.0, 46.0, 41.0)

     // Reds
     val defaultRed = Color.color(240.0, 61.0, 62.0)

     // Blues
     val defaultBlue = Color.color(66.0, 99.0, 235.0)

     // Purples
     val defaultPurple = Color.color(112.0, 72.0, 232.0)

     // Pinks

     val pinkTint1 = Color.color(81.0, 207.0, 102.0)
     val defaultPink = Color.color(224.0, 89.0, 137.0)

     // Greens

     val defaultGreen = Color.color(0.0, 189.0, 98.0)*/

}