package damas.juego
import scala.util.Random;

/**
  * @author david
  */
object Tablero {

     /**
      * Declaracion de variables globales.
      */
     private val POS_TAB_EMPTY  = 10;
     val CAR_ROW_COLUMN = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890"
     
     /**
      * Numero de fichas x jugador en funcion de dimenciones del Tablero.
      */
     def numFichasXjugador(dim: Int) = (((Math.log10(dim) / Math.log10(2)).toInt + (if (dim > 8) 2 else 0)) * (dim / 2));

     /**
       * Funcion que se encarga de generar el tablero de juego en funcion de las dimensiones y la dificul-
       * tad de juego seleccionada por el usuario.
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
       * Funcion que se encaraga de generar las fichas que se van colocando en el tablero durante su crecion
       * En funcion del las dimensiones del tablero ademas genera bombas y damas especiales en funcion de la
       * dificultada seleciconada.
       *
       *    - column      = Entero con las columnas.
       *    - row         = Entero con las filas.
       *    - cont        = Contado de almacena el numero de filas del tablero que se an construido.
       *    - dificultad  = Entero con la dificultad del juego selecionada de 1 al 5 siendo esto los niveles
       *                    disponibles.
       */
     private def generarFicha(column: Int, row: Int, cont: Int, dificultad: Int): Int = {
          val numRowFicha = (Math.log10(cont) / Math.log10(2)).toInt + (if (cont > 8) 2 else 0);      // Determinamos el numero def fichas a colocar.
          val bom = Random.nextInt(dificultad + 2);                                                   // Generamos bombas y fichas especiales en funcion de la dificultad selecionada.
          /**
            * Calculamos el pociconamiento de piezas y bombas y piezas especiales en el tablero en
            * funcion de las dimenciones del tablero lo multiplicamos por el doble para cuando nos
            * salimos de las dimensiones conbecionales de un tablero de damas.
            */
          return (if ((column + (if (row % 2 == 0) 1 else 0)) % 2 == 0) (if (row > (cont - numRowFicha)) 21 + bom else if (row <= numRowFicha) 32 + bom else POS_TAB_EMPTY) else POS_TAB_EMPTY);
     }

     /**
       * Medo Privado Que se encarga de Imprimir la fichas del Tablero en la
       * posiciones que estan ocupan.
       */
     private def imprimirTablero(tablero: List[ Int ], row: Int, col: Int): String = {
          val dim = (Math.sqrt(tablero.length)).toInt;                                    // Calculamos el tamño de las filas y columnass sera el mismo ya que es una matriz cudrada.
          if (row < dim) {
               val pos: Int = (row * dim + col)                                           // Calaculamos la posicion del tablero
               (if (pos < tablero.length) {
                    val bloque: Int = tablero(pos);  
                    val (out: String) = (col match {
                         case 0 ⇒ String.format("\n %s%4s%s ━┫", Console.CYAN + Console.BOLD, CAR_ROW_COLUMN.substring(row, row + 1), Console.RESET); 
                         case _ ⇒ 
                              if (col == dim) {
                                   String.format("┣━ %s%-4s%s", Console.CYAN + Console.BOLD,  CAR_ROW_COLUMN.substring(row, row + 1), Console.RESET);
                              } else {
                                   String.format("%s", "");
                              }
                    }).concat(if (((row + col) % 2 == 0)) Console.BLUE_B else Console.BLACK_B); // Dibujamos el tablero de damas.
                    if (col == dim) {
                         out + Console.RESET
                    } else {
                         val foreground = (Console.INVISIBLE, Console.RED, Console.WHITE, Console.CYAN, Console.GREEN, Console.MAGENTA, Console.YELLOW, Console.MAGENTA);
                         val ficha = String.format(" %s ", if (bloque != POS_TAB_EMPTY) (if ((bloque - (bloque % 10)) > POS_TAB_EMPTY * 2) "■" else "●") else "‌ ").toLowerCase();
                         out + Console.BOLD + foreground.productElement(bloque % 10) + ficha + Console.RESET
                    }
               }) + imprimirTablero(tablero, (row + (if (col == dim) 1 else 0)), (if (col < dim) (col + 1) else 0));
          } else {
               return "";
          }
     }

