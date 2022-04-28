package frontend.Primitives

import javafx.geometry.Insets
import javafx.scene.control.ComboBox
import javafx.scene.layout.Background
import javafx.scene.layout.BackgroundFill
import javafx.scene.layout.Border
import javafx.scene.layout.CornerRadii
import javafx.scene.paint.Color


class TagSelector(): ComboBox<Tags>() {
    val dummyTags = mutableListOf<Tags>()

    fun pullTags(){
        val tags = mutableListOf<String>("Hello", "Baby", "Yeet")
        val colours = mutableListOf<Color>(Colours().darkerPastelPurple, Colours().slightlyDarkerPastelPurple, Colours().pastelPink)
        // TODO: integrations
        for(i in 0..(tags.size-1)){
            val tag = Tags(tags[i], colours[i])
            dummyTags.add(tag)
        }
    }
    init{
        isEditable = true
        pullTags()
        promptText = "Add tag"
        items.addAll(dummyTags)
        background = Background(BackgroundFill(Colours().pastelPink, CornerRadii(8.0), Insets.EMPTY))

    }
}