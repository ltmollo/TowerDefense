package Ballons

import Vectors.Vector2D
import javafx.scene.paint.ImagePattern
import scalafx.scene.image.Image

class BossBallon (position: Vector2D) extends Ballon(position){
  maxHealth = 200
  currentHealth = maxHealth
  reward = 10
  speed = 1.5
  private val image = new Image("resources/ballons/blue.png")
  imgPattern = new ImagePattern(image)
}
