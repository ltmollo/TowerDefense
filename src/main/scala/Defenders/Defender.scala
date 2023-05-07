package Defenders

import Vectors.Vector2D
import javafx.scene.paint.ImagePattern

class Defender(var position: Vector2D){
  var damage : Int = _
  var cost : Int = _
  var imgPattern : ImagePattern = _
  var coolDown : Int = _
  var currentCoolDown : Int = 0
  var range : Int = _

  def decreaseCoolDown(): Unit = {
    if(currentCoolDown > 0) {
      currentCoolDown -= 1
    }
  }

  def checkRange(target: Vector2D): Boolean = {
    val rangeVector = new Vector2D(range, range)
    target.toLowerLeft(position - rangeVector) && target.toUpperRight(position + rangeVector)
  }

  def shoot(): Int = {
    if (currentCoolDown == 0) {
      currentCoolDown = coolDown + 1
      println("shotted")
      damage
    }
    else 0
  }

  override def toString() = "Defender" + " " + position.toString()
}
