package Maps

import javafx.scene.paint.ImagePattern
import scalafx.scene.image.Image

class Map {
  private val map: Image = new Image("resources/maps/map.png")
  val imgPattern: ImagePattern = new ImagePattern(map)

}
