package frontend

import javafx.scene.Node
import persistence.NoteTypeEnum

//This class will be used to represent the fields inside a note

interface FieldInterface {

    val type: NoteTypeEnum

    //To be implemented for displaying the proper javaFX
    fun display(): Node

    fun save(): String {
        //Default
        return "Default"
    }

}