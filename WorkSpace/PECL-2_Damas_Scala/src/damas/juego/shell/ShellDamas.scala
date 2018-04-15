package damas.juego.shell;

import scala.util.matching._;
import damas.util._;

/**
 * @author david
 */
object ShellDamas {
     
     /**
      * Declaracion de Variables Globales.
      */
     val str = new StringBuilder;
     
     
     /**
      * 
      */
     def initShell(): Unit = {
          str.append("\n ").append("┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
          str.append("\n ").append(String.format("┃   %s                                                             %-47s ┃", Console.CYAN, Console.RESET));
          str.append("\n ").append(String.format("┃ ❈ %s MENU de Juego                                               %-47s ┃", Console.CYAN, Console.RESET));
          str.append("\n ").append(String.format("┃   %s-----------------------------------------------------------  %-47s ┃", Console.CYAN, Console.RESET));
          str.append("\n ").append("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛").append("\n");
          str.append("\n ").append(String.format(" %-10s 1 %s) - %s", Console.MAGENTA, Console.RESET, "Iniciar partida Damas BOM 2 Jugadores."));
          str.append("\n ").append(String.format(" %-10s 2 %s) - %s", Console.MAGENTA, Console.RESET, "Iniciar partida Damas BOM 1 Jugador VS IA."));
          str.append("\n ").append(String.format(" %-10s 3 %s) - %s", Console.MAGENTA, Console.RESET, "Ver y Cargar Partida Guardadas."));
          str.append("\n ").append(String.format(" %-10s 4 %s) - %s", Console.MAGENTA, Console.RESET, "Configuracion del Juego.")).append("\n");
          str.append("\n ").append("┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
          str.append("\n ").append(String.format("┃ ❈ %s NOTA %s: %-96s ┃", Console.RED, Console.RESET, "Pulsa 0 para volver al menu de seleccion de interface de ejecucion."));
          str.append("\n ").append("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛"); 
          str.append("\n ").append("  ❈ Seleccione una opcion de juego (" + Console.GREEN + "Pulse X para salir del Juego" + Console.RESET + "): ")
          print(str);
          val opcion: String = Console.in.readLine().toUpperCase();
          opcion match {
               case "1" | "2" | "3" ⇒
                    var SetPlay:(List[Int], Int, Int, Int, (Int, Int), String, Boolean) = (Nil, 0, 0, 0, (0, 0), new String, false);
                    if (opcion != "3") {
                         val dim      = Cfg.getDimTablero()
                         val tablero  = Tab.generarTablero(dim, dim, dim, Cfg.getDificultad());                 // Generamos el tablero de juego segun las configuraciones.
                         val fichXJug = Tab.numFichasXjugadorInit(Math.sqrt(tablero.length).toInt);             // Determinamos el numero def fichas a colocar.
                         SetPlay = opcion match {
                              case "1" => (tablero, 0, 0, Cfg.getDificultad(), (fichXJug, fichXJug), new String, (opcion.equals("2")));
                              case "2" => (tablero, 0, 0, Cfg.getDificultad(), (fichXJug, fichXJug), new String, (opcion.equals("2")));
                         };
                         if (Cfg.getSound()) {                                                                  // Segun configuracion se abilita odesabilita los efectos de sonido.
                              UtilDamas.clipSoundEfect("start_up.wav").start();                                 // Efecto de Audio de Start UP. 
                         }
                    } else {
                         
                    }
                    if (SetPlay._1 != Nil) {                                                                     // Inciamos paratida dos jugadores o cargamos una partida guardada.
                         playDamasBom(SetPlay._1, SetPlay._2, SetPlay._3, SetPlay._4, false, SetPlay._5, SetPlay._6, SetPlay._7);                 
                    }
               case "4" ⇒ this.menuConfigShell()
               case "X" ⇒ System.exit(0);
               case _ ⇒ 
                    if (opcion != "0") {
                         str.append("\n - " + Console.RED + "ERROR: " + Console.RESET + "La opcion de juego selecionda no es valida.")  
                         Thread.sleep(500);
                    }
          }
          if (opcion != "0") this.initShell(); 
     }
     
     
     /**
      * Ejecucion del modo de juego de la partida.
      */
     def playDamasBom(tablero: List[Int], turno: Int, nivel: Int, dificultad: Int, isError: Boolean, numfichas:(Int, Int), event:String, modo_game:Boolean): Unit = { 
          var TabD:(Boolean, Boolean, List[Int], String) = (false, false, tablero, event);             // Formato tupla (isWinner, isError, tablero, event) para datos de jugada.
          var FicN:(Int, Int) = numfichas;                                                             // Formato tupla (NumFichJug1, NumFichJug2) ppara el conteo del numero .
          if (!isError) {                                                                              // Si se a producido un error en la jugada nanterior no se vuelve a imprimir el tablero.
               UtilDamas.clear(); str.clear(); str.append("\n");                                       // Borramos el pront limpiamos el strmenbuilder.o
               val info = (Console.GREEN, Console.RESET, (nivel + 1).toString(), (dificultad + 1).toString(), (if (turno == 0) "■" else "●"), 0);
               str.append("\n ").append("┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┳━━━━━━━━━━━━━━━━━━━━┳━━━━━━━━━━━━━━┓");
               str.append("\n ").append(String.format("┃   %s                                                             %-11s ┃ Nº fichas ■  : %-12s ┃ Turno de : %-10s ┃", Console.CYAN, info._2, (info._1 + FicN._1 + info._2), (info._1 + info._5 + info._2)));
               str.append("\n ").append(String.format("┃ ❈ %s Tablero de Juego                                            %-11s ┃ Nº fichas ●  : %-12s ┃ Dificult : %-10s ┃", Console.CYAN, info._2, (info._1 + FicN._2 + info._2), (info._1 + info._4 + info._2)));
               str.append("\n ").append(String.format("┃   %s-----------------------------------------------------------  %-11s ┃ Temporizador : %-12s ┃ Nivel    : %-10s ┃", Console.CYAN, info._2, (info._1 + info._6 + info._2), (info._1 + info._3 + info._2)));
               str.append("\n ").append("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┻━━━━━━━━━━━━━━━━━━━━┻━━━━━━━━━━━━━━┛");
               str.append("\n ").append(Tab.echoTablero(TabD._3, 1, 0)).append("\n")
               str.append("\n ").append("┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
               str.append("\n ").append(String.format("┃ ❈ %sADVERTENCIA %s: %-90s ┃", Console.RED, info._2, "La jugadas se deven de ser introducir con el siguiente formato 'X:Y:D' para poder realizar"));
               str.append("\n ").append(String.format("┃   %-104s ┃", "una jugada siendo (X = row, Y = column y D = (10 = sup-izq, 20 = inf-izq, 11 = sup-dech, 21 = inf-dech)."));
               str.append("\n ").append("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛"); 
          } else {
               str.clear();                                                                            // Limpiamos el String Builder     
          };
          str.append("\n").append(if (!event.isEmpty()) "\n" + TabD._4 else "");
          val imput: String = this.imputMovGamen(TabD._3, turno, modo_game);                                    // Entrda de los jugadores y movimientos de la IA.
          ((("([A-Z1-6]{1}):([A-Z1-6]{1}):((1|2){1}(0|1){1})").r).findFirstMatchIn(imput)) match {     // Expresion regular que define el formato de la jugadas.
               case Some(_) ⇒ 
                    val jugada:(Int, Int, Int) = this.getJugada(imput);  
                    TabD = Tab.damasPlayBom(tablero, jugada, turno);                                   // Validamos la ficha selecionada y realizamos los movimentos y
                    if (!TabD._2) {
                         FicN = Tab.numFichasXjugadorInCurse(TabD._3, 0, (0, 0));                      // Recalculamos el numero de fichas de cada jugador.
                         if (TabD._1 && nivel < 3) {                                                   // Si el nivel es inferior a 3 y la partida a sido ganda se incrementa el nivel.
                              UtilDamas.printtextArt("Nivel " + nivel, "")
                              val dim: Int = Math.sqrt(TabD._3.length).toInt * 2;                      // Dimension del nuevo tablero.
                              val fichXJug = Tab.numFichasXjugadorInit(dim);                           // Determinamos el numero def fichas a colocar.
                              val dificult = dificultad + (if(dificultad < 4) 2 else 0);               // Calaculamos el nivel de dificultada de forma creciente. 
                              if (Cfg.getSound()) {                                                    // Segun configuracion ejecuta o no los efectos de sonido.
                                   UtilDamas.clipSoundEfect("level_up.wav").start();                   // Efecto de sonido leven UP.
                              }
                              this.playDamasBom(Tab.generarTablero(dim, dim, dim, dificult), turno, (nivel + 1), dificult, false, (fichXJug, fichXJug), new String, modo_game);
                         } 
                    } 
               case None    ⇒
                    imput match {
                         case "S" ⇒ Nil
                         case "H" ⇒ Nil
                         case  _  ⇒
                              if (imput != "0") {                                             // Mostramos los posibles errores de introducion de teclado.
                                   TabD = (false, true, tablero, " - " + Console.RED + "ERROR: " + Console.RESET + "Caracter o movimiento introducido no valido."); // Habilitamos la bandera de eerores
                              }
                    }
          }
          if (!TabD._1 && (imput != "0")) this.playDamasBom(TabD._3, turno + (if (TabD._2) 0 else if (turno == 0) 1 else -1) , nivel, dificultad, TabD._2, FicN, TabD._4, modo_game); 
     }
     
     /**
      * Recupera la filas y columas y direcion de la jugada realizada y la
      * retorna como una tupla de 3 ele mento la cual presenta el siguiente
      * formato (filas, columnas direcion).
      */
     private def getJugada(imput:String): (Int, Int, Int) = {
          var Jug:(Int, Int, Int) = (0, 0, 0)
          try {
               val jugada = imput.split(":");
               Jug = (Tab.CAR_ROW_COLUMN.indexOf(jugada.apply(0)), Tab.CAR_ROW_COLUMN.indexOf(jugada.apply(1)), jugada.apply(2).toInt);
          } catch {
               case t: NullPointerException => t.getMessage(); // TODO: handle error
          }
          return Jug;
     }
     
     /**
      * Controla la entrada de juegos en abos modos de juego Jugador
      * VS Jugador VS IA de acuerdo a los turnos de juego.
      * 
      * 	- turno 		= Recibe un entero con el turno del jugador.
      * 	- modo_juego 	= Recibe un booleano cone modo de juego siendo true
      * 				  Jugador VS IA y false jugador vs jugador. 
      */
     private def imputMovGamen(tablero:List[Int], turno:Int, modo_juego:Boolean): String = {
          if ((turno != 0) && modo_juego) {                          // Turno de la IA que Relice su jugada en el modo Jugador VS IA.
               return "";
          } else {                                                   // Turno del Resto de Jugadores en el modo Jugador VS Jugador.
               str.append("\n").append(" ❈ Realice su jugada (" + Console.GREEN + "0 para salir de la partida, S para guardar la partida y H ayuda." + Console.RESET + "): ");
               print(str.toString());
               return Console.in.readLine().toUpperCase();           // Leemos la entrada estandar del teclado.
          }
     }
     
     
     /**
      * 	Selecionar el valor de configuracion de dificultad del juego.
      */
     private def setDificultad(): String = {
          UtilDamas.clear(); str.clear(); str.append("\n");                             // Limpiamos el prompt. y Vaciamos el StringBuilder.  
          str.append("\n ").append("┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
          str.append("\n ").append(String.format("┃   %s                                                             %-47s ┃", Console.CYAN, Console.RESET));
          str.append("\n ").append(String.format("┃ ❈ %s Configuracion de la dificultad del Juego                    %-47s ┃", Console.CYAN, Console.RESET));
          str.append("\n ").append(String.format("┃   %s-----------------------------------------------------------  %-47s ┃", Console.CYAN, Console.RESET));
          str.append("\n ").append("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛").append("\n");
          str.append("\n ").append(String.format(" %-10s 0 %s) - %s (%s%s%s).", Console.MAGENTA, Console.RESET, "Basico",     Console.GREEN, "damas convencionales",                Console.RESET));
          str.append("\n ").append(String.format(" %-10s 1 %s) - %s (%s%s%s).", Console.MAGENTA, Console.RESET, "Facil",      Console.GREEN, "escasas fichas trucadas",             Console.RESET));
          str.append("\n ").append(String.format(" %-10s 2 %s) - %s (%s%s%s).", Console.MAGENTA, Console.RESET, "Medio",      Console.GREEN, "pocas fichas trucadas",               Console.RESET))
          str.append("\n ").append(String.format(" %-10s 3 %s) - %s (%s%s%s).", Console.MAGENTA, Console.RESET, "Avanzado",   Console.GREEN, "Mitad de fichas trucada",             Console.RESET));
          str.append("\n ").append(String.format(" %-10s 4 %s) - %s (%s%s%s).", Console.MAGENTA, Console.RESET, "Experto",    Console.GREEN, "Muchas fichas trucadas y alguna BOM", Console.RESET));
          str.append("\n ").append(String.format(" %-10s 5 %s) - %s (%s%s%s).", Console.MAGENTA, Console.RESET, "Legendario", Console.GREEN, "Top fichas trucadas y BOM",           Console.RESET)).append("\n");
          str.append("\n ").append("┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
          str.append("\n ").append(String.format("┃ ❈ %s NOTA %s: %-96s ┃", Console.RED, Console.RESET, "Pulsa X para volver al menu de configuracion sin guardar cambios."));
          str.append("\n ").append("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛"); 
          str.append("\n ").append("  ❈ Seleccione el nivel de dificultad de la partida: ")
          print(str);
          val nivel: String = Console.in.readLine();
          if (!((nivel >= "0") && (nivel <= "5"))) {
               println("\n - " + Console.RED + "ERROR: " + Console.RESET + "El nivel de dificultad del juego no es valida.");;  
               Thread.sleep(500);
               this.setDificultad();
          }
          return nivel;
     }
     
     /**
      * 	Selecionar el valor de configuracion de dificultad del juego.
      */
     private def setDimension(): Int = {
          UtilDamas.clear(); str.clear(); str.append("\n");                             // Limpiamos el prompt. y Vaciamos el StringBuilder.  
          str.append("\n ").append("┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
          str.append("\n ").append(String.format("┃   %s                                                             %-47s ┃", Console.CYAN, Console.RESET));
          str.append("\n ").append(String.format("┃ ❈ %s Configuracion del tablero de juego                          %-47s ┃", Console.CYAN, Console.RESET));
          str.append("\n ").append(String.format("┃   %s-----------------------------------------------------------  %-47s ┃", Console.CYAN, Console.RESET));
          str.append("\n ").append("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛").append("\n");
          str.append("\n ").append(String.format(" %-10s 1 %s) - %s (%s%s%s).", Console.MAGENTA, Console.RESET, "8x8  ", Console.GREEN, "64 celdas 24 fichas en total",    Console.RESET));
          str.append("\n ").append(String.format(" %-10s 2 %s) - %s (%s%s%s).", Console.MAGENTA, Console.RESET, "16x16", Console.GREEN, "256 celdas 96 fichas en total",   Console.RESET));
          str.append("\n ").append(String.format(" %-10s 3 %s) - %s (%s%s%s).", Console.MAGENTA, Console.RESET, "32x32", Console.GREEN, "1024 celdas 224 fichas en total", Console.RESET)).append("\n");
          str.append("\n ").append("┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
          str.append("\n ").append(String.format("┃ ❈ %s NOTA %s: %-96s ┃", Console.RED, Console.RESET, "Pulsa 0 para volver al menu de configuracion sin guardar cambios."));
          str.append("\n ").append("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛"); 
          str.append("\n ").append("  ❈ Seleccione un tamaño de tablero: ")
          print(str);
          val dimension: String = Console.in.readLine();
          if (!((dimension >= "1") && (dimension <= "3"))) {
               println("\n - " + Console.RED + "ERROR: " + Console.RESET + "Configuracion de tablero no es valida.");;  
               Thread.sleep(500);
               this.setDimension();
          }
          return (if (dimension != "0") Array(8, 16, 32).apply(dimension.toInt - 1) else 0);
     };
     
     def menuConfigShell(): Unit = {
          UtilDamas.clear(); str.clear(); str.append("\n");                             // Limpiamos el prompt. y Vaciamos el StringBuilder.  
          str.append("\n ").append("┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
          str.append("\n ").append(String.format("┃   %s                                                             %-47s ┃", Console.CYAN, Console.RESET));
          str.append("\n ").append(String.format("┃ ❈ %s Menu de configuracion:                                      %-47s ┃", Console.CYAN, Console.RESET));
          str.append("\n ").append(String.format("┃   %s-----------------------------------------------------------  %-47s ┃", Console.CYAN, Console.RESET));
          str.append("\n ").append("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛").append("\n");
          str.append("\n ").append(String.format(" %-10s 1 %s) - %s %-67s [ %s%10s%5s ]", Console.MAGENTA, Console.RESET, "Sonido:"                        , "", Console.GREEN, Cfg.getStatusSound(),      Console.RESET));
          str.append("\n ").append(String.format(" %-10s 2 %s) - %s %-63s [ %s%10s%5s ]", Console.MAGENTA, Console.RESET, "Dificultad:"                    , "", Console.GREEN, Cfg.getStatusDificultad(), Console.RESET));
          str.append("\n ").append(String.format(" %-10s 3 %s) - %s %-66s [ %s%7s%8s ]", Console.MAGENTA, Console.RESET, "Tablero:"                        , "", Console.GREEN, Cfg.getStatusDimension(),  Console.RESET));
          str.append("\n ").append(String.format(" %-10s 4 %s) - %s %-48s [ %s%5s%4s ]", Console.MAGENTA, Console.RESET, "Restablecer configuracion:"      , "", Console.GREEN, " ● ● ● ● ● ",             Console.RESET));
          str.append("\n ").append(String.format(" %-10s ⊙ %s) - %s %-42s [ %s%7s%4s ]", Console.MAGENTA, Console.RESET, "Ruta de alamcenamiento partidas:", "", Console.GREEN, Cfg.getSavePath(),         Console.RESET)).append("\n");
          str.append("\n ").append("┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
          str.append("\n ").append(String.format("┃ ❈ %s NOTA %s: %-96s ┃", Console.RED, Console.RESET, "Selecione el valor de la configuracion que quiera cambiar."));
          str.append("\n ").append("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛"); 
          str.append("\n ").append("  ❈ Seleccione la configuracion (" + Console.GREEN + "Pulse 0 para salir si cambios" + Console.RESET + "): ")
          print(str);
          val config: String = Console.in.readLine();
          config match {
               case "1" ⇒ Cfg.setSound(!Cfg.getSound());
               case "2" ⇒
                    val dificutad = this.setDificultad();
                    if (dificutad != "X") {
                         Cfg.setDificultad(dificutad.toInt - 1);
                    }
               case "3" ⇒ 
                    val dimension = this.setDimension();
                    if (dimension != 0) {
                         Cfg.setDimTablero(dimension);
                    }     
               case "4" ⇒ Cfg.restoreConf();
               case _ ⇒ 
                    if (config != "0") {
                         str.append("\n - " + Console.RED + "ERROR: " + Console.RESET + "La opcion de configuracion selecionda no es valida.")  
                         Thread.sleep(500);
                    }
          }
          
          if (config != "0") this.menuConfigShell();
     }
     
}