package Graphics

import scalafx.scene.paint.Color.{Black, Gray, Orange, OrangeRed, Pink, Yellow}
import scalafx.scene.paint.{LinearGradient, Stops}
import scalafx.scene.text.Text

class DrawText extends Function5[String, Int, Double, Double, String, Text]{
  def apply(_text: String, scale: Int, _x: Double, _y: Double, option :String): Text = {
    val text = new Text {
      text = _text
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
      case "round" => {
        text.fill = new LinearGradient(stops = Stops(Gray, Black))
      }
    }
    text
  }

}
