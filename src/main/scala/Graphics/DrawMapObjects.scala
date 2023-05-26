package Graphics

import Ballons.Ballon
import Bullets.Bullet
import Coins.Coin
import Maps.Map
import Defenders.Defender
import Vectors.Vector2D
import scalafx.scene.shape.Rectangle

class DrawMapObjects extends ((Map, Coin, List[Ballon], List[Defender], List[Bullet]) => List[Rectangle]){
  val drawRectangle = new DrawRectangle()
  def apply(myMap: Map, coin: Coin, ballonsList: List[Ballon], defendersList: List[Defender],
            bulletsList: List[Bullet]): List[Rectangle] = {
    var objectsToDraw = List(drawRectangle(new Vector2D(0, 150), 1200, 600, myMap.imgPattern))

    objectsToDraw = objectsToDraw ::: List(drawRectangle(coin.position, 100, 100, coin.imgPattern))

    objectsToDraw = objectsToDraw ::: ballonsList.map { ballon =>
      drawRectangle(ballon.position, 50, 60, ballon.imgPattern)
    }

    objectsToDraw = objectsToDraw ::: defendersList.map { defender =>
      drawRectangle(defender.position, 50, 50, defender.imgPattern)
    }

    objectsToDraw = objectsToDraw ::: bulletsList.map { bullet =>
      drawRectangle(bullet.position, 20, 20, bullet.imgPattern)
    }
  return objectsToDraw
  }
}
