package frontend.Composites

import Primitives.Styling
import Primitives.TextStyles
import frontend.Primitives.Buttons
import frontend.Primitives.Colours
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.control.Label
import javafx.scene.layout.BorderPane
import javafx.scene.layout.HBox
import javafx.scene.text.Text

class WelcomeAndCreateBar(): BorderPane() {
    val title = Label("What's on your mind today?")
    val styling = TextStyles(Styling.H1)
    val newNoteButton = Buttons("Icons/add_purple.png", "Create", Colours().darkPurple)
    init {
        title.font = styling.font
        left = title
        right = newNoteButton
        title.padding = Insets(32.0, 32.0, 24.0, 64.0)
        BorderPane.setMargin(newNoteButton, Insets(32.0, 64.0, 24.0, 32.0))
        BorderPane.setAlignment(newNoteButton, Pos.BOTTOM_RIGHT)
        padding = Insets(100.0, 0.0, 0.0, 0.0)
    }
}