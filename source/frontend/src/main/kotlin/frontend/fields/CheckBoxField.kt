package frontend.fields

import Primitives.Styling
import Primitives.TextStyles
import frontend.FieldInterface
import javafx.scene.Node
import javafx.scene.control.CheckBox
import javafx.scene.layout.VBox
import persistence.NoteTypeEnum

class CheckBoxField(content: String, style: Styling): FieldInterface {

    override val type: NoteTypeEnum = NoteTypeEnum.CHECKBOX

    var name = "Option";
    var isChecked = false;
    var style = TextStyles(style);

    init {
        var content = content.split("|")

        if (content.size == 2){
            name = content[0]
            isChecked = content[1] == "true";
        }
    }
    var boxField = CheckBox(name)

    override fun display(): Node {

        boxField.font = style.font

        boxField.isSelected = isChecked
        return boxField

    }

    override fun save(): String {
        return boxField.text + " |" + boxField.isSelected
    }
}