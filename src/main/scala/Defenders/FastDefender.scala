package Defenders

import Vectors.Vector2D
import javafx.scene.paint.ImagePattern
import scalafx.scene.image.Image

class FastDefender (position : Vector2D) extends Defender(position){
  damage = 35
  cost = 15
  private val image = new Image("resources/defenders/purple.png")
  imgPattern = new ImagePattern(image)
  coolDown = 100
  range = 100
}
