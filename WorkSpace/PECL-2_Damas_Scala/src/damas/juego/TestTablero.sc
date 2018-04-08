package damas.juego

object TestTablero {

     val tablero1 = Tablero.generarTablero(8, 8, 8, 0)
                                                  //> tablero1  : List[Int] = List(10, 21, 10, 22, 10, 21, 10, 22, 22, 10, 22, 10, 
                                                  //| 22, 10, 21, 10, 10, 21, 10, 22, 10, 21, 10, 22, 10, 10, 10, 10, 10, 10, 10, 1
                                                  //| 0, 10, 10, 10, 10, 10, 10, 10, 10, 33, 10, 33, 10, 32, 10, 33, 10, 10, 32, 10
                                                  //| , 32, 10, 33, 10, 32, 32, 10, 32, 10, 33, 10, 33, 10)
     def insertMovimiento(tablero: List[Int], actual:Int, victima:Int, value:Int, cont:Int): List[Int] = {
          if (cont < tablero.length) {
               val value_aux = (if (cont == actual) 10 else if (cont == victima) value else tablero(cont));
               if ((actual, victima) != (0, 0)) {
                    val (act, vic) = ((if (cont == actual) 0 else actual), (if (cont == victima) 0 else victima));
                    value_aux :: insertMovimiento(tablero, act, vic, value, (cont + 1));
               } else {
                    value_aux :: insertMovimiento(tablero, actual, victima, value, (cont + 1));
               }
          } else {
               Nil;
          }
     }                                            //> insertMovimiento: (tablero: List[Int], actual: Int, victima: Int, value: Int
                                                  //| , cont: Int)List[Int]

                                                  
     Tablero.numFichasXjugadorInCurse(tablero1, 0, (0, 0));
                                                  //> res0: (Int, Int) = (12,12)

}