package frontend.Composites

import Primitives.Sizes
import Primitives.Styling
import Primitives.TextStyles
import frontend.Primitives.Colours
import frontend.Primitives.Tags
import frontend.Views
import javafx.event.ActionEvent
import javafx.event.EventHandler
import javafx.geometry.Insets
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.input.MouseEvent
import javafx.scene.layout.*
import javafx.scene.text.Font
import javafx.scene.text.FontPosture
import javafx.scene.text.FontWeight
import javafx.scene.text.Text
import persistence.Tag

class NoteCard(noteTitle: String, tags: ArrayList<Tag>, labelText: String, onAction: EventHandler<MouseEvent>): VBox(){

    var handleClick: EventHandler<MouseEvent> = onAction
    val title = Text(noteTitle)
    val styleTitle = TextStyles(Styling.H1).font
    val titleStyle = Font.font(styleTitle.family, FontWeight.MEDIUM, FontPosture.REGULAR,Sizes().h3)
    val text = Label(labelText)
    val stylePara = TextStyles(Styling.P).font
    val paraStyle = Font.font(stylePara.family, FontWeight.NORMAL, FontPosture.REGULAR,Sizes().paragraph)
    val paddingRegionTag = Pane()
    val paddingRegionBody = Pane()

    init{
        title.font = titleStyle
        text.textFill = Colours().darkPurple
        text.font = paraStyle
        text.isWrapText = true
        text.style = "-fx-line-spacing: 1em;"

        minWidth = 300.0
        maxWidth = 300.0
        minHeight = 300.0
        maxHeight = 300.0
        padding = Insets(16.0)
        background = Background(BackgroundFill(Colours().pastelPurple, CornerRadii(8.0), Insets.EMPTY))
        paddingRegionTag.minHeight = 8.0
        paddingRegionTag.maxHeight = 8.0
        paddingRegionBody.minHeight = 16.0
        paddingRegionBody.maxHeight = 16.0

        var tagList = VBox(8.0)
        for (tag in tags) {
            var tagText = tag.name
            tagList.children.add(Tags(tagText, Colours().darkPurple))
        }
        children.addAll(title, paddingRegionTag, text, paddingRegionBody, tagList)

        this.addEventFilter(MouseEvent.MOUSE_MOVED, { background = Background(BackgroundFill(Colours().hoverPastelPurple, CornerRadii(8.0), Insets.EMPTY)) })
        this.addEventFilter(MouseEvent.MOUSE_EXITED, { background = Background(BackgroundFill(Colours().pastelPurple, CornerRadii(8.0), Insets.EMPTY)) })
        this.addEventFilter(MouseEvent.MOUSE_CLICKED, this.handleClick);
    }
}