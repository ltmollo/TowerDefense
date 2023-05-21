package Graphics

import scalafx.scene.paint.Color.{Orange, OrangeRed, Pink, Yellow}
import scalafx.scene.paint.{LinearGradient, Stops}
import scalafx.scene.text.Text

class DrawText extends Function5[Int, Int, Double, Double, String, Text]{
  def apply(_text: Int, scale: Int, _x: Double, _y: Double, option :String): Text = {
    val text = new Text {
      text = _text.toString
      style = "-fx-font-size: " + scale + "pt"
      y = _y
      x = _x
      fill = new LinearGradient(

      )
    }
    option match{
      case "money" => {
        text.fill = new LinearGradient(stops = Stops(Yellow, Orange))
      }
      case "health" => {
        text.fill = new LinearGradient(stops = Stops(Pink, OrangeRed))
      }
    }
    text
  }

}
