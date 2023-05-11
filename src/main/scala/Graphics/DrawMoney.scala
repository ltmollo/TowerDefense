package Graphics

import scalafx.scene.paint.Color.{Orange, Yellow}
import scalafx.scene.paint.{LinearGradient, Stops}
import scalafx.scene.text.Text

class DrawMoney extends Function1[Int, Text]{
  def apply(money: Int): Text = {
    new Text {
      text = money.toString
      style = "-fx-font-size: 100pt"
      x = 110
      y = 100
      fill = new LinearGradient(
        stops = Stops(Yellow, Orange)
      )
    }
  }



}
