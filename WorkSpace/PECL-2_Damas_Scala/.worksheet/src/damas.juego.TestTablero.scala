package damas.juego
import scala.util.Random;
object TestTablero {;import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(95); 

     val POS_TAB_EMPTY = 10;System.out.println("""POS_TAB_EMPTY  : Int = """ + $show(POS_TAB_EMPTY ));$skip(603); 

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
     };System.out.println("""generarTablero: (column: Int, row: Int, cont: Int, dificultad: Int)List[Int]""");$skip(1554); 

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
     };System.out.println("""generarFicha: (column: Int, row: Int, cont: Int, dificultad: Int)Int""");$skip(48); 

     val tablero1 = generarTablero(8, 8, 0, 1);System.out.println("""tablero1  : List[Int] = """ + $show(tablero1 ));$skip(72); val res$0 = 
                                                  
    tablero1.length;System.out.println("""res0: Int = """ + $show(res$0));$skip(1046); ;
     
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
     };System.out.println("""imprimirTablero: (tablero: List[Int], row: Int, col: Int)Unit""");$skip(39); 

     imprimirTablero(tablero1, 0, 0);}
}
