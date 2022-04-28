package frontend.Composites

import Primitives.Styling
import frontend.Primitives.DeleteHover
import frontend.Primitives.FieldSelectorHover
import javafx.geometry.Pos
import javafx.scene.layout.StackPane
import logic.Director
import persistence.NoteTypeEnum

class FieldSelectionComponent(director: Director, type: NoteTypeEnum, callback: ()->Unit, delete: ()->Unit): StackPane() {
    var typeclicked = Styling.P
    init{
        val hoverItem = FieldSelectorHover()
        val deleteItem = DeleteHover(delete)
        val menu = FieldSelectorMenu(director, callback)

        height = hoverItem.height
        width = menu.width
        maxHeight = hoverItem.height
        maxWidth = menu.width

        hoverItem.setOnMouseClicked {
            children.add(menu)
        }

        menu.setOnMouseClicked {
            children.remove(menu)
        }

        prefWidth = 100.0
        maxWidth = 100.0
        minWidth = 100.0

        if (type != NoteTypeEnum.TITLE) {
            children.addAll(deleteItem, hoverItem)
        } else {
            children.addAll(hoverItem)
        }

        StackPane.setAlignment(hoverItem, Pos.TOP_RIGHT)
        StackPane.setAlignment(deleteItem, Pos.TOP_LEFT)
    }
}