package Bullets

import Ballons.Ballon
import Vectors.Vector2D
import javafx.scene.paint.ImagePattern
import scalafx.scene.image.Image

class Bullet(var position: Vector2D, val target: Ballon, val damage: Int){
  val image: Image = new Image("resources/bullets/bullet.png")
  val imgPattern: ImagePattern = new ImagePattern(image)
  def moveVector(): Vector2D = (target.position - position).unit

  def move(): Boolean = {
    position += moveVector()
    if((position - target.position).length < 0.5){
      target.currentHealth -= damage
      println(target.currentHealth)
      return true
    }
    false
  }
}
