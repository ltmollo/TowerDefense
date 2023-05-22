package Bullets

import Ballons.Ballon
import Vectors.Vector2D
import javafx.scene.paint.ImagePattern
import scalafx.scene.image.Image

class Bullet(var position: Vector2D, val target: Ballon, val damage: Int){
  private val image: Image = new Image("resources/bullets/bullet.png")
  val imgPattern: ImagePattern = new ImagePattern(image)
  private val vectorToMiddle = new Vector2D(15, 15)
  def moveVector(): Vector2D = (target.position + vectorToMiddle - position).unit*1.5

  def move(): Boolean = {
    position += moveVector()
    if((position - target.position - vectorToMiddle).length < 1.5){
      target.currentHealth -= damage
      return true
    }
    false
  }
}
