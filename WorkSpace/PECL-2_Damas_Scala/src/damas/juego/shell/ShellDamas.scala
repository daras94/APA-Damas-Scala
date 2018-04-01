package damas.juego.shell
import scala.sys.process._
import scala.util.matching._
import damas.util._

/**
 * @author david
 */
object ShellDamas {
     
     /**
      * Declaracion de Variables Globales.
      */
     val str = new StringBuilder;
     
     /**
      * Fucion que borra el contenido del pront en funcion de S.O en el que se
      * ejecute la aplicacion.
      */
     def clear() = (if (System.getProperty("os.name").contains("Windows")) "cls".! else "clear".!);
     
     /**
      * 
      */
     def initShell(): Unit = {
          this.clear(); str.clear(); str.append("\n");                                                     // Limpiamos el prompt. y Vaciamos el StringBuilder. 
          str.append("┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓").append("\n");
          str.append(String.format("┃ %25s  ===> {  %s  } <===  %-25s ┃", "", Console.CYAN + " MENU Damas BOM " + Console.RESET, "")).append("\n");
          str.append("┣━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┫").append("\n");
          str.append(String.format("┃ %-95s ┃", Console.MAGENTA + "1" + Console.RESET + ") - Inciar partida Damas BOM 2 Jugadores.")).append("\n");
          str.append(String.format("┃ %-95s ┃", Console.MAGENTA + "2" + Console.RESET + ") - Inciar partida Damas BOM 1 Jugador VS IA.")).append("\n");
          str.append(String.format("┃ %-95s ┃", Console.MAGENTA + "3" + Console.RESET + ") - Ver y Cargar Partida Guardadas.")).append("\n");
          str.append("┣━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┫").append("\n");
          str.append(String.format("┃ - %-93s ┃",Console.RED + "NOTA" + Console.RESET + ": Pulsa 0 para volver al menu de selecion de interface de ejecucion.")).append("\n");
          str.append("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛").append("\n");
          str.append(" ❈ Selecione una opcion de juego (" + Console.GREEN + "Pulse X para salir del Juego" + Console.RESET + "): ")
          print(str);
          var opc: String = Console.in.readLine();
          opc.toUpperCase() match {
               case "1" ⇒
                    val dificultad = setDificultad();                                                     // Llamamos a el menu de configuracion del nivel de dificultad.
                    //if (Cfg.sound) {
                         UtilDamas.clipSoundEfect("start_up.wav").start();                                // Efecto de Audio de Start UP. 
                    //}
                    playDamasBom(Tab.generarTablero(16, 16, 16, dificultad), 0, 0, dificultad, false);    // Comenzamos Con el Nivel 0 y con un Tablero de 8x8.                
               case "2" ⇒   
               case "3" ⇒
               case "X" ⇒ System.exit(0);
               case _ ⇒ 
                    if (opc != "0") {
                         str.append("\n - " + Console.RED + "ERROR: " + Console.RESET + "Opcion de juego selecionda no es Valida.")  
                         Thread.sleep(500);
                    }
          }
          if (opc != "0") this.initShell(); 
     }
     
