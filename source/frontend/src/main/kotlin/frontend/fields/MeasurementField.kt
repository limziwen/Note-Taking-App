package frontend.fields

import frontend.FieldInterface
import javafx.scene.Node
import persistence.NoteTypeEnum

class MeasurementField: FieldInterface {

    override val type: NoteTypeEnum = NoteTypeEnum.MEASUREMENT

    override fun display(): Node {
        TODO("Not yet implemented")
    }
}