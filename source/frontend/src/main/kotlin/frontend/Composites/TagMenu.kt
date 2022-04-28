package frontend.Composites

import frontend.Primitives.Colours
import frontend.Primitives.MenuItem
import javafx.scene.input.MouseEvent
import javafx.scene.layout.*
import javafx.scene.paint.Color
import logic.Director
import persistence.Tag
import java.awt.Menu

class TagMenu(director: Director): VBox() {
    var tags = director.getAllTags()
    var colours = mutableListOf<Color>()
    var menuItems: ArrayList<MenuItem> = ArrayList()
    var selectedTags: ArrayList<Tag> = ArrayList()
    var director = director

    init {
        director.tagMenu = this;
    }

    fun pullTags(){
        colours = mutableListOf<Color>(Colours().darkerPastelPurple, Colours().slightlyDarkerPastelPurple, Colours().pastelPink)
    }

    fun initMenu(){
        tags = director.getAllTags()
        menuItems.clear()
        children.clear()
        for (i in tags.indices) {
            val m = MenuItem(tags[i].name, colours[i%3])
            m.addEventFilter(MouseEvent.MOUSE_CLICKED) {
                if (selectedTags.indexOf(tags[i]) != -1) {
                    selectedTags.remove(tags[i])
                    this.initMenu()
                } else {
                    selectedTags.add(tags[i])
                    this.initMenu()
                }
            }
            if (selectedTags.indexOf(tags[i]) != -1 ){
                m.border = Border(
                    BorderStroke(Color.BLACK,
                        BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)
                );
            }
            menuItems.add(m)
        }

        director.updateSelectedTags(selectedTags)
        director.view?.render()

        for(i in menuItems){
            children.add(i)
        }
    }

    init {
        pullTags()
        initMenu()
    }

}