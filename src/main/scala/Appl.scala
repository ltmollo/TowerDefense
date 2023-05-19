import Ballons._
import Bullets.Bullet
import Coins.Coin
import Defenders._
import Graphics.{DrawRectangle, DrawText}
import Vectors._
import Maps._
import scalafx.scene.image.Image
import scalafx.animation.AnimationTimer
import scalafx.application.{JFXApp3, Platform}
import scalafx.beans.property.ObjectProperty
import scalafx.scene.Scene
import javafx.scene.layout.{Background, BackgroundImage, BackgroundPosition, BackgroundRepeat, BackgroundSize}
import javafx.scene.paint.ImagePattern
import scalafx.scene.image.Image
import scalafx.scene.layout.Pane
import scalafx.scene.paint.Color._
import scalafx.scene.shape.Rectangle


object Appl extends JFXApp3 {

  override def start(): Unit = {
    val beginVector = new Vector2D(0, 170)

    var money = 0
    var health = 3
    val coin = new Coin()
    val drawRectangle = new DrawRectangle()
    val drawText = new DrawText()
    val moveBallon = new MoveBallon()
    val myMap = new Map

    var ballonsList: List[Ballon] = List(new FastBallon(beginVector), new SlowBallon(beginVector), new BossBallon(beginVector))
    var defendersList: List[Defender] = List(new FastDefender(new Vector2D(530, 250)))
    var bulletsList: List[Bullet] = List()
    var objectsToDraw: List[Rectangle] = List()

    def addRectanglesToDraw(): Unit = {

      objectsToDraw = List(drawRectangle(new Vector2D(0, 150), 1200, 600, myMap.imgPattern))

      objectsToDraw = objectsToDraw ::: List(drawRectangle(coin.position, 100, 100, coin.imgPattern))

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

    // Rejestrujemy onUpdate nasłuchiwacz dla AnimationTimer

    val timer = AnimationTimer { now =>
      ballonsList.foreach{ ballon =>

        if(!moveBallon(ballon)) {
          ballonsList = ballonsList.filterNot(_ == ballon)
          ballonsList = ballonsList ::: List(new FastBallon(beginVector))
          health -= 1
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

          val moneyText = drawText(money, "money")
          val healthText = drawText(health, "health")
          val myPane = new Pane()

          myPane.getChildren.add(moneyText)
          myPane.getChildren.add(healthText)
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

