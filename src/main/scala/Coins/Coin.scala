package Coins

import Vectors.Vector2D
import javafx.scene.paint.ImagePattern
import scalafx.scene.image.Image

class Coin {
  private val image = new Image("resources/coins/coin.png")
  val imgPattern = new ImagePattern(image)
  val position = new Vector2D(0, 0)
}
