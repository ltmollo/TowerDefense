package Defenders

import Defenders.DefenderChoice.Choice.{Choice, FastDefender, SlowDefender, RangeDefender}
import Vectors.Vector2D

class AddDefender extends Function3[Vector2D, Choice, Int, Defender]{

  val mapBorders: List[(Vector2D, Vector2D)] = List(
    (new Vector2D(-25, 140), new Vector2D(500, 200)),
    (new Vector2D(445, 140), new Vector2D(500, 315)),
    (new Vector2D(240, 285), new Vector2D(500, 345)),
    (new Vector2D(240, 295), new Vector2D(295, 520)),
    (new Vector2D(245, 460), new Vector2D(625, 520)),
    (new Vector2D(565, 210), new Vector2D(623, 520)),
    (new Vector2D(575, 210), new Vector2D(830, 268)),
    (new Vector2D(774, 210), new Vector2D(830, 705)),
    (new Vector2D(774, 650), new Vector2D(1200, 705)))

  var defendersOnMap: List[Defender] = List()

  def checkBorders(position: Vector2D): Boolean = {
    for((corner1, corner2) <- mapBorders) {
      if(position.toLowerLeft(corner1) && position.toUpperRight(corner2)){
        println("Wrong place")
        return false
      }
    }
    true
  }

  def checkDefenders(position: Vector2D): Boolean = {

    for(defender <- defendersOnMap){
      if(position.distance(defender.position) < 40){
        return false
      }
    }
    true
  }

  def rewind(): Boolean = {
    if(defendersOnMap.nonEmpty) {
      defendersOnMap = defendersOnMap.init
    }
      return true
    false
  }
  def apply(position: Vector2D, defenderChoice: Choice, money: Int): Defender = {
    val defenderPosition = position - new Vector2D(25, 25)

    if(!checkBorders(defenderPosition) || !checkDefenders(defenderPosition)){ return null}

    val newDefender: Defender = defenderChoice match {
      case FastDefender => new FastDefender(defenderPosition)
      case SlowDefender => new SlowDefender(defenderPosition)
      case RangeDefender => new RangeDefender(defenderPosition)

    }
    if (newDefender.cost <= money) {
      defendersOnMap = defendersOnMap :+ newDefender
      return newDefender
    }
    null
  }

}
