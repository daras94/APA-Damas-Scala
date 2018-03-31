package damas.juego.shell
import scala.sys.process._
import damas.util._

/**
 * @author david
 */
object ShellDamas {
     
     /**
      * Fucion que borra el contenido del pront en funcion de S.O en el que se
      * ejecute la aplicacion.
      */
     def clear() = (if (System.getProperty("os.name").contains("Windows")) "cls".! else "clear".!);
     
     /**
      * 
      */
     def initShell(): Unit = {
          this.clear();
          println("┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
          println(String.format("┃ %25s  ===> {  %s  } <===  %-25s ┃", "", Console.CYAN + " MENU Damas BOM " + Console.RESET, ""));
          println("┣━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┫");
          println(String.format("┃ %-95s ┃", Console.MAGENTA + "1" + Console.RESET + ") - Inciar partida Damas BOM 2 Jugadores."));
          println(String.format("┃ %-95s ┃", Console.MAGENTA + "2" + Console.RESET + ") - Inciar partida Damas BOM 1 Jugador VS IA."));
          println(String.format("┃ %-95s ┃", Console.MAGENTA + "3" + Console.RESET + ") - Ver y Cargar Partida Guardadas."));
          println("┣━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┫");
          println(String.format("┃ - %-93s ┃",Console.RED + "NOTA" + Console.RESET + ": Pulsa 0 para volver al menu de selecion de interface de ejecucion."));
          println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
          print(" ❈ Selecione una opcion de juego (" + Console.GREEN + "Pulse X para salir del Juego" + Console.RESET + "): ")
          var opc: String = Console.in.readLine();
          opc.toUpperCase() match {
               case "1" ⇒
                    val dificultad = setDificultad();                                                     // Llamamos a el menu de configuracion del nivel de dificultad.
                    UtilDamas.clipSoundEfect("start_up.wav").start();                                     // Efecto de Audio de Start UP.
                    playDamasBom(Tab.generarTablero(32, 32, 32, dificultad), 0, 0, dificultad, false);       // Comenzamos Con el Nivel 0 y con un Tablero de 8x8.                
               case "2" ⇒   
               case "3" ⇒
               case "X" ⇒ System.exit(0);
               case _ ⇒ 
                    if (opc != "0") {
                         println("\n - " + Console.RED + "ERROR: " + Console.RESET + "Opcion de juego selecionda no es Valida.")  
                         Thread.sleep(500);
                    }
          }
          if (opc != "0") this.initShell(); 
     }
     
     /**
      * 
      */
     def setDificultad(): Int = {
          this.clear();
          println("┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
          println(String.format("┃ %17s  ===> {  %s  } <===  %-17s ┃", "", Console.CYAN + " Nivel de dificulta de partida: " + Console.RESET, ""));
          println("┣━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┫");
          println(String.format("┃ %s) - %s (%s). \t %43s ┃", Console.MAGENTA + "1" + Console.RESET, "Muy Facil", Console.YELLOW + "Escasas fichas trucadas",             Console.RESET));
          println(String.format("┃ %s) - %s (%s). \t %51s ┃", Console.MAGENTA + "2" + Console.RESET, "Facil",     Console.YELLOW + "Pocas fichas trucadas",               Console.RESET));
          println(String.format("┃ %s) - %s (%s). \t %43s ┃", Console.MAGENTA + "3" + Console.RESET, "Mendio",    Console.YELLOW + "Mitad de fichas trucadas",            Console.RESET));
          println(String.format("┃ %s) - %s (%s). \t %35s ┃", Console.MAGENTA + "4" + Console.RESET, "Avanzado",  Console.YELLOW + "Muchas fichas Trucadas y alguna BOM", Console.RESET));
          println(String.format("┃ %s) - %s (%s). \t %43s ┃", Console.MAGENTA + "5" + Console.RESET, "Experto",   Console.YELLOW + "Top fichas trucadas y BOM",           Console.RESET));
          println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
          print(" ❈ Selecione el nivel de dificultad de la partida :")
          val nivel: String = Console.in.readLine();
          if (!((nivel >= "0") && (nivel <= "5"))) {
               println("\n - " + Console.RED + "ERROR: " + Console.RESET + "El nivel de dificultada del Juego no es valida.")  
               Thread.sleep(1000);
               this.setDificultad();
          }
          return (nivel.toInt) - 1;
     }
     
     /**
      * 
      */
     def playDamasBom(tablero: List[Int], turno: Int, nivel: Int, dificultad: Int, isWinner: Boolean): Unit = {
          this.clear();                                 // Borramos el Anterior Tablero de Juego.
          println("┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┳━━━━━━━━━━━━━━┳━━━━━━━━━━━━━━┓");
          println(String.format("┃ ❈  %-62s ┃ Nivel: %-14s ┃ Turno de: %-11s ┃", Console.CYAN + "Tablero de Juego " + Console.RESET, Console.GREEN + (nivel + 1) + Console.RESET, Console.GREEN + (if (turno % 2 == 0) "#" else "O") + Console.RESET));
          println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┻━━━━━━━━━━━━━━┻━━━━━━━━━━━━━━┛");
          Tab.echoTablero(tablero, 1, 0);               // Imprimimos el Tablero de Juego.
          println("┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
          println("┃ - " + Console.RED + "NOTA" + Console.RESET + ": Jugada con el formato X:Y:D (X = column, Y = row y D = (10 = sup-izq, \t ┃");
          println("┃         20 = inf-izq, 11 = sup-dech, 21 = inf-dech))                                   ┃");
          println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
          print(" ❈ Realice su jugada (" + Console.GREEN + "0 para salir de la partida s para guardar la partida." + Console.RESET + "):")
          /**
           * En Construcion.
           */
          val jugada: String = Console.in.readLine();
          if (!isWinner) {
               this.playDamasBom(tablero, turno, nivel, dificultad, isWinner); 
          } else {
               if (isWinner && (nivel < 3)) {                                   // Si se Gana la partida se sube de nivel y el tablero sera el doble del actul.
                    val dim: Int = Math.sqrt(tablero.length).toInt * 2;
                    UtilDamas.clipSoundEfect("level_up.wav").start();           // Efecto de sonido leven UP.
                    this.playDamasBom(Tab.generarTablero(dim, dim, dim, dificultad), turno, (nivel + 1), dificultad, isWinner);
               }
          }
     }
}