     /**
      * 
      */
     def setDificultad(): Int = {
          this.clear(); str.clear(); str.append("\n");                             // Limpiamos el prompt. y Vaciamos el StringBuilder.                                                        
          str.append("┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓").append("\n");
          str.append(String.format("┃ %17s  ===> {  %s  } <===  %-17s ┃", "", Console.CYAN + " Nivel de dificulta de partida: " + Console.RESET, "")).append("\n");
          str.append("┣━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┫").append("\n");
          str.append(String.format("┃ %s) - %s (%s). \t %43s ┃", Console.MAGENTA + "1" + Console.RESET, "Muy Facil", Console.YELLOW + "Escasas fichas trucadas",             Console.RESET)).append("\n");
          str.append(String.format("┃ %s) - %s (%s). \t %51s ┃", Console.MAGENTA + "2" + Console.RESET, "Facil",     Console.YELLOW + "Pocas fichas trucadas",               Console.RESET)).append("\n");
          str.append(String.format("┃ %s) - %s (%s). \t %43s ┃", Console.MAGENTA + "3" + Console.RESET, "Mendio",    Console.YELLOW + "Mitad de fichas trucadas",            Console.RESET)).append("\n");
          str.append(String.format("┃ %s) - %s (%s). \t %35s ┃", Console.MAGENTA + "4" + Console.RESET, "Avanzado",  Console.YELLOW + "Muchas fichas Trucadas y alguna BOM", Console.RESET)).append("\n");
          str.append(String.format("┃ %s) - %s (%s). \t %43s ┃", Console.MAGENTA + "5" + Console.RESET, "Experto",   Console.YELLOW + "Top fichas trucadas y BOM",           Console.RESET)).append("\n");
          str.append("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛").append("\n");
          str.append(" ❈ Seleccione el nivel de dificultad de la partida :")
          print(str);
          val nivel: String = Console.in.readLine();
          if (!((nivel >= "0") && (nivel <= "5"))) {
               println("\n - " + Console.RED + "ERROR: " + Console.RESET + "El nivel de dificultada del Juego no es valida.");;  
               Thread.sleep(500);
               this.setDificultad();
          }
          return (nivel.toInt) - 1;
     }
     
     /**
      * 
      */
     def playDamasBom(tablero: List[Int], turno: Int, nivel: Int, dificultad: Int, isWinner: Boolean): Unit = {
          this.clear(); str.clear(); str.append("\n");                           // Borramos el Anterior Tablero de Juego y Vaciamos el StringBuilder.
          str.append("┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┳━━━━━━━━━━━━━━┳━━━━━━━━━━━━━━┓").append("\n");
          str.append(String.format("┃ ❈  %-62s ┃ Nivel: %-14s ┃ Turno de: %-11s ┃", Console.CYAN + "Tablero de Juego " + Console.RESET, Console.GREEN + (nivel + 1) + Console.RESET, Console.GREEN + (if (turno % 2 == 0) "■" else "●") + Console.RESET)).append("\n");
          str.append("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┻━━━━━━━━━━━━━━┻━━━━━━━━━━━━━━┛").append("\n");
          str.append(Tab.echoTablero(tablero, 1, 0)).append("\n");               // Imprimimos el Tablero de Juego.
          str.append("┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓").append("\n");
          str.append("┃ - " + Console.RED + "NOTA" + Console.RESET + ": Jugada con el formato X:Y:D (X = column, Y = row y D = (10 = sup-izq, \t ┃").append("\n");
          str.append("┃         20 = inf-izq, 11 = sup-dech, 21 = inf-dech))                                   ┃").append("\n");
          str.append("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛").append("\n");
          str.append(" ❈ Realice su jugada (" + Console.GREEN + "0 para salir de la partida s para guardar la partida." + Console.RESET + "):")
          print(str);                                                           // Imprimimos el tinglado menos IO mas Optimo.
          val imput: String   = Console.in.readLine().toUpperCase();
          val reg_expre: Regex = ("([A-Z]{1}):([A-Z]{1}):((1|2){1}(0|1){1})").r;
          (reg_expre.findFirstMatchIn(imput)) match {
               case Some(_) ⇒ 
                    /**
                     * En construcion
                     */
                    if (isWinner && (nivel < 3)) {                                   // Si se Gana la partida se sube de nivel y el tablero sera el doble del actul.
                         val dim: Int = Math.sqrt(tablero.length).toInt * 2;
                         UtilDamas.clipSoundEfect("level_up.wav").start();           // Efecto de sonido leven UP.
                         this.playDamasBom(Tab.generarTablero(dim, dim, dim, dificultad), turno, (nivel + 1), dificultad, isWinner);
                    }
               case None    ⇒
                    imput match {
                         case "S" ⇒ Nil
                         case "H" ⇒ Nil
                         case  _  ⇒
                              if (imput != "0") {
                                   println("\n - " + Console.RED + "ERROR: " + Console.RESET + "Carrater o movimiento introducido no valido no valida.");;  
                                   Thread.sleep(500);
                              }
                    }
          }
          //print("read -n1 -r -p \"Press any key to continue...\" key".!!);
          if (!isWinner && imput != "0") this.playDamasBom(tablero, turno, nivel, dificultad, isWinner); 
     }
}