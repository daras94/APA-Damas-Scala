package damas.juego
import scala.util.Random;
import damas.util._;
import scala.collection.immutable;

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
                    case 1 => Nil;
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
          return (if ((column + (if ((row) % 2 == 0) 1 else 0)) % 2 == 0) (if (row > (cont - numRowFicha)) 21 + bom else if (row <= numRowFicha) 32 + bom else POS_TAB_EMPTY) else POS_TAB_EMPTY);
     }

     /**
       * Medo Privado Que se encarga de Imprimir la fichas del Tablero en la
       * posiciones que estan ocupan.
       */
     private def imprimirTablero(tablero: List[ Int ], row: Int, col: Int): String = {
          val dim = (Math.sqrt(tablero.length)).toInt;                                    // Calculamos el tamño de las filas y columnass sera el mismo ya que es una matriz cudrada.
          if (row < dim) {
               val pos: Int = (row * dim + col);                                           // Calaculamos la posicion del tablero
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
                    if (col == dim ) {
                         out + Console.RESET
                    } else {
                         val foreground = (Console.INVISIBLE, Console.RED, Console.WHITE, Console.CYAN, Console.GREEN, Console.YELLOW, Console.MAGENTA, Console.BLINK + Console.BLUE);
                         val ficha = String.format(" %s ", if (bloque != POS_TAB_EMPTY) (if ((bloque - (bloque % 10)) > POS_TAB_EMPTY * 2) "■" else "●") else "‌ ");
                         out + Console.BOLD + foreground.productElement(bloque % 10) + ficha + Console.RESET
                    }
               } else "") + imprimirTablero(tablero, (row + (if (col == dim) 1 else 0)), (if (col < dim) (col + 1) else 0));
          } else {
               return String.format("┣━ %s%-4s%s", Console.CYAN + Console.BOLD,  CAR_ROW_COLUMN.substring(row - 1, row), Console.RESET);;
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
               }).concat(this.echoTablero(tablero, (if (cont != 2) dim + 1 else Math.sqrt(tablero.length).toInt + 1), cont));
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
     def damasPlayBom(tablero:List[Int], mov:(Int, Int, Int), turno:Int): (Boolean, Boolean, List[Int]) = {
          val dama:Int = tablero((mov._1 * Math.sqrt(tablero.length).toInt) + mov._2);
          if ((dama != POS_TAB_EMPTY) && (if (turno == 0) 30 else 20).equals(dama - (dama % 10))) {
               val movH    = Array(-1, 1).apply((mov._3 % 10));					        // Determinamos el movimiento vertical en funcion de la direcion.
			val movV    = Array(-1, 1).apply(((mov._3 - (mov._3 % 10)) / 10) - 1);        // Determinamos el movimiento horizontal en funcion de la direcion.
               if (!isCamarada(tablero, movV, movH, mov._1, mov._2, 0)) {
                    val tab = this.setMovGamen(tablero, movV, movH, mov._1, mov._2, (if ((dama % 10) <= 2) 1 else (dama % 10)), 0, false);
                    return new Tuple3(false, false, tab);
               } else {
                    println(" ❈ " + Console.RED + "ERROR" + Console.RESET + ": La jugada realizada nos se puede cosidera una jugada valida !!!");
                    return new Tuple3(false, true, tablero);
               }
          } else {
               println(" ❈ " + Console.RED + "ERROR" + Console.RESET + ": NO pudee mover la ficha selecionada, las fichas que usted pude tocar son '" + (if (turno == 0) "■" else "●") + "' !!!");
               return new Tuple3(false, true, tablero);    
          }
     }
     
     /**
      * 
      */
     private def isCamarada(tablero:List[Int], movV:Int, movH:Int, row:Int, col:Int, pos:Int): Boolean = {
          val isMovValido = ((col + ((pos + 1) * movH) > -1) && (col + ((pos + 1) * movH) < Math.sqrt(tablero.length).toInt)) &&
                            ((row + ((pos + 1) * movV) > -1) && (row + ((pos + 1) * movV) < Math.sqrt(tablero.length).toInt));
          if (isMovValido) {
               val fichInMov = tablero((row + (pos * movV)) * (Math.sqrt(tablero.length).toInt) + (col + (pos * movH)));
               val victima   = tablero((row + ((pos + 1) * movV)) * (Math.sqrt(tablero.length).toInt) + (col + ((pos + 1) * movH)));
               return isMovValido && (victima - ((victima % 10)) == (fichInMov - (fichInMov % 10)));
          }
          return isMovValido;
     }
  
     /**
      * 
      */
     private def setMovGamen(tablero:List[Int], movV:Int, movH:Int, row:Int, col:Int, type_bom:Int, cont: Int, isPacMan:Boolean): List[Int] = {
          if(cont < type_bom){
               if (!isCamarada(tablero, movV, movH, row, col, cont) && !isPacMan) {
                    val posVictima  = (row + ((cont + 1) * movV))* Math.sqrt(tablero.length).toInt + (col + ((cont + 1) * movH));
                    val posActual   = (row + (cont * movV))* Math.sqrt(tablero.length).toInt + (col + (cont * movH));
                    val isSetPacMan = tablero(posVictima) != POS_TAB_EMPTY;				 
                    val tab = this.insertMovimiento(tablero, posActual, posVictima, tablero(posActual), 0);
                    this.setMovGamen(tab, movV, movH, row, col, type_bom, cont + 1, isSetPacMan);
               } else {
                    if (isPacMan) {
                         val tab_bom = cont match {
                              case 3 => Nil;     // BOM BUILD
                              case 4 => Nil;     // BOM BUILD
                              case 5 => Nil;     // BOM BUILD
                              case 6 => Nil;     // BOM BUILD
                              case 7 => Nil;     // BOM BUILD
                              case _ => tablero; // BOM BUILD
                         }
                         println(" ❈ " + Console.GREEN + "Evento " + Console.RESET + ":La dama se trasmuto en PacMan y " + Console.RED + "MATO" + Console.RESET + " WAKKA WAKKA !!!");
                         UtilDamas.clipSoundEfect("super-pacman_wakka.wav").start();
                    }
                    this.setMovGamen(tablero, movV, movH, row, col, type_bom, type_bom + 1, isPacMan);
               }
          } else {
               return tablero;
          } 
     }
     
     /**
      * 
      */
     private def insertMovimiento(tablero: List[Int], actual:Int, victima:Int, value:Int, cont:Int): List[Int] = {
          if (cont < tablero.length) {
               val value_aux = (if (cont == actual) POS_TAB_EMPTY else value);
               if ((actual == cont) || (victima == cont)) {
                    val (act, vic) = ((if (cont == actual) 0 else actual), (if (cont == victima) 0 else victima));
                    value_aux :: insertMovimiento(tablero, act, vic, value, (cont + 1));
               } else {
                    tablero(cont) :: insertMovimiento(tablero, actual, victima, value, (cont + 1));    
               }
          } else {
               return Nil;
          }
     }
     
}