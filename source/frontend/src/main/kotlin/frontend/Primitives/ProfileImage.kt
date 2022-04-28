package frontend.Primitives

import javafx.scene.image.Image
import javafx.scene.image.ImageView

class ProfileImage(var path: String): ImageView() {
    var img = Image(path)
    init {
        image = img
        fitHeight = 30.0
        fitWidth = 30.0
        style = "-fx-border-radius: 1000 1000 1000 1000;\n" +
                "-fx-background-radius: 1000 1000 1000 1000;"
    }
}
