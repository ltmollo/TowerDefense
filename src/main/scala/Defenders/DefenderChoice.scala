package Defenders

object DefenderChoice {
  object Choice extends Enumeration {
    type Choice = Value
    val FastDefender, SlowDefender, RangeDefender = Value
  }
}
