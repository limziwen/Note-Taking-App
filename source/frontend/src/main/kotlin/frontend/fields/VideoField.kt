package frontend.fields

import frontend.FieldInterface
import javafx.scene.Node
import persistence.NoteTypeEnum

class VideoField: FieldInterface {

    override val type: NoteTypeEnum = NoteTypeEnum.VIDEO

    override fun display(): Node {
        TODO("Not yet implemented")
    }
}