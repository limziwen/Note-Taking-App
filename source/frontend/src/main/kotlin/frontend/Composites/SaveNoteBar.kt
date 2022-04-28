package frontend.Composites

import frontend.Primitives.Buttons
import frontend.Primitives.Colours
import javafx.event.ActionEvent
import javafx.event.EventHandler
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.control.RadioButton
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.layout.HBox
import javafx.scene.paint.Color

class SaveNoteBar(online: Boolean, back: () -> Unit,cloud: () -> Unit,save: () -> Unit,delete: () -> Unit): HBox(10.0) {
    val backButton = Buttons("Icons/arrow_back.png", "Back Home", Colours().darkPurple)
    val saveButton = Buttons("Icons/save.png", "Save Locally", Colours().darkPurple)
    val deleteButton = Buttons("Icons/delete.png", "Delete Note", Colours().darkRed)
    var onlineToggle = RadioButton("Backup to Cloud")
    init{
        alignment = Pos.CENTER
        onlineToggle.graphic = ImageView(Image("Icons/cloud.png"))
        onlineToggle.setPrefSize(140.0,50.0)
        this.padding = Insets(10.0)
        onlineToggle.isSelected = online

        backButton.onAction = EventHandler { back() }
        saveButton.onAction = EventHandler { save() }
        onlineToggle.onAction = EventHandler { cloud() }
        deleteButton.onAction = EventHandler { delete() }

        //Add callback listeners
        children.addAll(backButton, onlineToggle, saveButton, deleteButton)
    }
}