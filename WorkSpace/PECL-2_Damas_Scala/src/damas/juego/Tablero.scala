package damas.juego
import scala.util.Random;
import damas.util._;
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
          val dim = (Math.sqrt(tablero.length)).toInt;                                                // Calculamos el tamño de las filas y columnass sera el mismo ya que es una matriz cudrada.
          if (row < dim) {
               val pos: Int = (row * dim + col);                                                      // Calaculamos la posicion del tablero
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
                         val foreground = (Console.INVISIBLE, Console.RED, Console.WHITE, Console.CYAN, Console.GREEN, Console.YELLOW, Console.MAGENTA, "\u001b[38;5;208m", "\u001b[38;5;216m");
                         val ficha = String.format("\u2007%s\u2007", if (bloque != POS_TAB_EMPTY) {
                              if ((bloque - (bloque % 10)) > POS_TAB_EMPTY * 2){
                                   if ((bloque % 10) == 8) "\uD83D\uDF91" else "\u25FC"; // Reina Jug 2 = □ , Damas Jug 2 = ■
                              } else {
                                   if ((bloque % 10) == 8) "\uD83D\uDF88" else "\u25CF"; // Reina Jug 1 = ○ , Damas Jug 1 = ●
                              }
                         } else "‌ ");
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
     def damasPlayBom(tablero:List[Int], mov:(Int, Int, Int), turno:Int, score:(Int, Int), modo_juego:Boolean): (Boolean, Boolean, Boolean, List[Int], String, (Int, Int)) = {
          val dama:Int = tablero((mov._1 * Math.sqrt(tablero.length).toInt) + mov._2);
          if ((dama != POS_TAB_EMPTY) && (if (turno == 0) 30 else 20).equals(dama - (dama % 10))) {
               val movH    = List(-1, 1).apply((mov._3 % 10));					        // Determinamos el movimiento horizontal en funcion de la direcion.
			val movV    = List(-1, 1).apply(((mov._3 - (mov._3 % 10)) / 10) - 1);         // Determinamos el movimiento vertical en funcion de la direcion.
               if (isValido(tablero, movV, movH, mov._1, mov._2, 0)) {
                    val (tab, event, puntuacion)    = this.setMovGamen(tablero, movV, movH, mov._1, mov._2, (if ((dama % 10) <= 2) 1 else (dama % 10)), 0, new StringBuilder, false, 0);
                    val (score_1, score_2)          = ((score._1 + (if (turno == 0) puntuacion else 0)), (score._2 + (if (turno == 1) puntuacion else 0)));
                    val (isTable, isWinner, event2) = this.checkGamen(this.numFichasXjugadorInCurse(tab, 0, (0, 0)), (score_1, score_2), modo_juego)
                    return new Tuple6(isTable, isWinner, false, tab, (if(isWinner || isTable) event2 else event2 + event), (score_1, score_2));
               } else {
                    val evento = " ❈ " + Console.RED + "ERROR" + Console.RESET + ": La jugada realizada nos se puede cosidera una jugada valida !!!";
                    return new Tuple6(false, false, true, tablero, evento, score);
               }
          } else {
               val evento = " ❈ " + Console.RED + "ERROR" + Console.RESET + ": NO pudee mover la ficha selecionada, las fichas que usted pude tocar son '" + (if (turno == 0) "■" else "●") + "' !!!";
               return new Tuple6(false, false, true, tablero, evento, score);    
          }
     }
    
     /**
      * Determina si el movimiento de la jugada introducida en funcion del tipo
      * de ficha es valido teniendo en cuenta las reinas y las damas y que los
      * peones puedan realizar un salto.
      */
     private def isValido(tablero:List[Int], movV:Int, movH:Int, row:Int, col:Int, pos:Int): Boolean = {
          val isMovValido = ((col + ((pos + 1) * movH) > -1) && (col + ((pos + 1) * movH) < Math.sqrt(tablero.length).toInt)) &&
                            ((row + ((pos + 1) * movV) > -1) && (row + ((pos + 1) * movV) < Math.sqrt(tablero.length).toInt));
          if (isMovValido) {
               val fichInMov   = tablero((row + (pos * movV)) * (Math.sqrt(tablero.length).toInt) + (col + (pos * movH)));
               val victima     = tablero((row + ((pos + 1) * movV)) * (Math.sqrt(tablero.length).toInt) + (col + ((pos + 1) * movH)));
               val isJmpValido = ((col + ((pos + 2) * movH) > -1) && (col + ((pos + 2) * movH) < Math.sqrt(tablero.length).toInt)) &&
                                 ((row + ((pos + 2) * movV) > -1) && (row + ((pos + 2) * movV) < Math.sqrt(tablero.length).toInt));
               val isDriValido:Boolean = (fichInMov % 10) match {
                    case 8 ⇒ true;
                    case _ ⇒ 
                         val salto = if (isJmpValido) tablero((row + ((pos + 2) * movV)) * (Math.sqrt(tablero.length).toInt) + (col + ((pos + 2) * movH))) else POS_TAB_EMPTY;
                         (fichInMov - (fichInMov % 10)) match {
                              case 30 ⇒ (movV == -1) && (((victima % 10) == 0) || ((salto == POS_TAB_EMPTY) && isJmpValido));
                              case 20 ⇒ (movV ==  1) && (((victima % 10) == 0) || ((salto == POS_TAB_EMPTY) && isJmpValido));
                         }
               }
               return isMovValido && isDriValido && ((victima - (victima % 10)) != (fichInMov - (fichInMov % 10)));
          }
          return isMovValido;
     }

     /**
      * 
      */
     private def setMovGamen(tablero:List[Int], movV:Int, movH:Int, row:Int, col:Int, type_bom:Int, cont:Int, event:StringBuilder, isPacMan:Boolean, puntos:Int): (List[Int], String, Int) = {
          if(cont < type_bom){
               if (!isPacMan && isValido(tablero, movV, movH, row, col, cont)) {
                    val posVictima  = (row + ((cont + 1) * movV)) * Math.sqrt(tablero.length).toInt + (col + ((cont + 1) * movH));
                    val posSalto    = (row + ((cont + 2) * movV)) * Math.sqrt(tablero.length).toInt + (col + ((cont + 2) * movH));
                    val posActual   = (row + (cont * movV)) * Math.sqrt(tablero.length).toInt + (col + (cont * movH));
                    val isSetPacMan = (tablero(posVictima) != POS_TAB_EMPTY);	
                    val fichaInsert = (tablero(posActual) - (tablero(posActual) % 10)) match {     // Determina si la ficha se pude coronar en el movimiento en curso.
                         case 20 | 30 ⇒ 
                              if (((tablero(posActual) % 10) != 8) && (((row + ((cont + 1) * movV)) == 0) || ((row + ((cont + 1) * movV)) == (Math.sqrt(tablero.length).toInt)))) {
                                   event.append(" ❈ " + Console.GREEN + "Evento " + Console.RESET + ": El peon se " + Console.RED + "CORONO" + Console.RESET + " Larga vida a la REINA !!!");
                                   if (Setting.getSound()) {
                                        UtilDamas.clipSoundEfect("get_king.wav").start();  
                                   }
                                   (tablero(posActual) - (tablero(posActual) % 10)) + 8;           // Convertimos al peon en reina.
                              } else {
                                   tablero(posActual);
                              }
                    }
                    val points = (if (isSetPacMan) (if (tablero(posVictima) % 10 < 3) 1 else tablero(posVictima) % 10) else 0) 
                    val tab    = this.insertMovimiento(tablero, posActual, posVictima, (if (isSetPacMan && ((tablero(posActual) % 10) != 8)) posSalto else -1), fichaInsert, 0);
                    this.setMovGamen(tab, movV, movH, row, col, type_bom, cont + 1, event, isSetPacMan, (puntos + points));
               } else {
                    val (new_row, new_col) = ((row + ((cont + 1) * movV)), (col + ((cont + 1) * movH)));
                    val bom_status:(List[Int], Int, StringBuilder) = (if (isPacMan) {
                         type_bom match {
                              case 3 => (this.BOM(tablero, puntos, new_row, new_col, 0, event));  // BOM CIAN BUILD
                              case 4 => (this.BOM(tablero, puntos, new_row, new_col, 0, event));  // BOM VERDE BUILD
                              case 5 => (this.BOM(tablero, puntos, new_row, new_col, 0, event));  // BOM AMARILLO BUILD
                              case 6 => (this.BOM(tablero, puntos, new_row, new_col, 0, event));  // BOM MAGENTA BUILD
                              case 7 => (this.BOM(tablero, puntos, new_row, new_col, 0, event));  // BOM NARANJA BUILD
                              case _ => (tablero, puntos, event);                                 // NOT BOM.
                         }
                    } else (tablero, puntos, event));
                    this.setMovGamen(bom_status._1, movV, movH, row, col, type_bom, type_bom, event, isPacMan, bom_status._2);
               }
          } else {
               if (isPacMan) {
                    event.append(" ❈ " + Console.GREEN + "Evento " + Console.RESET + ":El peon se trasmuto en PacMan y " + Console.RED + "MATO" + Console.RESET + " WAKKA WAKKA !!!");
                    if (Setting.getSound()) {
                         UtilDamas.clipSoundEfect("captura_wakka.wav").start();  
                    }
               }
               return (tablero, event.toString(), puntos);
          } 
     }
    
     /**
      * Metodo el cual se encarga de insertar los movimentos de cada jugador y
      * deejectuar los saltos de los peones si esto se dieran.
      */
     private def insertMovimiento(tablero: List[Int], actual:Int, victima:Int, salto:Int, value:Int, cont:Int): List[Int] = {
          if (cont < tablero.length) {
               val value_aux = (if (cont == actual || ((salto != -1) && cont == victima)) POS_TAB_EMPTY else value);
               if ((actual == cont) || (victima == cont) || (salto == cont)) {
                    val (act, vic, jmp) = ((if (cont == actual) 0 else actual), (if (cont == victima) 0 else victima), (if (cont == salto) 0 else salto));
                    value_aux :: insertMovimiento(tablero, act, vic, jmp, value, (cont + 1));
               } else {
                    tablero(cont) :: insertMovimiento(tablero, actual, victima, salto, value, (cont + 1));    
               }
          } else {
               return Nil;
          }
     }
     
     /**
      * Numero de fichas x jugador en funcion de dimenciones del Tablero es el
      * calculo incial del numeo de fichas las cuales se generan inicialmente
      * al geerrar la partida.
      */
     def numFichasXjugadorInit(dim: Int) = (((Math.log10(dim) / Math.log10(2)).toInt + (if (dim > 8) 2 else 0)) * (dim / 2));
     
     /**
      * Contabiliza el numero de fichas x jugador en tiempo real a medida que
      * se van realizando jugadas.
      */
     def numFichasXjugadorInCurse(tablero:List[Int], cont:Int, numFichas:(Int, Int)): (Int, Int) = {
          if (cont < tablero.length) {
               val dama  = tablero(cont);
               val numFich:(Int, Int) = (dama - (dama % 10)) match {
                    case 30 ⇒ (numFichas._1 + 1, numFichas._2);
                    case 20 ⇒ (numFichas._1, numFichas._2 + 1);
                    case POS_TAB_EMPTY ⇒ numFichas;
               }
               this.numFichasXjugadorInCurse(tablero, (cont + 1), numFich);
          } else {
               return numFichas;
          }
     }
     
     /**
      * Metodo Que deternina si se a produciodo una victoria o si la partida
      * acaba en tablas para lo cual para determinar el ganador en cada instante
      * ademas de usar el numero de fichas restantes de cada jugador tabien se
      * tiene en cuenta la puntuacion de cada jugador. El formato de la tupla
      * que devuelve seria el siguiente: (isTablas, isWinner Evento);
      */
     private def checkGamen(numFich:(Int, Int), score:(Int, Int), modo_juego:Boolean): (Boolean, Boolean, String) = {
          if ((numFich._1 > numFich._2) && ((score._1 > score._2) || (score._1 == score._2))) {
               if ((numFich._1 == 3) && (numFich._2 == 1)) {
                    return (true, false,  " ❈ " + Console.GREEN + "Evento" + Console.RESET + ": Son Tablas, gana el Jugador por FICHAS !!");  // Tablas a favor Jugador 1.
               } else if ((numFich._1 == 1) && (numFich._2 == 0)) { 
                    return (false, true,  " ❈ " + Console.GREEN + "Evento" + Console.RESET + ": Gana el Jugador 1 por FICHAS !!");            // Victoria Jugador 1.
               } else {
                    return (false, false, " ❈ " + Console.GREEN + "Evento" + Console.RESET + ": No Gana Nadie AUN!!");                        // No hay victoria para nigun Jugador AUN.
               }  
          } else if ((numFich._1 < numFich._2) && ((score._1 < score._2) || (score._1 == score._2))) {
               if ((numFich._1 == 1) && (numFich._2 == 3)) {                                                                                  // Tablas a favor Jugador 2
                    return (true, false,  " ❈ " + Console.GREEN + "Evento" + Console.RESET + ": Son Tablas, gana " + (if (!modo_juego) " el Jugador 2" else "la Maquina") + " por FICHAS !!");
               } else if ((numFich._1 == 0) && (numFich._2 == 1)) {                                                                           // Victoria Jugaor 2 o IA.
                    return (false, true,  " ❈ " + Console.GREEN + "Evento" + Console.RESET + ": Gana " + (if (!modo_juego) " el Jugador 2" else "la Maquina") + " por FICHAS !!");
               } else {
                    return (false, false, " ❈ " + Console.GREEN + "Evento" + Console.RESET + ": No Gana Nadie AUN !!");                       // No hay victoria para nigun Jugador AUN.
               }  
          } else if ((numFich._1 == numFich._2) && ((numFich._1 == 1) && (numFich._2 == 1))) {
               if (score._1 > score._2) {
                    return (true, false,  " ❈ " + Console.GREEN + "Evento" + Console.RESET + ": Son Tablas gana el Jugador 1 por SCORE !!");  // Tablas a favor Jugador 1.
               } else if (score._1 < score._2) {                                                                                              // Tablas a favor Jugador 2 o IA.
                    return (true, false,  " ❈ " + Console.GREEN + "Evento" + Console.RESET + ": Son Tablas gana " + (if (!modo_juego) " el Jugador 2" else "la Maquina") + " por SCORE !!");
               } else {
                    return (false, false, " ❈ " + Console.GREEN + "Evento" + Console.RESET + ": Tablas no gana nadie la partida FIN !!");     // No hay victoria para nigun Jugador Tablas Absolutas.
               }
          } else {
               return (false, false, " ❈ " + Console.GREEN + "Evento" + Console.RESET + ": No Gana Nadie AUN !!");                            // No hay victoria para nigun Jugador AUN.
          }
     }
     
     /**
      * LA bomba detecta el patro pero debido a escazed de tiepo no soy capaz de
      * actualizar correctamente la lista que constitulle el tablero ta que se
      * generan dos entradas de forma simultanea las cuales no siiempre son
      * ejecutadas x lo cual no se llega a retornar el tablero actualizado de
      * forma corriente.
      */
     private def BOM(tablero:List[Int], puntos:Int, row:Int, col:Int, pos:Int, event:StringBuilder): (List[Int], Int, StringBuilder) = {
          val width  = (Math.sqrt(tablero.length).toInt);
          if (pos < width) {
               val post_2       = (if ((((row + pos * (-1))) > -1 ) && ((col + pos * (-1)) > -1)) ((row + pos * (-1)) * width + (col + pos * (-1))) else 0);
               val typo_ficha_2 = ((post_2 != 0) && ((tablero(row * width + col) - (tablero(row * width + col) % 10)) / 10) != ((tablero(post_2) - (tablero(post_2) % 10)) / 10))
               val score_2      = (if (post_2 != 0) {
                    (tablero((row + pos * (-1)) * width + (col + pos * (-1))) % 10);
               } else 0);
               val post_1       = (if (((row + pos) < width) && ((col + pos) < width)) (row + pos) * width + (col + pos) else 0);
               val typo_ficha_1 = ((post_1 != 0) && ((tablero(row * width + col) - (tablero(row * width + col) % 10)) / 10) != ((tablero(post_1) - (tablero(post_1) % 10)) / 10))
               val score_1      = (if (post_1 != 0) {
                    (tablero((row + pos) * width + (col + pos)) % 10);
               } else 0);
			val tab = this.dropBom(tablero, (if (typo_ficha_1) post_1 else 0), (if (typo_ficha_2) post_2 else 0), 0);
			this.BOM(tab, (puntos + score_1 + score_2), row, col, (pos + 1), event);
          } else {
               event.append("\n ").append(" ❈ " + Console.GREEN + "Evento" + Console.RESET + ": BOM Diagonal !!").append("\n")
               return (tablero, puntos, event);
          }
          
     }
     
     /**
      * Metod de borrado para la bomba.
      */
     private def dropBom(tablero:List[Int], post_1:Int, post_2:Int, cont:Int): List[Int] = {
          if (cont < tablero.length) {
               if ((post_1 == cont) || (post_2 == cont)) {
                    val (act, vic) = ((if (cont == post_1) 0 else post_1), (if (cont == post_2) 0 else post_2));
                    POS_TAB_EMPTY :: dropBom(tablero, post_1, post_2, (cont + 1));
               } else {
                    tablero(cont) :: dropBom(tablero, post_1, post_2, (cont + 1));   
               }
          } else {
               return Nil;
          }    
     }
     
}