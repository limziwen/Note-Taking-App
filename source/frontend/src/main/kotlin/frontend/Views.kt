package frontend

import frontend.Composites.LeftSidebar
import frontend.Composites.NoteCard
import javafx.event.ActionEvent
import javafx.event.EventHandler
import javafx.geometry.Insets
import javafx.scene.Node
import javafx.scene.control.Button
import javafx.scene.control.ScrollPane
import javafx.scene.layout.*
import javafx.scene.paint.Color
import logic.Director
import persistence.NoteMetaData
import java.time.format.DateTimeFormatter

class Views(mainPane: BorderPane, director: Director) {


    //Represents the 3 types of views

    //1 == grid
    var type = 1;

    //Store a list of notes
    var notes: ArrayList<NoteMetaData> = ArrayList()
    var curNote: NoteLayout? = null;
    var director = director;
    val myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")
    var mainPane = BorderPane()
    var online = false;

    init {
        this.mainPane = mainPane
        this.director = director
        //Get all note data
        this.notes = director.getAllNotes()
        director.view = this
    }

    fun goBacktoMainScreen() {
        director.resetNoteBuilder()
        curNote = null
        this.notes = director.getAllNotes()
        this.render()
    }

    fun render(): Node {
        this.notes = director.getAllNotes()
        var gridPane = GridPane()

        var notePane = FlowPane()
        notePane.vgap = 50.0
        notePane.hgap = 50.0
        notePane.padding = Insets(60.0, 0.0, 60.0, 60.0)

        var scrollPane = ScrollPane()
        scrollPane.isFitToWidth = true;
        scrollPane.background = Background(BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY))
        scrollPane.style = "-fx-background: rgb(255,255,255);\n -fx-background-color: rgb(255,255,255)"
        scrollPane.content = notePane

        //If the curNote is -1 then we display a grid of notes
        if (curNote == null) {

            //Add notes to pane
            for (i in notes.indices){

                val date = notes[i].dateModified.format(myFormatObj).substring(0, 8)
                val time = notes[i].dateModified.format(myFormatObj).substring(10, 16)
                var noteBox = NoteCard(notes[i].title, notes[i].tags!!, "Last Edited at" +time + " on " + date) {
                    director.loadNote(notes[i].id)
                    online = notes[i].online == true
                    val note = director.getNote()
                    if (note != null) {
                        curNote = NoteLayout(note, director)
                    }
                    this.render()
                };
                notePane.children.add(noteBox)
            }

            //Create the "Add New" button
            var addNewButton = Button("+")
            addNewButton.setPrefSize(300.0,300.0)
            addNewButton.style = "-fx-font-size:100"
            addNewButton.onAction = EventHandler { value: ActionEvent? ->
                director.createNote()
                this.notes = director.getAllNotes()
                curNote = null; //Might not be needed
                this.render()
            }

            //set the grid as the mainPane
            notePane.children.add(addNewButton)
            mainPane.center = scrollPane
            //mainPane.left = LeftSidebar(director)
        }
        else {
            //set the VBox from the note as the mainPane
            mainPane.center = curNote!!.render(this)
            //director.tagMenu?.initMenu()
        }

        //return the main pane to the scene for re-rendering
        return mainPane.center;
    }
}