package frontend.Primitives

import javafx.geometry.Insets
import javafx.scene.control.Button
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.input.MouseEvent
import javafx.scene.layout.*
import javafx.scene.paint.Color
import logic.Director

class DeleteHover(delete: () -> Unit): Button() {

    init {
        background = Background(BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY))
        val path = "Icons/delete.png"
        val img = Image(path)
        val imgv = ImageView(img)
        //
        prefWidth = 30.0
        prefHeight = prefWidth
        graphic = imgv
        this.addEventFilter(MouseEvent.MOUSE_MOVED) {
            background = Background(BackgroundFill(Colours().lightRed, CornerRadii.EMPTY, Insets.EMPTY))
            graphic.isVisible = true
        }
        this.addEventFilter(MouseEvent.MOUSE_EXITED) {
            background = Background(BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY))
        }
        this.addEventFilter(MouseEvent.MOUSE_CLICKED) { delete() }
    }
}