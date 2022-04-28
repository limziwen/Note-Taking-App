package frontend.fields

import Primitives.Styling
import Primitives.TextStyles
import frontend.FieldInterface
import javafx.scene.Node
import javafx.scene.control.DatePicker
import persistence.NoteTypeEnum
import java.time.LocalDate
import java.time.LocalDateTime

class DateField(content: String): FieldInterface {

    override val type: NoteTypeEnum = NoteTypeEnum.DATE

    var year = LocalDateTime.now().year
    var month = LocalDateTime.now().monthValue
    var day =  LocalDateTime.now().dayOfMonth

    init {
        if (content.split("-").size == 3){
            year = content?.split("-")[0].toInt()
            month = content?.split("-")[1].toInt()
            day =  content?.split("-")[2].toInt()
        }
    }

    var date = LocalDate.of(year, month, day)
    var datePicker = DatePicker(date)

    override fun display(): Node {
        return datePicker
    }

    override fun save(): String {
        return datePicker.value.toString()
    }
}