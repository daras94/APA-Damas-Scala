package damas.juego

object TestTablero {

     val tablero1 = Tablero.generarTablero(8, 8, 8, 0)
<<<<<<< HEAD
                                                  //> tablero1  : List[Int] = List(10, 22, 10, 21, 10, 21, 10, 22, 22, 10, 22, 10, 
                                                  //| 22, 10, 21, 10, 10, 22, 10, 22, 10, 22, 10, 21, 10, 10, 10, 10, 10, 10, 10, 1
                                                  //| 0, 10, 10, 10, 10, 10, 10, 10, 10, 32, 10, 32, 10, 32, 10, 33, 10, 10, 33, 10
                                                  //| , 32, 10, 33, 10, 32, 33, 10, 32, 10, 33, 10, 32, 10)

                                                 
=======
                                                  //> tablero1  : List[Int] = List(10, 22, 10, 21, 10, 22, 10, 21, 21, 10, 21, 10, 
                                                  //| 22, 10, 21, 10, 10, 21, 10, 22, 10, 22, 10, 21, 10, 10, 10, 10, 10, 10, 10, 1
                                                  //| 0, 10, 10, 10, 10, 10, 10, 10, 10, 32, 10, 32, 10, 32, 10, 33, 10, 10, 33, 10
                                                  //| , 33, 10, 32, 10, 32, 32, 10, 32, 10, 32, 10, 33, 10)
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

                                                  
     val tablero2 = insertMovimiento(tablero1, 1, 8, tablero1(0), 0);
                                                  //> tablero2  : List[Int] = List(10, 10, 10, 21, 10, 22, 10, 21, 10, 10, 21, 10,
                                                  //|  22, 10, 21, 10, 10, 21, 10, 22, 10, 22, 10, 21, 10, 10, 10, 10, 10, 10, 10,
                                                  //|  10, 10, 10, 10, 10, 10, 10, 10, 10, 32, 10, 32, 10, 32, 10, 33, 10, 10, 33,
                                                  //|  10, 33, 10, 32, 10, 32, 32, 10, 32, 10, 32, 10, 33, 10)
                                                  
     print(tablero1.length);                      //> 64
     print(tablero2.length);                      //> 64
     print(tablero1.toString());                  //> List(10, 22, 10, 21, 10, 22, 10, 21, 21, 10, 21, 10, 22, 10, 21, 10, 10, 21
                                                  //| , 10, 22, 10, 22, 10, 21, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 1
                                                  //| 0, 10, 10, 10, 32, 10, 32, 10, 32, 10, 33, 10, 10, 33, 10, 33, 10, 32, 10, 
                                                  //| 32, 32, 10, 32, 10, 32, 10, 33, 10)
     
     print(tablero2.toString());                  //> List(10, 10, 10, 21, 10, 22, 10, 21, 10, 10, 21, 10, 22, 10, 21, 10, 10, 21
                                                  //| , 10, 22, 10, 22, 10, 21, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 1
                                                  //| 0, 10, 10, 10, 32, 10, 32, 10, 32, 10, 33, 10, 10, 33, 10, 33, 10, 32, 10, 
>>>>>>> cc3f73c... Merge branch 'develop-2' of https://github.com/daras94/APA-Damas-Scala into develop-2
     
}