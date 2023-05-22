package Vectors

import scala.math.sqrt

class Vector2D(var x: Double, var y:Double){
  def +(other: Vector2D) = new Vector2D(x + other.x, y + other.y)
  def -(other: Vector2D) = new Vector2D(x - other.x, y - other.y)

  def *(k: Double): Vector2D = new Vector2D(x*k, y*k)
  def unary_- = new Vector2D(-x, -y)
  def toUpperRight(other: Vector2D): Boolean = x <= other.x && y <= other.y
  def toLowerLeft(other: Vector2D): Boolean = x >= other.x && y >= other.y
  def length: Double = sqrt(x * x + y * y)
  def distance(other: Vector2D): Double = (new Vector2D(x - other.x, y - other.y)).length
  def unit: Vector2D = new Vector2D(x/length, y/length)
  override def toString() = "(" + x.toString + "," + y.toString + ")"
}

