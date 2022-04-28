package frontend.Composites

import Primitives.Styling
import frontend.Primitives.FieldSelectorItem
import javafx.scene.layout.VBox
import logic.Director
import persistence.Module
import persistence.NoteTypeEnum

class FieldSelectorMenu(director: Director, callback: ()->Unit): VBox() {

    val listOfFieldName = mutableListOf<String>("Header 1", "Header 2", "Header 3", "Header 4", "Header 5", "Default Text", "Small Text", "Checkbox", "Date")
    val listOfFieldType = mutableListOf<Styling>(Styling.H1, Styling.H2, Styling.H3, Styling.H4, Styling.H5, Styling.P, Styling.SMALL, Styling.P, Styling.P)
    val defaultText: String = "Write to your heart's content..."
    init {
        for(i in 0..(listOfFieldType.size - 1)){
            val item = FieldSelectorItem(listOfFieldName[i], listOfFieldType[i])
            item.setOnMouseClicked {
                if (item.name == "Header 1") {
                    director.addModuleToNote(Module(NoteTypeEnum.HEADER1, defaultText))
                } else if (item.name == "Header 2") {
                    director.addModuleToNote(Module(NoteTypeEnum.HEADER2, defaultText))
                } else if (item.name == "Header 3") {
                    director.addModuleToNote(Module(NoteTypeEnum.HEADER3, defaultText))
                } else if (item.name == "Header 4") {
                    director.addModuleToNote(Module(NoteTypeEnum.HEADER4, defaultText))
                } else if (item.name == "Header 5") {
                    director.addModuleToNote(Module(NoteTypeEnum.HEADER5, defaultText))
                } else if (item.name == "Default Text") {
                    director.addModuleToNote(Module(NoteTypeEnum.BODY, defaultText))
                } else if (item.name == "Small Text") {
                    director.addModuleToNote(Module(NoteTypeEnum.SMALLBODY, defaultText))
                } else if (item.name == "Checkbox") {
                    director.addModuleToNote(Module(NoteTypeEnum.CHECKBOX))
                } else if (item.name == "Date") {
                    director.addModuleToNote(Module(NoteTypeEnum.DATE))
                }


                //Tell the parent an action was taken
                callback()
            }
            children.add(item)
        }
        setOnMouseClicked {
            children.clear()
        }
    }
}