package Coins

import javafx.scene.paint.ImagePattern
import scalafx.scene.image.Image

class Coin {
  val image = new Image("resources/coins/coin.png")
  val imgPattern = new ImagePattern(image)
}
