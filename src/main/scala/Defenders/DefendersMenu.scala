package Defenders

import Coins.Coin
import Vectors.Vector2D
import scalafx.scene.layout.Pane
import Graphics.{DrawRectangle, DrawText}
import scalafx.scene.paint.Color.{Green, Red}
class DefendersMenu extends Function3[List[Defender], Int, Coin, Pane] {
  val drawRectangle = new DrawRectangle()
  val drawText = new DrawText()
  def apply(defendersToChoose: List[Defender], money: Int, coin: Coin): Pane = {
    val pane = new Pane()
    defendersToChoose.foreach { defender =>
      val background = drawRectangle(defender.position, 80, 110, null)
      if (defender.cost <= money) {
        background.fill = Green
      }
      else background.fill = Red
      background.opacity = 0.5

      pane.getChildren.add(background)
      pane.getChildren.add(drawRectangle(defender.position, 80, 80, defender.imgPattern))
      pane.getChildren.add(drawRectangle(defender.position + new Vector2D(10, 75), 30, 30, coin.imgPattern))
      pane.getChildren.add(drawText(defender.cost, 15, defender.position.x + 36, defender.position.y + 100, "money"))
    }
    pane
  }

}
