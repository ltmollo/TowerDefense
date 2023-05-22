package Arrows

import Vectors.Vector2D
import javafx.scene.paint.ImagePattern
import scalafx.scene.image.Image

class Arrow {
  private val image = new Image("resources/arrows/arrow.png")
  val imgPattern = new ImagePattern(image)
  val position = new Vector2D(0, 580)
}
