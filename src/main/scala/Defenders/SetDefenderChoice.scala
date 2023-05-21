package Defenders

import Defenders.DefenderChoice.Choice.{Choice, FastDefender, RangeDefender, SlowDefender}
import Vectors.Vector2D

class SetDefenderChoice extends Function1[Vector2D, Choice]{

  def apply(position: Vector2D): Choice = {
    val y = position.y
    if (y < 360) {
      return FastDefender
    }
    else if (y < 470) {
      return SlowDefender
    }
    else {
      return RangeDefender
    }
  }

}
