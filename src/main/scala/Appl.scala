import Vectors._
import Ballons._
import Defenders._
import Appl.stage
import Bullets.Bullet
import Coins.Coin
import javafx.scene.paint.ImagePattern
import jdk.jfr.internal.consumer.EventLog.stop
import scalafx.animation.AnimationTimer
import scalafx.application.{JFXApp3, Platform}
import scalafx.beans.property.{IntegerProperty, ObjectProperty}
import scalafx.geometry.Insets
import scalafx.scene.Scene
import scalafx.scene.effect.{BlendMode, DropShadow}
import scalafx.scene.image.Image
import scalafx.scene.input.TouchPoint.State
import scalafx.scene.layout.{HBox, Pane, StackPane}
import scalafx.scene.paint.Color._
import scalafx.scene.paint._
import scalafx.scene.shape.Rectangle
import scalafx.scene.text.Text
import scalafx.Includes._
import scalafx.scene.input.RotateEvent.Rotate

import java.lang.Thread.sleep
import java.nio.file.Paths


object Appl extends JFXApp3 {

  override def start(): Unit = {
    val v1 = new Vector2D(25, 75)
    val v2 = new Vector2D(25, 65)
    val v3 = new Vector2D(90, 250)
    val v4 = new Vector2D(180, 350)

    var money = 0
    val coin = new Coin()

    var ballonsList: List[Ballon] = List(new FastBallon(v1), new SlowBallon(v2), new BossBallon(v1+v2))
    var defendersList: List[Defender] = List(new FastDefender(v3), new SlowDefender(v4))
    var bulletsList: List[Bullet] = List();
    var objectsToDraw: List[Rectangle] = List()

    def addRectanglesToDraw(): Unit = {
      objectsToDraw = ballonsList.map{ ballon =>
        var rectangle = new Rectangle {
          x = ballon.position.x
          y = ballon.position.y
          width = 50
          height = 60
        }
        rectangle.setFill(ballon.imgPattern)
        rectangle
      }

      objectsToDraw = objectsToDraw ::: defendersList.map{defender =>
        var rectangle = new Rectangle {
          x  = defender.position.x
          y = defender.position.y
          width = 50
          height = 50
        }
        rectangle.setFill(defender.imgPattern)
        rectangle
      }

      objectsToDraw = objectsToDraw ::: bulletsList.map { bullet =>
        var rectangle = new Rectangle {
          x =  bullet.position.x
          y = bullet.position.y
          width = 20
          height = 20
        }
        rectangle.setFill(bullet.imgPattern)
        rectangle
      }
    }

    val state = ObjectProperty(ballonsList.last.position)

    var newPostion = new Vector2D(1.0, 2.0)
    val border = new Vector2D(300.0, 300.0)
    // Rejestrujemy onUpdate nasłuchiwacz dla AnimationTimer

    val timer = AnimationTimer { now =>
      ballonsList.foreach{ ballon =>
        newPostion = ballon.position + new Vector2D(0.11, 0.11)
        if(ballon.position.toUpperRight(border)) {
           ballon.changePosition(newPostion)
        }
        else {
          ballonsList = ballonsList.filterNot(_ == ballon)
          val mons: List[Ballon] = List(new FastBallon(v1))
          ballonsList = ballonsList ::: mons
        }
        if (ballonsList.isEmpty) {
          // end game
        }

        defendersList.foreach{defender =>
          if(defender.checkRange(ballon.position)) {
            val damage = defender.shoot()
            if(damage > 0){
              val bullet = new Bullet(defender.position + new Vector2D(15, 15), ballon, damage)
              bulletsList = bulletsList ::: List(bullet)
            }
          }
        }
      }

      bulletsList.foreach{bullet =>
        if(bullet.move()){
            bulletsList = bulletsList.filterNot(_ == bullet)
            if(bullet.target.currentHealth <= 0){
              ballonsList = ballonsList.filterNot(_ == bullet.target)
              money += bullet.target.reward
              println("money: " + money)
            }
        }
      }
      defendersList.foreach(defender => defender.decreaseCoolDown())
      addRectanglesToDraw()
      if(ballonsList.isEmpty){
        state.value = defendersList.last.position
      }
      else state.value = ballonsList.last.position
    }
    timer.start() // Uruchamiamy AnimationTimer

    stage = new JFXApp3.PrimaryStage {
      width = 1200
      height = 800
      scene = new Scene {
        fill = White
        content = objectsToDraw

        state.onChange(Platform.runLater {

          val text = new Text {
            text = money.toString
            style = "-fx-font-size: 100pt"
            x = 110
            y = 100
            fill = new LinearGradient(
              stops = Stops(Yellow, Orange)
            )}

          val coinRectangle = new Rectangle {
            x = 0
            width = 100
            height = 100
          }

          coinRectangle.setFill(coin.imgPattern)

          val myPane = new Pane()

          myPane.getChildren.add(coinRectangle)
          myPane.getChildren.add(text)
          objectsToDraw.foreach{
            rectangle =>
              myPane.getChildren.add(rectangle)
          }
          content = myPane
        })
      }
    }
  }
}

