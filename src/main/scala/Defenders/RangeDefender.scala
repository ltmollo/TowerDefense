package Defenders

import Vectors.Vector2D
import javafx.scene.paint.ImagePattern
import scalafx.scene.image.Image

class RangeDefender(position: Vector2D) extends Defender(position){
  damage = 45
  cost = 30
  private val image = new Image("resources/defenders/brown.png")
  imgPattern = new ImagePattern(image)
  coolDown = 10
  range = 15
}
