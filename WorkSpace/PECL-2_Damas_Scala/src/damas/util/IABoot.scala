package damas.util
import damas.juego.Tablero._

object NodoMovimiento {
  val peso = List(1, 2, 3, 4)
  val nodoMovimiento = List((Int, Int, Int), peso)
}
object IABoot {

  val direccion = List(10, 11, 20, 21)

  def posiblesMovimientos(mov: (Int, Int, Int),turno: Int, tam: Int): List[Int] = {
     tam match {
        case 8 ⇒  if(tam==8) {
                    posiblesMovimientos((0,0,direccion(0)),turno,tam)
                  }
                
        case 16 ⇒ 
        case 32 ⇒ 
     }
    
  }

  def evaluarPosicion(tablero: List[Int], mov: (Int, Int, Int)): Boolean = {
    val movH = List(-1, 1).apply((mov._3 % 10));
    val movV = List(-1, 1).apply(((mov._3 - (mov._3 % 10)) / 10) - 1);
    
    return (isValido(tablero, movV, movH, mov._1, mov._2, 0))
  }

  def IA(tablero: List[Int], mov: (Int, Int, Int), height: Int, turno: Int, tamaño: Int): (Int, Int, Int) = {
    if (evaluarPosicion(tablero, mov)) {
      val Listmov = posiblesMovimientos(mov, turno, tamaño) 
      return (0, 0, 0)
    } else {
      return (0, 0, 0)
    }
  }
}