package Ballons

import Vectors.Vector2D
import javafx.scene.paint.ImagePattern
import scalafx.scene.image.Image

class SlowBallon(position: Vector2D) extends Ballon(position){
  maxHealth = 150
  currentHealth = maxHealth
  reward = 5
  speed = 1
  private val image = new Image("resources/ballons/green.png")
  imgPattern = new ImagePattern(image)
}
