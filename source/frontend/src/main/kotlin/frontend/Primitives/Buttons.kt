package frontend.Primitives

import javafx.geometry.Insets
import javafx.scene.control.Button
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.input.MouseEvent
import javafx.scene.layout.*
import javafx.scene.paint.Color

class Buttons(iconPath: String, text: String, colour: Color): Button(text){

    val img = Image(iconPath)
    val imgV = ImageView(img)
    val rad = CornerRadii(12.0)

    init {
        background = Background(BackgroundFill( Color.TRANSPARENT, rad, Insets.EMPTY))
        prefWidth = 120.0
        minWidth = 120.0
        prefHeight = 10.0
        graphic = imgV
        padding = Insets(8.0)
        

        val hoverColor = Colours().hoverColour(colour)

        border = Border(BorderStroke(colour, BorderStrokeStyle.SOLID, rad, BorderWidths(1.0), Insets.EMPTY))
        this.addEventFilter(MouseEvent.MOUSE_MOVED, { background = Background(BackgroundFill(hoverColor, rad, Insets.EMPTY))
            border = Border(BorderStroke(colour.darker(), BorderStrokeStyle.SOLID, rad, BorderWidths(1.0), Insets.EMPTY))})
        this.addEventFilter(MouseEvent.MOUSE_EXITED, { background = Background(BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY))
            border = Border(BorderStroke(colour, BorderStrokeStyle.SOLID, rad, BorderWidths(1.0), Insets.EMPTY))})
    }
}