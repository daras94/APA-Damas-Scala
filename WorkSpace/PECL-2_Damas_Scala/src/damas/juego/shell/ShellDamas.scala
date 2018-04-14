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
          str.append("\n ").append(String.format(" %-10s 3 %s) - %s", Console.MAGENTA, Console.RESET, "Ver y Cargar Partida Guardadas.")).append("\n");
          str.append("\n ").append("┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
          str.append("\n ").append(String.format("┃ ❈ %s NOTA %s: %-96s ┃", Console.RED, Console.RESET, "Pulsa 0 para volver al menu de seleccion de interface de ejecucion."));
          str.append("\n ").append("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛"); 
          str.append("\n ").append("  ❈ Seleccione una opcion de juego (" + Console.GREEN + "Pulse X para salir del Juego" + Console.RESET + "): ")
          print(str);
          var opc: String = Console.in.readLine();
          opc.toUpperCase() match {
               case "1" ⇒
                    val dificultad = setDificultad();                                                     // Llamamos a el menu de configuracion del nivel de dificultad.
                    val tablero    = Tab.generarTablero(Cfg.dim, Cfg.dim, Cfg.dim, dificultad);           // Generamos el tablero de juego segun las configuraciones.
                    val fichXJug   = Tab.numFichasXjugadorInit(Math.sqrt(tablero.length).toInt);          // Determinamos el numero def fichas a colocar.
                    if (Cfg.sound) {                                                                      // Segun configuracion se abilita odesabilita los efectos de sonido.
                         UtilDamas.clipSoundEfect("start_up.wav").start();                                // Efecto de Audio de Start UP. 
                    }
                    playDamasBom(tablero, 0, 0, dificultad, false, (fichXJug, fichXJug), "");             // Comenzamos Con el Nivel 0 y con un Tablero de 8x8.                
               case "2" ⇒   
               case "3" ⇒
               case "X" ⇒ System.exit(0);
               case _ ⇒ 
                    if (opc != "0") {
                         str.append("\n - " + Console.RED + "ERROR: " + Console.RESET + "La opcion de juego selecionda no es valida.")  
                         Thread.sleep(500);
                    }
          }
          if (opc != "0") this.initShell(); 
     }
     
     /**
      * 
      */
     def setDificultad(): Int = {
          UtilDamas.clear(); str.clear(); str.append("\n");                             // Limpiamos el prompt. y Vaciamos el StringBuilder.                                                        
          str.append("┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓").append("\n");
          str.append(String.format("┃ %17s  ===> {  %s  } <===  %-17s ┃", "", Console.CYAN + " Nivel de dificultad de partida: " + Console.RESET, "")).append("\n");
          str.append("┣━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┫").append("\n");
          str.append(String.format("┃ %s) - %s (%s). \t %43s ┃", Console.MAGENTA + "1" + Console.RESET, "Muy Facil", Console.YELLOW + "Escasas fichas trucadas",             Console.RESET)).append("\n");
          str.append(String.format("┃ %s) - %s (%s). \t %51s ┃", Console.MAGENTA + "2" + Console.RESET, "Facil",     Console.YELLOW + "Pocas fichas trucadas",               Console.RESET)).append("\n");
          str.append(String.format("┃ %s) - %s (%s). \t %43s ┃", Console.MAGENTA + "3" + Console.RESET, "Mendio",    Console.YELLOW + "Mitad de fichas trucadas",            Console.RESET)).append("\n");
          str.append(String.format("┃ %s) - %s (%s). \t %35s ┃", Console.MAGENTA + "4" + Console.RESET, "Avanzado",  Console.YELLOW + "Muchas fichas trucadas y alguna BOM", Console.RESET)).append("\n");
          str.append(String.format("┃ %s) - %s (%s). \t %43s ┃", Console.MAGENTA + "5" + Console.RESET, "Experto",   Console.YELLOW + "Top fichas trucadas y BOM",           Console.RESET)).append("\n");
          str.append("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛").append("\n");
          str.append(" ❈ Seleccione el nivel de dificultad de la partida :")
          print(str);
          val nivel: String = Console.in.readLine();
          if (!((nivel >= "0") && (nivel <= "5"))) {
               println("\n - " + Console.RED + "ERROR: " + Console.RESET + "El nivel de dificultad del juego no es valida.");;  
               Thread.sleep(500);
               this.setDificultad();
          }
          return (nivel.toInt) - 1;
     }
     
     /**
      * Ejecucion del modo de juego de la partida.
      */
     def playDamasBom(tablero: List[Int], turno: Int, nivel: Int, dificultad: Int, isError: Boolean, numfichas:(Int, Int), event:String): Unit = { 
          var TabD:(Boolean, Boolean, List[Int], String) = (false, false, tablero, event);   // Formato tupla (isWinner, isError, tablero, event) para datos de jugada.
          var FicN:(Int, Int) = numfichas;                                                   // Formato tupla (NumFichJug1, NumFichJug2) ppara el conteo del numero .
          if (!isError) {                                                                    // Si se a producido un error en la jugada nanterior no se vuelve a imprimir el tablero.
               UtilDamas.clear(); str.clear(); str.append("\n");                             // Borramos el pront limpiamos el strmenbuilder.o
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
               str.clear();                                                                  // Limpiamos el String Builder     
          } 
          print(str.append(if (!event.isEmpty()) "\n" + TabD._4 else "").append("\n").append(" ❈ Realice su jugada (" + Console.GREEN + "0 para salir de la partida, S para guardar la partida y H ayuda." + Console.RESET + "): "));
          val imput: String   = Console.in.readLine().toUpperCase();                         // Leemos la entrada estandar del teclado.
          val reg_expre: Regex = ("([A-Z1-6]{1}):([A-Z1-6]{1}):((1|2){1}(0|1){1})").r;       // Expresion regular que define el formato de la jugadas.
          (reg_expre.findFirstMatchIn(imput)) match {
               case Some(_) ⇒ 
                    val aux_jug = imput.split(":");
                    val jugada:(Int, Int, Int) = (Tab.CAR_ROW_COLUMN.indexOf(aux_jug.apply(0)), Tab.CAR_ROW_COLUMN.indexOf(aux_jug.apply(1)), aux_jug.apply(2).toInt); 
                    TabD = Tab.damasPlayBom(tablero, jugada, turno);                        // Validamos la ficha selecionada y realizamos los movimentos y
                    if (!TabD._2) {
                         FicN = Tab.numFichasXjugadorInCurse(TabD._3, 0, (0, 0));           // Recalculamos el numero de fichas de cada jugador.
                         if (TabD._1 && nivel < 3) {                                        // Si el nivel es inferior a 3 y la partida a sido ganda se incrementa el nivel.
                              UtilDamas.printtextArt("Nivel " + nivel, "")
                              val dim: Int = Math.sqrt(TabD._3.length).toInt * 2;           // Dimension del nuevo tablero.
                              val fichXJug = Tab.numFichasXjugadorInit(dim);                // Determinamos el numero def fichas a colocar.
                              if (Cfg.sound) {                                              // Segun configuracion ejecuta o no los efectos de sonido.
                                   UtilDamas.clipSoundEfect("level_up.wav").start();        // Efecto de sonido leven UP.
                              }
                              this.playDamasBom(Tab.generarTablero(dim, dim, dim, dificultad), turno, (nivel + 1), dificultad, false, (fichXJug, fichXJug), "");
                         } 
                    } 
               case None    ⇒
                    imput match {
                         case "S" ⇒ Nil
                         case "H" ⇒ Nil
                         case  _  ⇒
                              if (imput != "0") {                                             // Mostramos los posibles errores de introducion de teclado.
                                   TabD = (false, true, tablero, " - " + Console.RED + "ERROR: " + Console.RESET + "Caracter o movimiento introducido no valido.");                         // Habilitamos la bandera de eerores
                              }
                    }
          }
          if (!TabD._1 && (imput != "0")) this.playDamasBom(TabD._3, turno + (if (TabD._2) 0 else if (turno == 0) 1 else -1) , nivel, dificultad, TabD._2, FicN, TabD._4); 
     }
}