     /**
       * Metodo Que se encaraga de mostrar el tablero de Juego de forma
       * Recursiva.
       * 
       * 	tablero =  Recibe la lista que constitulle el tablero de juego.
       * 	dim	   =  Es un contador utilizado para almacenra la dimension del
       * 			 tablero a medida que este se va construllendo.
       *  cont    =  Contador que controla que parte del algoritmo se ejecuta en
       * 			 cada ineteracion recursiva.
       * 
       */
     def echoTablero(tablero: List[Int], dim: Int, cont: Int): String = {
          if (dim != Math.sqrt(tablero.length).toInt + 1) {
               (if (cont == 0 || cont == 4 ) {
                    Console.MAGENTA + Console.BOLD + String.format(if (dim == 1) "\n %9s" else if (dim < 9) "%3s" else "%3.95s", CAR_ROW_COLUMN.substring(dim - 1, dim)) + Console.RESET
               } else {
                    (if (cont != 2) {
                         String.format(if (dim == 1) "\n %10s" else "%3s", dim match {
                              case 1 ⇒  (if (cont == 1) "┏━┻━" else "┗━┳━"); 
                              case _ ⇒
                                   if (dim == Math.sqrt(tablero.length).toInt) {
                                        (if (cont == 1) "━┻━┓" else "━┳━┛");
                                   } else {
                                        (if (cont == 1) "━┻━"  else "━┳━");
                                   }
                         })    
                    } else "");
               }) + this.echoTablero(tablero, (if (cont != 2) dim + 1 else Math.sqrt(tablero.length).toInt + 1), cont);
          } else {
               if (cont < 4 ) {
                    (if (cont == 2) {
                         this.imprimirTablero(tablero, 0, 0);
                    } else "") + this.echoTablero(tablero, 1, cont + 1) 
               } else {
                    return ""    
               }
          }
     }
     
     /**
      * 
      */
     def damasPlayBom(tablero:List[Int], mov:(Int, Int, Int), select_error:Boolean, turno:Int): Boolean = {
          val dama:Int = tablero((mov._1 * Math.sqrt(tablero.length).toInt) + mov._2);
          if (!select_error && (dama != POS_TAB_EMPTY) && (if (turno == 1) 20 else 30).equals(dama - (dama % 10))) {
               val movV:Int = Array(-1, 1).apply((mov._3 % 10));					        // Determinamos el movimiento vertical en funcion de la direcion.
			val movH:Int = Array(-1, 1).apply(((mov._3 - (mov._3 % 10)) / 10) - 1);       // Determinamos el movimiento horizontal en funcion de la direcion.
			val isError = isCamarada(tablero, new Tuple2(movV, movH), mov._1, mov._2, 0);
               if (!isError) {
                    this.setMovGamen(tablero, new Tuple2(movV, movH), (dama % 10), 0);
               } else {
                    this.damasPlayBom(tablero, mov, isError, -1);
               }
          } else  {
               if (turno != -1) {
                    val ficha = (if (turno == 0) "■" else "●");
                    println("\n ❈ " + Console.RED + "ERROR" + Console.RESET + ": NO pudee mover la ficha selecionada, las fichas que usted pude tocar son '" + ficha + "' !!!");
               } else if (select_error) {
                    println("\n ❈ " + Console.RED + "ERROR" + Console.RESET + ": La jugada realizada nos se puede cosidera una jugada valida !!!");
               }
               return select_error;
          }
     }
     
     private def isCamarada(tablero:List[Int], mov:(Int, Int), row:Int, col:Int, pos:Int): Boolean = {
          val isFriend = ((col + (pos * mov._2) > -1) && (col + (pos * mov._2) < Math.sqrt(tablero.length).toInt)) &&
                         ((row + (pos * mov._1) > -1) && (row + (pos * mov._1) < Math.sqrt(tablero.length).toInt));
          if (isFriend) {
               val fichInMov = tablero((row + (pos * mov._1)) * (Math.sqrt(tablero.length).toInt) + (col + (pos * mov._2)));
               val victima   = tablero((row + ((pos - 1) * mov._1)) * (Math.sqrt(tablero.length).toInt) + (col + ((pos - 1) * mov._2)));
               return isFriend && (victima - ((victima % 10)) != (fichInMov - (fichInMov % 10)));
          }
          return isFriend;
     }
     
     private def setMovGamen(tablero:List[Int], mov:(Int, Int), type_bom:Int, cont: Int): Boolean = {
       
          return true;
     }
     
}