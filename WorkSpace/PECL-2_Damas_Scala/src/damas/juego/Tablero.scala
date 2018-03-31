package damas.juego
import scala.util.Random;
/**
  * @author david
  */
object Tablero {

     val POS_TAB_EMPTY = 10

     /**
       * Funcion Que se encaraga de generra el tablero de juego en funcion de las dimensiones y la dificul-
       * tad de jueo seleciionada por el usuario.
       */
     def generarTablero(column: Int, row: Int, cont: Int, dificultad: Int): List[ Int ] = column match {
          case 0 =>
               row match {
                    case 0 => Nil;
                    case _ => generarTablero(cont, row - 1, cont, dificultad);
               };
          case _ => generarFicha(column, row, cont, dificultad) :: generarTablero(column - 1, row, cont, dificultad);
     }

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
     private def generarFicha(column: Int, row: Int, cont: Int, dificultad: Int): Int = {
          val numRowFicha = (Math.log10(cont) / Math.log10(2)).toInt + (if (cont > 8) 2 else 0);   // Determinamos el numero def fichas a colocar.
          val bom = Random.nextInt(dificultad + 2);                                                // Generamos bombas y fichas especiales en funcion de la dificultad selecionada.
          /**
            * Calacualmos el pociconamiento de piezas y bombas y piezas especiales en el tablero en
            * funcion de las dimenciones del tablero lo multiplicamos por el doble para cuando nos
            * salimos de las dimensiones conbecionales de un tablero de damas.
            */
          return (if ((column + (if (row % 2 == 0) 0 else 1)) % 2 == 0) (if (row > (cont - numRowFicha)) 21 + bom else if (row <= numRowFicha) 32 + bom else POS_TAB_EMPTY) else POS_TAB_EMPTY);
     }
     
     /**
      * 
      */
     def echoTablero(tablero: List[Int], dim: Int): Unit = {
          this.imprimirColumnas(Math.sqrt(tablero.length).toInt, 0);
          this.imprimirTablero(tablero, dim, dim)
          println("\n");
     }
     
     /**
       *
       */
     private def imprimirTablero(tablero: List[ Int ], row: Int, col: Int): Unit = {
          val dim = (Math.sqrt(tablero.length)).toInt;                                          // Calculamos el tamño de las filas y columnass sera el mismo ya que es una matriz cudrada.
          if (row < dim ) {
               val pos:Int = (row * dim + col)                                                  // Calaculamos la posicion del tablero
               if ((col < dim) && ( pos < tablero.length)) {
                    val bloque: Int = tablero(pos);
                    val (out: String) = col match {
                         case 0 ⇒ String.format("\n %4s ━━┫", (row + 1).toString());
                         case _ ⇒ " ";
                    }
                    print(out + String.format("%3s", if (bloque != POS_TAB_EMPTY) (if ((bloque - (bloque % 10)) > POS_TAB_EMPTY * 2) "#" else "O") else " "));
               }
               imprimirTablero(tablero, (row + (if (col == dim) 1 else 0)), (if (col < dim) (col + 1) else 0));
          }
     }
     
     /**
      * 
      */
     private def imprimirColumnas(dim: Int, cont: Int): Unit = {
          
     }

}