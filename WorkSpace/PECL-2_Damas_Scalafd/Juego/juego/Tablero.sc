package juego

object tablero {

     val POS_TAB_JUEGO_EMPTY = 10;                //> POS_TAB_JUEGO_EMPTY  : Int = 10

     /**
       * Funcion Que se encaraga de generra el tablero de juego en funcion de las dimensiones y la dificul-
       * tad de jueo seleciionada por el usuario.
       */
     def generarTablero(column: Int, row: Int, cont: Int, dificultad: Int): List[ Int ] = column match {
          case 0 =>
               row match {
                    case 0 => Nil;
                    case _ => generarTablero((row + (cont - 1)), row - 1, cont + 1, dificultad);
               };
          case _ => generarFicha(column, row, cont, dificultad) :: generarTablero(column - 1, row, cont, dificultad);
     }                                            //> generarTablero: (column: Int, row: Int, cont: Int, dificultad: Int)List[Int]
                                                  //| 

     /**
       * Funcion Que se encaraga de generra las fichas que se van colocando en el tablero durante su crecion
       * En funcion del las dimensiones del tablero ademas genera bombas y dams especiales en funcion de la
       * dificultada selecionada.
       *
       *    - column      = Entero con las columnas.
       *    - row         = Entero con las filas.
       *    - cont        = Contado de almacena el numero de filas del tablero que se an construido.
       *    - dificultad  = Entero con la dificultad del juego selecionada de 1 al 5 siendo esto los niveles
       *                    disponibles.
       */
     def generarFicha(column: Int, row: Int, cont: Int, dificultad: Int): Int = {
          val tam_tablero = row + cont;                                                                   // Detenimamos el tamaño del tablero de juego que.
          val numRowFicha = (Math.log10(tam_tablero) / Math.log10(2)) + (if (tam_tablero > 8) 2 else 0);  // Determinamos el numero def fichas a colocar.
          val bom = (new scala.util.Random()).nextInt(dificultad + 2);                                    // Generamos bombas y fichas especiales en funcion de la dificultad selecionada.
          /**
            * Calacualmos el pociconamiento de piezas y bombas y piezas especiales en el tablero en
            * funcion de las dimenciones del tablero lo multiplicamos por el doble para cuando nos
            * salimos de las dimensiones conbecionales de un tablero de damas.
            */
          return (if ((column + (if ((row - 1) % 2 == 0) 1 else 0)) % 2 == 0) (if (row > (tam_tablero - numRowFicha)) 21 + bom else POS_TAB_JUEGO_EMPTY) else (if (row < numRowFicha) 32 + bom else POS_TAB_JUEGO_EMPTY));
     }                                            //> generarFicha: (column: Int, row: Int, cont: Int, dificultad: Int)Int

     val tablero = generarTablero(16, 16, 0, 5)   //> tablero  : List[Int] = List(24, 10, 25, 10, 26, 10, 27, 10, 21, 10, 23, 10,
                                                  //|  26, 10, 22, 10, 22, 10, 27, 10, 23, 10, 24, 10, 25, 10, 26, 10, 24, 10, 23
                                                  //| , 10, 23, 10, 27, 10, 23, 10, 27, 10, 22, 10, 21, 10, 22, 10, 23, 10, 21, 1
                                                  //| 0, 25, 10, 22, 10, 21, 10, 25, 10, 23, 10, 23, 10, 21, 10, 23, 10, 21, 10, 
                                                  //| 22, 10, 22, 10, 21, 10, 26, 10, 27, 10, 21, 10, 23, 10, 25, 10, 23, 10, 26,
                                                  //|  10, 25, 10, 21, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10
                                                  //| , 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 1
                                                  //| 0, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 
                                                  //| 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10,
                                                  //|  10, 10, 10, 10, 10, 38, 10, 32, 10, 34, 10, 37, 10, 38, 10, 37, 10, 35, 10
                                                  //| , 37, 10, 36, 10, 37, 10, 38, 10, 38, 10, 32, 10, 34, 10, 34, 10, 36, 10, 3
                                                  //| 7, 10, 33, 10, 32, 10, 33, 10, 32, 10, 37, 10, 34, 10, 36, 10, 38, 10, 34, 
                                                  //| 10, 32, 10, 36, 10, 37, 10, 32, 10, 32, 10, 33, 10, 36, 10, 33, 10, 37, 10,
                                                  //|  32, 10, 33, 10, 38, 10, 34, 10, 32, 10, 37, 10, 32, 10, 33, 10, 38, 10, 36
                                                  //| )

}