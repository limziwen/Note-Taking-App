import frontend.Composites.*
import frontend.Views
import javafx.application.Application
import javafx.geometry.Insets
import javafx.scene.Scene
import javafx.scene.image.Image
import javafx.scene.layout.*
import javafx.scene.paint.Color
import javafx.stage.Stage
import logic.Director


class Main : Application() {

    val director = Director()


    override fun start(stage: Stage) {

        //--------------------------------------------------------------------------------------------------------------
        //Initial Setup Stuff
        //--------------------------------------------------------------------------------------------------------------

        val mainPane = BorderPane()

        //--------------------------------------------------------------------------------------------------------------
        //Create Menus
        //--------------------------------------------------------------------------------------------------------------

        //TODO("CowTear")

        //--------------------------------------------------------------------------------------------------------------
        //Create Notes
        //--------------------------------------------------------------------------------------------------------------

        //Create the view pane
        var view = Views(mainPane, director)

        mainPane.center = view.render()
        mainPane.left = LeftSidebar(director)
        mainPane.background = Background(BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY))

        //--------------------------------------------------------------------------------------------------------------
        //Display
        //--------------------------------------------------------------------------------------------------------------
        //Stage by default allows users to resize
        val scene = Scene(mainPane, 1600.0, 1200.0)

        //Set the initial scene and show the stage
        stage.scene = scene
        //mainPane.background = Background(BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY))
        stage.title = "Group 207 Not Taking App"

        stage.icons.add(Image("https://is2-ssl.mzstatic.com/image/thumb/Purple118/v4/6b/9c/1e/6b9c1e7d-0f6d-c076-7532-40606623963b/source/512x512bb.jpg"))
        stage.show()

    }
}