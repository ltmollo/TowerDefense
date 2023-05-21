import Ballons._
import Bullets.Bullet
import Coins.Coin
import Defenders.DefenderChoice.Choice._
import Defenders._
import Graphics.{DrawMapObjects, DrawRectangle, DrawText}
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



object Appl extends JFXApp3 {

  override def start(): Unit = {
    val beginVector = new Vector2D(0, 170)

    var money = 1000
    var health = 3
    val coin = new Coin()
    val drawRectangle = new DrawRectangle()
    val drawText = new DrawText()
    val moveBallon = new MoveBallon()
    val setDefenderChoice = new SetDefenderChoice()
    val drawDefenderMenu = new DefendersMenu()
    val addNewDefender = new AddDefender()
    val drawMapObjects = new DrawMapObjects()
    val myMap = new Map
    var defenderChoice : Choice = FastDefender


    var ballonsList: List[Ballon] = List(new FastBallon(beginVector), new SlowBallon(beginVector), new BossBallon(beginVector))
    var defendersList: List[Defender] = List(new FastDefender(new Vector2D(530, 250)))
    var bulletsList: List[Bullet] = List()
    val defendersToChoose: List[Defender] = List(new FastDefender(new Vector2D(0, 250)),
      new SlowDefender(new Vector2D(0, 360)), new RangeDefender(new Vector2D(0, 470)))
    var objectsToDraw: List[Rectangle] = List()


    val state = ObjectProperty(ballonsList.last.position)


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
      objectsToDraw = drawMapObjects(myMap, coin, ballonsList, defendersList, bulletsList)

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


        onMouseClicked = (event: MouseEvent) => {
          val clickX = event.getX
          val clickY = event.getY
          val clickVector = new Vector2D(clickX, clickY)

          if(clickVector.toLowerLeft(defendersToChoose.head.position) &&
          clickVector.toUpperRight(defendersToChoose.last.position + new Vector2D(80, 110))) {
            defenderChoice = setDefenderChoice(clickVector)
          }
          else if(!clickVector.toLowerLeft(new Vector2D(0, 120))){
            println("Wrong place")
          }

          else{
            val newDefender = addNewDefender(clickVector, defenderChoice, money)
            if(newDefender!= null){
              defendersList = defendersList :+ newDefender
              money -= newDefender.cost
            }
          }

        }

        state.onChange(Platform.runLater {

          val moneyText = drawText(money, 100, 110 ,100, "money")
          val healthText = drawText(health, 100, 1100 ,100, "health")
          val myPane = new Pane()

          myPane.getChildren.add(moneyText)
          myPane.getChildren.add(healthText)
          objectsToDraw.foreach{
            rectangle =>
              myPane.getChildren.addAll(rectangle)
          }
          myPane.getChildren.add(drawDefenderMenu(defendersToChoose, money, coin))
          content = myPane
        })
      }
    }
  }
}

