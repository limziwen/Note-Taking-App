package frontend.Primitives

import Primitives.Styling
import Primitives.TextStyles
import javafx.geometry.Insets
import javafx.scene.control.Label
import javafx.scene.input.MouseEvent
import javafx.scene.layout.*
import javafx.scene.paint.Color

class Tags(tag: String, colour: Color): BorderPane() {

    val hoverColour = colour.darker()
    val label = Label(tag)

    init {
        background = Background(BackgroundFill(colour, CornerRadii(20.0), Insets.EMPTY))
        center = label
        label.font = TextStyles(Styling.SMALL).font
        label.textFill = Color.WHITE
        prefWidth = label.text.length*7.0 + 12.0
        prefHeight = 25.0
        maxHeight = 25.0
        minHeight = 25.0

        HBox.setHgrow(this, Priority.NEVER)

        this.addEventFilter(MouseEvent.MOUSE_MOVED, { background = Background(BackgroundFill(hoverColour, CornerRadii(20.0), Insets.EMPTY)) })
        this.addEventFilter(MouseEvent.MOUSE_EXITED, { background = Background(BackgroundFill(colour, CornerRadii(20.0), Insets.EMPTY)) })

    }
}