package Primitives

import javafx.geometry.Insets
import javafx.scene.control.Label
import javafx.scene.layout.Background
import javafx.scene.layout.BackgroundFill
import javafx.scene.layout.CornerRadii
import javafx.scene.text.Font
import javafx.scene.paint.Color
import javafx.scene.text.Text

class TextComponent(var content: String = "This is sample text.", var style: Font = TextStyles(Styling.P).font, var colour: Color = Color.RED): Label() {

    init {
        text = content
        font = style
        textFill = colour
    }
}
