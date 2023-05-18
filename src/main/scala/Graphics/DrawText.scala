package Graphics

import scalafx.scene.paint.Color.{Orange, OrangeRed, Pink, Yellow}
import scalafx.scene.paint.{LinearGradient, Stops}
import scalafx.scene.text.Text

class DrawText extends Function2[Int, String, Text]{
  def apply(_text: Int, option :String): Text = {
    val text = new Text {
      text = _text.toString
      style = "-fx-font-size: 100pt"
      y = 100
      fill = new LinearGradient(

      )
    }
    option match{
      case "money" => {
        text.fill = new LinearGradient(stops = Stops(Yellow, Orange))
        text.x = 110
      }
      case "health" => {
        text.fill = new LinearGradient(stops = Stops(Pink, OrangeRed))
        text.x = 1100
      }
    }
    text
  }

}
