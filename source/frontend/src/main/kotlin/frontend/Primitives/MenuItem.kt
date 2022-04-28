package frontend.Primitives

import Primitives.TextComponent
import javafx.geometry.Insets
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.input.MouseEvent
import javafx.scene.layout.*
import javafx.scene.paint.Color

class MenuItem(var tag: String, var colour: Color, val isParent: Boolean = false): BorderPane() {
    var textComponent = TextComponent(content = tag, colour = Color.BLACK)
    var expanded = false
    init {
        left = textComponent
        textComponent.padding = Insets(0.0, 0.0, 0.0, 16.0)
        setMargin(textComponent, Insets(4.0))
        background = Background(BackgroundFill(colour, CornerRadii.EMPTY, Insets.EMPTY))
        prefWidth = 300.0
        maxWidth = 300.0
        prefHeight = 30.0
        HBox.setHgrow(this, Priority.NEVER)

        val hoverColour = colour.darker()

        this.addEventFilter(MouseEvent.MOUSE_MOVED, { background = Background(BackgroundFill(hoverColour, CornerRadii.EMPTY, Insets.EMPTY)) })
        this.addEventFilter(MouseEvent.MOUSE_EXITED, { background = Background(BackgroundFill(colour, CornerRadii.EMPTY, Insets.EMPTY)) })
        if(isParent){
            val img_expand_less = Image("Icons/expand_less.png")
            val img_expand_more = Image("Icons/expand_more.png")
            val imgv = ImageView(img_expand_less)
            setMargin(imgv, Insets(4.0))

            this.setOnMouseClicked {
                if(expanded){
                    imgv.image = img_expand_less
                    expanded = false
                }else{
                    imgv.image = img_expand_more
                    expanded = true
                }
            }

            right = imgv

        }

    }

}