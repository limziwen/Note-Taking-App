package frontend.Primitives

import Primitives.Styling
import Primitives.TextStyles
import javafx.geometry.Insets
import javafx.scene.control.TextArea
import javafx.scene.layout.Background
import javafx.scene.layout.BackgroundFill
import javafx.scene.layout.Border
import javafx.scene.layout.CornerRadii
import javafx.scene.paint.Color
import javafx.scene.text.Font
import javafx.scene.text.FontPosture
import javafx.scene.text.FontWeight
import java.io.File
import java.io.FileInputStream


class PlainTextField(style: Styling, isInitial: Boolean = false): TextArea() {

    init{
        background = Background(BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY))
        prefWidth = 9000.0
        minWidth = 300.0
        maxWidth = 90000.0
        isWrapText = true
        prefHeight = 30.0


        var textSize = TextStyles(style).font.size
        this.font = TextStyles(style).font
        val textField = this

        if(style == Styling.H1){
            this.text = "Untitled Note"
        }else{
            if(isInitial)  this.text = "Write to your heart's content..."
        }

        textField.isWrapText = true
        textField.autosize()




        textField.isWrapText = true
        textField.autosize()

        textField.setMinSize(300.0,textSize*3)
        textField.setPrefSize(9000.0,textSize*3)

        textField.textProperty()
            .addListener { observable, oldValue, newValue ->
                var count = newValue.filter { it == '\n' }.count() + 2
                //println("textfield changed from $oldValue to $newValue")
                print(count)

                textField.setPrefSize(9000.0,textSize*count)
                //textField.background = Background(BackgroundFill(paint, CornerRadii.EMPTY, Insets.EMPTY)))



            }

        textField.onKeyPressedProperty()
            .addListener { observable, oldValue, newValue -> println("height changed from $oldValue to $newValue") }

        border = Border.EMPTY

        // how do we remove the silly silly highlight???????


    }
}