package damas.juego

object TestTablero {

     val tablero1 = Tablero.generarTablero(8, 8, 8, 1)
                                                  //> tablero1  : List[Int] = List(21, 10, 23, 10, 22, 10, 22, 10, 10, 22, 10, 23, 
                                                  //| 10, 23, 10, 21, 22, 10, 23, 10, 22, 10, 21, 10, 10, 10, 10, 10, 10, 10, 10, 1
                                                  //| 0, 10, 10, 10, 10, 10, 10, 10, 10, 10, 34, 10, 33, 10, 34, 10, 32, 32, 10, 32
                                                  //| , 10, 34, 10, 32, 10, 10, 33, 10, 33, 10, 34, 10, 33, 32, 10, 34, 10, 32, 10,
                                                  //|  34, 10)

                                                  
     tablero1.length;                             //> res0: Int = 72
     
     Tablero.imprimirTablero(tablero1, 0, 0);     //> 
                                                  //|   0 ━━┫  O       O       O       O    
                                                  //|   1 ━━┫      O       O       O       O
                                                  //|   2 ━━┫  O       O       O       O    
                                                  //|   3 ━━┫                               
                                                  //|   4 ━━┫                               
                                                  //|   5 ━━┫      #       #       #       #
                                                  //|   6 ━━┫  #       #       #       #    
}