package damas.juego.shell
import scala.sys.process._
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
          println("┃\t\t\t ===> {" + Console.CYAN + " \t MENU Damas BOM \t " + Console.RESET + "} <=== \t\t ┃");
          println("┣━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┫");
          println("┃ " + Console.MAGENTA + "1" + Console.RESET + ") - Inciar partida Damas BOM 2 Jugadores.    \t\t\t\t\t ┃");
          println("┃ " + Console.MAGENTA + "2" + Console.RESET + ") - Inciar partida Damas BOM 1 Jugador VS IA.\t\t\t\t\t ┃");
          println("┃ " + Console.MAGENTA + "3" + Console.RESET + ") - Ver y Cargar Partida Guardadas.	      \t\t\t\t\t\t ┃");
          println("┣━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┫");
          println("┃ - " + Console.RED + "NOTA" + Console.RESET + ": Pulsa 0 para volver al menu de selecion de interface de ejecucion. \t\t ┃");
          println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
          print(" ❈ Selecione una opcion de juego (" + Console.GREEN + "Pulse X para salir del Juego" + Console.RESET + "): ")
          var opc: String = Console.in.readLine();
          opc.toUpperCase() match {
               case "1" ⇒
                    val dificultad = setDificultad();                                                     // Llamamos a el menu de configuracion del nivel de dificultad.
                    playDamasBom(Tab.generarTablero(16, 16, 16, dificultad), 0, 0, dificultad, false);       // Comenzamos Con el Nivel 0 y con un Tablero de 8x8.                
               case "2" ⇒   
               case "3" ⇒
               case "X" ⇒ System.exit(0);
               case _ ⇒ 
                    if (opc != "0") {
                         println("\n - " + Console.RED + "ERROR: " + Console.RESET + "Opcion de juego selecionda no es Valida.")  
                         Thread.sleep(1000);
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
          println("┃ \t\t ===> { " + Console.CYAN + " \t Nivel de dificulta de partida: \t " + Console.RESET + "} <=== \t ┃");
          println("┣━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┫");
          println("┃ " + Console.MAGENTA + "1" + Console.RESET + ") - Muy Facil (" + Console.YELLOW + "Escasas fichas trucadas" + Console.RESET + ").  \t\t\t\t\t\t ┃");
          println("┃ " + Console.MAGENTA + "2" + Console.RESET + ") - Facil     (" + Console.YELLOW + "Pocas fichas trucadas" + Console.RESET + ").    \t\t\t\t\t\t ┃");
          println("┃ " + Console.MAGENTA + "3" + Console.RESET + ") - Mendio    (" + Console.YELLOW + "Mitad de fichas trucadas" + Console.RESET + "). \t\t\t\t\t\t ┃");
          println("┃ " + Console.MAGENTA + "4" + Console.RESET + ") - Avanzado  (" + Console.YELLOW + "Muchas fichas Trucadas y alguna BOM" + Console.RESET + ").\t\t\t\t\t ┃");
          println("┃ " + Console.MAGENTA + "5" + Console.RESET + ") - Experto   (" + Console.YELLOW + "Top fichas trucadas y BOM" + Console.RESET + ").\t\t\t\t\t\t ┃");
          println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
          print(" ❈ Selecione el nivel de dificultad de la partida :")
          val nivel: String = Console.in.readLine();
          if ((nivel < "0") && (nivel > "5")) {
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
          this.clear();                               // Borramos el Anterior Tablero de Juego.
          println("┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
          println("┃ ❈ " + Console.CYAN + "Tablero de Juego " + Console.RESET + ":                          ┃");
          println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
          Tab.echoTablero(tablero, 0);               // Imprimimos el Tablero de Juego.
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
               if (isWinner && (nivel < 3)) {         // Si se Gana la partida se sube de nivel y el tablero sera el doble del actul.
                    val dim: Int = Math.sqrt(tablero.length).toInt * 2;
                    this.playDamasBom(Tab.generarTablero(dim, dim, dim, dificultad), turno, (nivel + 1), dificultad, isWinner);
               }
          }
     }
}