package frontend

import Primitives.Styling
import Primitives.TextStyles
import frontend.Composites.FieldSelectionComponent
import frontend.Composites.SaveNoteBar
import frontend.Primitives.Colours
import frontend.Primitives.Tags
import frontend.fields.BodyField
import frontend.fields.CheckBoxField
import frontend.fields.DateField
import frontend.fields.TitleField
import io.ktor.utils.io.*
import javafx.event.ActionEvent
import javafx.event.EventHandler
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.control.Button
import javafx.scene.control.ColorPicker
import javafx.scene.control.Label
import javafx.scene.control.TextField
import javafx.scene.input.KeyCode
import javafx.scene.layout.*
import javafx.scene.paint.Color
import logic.Director
import persistence.Module
import persistence.Note
import persistence.NoteTypeEnum
import persistence.Tag
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class NoteLayout(note: persistence.Note, director: Director) {

    var coverPhoto: String = ""
    var id: Int

    var tags: ArrayList<Tag> = ArrayList()
    var fields: ArrayList<FieldInterface> = ArrayList()
    var director = director
    var currentNote = note;
    var online = false;
    val myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")
    var lastModified = ""

    var style = TextStyles(Styling.P);

    init {
        this.load(currentNote)
        this.id = note.id
    }

    //Returns a flow pane with all the elements of a note
    fun render(view: Views): VBox {
        online = view.online
        this.load(director.getNote()?: currentNote)
        val date = director.getNote()?.getLastModifiedDate()?.format(myFormatObj)?.substring(0, 8)
        val time = director.getNote()?.getLastModifiedDate()?.format(myFormatObj)?.substring(10, 16)
        lastModified = time + " on " + date
        //Initialize a vertical flow plan
        var notePane = VBox(5.0)
        notePane.children.add(SaveNoteBar(online,
            { this.save(); view.goBacktoMainScreen()},
            {online = !online},
            { this.save(); view.render()},
            {this.save(); director.deleteNote(id);view.goBacktoMainScreen()}))

        var lastSavedLabel = Label("Last Saved at" + lastModified)
        lastSavedLabel.textFill = Colours().darkPurple
        lastSavedLabel.padding = Insets(8.0, 0.0, 8.0, 64.0)
        lastSavedLabel.alignment = Pos.CENTER_RIGHT
        notePane.children.add(lastSavedLabel)

        //Add all the fields to the pane
        for (field in fields) {

            val fieldRow = HBox()
            val fieldselection = FieldSelectionComponent(director, field.type,
                { view.render(); this.save(); },
                { director.deleteModuleFromNote(fields.indexOf(field)); view.render(); this.save();})

            var node = field.display()
            //Save the note on focus change
            node.focusedProperty().addListener { value ->
                    lastModified = LocalDateTime.now().format(myFormatObj)
                this.save()
            }
            fieldRow.spacing = 5.0;
            fieldRow.children.addAll(fieldselection, node);
            notePane.children.add(fieldRow);

            //Tags
            var tagsBar = HBox()
            tagsBar.spacing = 5.0
            tagsBar.setPrefSize(100.0,30.0)
            tagsBar.padding = Insets(0.0, 0.0, 0.0, 100.0)

            if (field.type == NoteTypeEnum.TITLE){
                for (tag in tags) {
                    var tagText = tag.name
                    var backgroundStyle = Background(BackgroundFill(Colours().darkerPastelPurple, CornerRadii(5.0), Insets(-5.0)))
                    var tagBox = HBox()
                    var close = Button("X")

                    //Styling
                    tagBox.background = backgroundStyle

                    //Delete Tag
                    close.onAction = EventHandler { value: ActionEvent? ->
                        tags.remove(tag)
                        this.save()
                        view.render()
                    }

                    //Add to screen
                    tagsBar.children.add(Tags(tagText, Colours().darkPurple))
                }
                var createTag = TextField()
                createTag.setPrefSize(150.0,30.0)
                createTag.font = style.font
                createTag.promptText = "Enter tag name"
                createTag.setOnKeyPressed { event ->
                    if (event.code === KeyCode.ENTER) {
                        val tag = director.addTagToNote(createTag.text.lowercase())
                        director.tagMenu?.initMenu()
                        if (tag != null) {
                            tags.add(tag)
                        }
                        createTag.clear()
                        view.render()
                    }
                }
                tagsBar.children.add(createTag)
                notePane.children.add(tagsBar);

            }
        }

        return notePane
    }

    @JvmName("getTitle1")
    fun getTitle(): String {
        return fields[0].save()
    }

    fun addField(field: FieldInterface) {
        fields.add(field)
    }

    //Returns data to save the note
    fun save() {
        director.clearModules()
        //director.setOnline(online)
        for (field in fields){
            if (field.type == NoteTypeEnum.TITLE) {
                director.setTitle(field.save())
            }
            var module = Module(field.type, field.save())
            director.addModuleToNote(module)
        }
        director.saveNote(id, online)

    }

    //Function to load data into the notes
    fun load(note: Note) {
        fields.clear()
        tags.clear()
        for (module in note.getModules()) {
            addField(when (module.getType()) {
                NoteTypeEnum.TITLE -> TitleField(module.getContent(),Styling.H1)
                NoteTypeEnum.BODY -> BodyField(module.getContent(), Styling.P)
                NoteTypeEnum.HEADER1 -> BodyField(module.getContent(), Styling.H1)
                NoteTypeEnum.HEADER2 -> BodyField(module.getContent(), Styling.H2)
                NoteTypeEnum.HEADER3 -> BodyField(module.getContent(), Styling.H3)
                NoteTypeEnum.HEADER4 -> BodyField(module.getContent(), Styling.H4)
                NoteTypeEnum.HEADER5 -> BodyField(module.getContent(), Styling.H5)
                NoteTypeEnum.SMALLBODY -> BodyField(module.getContent(), Styling.SMALL)
                NoteTypeEnum.CHECKBOX -> CheckBoxField(module.getContent(),Styling.P)
                NoteTypeEnum.DATE -> DateField(module.getContent())
                else -> BodyField(style = Styling.P)
            })
        }
        for (tag in director.getNote()?.getTags()!!) {
            print(tag)
            tags.add(tag)
        }
    }



}