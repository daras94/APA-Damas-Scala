package damas.juego.shell;

import scala.util.matching._;
import java.io.File;
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
                    val SetPlay:(List[Int], Int, Int, Int, (Int, Int), String, Boolean, (Int, Int)) = (if (opcion != "3") {
                         val dim      = Cfg.getDimTablero()
                         val tablero  = Tab.generarTablero(dim, dim, dim, Cfg.getDificultad());                 // Generamos el tablero de juego segun las configuraciones.
                         val fichXJug = Tab.numFichasXjugadorInit(Math.sqrt(tablero.length).toInt);             // Determinamos el numero def fichas a colocar.
                         if (Cfg.getSound()) {                                                                  // Segun configuracion se abilita odesabilita los efectos de sonido.
                              UtilDamas.clipSoundEfect("start_up.wav").start();                                 // Efecto de Audio de Start UP. 
                         }
                         opcion match {
                              case "1" => (tablero, 0, 0, Cfg.getDificultad(), (fichXJug, fichXJug), new String, false, (0, 0));
                              case "2" => (tablero, 0, 0, Cfg.getDificultad(), (fichXJug, fichXJug), new String, true, (0, 0));
                         };
                    } else {
                         val f_play = this.setChangePlay(Persistencia.getListOfFiles());
                         if (f_play != null) {
                              Persistencia.changePlay(f_play.getName()); 
                         } else {
                              new Tuple8(Nil, 0, 0, 0, (0, 0), new String, false, (0, 0));
                         }
                    });
                    if (SetPlay._1 != Nil) {                                                                     // Inciamos paratida dos jugadores o cargamos una partida guardada.
                         this.playDamasBom(SetPlay._1, SetPlay._2, SetPlay._3, SetPlay._4, false, SetPlay._5, SetPlay._6, SetPlay._7, SetPlay._8);                 
                    }
               case "4" ⇒ this.menuConfigShell();
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
     def playDamasBom(tablero: List[Int], turno: Int, nivel: Int, dificultad: Int, isError: Boolean, numfichas:(Int, Int), event:String, modo_game:Boolean, score:(Int, Int)): Unit = { 
          var FicN:(Int, Int) = numfichas;                                                                        // Formato tupla (NumFichJug1, NumFichJug2) ppara el conteo del numero .
          if (!isError) {                                                                                         // Si se a producido un error en la jugada nanterior no se vuelve a imprimir el tablero.
               UtilDamas.clear(); str.clear(); str.append("\n");                                                  // Borramos el pront limpiamos el strmenbuilder.o
               val info = (Console.GREEN, Console.RESET, (nivel + 1).toString(), (dificultad + 1).toString(), (if (turno == 0) "■" else "●"), 0, "Puntuacion:", score.productElement(turno).toString(), new String);
               str.append("\n ").append("┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┳━━━━━━━━━━━━━┳━━━━━━━━━━━━━━━━━━━━┳━━━━━━━━━━━━━━┓");
               str.append("\n ").append(String.format("┃   %s                                                  %-8s ┃ %s%9s%3s ┃ Nº fichas ■  : %-12s ┃ Turno de : %-10s ┃", Console.CYAN, info._2, Console.BOLD,  info._7, info._2, (info._1 + FicN._1 + info._2), (info._1 + info._5 + info._2)));
               str.append("\n ").append(String.format("┃ ❈ %s Tablero de Juego                                 %-8s ┃ %s%7s%8s ┃ Nº fichas ●  : %-12s ┃ Dificult : %-10s ┃", Console.CYAN, info._2, Console.GREEN, info._9, info._2, (info._1 + FicN._2 + info._2), (info._1 + info._4 + info._2)));
               str.append("\n ").append(String.format("┃   %s------------------------------------------------  %-8s ┃ %s%6s%9s ┃ Temporizador : %-12s ┃ Nivel    : %-10s ┃", Console.CYAN, info._2, Console.GREEN, info._8, info._2, (info._1 + info._6 + info._2), (info._1 + info._3 + info._2)));
               str.append("\n ").append("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┻━━━━━━━━━━━━━┻━━━━━━━━━━━━━━━━━━━━┻━━━━━━━━━━━━━━┛");
               str.append("\n ").append(Tab.echoTablero(tablero, 1, 0)).append("\n")
               str.append("\n ").append("┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
               str.append("\n ").append(String.format("┃ ❈ %sADVERTENCIA %s: %-90s ┃", Console.RED, info._2, "La jugadas se deven de ser introducir con el siguiente formato 'X:Y:D' para poder realizar"));
               str.append("\n ").append(String.format("┃   %-104s ┃", "una jugada siendo (X = row, Y = column y D = (10 = sup-izq, 20 = inf-izq, 11 = sup-dech, 21 = inf-dech)."));
               str.append("\n ").append("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛"); 
          } else {
               str.clear();                                                                                      // Limpiamos el String Builder     
          };
          str.append(if (!event.isEmpty()) "\n" + event else "");
          val imput: String = this.imputMovGamen(tablero, turno, modo_game, (if(turno !=0 ) score._2 else score._2));                                     // Entrda de los jugadores y movimientos de la IA.
          /**
           * Expresion regular que define el formato de la jugadas el formato
           * de la tupla que se devuelve seria el que sigueo el formato tupla 
           * (isTablas, isWinner, isError, tablero, event) para datos de jugada.
           */
          val TabD:(Boolean, Boolean, Boolean, List[Int], String, (Int, Int)) = (((("([A-Z1-6]{1}):([A-Z1-6]{1}):((1|2){1}(0|1){1})").r).findFirstMatchIn(imput)) match {               
               case Some(_) ⇒ 
                    val jugada:(Int, Int, Int) = this.getJugada(imput, 0, 0, 0);  
                    val jugada_ret = Tab.damasPlayBom(tablero, jugada, turno, score, modo_game);                 // Validamos la ficha selecionada y realizamos los movimentos y
                    if (!jugada_ret._3) {
                         FicN = Tab.numFichasXjugadorInCurse(jugada_ret._4, 0, (0, 0));                          // Recalculamos el numero de fichas de cada jugador.
                         if (jugada_ret._2 && nivel < 3) {                                                       // Si el nivel es inferior a 3 y la partida a sido ganda se incrementa el nivel.
                              UtilDamas.printtextArt("Nivel " + nivel + 1, "")
                              val dim: Int = Math.sqrt(jugada_ret._4.length).toInt * 2;                          // Dimension del nuevo tablero.
                              val fichXJug = Tab.numFichasXjugadorInit(dim);                                     // Determinamos el numero def fichas a colocar.
                              val dificult = dificultad + (if(dificultad < 4) 2 else 0);                         // Calaculamos el nivel de dificultada de forma creciente. 
                              if (Cfg.getSound()) {                                                              // Segun configuracion ejecuta o no los efectos de sonido.
                                   UtilDamas.clipSoundEfect("level_up.wav").start();                             // Efecto de sonido leven UP.
                              }
                              return this.playDamasBom(Tab.generarTablero(dim, dim, dim, dificult), turno, (nivel + 1), dificult, false, (fichXJug, fichXJug), new String, modo_game, score);
                         } 
                    }
                    jugada_ret;
               case None    ⇒
                    imput match {
                         case "S" ⇒ Persistencia.savePlayDamas(tablero, modo_game, nivel, turno, dificultad, numfichas, score);
                         case "H" ⇒ (false, false, false, tablero, new String, score);
                         case  _  ⇒
                              if (imput != "0") {                                                                // Mostramos los posibles errores de introducion de teclado.
                                   (false, false, true, tablero, " - " + Console.RED + "ERROR: " + Console.RESET + "Caracter o movimiento introducido no valido.", score); 
                              } else {
                                   (false, false, false, tablero, new String, score);
                              }
                    }
          });
          if ((!TabD._1 && !TabD._2) && (imput != "0")) this.playDamasBom(TabD._4, turno + (if (TabD._3) 0 else if (turno == 0) 1 else -1) , nivel, dificultad, TabD._3, FicN, TabD._5, modo_game, TabD._6); 
     }
     
     /**
      * Recupera la filas y columas y direcion de la jugada realizada y la
      * retorna como una tupla de 3 ele mento la cual presenta el siguiente
      * formato (filas, columnas direcion).
      */
     private def getJugada(imput:String, row:Int, col:Int, dir:Int): (Int, Int, Int) = {
          if ((row, col, dir) == (0, 0, 0)) {
               try {
                    val jugada = imput.split(":");
                    this.getJugada(imput, Tab.CAR_ROW_COLUMN.indexOf(jugada.apply(0)), Tab.CAR_ROW_COLUMN.indexOf(jugada.apply(1)), jugada.apply(2).toInt);
               } catch {
                    case t: NullPointerException => 
                         t.getMessage(); // TODO: handle error
                         return (row, col, dir);
               } 
          } else {
               return new Tuple3(row, col, dir);    
          }
     }
     
     /**
      * Controla la entrada de juegos en abos modos de juego Jugador
      * VS Jugador VS IA de acuerdo a los turnos de juego.
      * 
      * 	- turno 		= Recibe un entero con el turno del jugador.
      * 	- modo_juego 	= Recibe un booleano cone modo de juego siendo true
      * 				  Jugador VS IA y false jugador vs jugador. 
      */
     private def imputMovGamen(tablero:List[Int], turno:Int, modo_juego:Boolean, puntuacion:Int): String = {
          if ((turno != 0) && modo_juego) {  // Turno de la IA que Relice su jugada en el modo Jugador VS IA.
               val ia = Ia.jugadaOptima(tablero, 0, 0, 0, 0, 0, 0, Math.sqrt(tablero.length).toInt, Math.sqrt(tablero.length).toInt, puntuacion);
               return (Tab.CAR_ROW_COLUMN.indexOf(ia._1) + ":" + Tab.CAR_ROW_COLUMN.indexOf(ia._2) + ":" + ia._3);
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
          val nivel: String = Console.in.readLine().toUpperCase();
          if (!((nivel >= "0") && (nivel <= "5")) && nivel != "X") {
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
          val dim_conf: String = Console.in.readLine();
          if (!((dim_conf >= "1") && (dim_conf <= "3")) && dim_conf != "0") {
               println("\n - " + Console.RED + "ERROR: " + Console.RESET + "Configuracion de tablero no es valida.");;  
               Thread.sleep(500);
               this.setDimension();
          }
          return (if (dim_conf != "0") List(8, 16, 32).apply(dim_conf.toInt - 1) else 0);
     };
     
     /**
      * Metodos que establece la configuracion y modifica o rectifica la
      * configuracion de fabrica del arcade.
      */
     private def menuConfigShell(): Unit = {
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
     
     private def setChangePlay(f_list:List[File]): File = {
          UtilDamas.clear(); str.clear(); str.append("\n");                             // Limpiamos el prompt. y Vaciamos el StringBuilder.  
          str.append("\n ").append("┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
          str.append("\n ").append(String.format("┃   %s                                                             %-47s ┃", Console.CYAN, Console.RESET));
          str.append("\n ").append(String.format("┃ ❈ %s Cargar partida Guarda                                       %-47s ┃", Console.CYAN, Console.RESET));
          str.append("\n ").append(String.format("┃   %s-----------------------------------------------------------  %-47s ┃", Console.CYAN, Console.RESET));
          str.append("\n ").append("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛").append("\n");
          if (!f_list.isEmpty) {
               str.append("\n ").append(Persistencia.echoGetFileSave(f_list, 0)); 
          } else {
               str.append("\n ").append(String.format(" %50s%7s%4s ", Console.GREEN, "No Hay partidas Guardadas !!", Console.RESET)).append("\n"); 
          }
          str.append("\n ").append("┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
          str.append("\n ").append(String.format("┃ ❈ %s NOTA %s: %-96s ┃", Console.RED, Console.RESET, "Pulsa 0 para volver al menu principal del juego."));
          str.append("\n ").append("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛"); 
          str.append("\n ").append("  ❈ Seleccione la partida que dese cargar: ")
          print(str);
          val select_file: String = Console.in.readLine();
          if (!((select_file >= "1") && (select_file <= f_list.length.toString())) && select_file != "0") {
               println("\n - " + Console.RED + "ERROR: " + Console.RESET + "La opcion selecionada no es valida.");;  
               Thread.sleep(500);
               this.setChangePlay(Persistencia.getListOfFiles());
          }
          return (if (select_file != "0") f_list.apply(select_file.toInt - 1) else null);
     };
     
}