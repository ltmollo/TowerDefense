package Ballons

import Vectors.Vector2D

class MoveBallon extends Function1[Ballon, Boolean]{
  private val north = new Vector2D(0, -0.5)
  private val east = new Vector2D(0.5, 0)
  private val south = new Vector2D(0, 0.5)
  private val west = new Vector2D(-0.5, 0)
  private val moveVectors = List(east, south, west, south, east, north, east, south, east)
  private val lowerLeftBorder = new Vector2D(0, 800)
  private val upperRightBorder = new Vector2D(1200, 0)

  def apply(ballon: Ballon): Boolean = {
    var newPostion = ballon.position + moveVectors(ballon.moveVectorIndex)*ballon.speed
    ballon.moveVectorIndex match{
      case 0 => {
        if(newPostion.x >= 475) {
          newPostion = new Vector2D(475, newPostion.y)
          ballon.moveVectorIndex += 1
        }
      }
      case 1 => {
        if (newPostion.y >= 320) {
          newPostion = new Vector2D(newPostion.x, 320)
          ballon.moveVectorIndex += 1
        }
      }
      case 2 => {
        if (newPostion.x <= 270) {
          newPostion = new Vector2D(270, newPostion.y)
          ballon.moveVectorIndex += 1
        }
      }
      case 3 => {
        if (newPostion.y >= 495) {
          newPostion = new Vector2D(newPostion.x, 495)
          ballon.moveVectorIndex += 1
        }
      }
      case 4 => {
        if (newPostion.x >= 600) {
          newPostion = new Vector2D(600, newPostion.y)
          ballon.moveVectorIndex += 1
        }
      }
      case 5 => {
        if (newPostion.y <= 240) {
          newPostion = new Vector2D(newPostion.x, 240)
          ballon.moveVectorIndex += 1
        }
      }
      case 6 => {
        if (newPostion.x >= 805) {
          newPostion = new Vector2D(805, newPostion.y)
          ballon.moveVectorIndex += 1
        }
      }
      case 7 => {
        if (newPostion.y >= 680) {
          newPostion = new Vector2D(newPostion.x, 680)
          ballon.moveVectorIndex += 1
        }
      }
      case 8 => {
        if (newPostion.x >= 1200) {
          return false
        }
      }
      case _ => {
        ballon.position = newPostion
        return false
      }
    }
    ballon.position = newPostion
    return true
  }
}
