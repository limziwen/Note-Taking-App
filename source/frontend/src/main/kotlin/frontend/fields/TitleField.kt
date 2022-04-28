package frontend.fields

import Primitives.Styling
import Primitives.TextStyles
import frontend.FieldInterface
import javafx.geometry.Insets
import javafx.scene.Node
import javafx.scene.control.TextField
import javafx.scene.layout.Background
import javafx.scene.layout.BackgroundFill
import javafx.scene.layout.CornerRadii
import javafx.scene.paint.Color
import persistence.NoteTypeEnum

class TitleField(title: String, style: Styling): FieldInterface {

    override val type: NoteTypeEnum = NoteTypeEnum.TITLE

    var titleField = TextField(title)
    var style = TextStyles(style);

    //Source: https://www.geeksforgeeks.org/javafx-textfield/
    override fun display(): Node {

        titleField.background = Background(BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY))
        titleField.prefWidth = 9000.0
        titleField.minWidth = 300.0
        titleField.maxWidth = 90000.0
        titleField.prefHeight = 30.0
        titleField.font = style.font

        titleField.autosize()
        var textSize = style.font.size

        titleField.setMinSize(300.0,textSize*3)
        titleField.setPrefSize(1000.0,textSize*3)

        return titleField;
    }


    override fun save(): String {
        return titleField.text
    }
}