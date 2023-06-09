package Ballons

  import Vectors.Vector2D
  import javafx.scene.paint.ImagePattern

  class Ballon(var position: Vector2D){
    var maxHealth : Int = _
    var currentHealth : Int = _
    var imgPattern : ImagePattern = _
    var reward : Int = _
    var speed : Double = _
    var moveVectorIndex: Int = 0
    def changePosition(newPosition: Vector2D): Unit = {position = newPosition}
    override def toString() = "Ballon" + " " + position.toString()
  }
