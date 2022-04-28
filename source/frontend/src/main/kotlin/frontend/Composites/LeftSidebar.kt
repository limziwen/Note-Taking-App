package frontend.Composites

import Primitives.Sizes
import Primitives.Styling
import Primitives.TextStyles
import frontend.Primitives.Buttons
import frontend.Primitives.Colours
import javafx.event.ActionEvent
import javafx.event.EventHandler
import javafx.geometry.Insets
import javafx.scene.control.Label
import javafx.scene.layout.*
import javafx.scene.paint.Color
import javafx.scene.text.Font
import javafx.scene.text.FontPosture
import javafx.scene.text.FontWeight
import javafx.scene.text.Text
import logic.Director

class LeftSidebar(director: Director): BorderPane() {
    val title = Label("Categories")
    val style = TextStyles(Styling.H1).font
    val menu = TagMenu(director)
    val container = VBox(8.0)

    init {
        prefWidth = 300.0
        maxWidth = 300.0
        minWidth = 300.0
        title.font = Font.font(style.family, FontWeight.MEDIUM, FontPosture.REGULAR, Sizes().h4)
        title.padding = Insets(0.0, 0.0, 0.0, 16.0)
        container.children.addAll(title, menu)
        setMargin(container, Insets(100.0, 0.0, 0.0, 0.0))
        center = container
        background = Background(BackgroundFill(Colours().pastelPurple.brighter(), CornerRadii.EMPTY, Insets.EMPTY))
    }
}