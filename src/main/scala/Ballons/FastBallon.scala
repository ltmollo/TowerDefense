package Ballons

import Vectors.Vector2D
import javafx.scene.paint.ImagePattern
import scalafx.scene.image.Image

class FastBallon(position: Vector2D) extends Ballon(position){
  maxHealth = 100
  currentHealth = maxHealth
  reward = 7
  speed = 2
  private val image = new Image("resources/ballons/red.png")
  imgPattern = new ImagePattern(image)
}
