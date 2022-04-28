package frontend.Composites

import frontend.Primitives.Colours
import frontend.Primitives.TagSelector
import frontend.Primitives.Tags
import javafx.geometry.Insets
import javafx.scene.layout.HBox
import javafx.scene.layout.Priority

class TagSelectorNoteBuilder(): HBox() {
    val selector = TagSelector()
    val tag = Tags("Uncategorized", Colours().hoverPastelPurple)
    init {
        children.add(selector)
        selector.setOnMouseClicked {
            children.remove(selector)
            children.add(tag)
        }

        minHeight = 30.0
        padding = Insets(0.0, 0.0, 0.0, 100.0)
    }
}