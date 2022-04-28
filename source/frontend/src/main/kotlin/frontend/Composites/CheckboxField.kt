package frontend.Composites

import Primitives.Styling
import Primitives.TextStyles
import frontend.Primitives.PlainTextField
import javafx.geometry.Insets
import javafx.scene.control.TextField
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.layout.*
import javafx.scene.paint.Color
import javafx.scene.text.Font

class CheckboxField(): HBox() {
    val img_untoggled = Image("Icons/check_box_outline_blank.png")
    val img_toggled = Image("Icons/check_box.png")

    val imgv_untoggled = ImageView(img_untoggled)
    val imgv_toggled = ImageView(img_toggled)
    val checkboxFieldContainer = VBox()

    val textfield = TextField()


    var isToggled = false
    init {

        checkboxFieldContainer.prefHeight = 29.0
        checkboxFieldContainer.prefWidth = 29.0
        imgv_toggled.fitHeight = 29.0
        imgv_toggled.fitWidth = 29.0
        imgv_untoggled.fitHeight = 29.0
        imgv_untoggled.fitWidth = 29.0

        checkboxFieldContainer.children.add(imgv_untoggled)

        HBox.setHgrow(checkboxFieldContainer, Priority.NEVER)
        HBox.setHgrow(textfield, Priority.ALWAYS)

        textfield.font = TextStyles(Styling.P).font
        textfield.border = Border.EMPTY
        textfield.background = Background(BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY))
        checkboxFieldContainer.setOnMouseClicked {
            if(isToggled){
                checkboxFieldContainer.children.clear()
                checkboxFieldContainer.children.add(imgv_untoggled)
                println("yeet2")
                isToggled = false
            }else{
                checkboxFieldContainer.children.clear()
                checkboxFieldContainer.children.add(imgv_toggled)
                println("yeet1")
                isToggled = true
                println(textfield.height)
            }

        }


        this.children.addAll(checkboxFieldContainer, textfield)
    }
}