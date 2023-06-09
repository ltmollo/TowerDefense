import Ballons._
import Bullets.Bullet
import Coins.Coin
import Defenders.DefenderChoice.Choice._
import Defenders._
import Graphics.{DrawMapObjects, DrawText}
import Vectors._
import Maps._
import scalafx.animation.AnimationTimer
import scalafx.application.{JFXApp3, Platform}
import scalafx.beans.property.ObjectProperty
import scalafx.scene.Scene
import javafx.scene.input.MouseEvent
import scalafx.scene.Parent.sfxParent2jfx
import scalafx.scene.layout.Pane
import scalafx.scene.paint.Color._
import scalafx.scene.shape.Rectangle

import scala.math.ceil


object Appl extends JFXApp3 {

  override def start(): Unit = {
    var money = 22
    var health = 3
    var round = 1
    var ballonsLeft = 2
    var runGame = false
    val coin = new Coin()
    val drawText = new DrawText()
    val moveBallon = new MoveBallon()
    val setDefenderChoice = new SetDefenderChoice()
    val drawDefenderMenu = new DefendersMenu()
    val addNewDefender = new AddDefender()
    val addNewBallon = new BallonManager()
    val drawMapObjects = new DrawMapObjects()
    val myMap = new Map
    var defenderChoice: Choice = FastDefender

    var ballonsList: List[Ballon] = List(new FastBallon(new Vector2D(0, 170)))
    var defendersList: List[Defender] = List()
    var bulletsList: List[Bullet] = List()
    val defendersToChoose: List[Defender] = List(new FastDefender(new Vector2D(0, 250)),
      new SlowDefender(new Vector2D(0, 360)), new RangeDefender(new Vector2D(0, 470)))
    var objectsToDraw: List[Rectangle] = List()


    val state = ObjectProperty(ballonsList.last.position)


    val timer = AnimationTimer {  now =>

      if ((defendersList.nonEmpty || runGame) && health > 0) {

        ballonsList.foreach { ballon =>

          if (!moveBallon(ballon)) {
            ballonsList = ballonsList.filterNot(_ == ballon)
            health -= 1
            if (health == 0) {
              stopApp()
            }
          }

          defendersList.foreach { defender =>
            if (defender.checkRange(ballon.position)) {
              val damage = defender.shoot()
              if (damage > 0) {
                val bullet = new Bullet(defender.position + new Vector2D(15, 15), ballon, damage)
                bulletsList = bulletsList :+ bullet
              }
            }
          }
        }

        bulletsList.foreach { bullet =>
          if (bullet.move()) {
            bulletsList = bulletsList.filterNot(_ == bullet)
            if (bullet.target.currentHealth <= 0) {
              ballonsList = ballonsList.filterNot(_ == bullet.target)
              money += ceil(bullet.target.reward.toDouble / round.toDouble).toInt
            }
          }
        }
        defendersList.foreach(defender => defender.decreaseCoolDown())
        objectsToDraw = drawMapObjects(myMap, coin, ballonsList, defendersList, bulletsList)

        if (ballonsList.isEmpty && ballonsLeft == 0) {
          round += 1
          ballonsLeft = round * round
        }
        else if (ballonsList.isEmpty) {
          if (defendersList.nonEmpty) {
            state.value = defendersList.last.position
          }
          else if (bulletsList.nonEmpty) {
            state.value = bulletsList.last.position
          }
          else state.value = coin.position
        }
        else {
          state.value = ballonsList.last.position
        }
        if (ballonsLeft > 0) {
          val newBallon = addNewBallon()
          if (newBallon != null) {
            ballonsLeft -= 1
            ballonsList = ballonsList :+ newBallon
          }
        }
        runGame = true

      }
    }

    timer.start()

    def updateMap(): Pane = {
      val moneyText = drawText(money.toString, 100, 110, 100, "money")
      val roundText = drawText(round.toString, 100, 550, 100, "round")
      val myPane = new Pane()

      for (i <- 1 to health) {
        val heartText = drawText("❤", 120, 1020 - (i - 1) * 140, 110, "health")
        myPane.getChildren.add(heartText)
      }

      myPane.getChildren.addAll(moneyText, roundText)
      objectsToDraw.foreach {
        rectangle =>
          myPane.getChildren.add(rectangle)
      }
      myPane.getChildren.add(drawDefenderMenu(defendersToChoose, money, coin))
      myPane
    }

    stage = new JFXApp3.PrimaryStage {
      width = 1200
      height = 800
      scene = new Scene {
        fill = White
        objectsToDraw = drawMapObjects(myMap, coin, ballonsList, defendersList, bulletsList)
        content = updateMap()


        onMouseClicked = (event: MouseEvent) => {
          val clickX = event.getX
          val clickY = event.getY
          val clickVector = new Vector2D(clickX, clickY)

          if (clickVector.toLowerLeft(defendersToChoose.head.position) &&
            clickVector.toUpperRight(defendersToChoose.last.position + new Vector2D(80, 110))) {
            defenderChoice = setDefenderChoice(clickVector)
          }
          else if (clickVector.toLowerLeft(defendersToChoose.head.position) &&
            clickVector.toUpperRight(defendersToChoose.last.position + new Vector2D(80, 190))) {
            if (addNewDefender.rewind()) {
              money += defendersList.last.cost
              defendersList = defendersList.init
            }
          }
          else if (!clickVector.toLowerLeft(new Vector2D(0, 120))) {
            println("Wrong place")
          }

          else {
            val newDefender = addNewDefender(clickVector, defenderChoice, money)
            if (newDefender != null) {
              defendersList = defendersList :+ newDefender
              money -= newDefender.cost
            }
          }

        }

        state.onChange(Platform.runLater {
          content = updateMap()
        })
      }
    }
  }
}

