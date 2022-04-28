package frontend.fields

import Primitives.Styling
import Primitives.TextStyles
import frontend.FieldInterface
import javafx.geometry.Insets
import javafx.scene.Node
import javafx.scene.control.TextArea
import javafx.scene.layout.Background
import javafx.scene.layout.BackgroundFill
import javafx.scene.layout.CornerRadii
import javafx.scene.paint.Color
import persistence.NoteTypeEnum

class BodyField(text: String = "Write to your heart's content...", style: Styling): FieldInterface {

    override val type: NoteTypeEnum = NoteTypeEnum.BODY

    var textField = TextArea(text)

    var style = TextStyles(style);

    //Source: https://www.geeksforgeeks.org/javafx-textfield/
    override fun display(): Node {

        textField.background = Background(BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY))
        textField.prefWidth = 9000.0
        textField.minWidth = 300.0
        textField.maxWidth = 90000.0
        textField.isWrapText = true
        textField.prefHeight = 30.0
        textField.font = style.font
        textField.autosize()
        var textSize = style.font.size

        var countT = textField.text.filter { it == '\n' }.count() + 2

        textField.setMinSize(200.0,textSize*1.6*countT)
        textField.setPrefSize(1000.0,textSize*1.6*countT)

        textField.textProperty()
            .addListener { observable, oldValue, newValue ->
                var count = newValue.filter { it == '\n' }.count() + 2
                textField.setPrefSize(1000.0,textSize*1.6*count)
            }

        textField.onKeyPressedProperty()
            .addListener { observable, oldValue, newValue -> println("height changed from $oldValue to $newValue") }

        //textField.focusedProperty().addListener( { value -> })

        return textField;
    }

    override fun save(): String {
        return textField.text
    }


}

