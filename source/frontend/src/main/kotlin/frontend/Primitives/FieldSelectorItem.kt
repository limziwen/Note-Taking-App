package frontend.Primitives

import Primitives.Styling
import Primitives.TextStyles
import javafx.geometry.Insets
import javafx.scene.control.Label
import javafx.scene.input.MouseEvent
import javafx.scene.layout.*
import javafx.scene.paint.Color
import javafx.scene.text.Font
import javafx.scene.text.FontPosture
import javafx.scene.text.Text

class FieldSelectorItem(val name: String, var style: Styling = Styling.P): HBox()  {

    init {
        val holder = TextStyles(style).font
        var style = Font.font(holder.family, FontPosture.REGULAR, 14.0)
        prefWidth = 100.0
        maxWidth = 100.0
        prefHeight = 25.0
        maxHeight = 25.0
        minHeight = 25.0
        val label = Text(name)
        label.font = style
        children.add(label)
        HBox.setMargin(label, Insets(4.0))
        val colour = Colours().pastelPurple
        background = Background(BackgroundFill(colour, CornerRadii.EMPTY, Insets.EMPTY))
        this.addEventFilter(MouseEvent.MOUSE_MOVED, { background = Background(BackgroundFill(colour.darker(), CornerRadii.EMPTY, Insets.EMPTY)) })
        this.addEventFilter(MouseEvent.MOUSE_EXITED, { background = Background(BackgroundFill(colour, CornerRadii.EMPTY, Insets.EMPTY)) })
    }
}