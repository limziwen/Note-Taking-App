package frontend.fields

import frontend.FieldInterface
import javafx.scene.Node
import persistence.NoteTypeEnum

class ImageField: FieldInterface {

    override val type: NoteTypeEnum = NoteTypeEnum.IMAGE

    override fun display(): Node {
        TODO("Not yet implemented")
    }
}