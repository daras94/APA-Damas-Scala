package damas.juego
import scala.util.Random;
object TestTablero {

     val POS_TAB_EMPTY = 10                       //> POS_TAB_EMPTY  : Int = 10

     /**
       * Funcion Que se encaraga de generra el tablero de juego en funcion de las dimensiones y la dificul-
       * tad de jueo seleciionada por el usuario.
       */
     def generarTablero(column: Int, row: Int, cont: Int, dificultad: Int): List[ Int ] = column match {
          case 0 =>
               row match {
                    case 0 => Nil;
                    case _ => generarTablero((row + cont - 1), row - 1, cont + 1, dificultad);
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
          val numRowFicha = (Math.log10(row + cont) / Math.log10(2)).toInt + (if (row + cont > 8) 2 else 0);   // Determinamos el numero def fichas a colocar.
          val bom = Random.nextInt(dificultad + 2);                                         // Generamos bombas y fichas especiales en funcion de la dificultad selecionada.
          /**
            * Calacualmos el pociconamiento de piezas y bombas y piezas especiales en el tablero en
            * funcion de las dimenciones del tablero lo multiplicamos por el doble para cuando nos
            * salimos de las dimensiones conbecionales de un tablero de damas.
            */
          return (if ((row * (row + cont)) + column % 2 == 0) (if (row > ((row + cont) - numRowFicha)) 31 + bom else POS_TAB_EMPTY) else (if (row < numRowFicha) 22 + bom else POS_TAB_EMPTY));
     }                                            //> generarFicha: (column: Int, row: Int, cont: Int, dificultad: Int)Int

     val tablero1 = generarTablero(8, 8, 0, 1)    //> tablero1  : List[Int] = List(10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10
                                                  //| , 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 1
                                                  //| 0, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 23, 22, 24, 22, 22, 23, 
                                                  //| 24, 22, 24, 22, 23, 24, 23, 23, 22, 10, 23, 10, 23, 10, 24)
                                                  
    tablero1.length;                              //> res0: Int = 64
     
     def imprimirTablero(tablero: List[ Int ], row: Int, col: Int): Unit = {
          val dim = (Math.sqrt(tablero.length)).toInt;                                          // Calculamos el tamño de las filas y columnass sera el mismo ya que es una matriz cudrada.
          if (row < dim ) {
               val pos:Int = (row * dim + col)                                                  // Calaculamos la posicion del tablero
               if ((col < dim) && ( pos < tablero.length)) {
                    val bloque: Int = tablero(pos);
                    val (out: String) = col match {
                         case 0 ⇒ String.format("\n %2s ━━┫", row.toString());
                         case _ ⇒ " ";
                    }
                    print(out + String.format("%3s", if (bloque != POS_TAB_EMPTY) (if ((bloque - (bloque % 10)) > POS_TAB_EMPTY * 2) "#" else "O") else " "));
               }
               imprimirTablero(tablero, (row + (if (col == dim) 1 else 0)), (if (col < dim) (col + 1) else 0));
          }
     }                                            //> imprimirTablero: (tablero: List[Int], row: Int, col: Int)Unit

     imprimirTablero(tablero1, 0, 0);             //> 
                                                  //|   0 ━━┫                               
                                                  //|   1 ━━┫                               
                                                  //|   2 ━━┫                               
                                                  //|   3 ━━┫                               
                                                  //|   4 ━━┫                               
                                                  //|   5 ━━┫              O   O   O   O   O
                                                  //|   6 ━━┫  O   O   O   O   O   O   O   O
}