package Graphics

import Vectors.Vector2D
import scalafx.scene.shape.Rectangle
import javafx.scene.paint.ImagePattern
import scalafx.scene.image.Image
class DrawRectangle extends Function4[Vector2D, Int, Int, ImagePattern, Rectangle]{
  def apply(position: Vector2D, _width: Int, _height: Int, imgPattern: ImagePattern): Rectangle = {
    var rectangle = new Rectangle{
      x = position.x
      y = position.y
      width = _width
      height = _height
    }
    rectangle.setFill(imgPattern)
    rectangle
  }
}
