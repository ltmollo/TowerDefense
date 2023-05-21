package Defenders

import Vectors.Vector2D
import javafx.scene.paint.ImagePattern
import scalafx.scene.image.Image

class SlowDefender(position: Vector2D) extends Defender(position){
  damage = 80
  cost = 25
  private val image = new Image("resources/defenders/pink.png")
  imgPattern = new ImagePattern(image)
  coolDown = 150
  range = 160
}
