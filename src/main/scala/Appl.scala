import Ballons._
import Bullets.Bullet
import Coins.Coin
import Defenders._
import Graphics.{DrawMoney, DrawRectangle}
import Vectors._
import scalafx.animation.AnimationTimer
import scalafx.application.{JFXApp3, Platform}
import scalafx.beans.property.ObjectProperty
import scalafx.scene.Scene
import scalafx.scene.layout.Pane
import scalafx.scene.paint.Color._
import scalafx.scene.paint._
import scalafx.scene.shape.Rectangle
import scalafx.scene.text.Text


object Appl extends JFXApp3 {

  override def start(): Unit = {
    val v1 = new Vector2D(25, 75)
    val v2 = new Vector2D(25, 65)
    val v3 = new Vector2D(90, 250)
    val v4 = new Vector2D(180, 350)
    var money = 0
    val coin = new Coin()
    val drawRectangle = new DrawRectangle()
    val drawMoney = new DrawMoney()

    var ballonsList: List[Ballon] = List(new FastBallon(v1), new SlowBallon(v2), new BossBallon(v1+v2))
    var defendersList: List[Defender] = List(new FastDefender(v3), new SlowDefender(v4))
    var bulletsList: List[Bullet] = List();
    var objectsToDraw: List[Rectangle] = List()

    def addRectanglesToDraw(): Unit = {

      objectsToDraw = List(drawRectangle(coin.position, 100, 100, coin.imgPattern))

      objectsToDraw = objectsToDraw ::: ballonsList.map{ ballon =>
        drawRectangle(ballon.position, 50, 60, ballon.imgPattern)
      }

      objectsToDraw = objectsToDraw ::: defendersList.map{defender =>
        drawRectangle(defender.position, 50, 50, defender.imgPattern)
      }

      objectsToDraw = objectsToDraw ::: bulletsList.map { bullet =>
        drawRectangle(bullet.position, 20, 20, bullet.imgPattern)
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

          val text = drawMoney(money)

          val myPane = new Pane()

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

