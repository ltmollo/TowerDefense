package Ballons

import Vectors.Vector2D

class BallonManager extends Function0[Ballon]{
  private var coolDown = 100
  private var index = 0
  private val beginVector = new Vector2D(0, 170)

  def apply(): Ballon = {
    if(coolDown == 0){
      coolDown = 100
      index += 1
      index = index % 3
      val ballon: Ballon = index match{
        case 0 => new FastBallon(beginVector)
        case 1 => new SlowBallon(beginVector)
        case 2 => new BossBallon(beginVector)
      }
        return ballon
    }
    else{
      coolDown -= 1
    }
    null
  }

}
