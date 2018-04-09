package damas

//import java.awt._;

/**
  * @author david.
  * 
  * Declaracion de Objeto.
  */
object TestMainDamas {
     /**
       *  Declaracion del MAIN.
       */
     def main(args: Array[ String ]): Unit = {
          println("┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
          println("┃\t\t\t ===> { " + Console.CYAN + " MENU Start UP, Damas BOM " + Console.RESET + "} <=== \t\t\t ┃");
          println("┣━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┫");
          println("┃ " + Console.MAGENTA + "1" + Console.RESET + ") - Iniciar partida GUI Damas BOM.  \t\t\t\t\t\t\t ┃");
          println("┃ " + Console.MAGENTA + "2" + Console.RESET + ") - Iniciar partida Shell Damas BOM.\t\t\t\t\t\t\t ┃");
          println("┣━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┫");
          println("┃ - " + Console.RED + "NOTA" + Console.RESET + ": Seleccione la forma grafica de ejecucion del juego. \t\t\t\t ┃");
          println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
          print(" ❈ Seleccione una opcion(" + Console.GREEN + "Pulse 0 para salir" + Console.RESET + "): ")
          val opc:String = Console.in.readLine();
          opc match {
               case "1" ⇒ 
                    Util.clipSoundEfect("arcade_echo.wav").start();   // Efecto de sonido del Inicio.
                    Gui.InitGUI();                                    // Inicializa la interfaces Grafica.
               case "2" ⇒ 
                    Util.clipSoundEfect("arcade_echo.wav").start();   // Efecto de sonido del Inicio.
                    Cmd.initShell();                                  // Inicializa la interfaces Shell.
               case _ ⇒
                    if (opc != "0") {
                         println("\n - " + Console.RED + "ERROR: " + Console.RESET + "La opcion de juego seleccionda no es valida.");
                         Thread.sleep(1000);
                    }
          }
          if (opc != "0") this.main(args);   // Nos llamamos de nuevo de forma recursiba.
          println(Console.GREEN + "\n - Has salido del juego amigo espero que te guste Scala ya que este juego lo patrocinan Linux OS y Mac OS orgullosos OS de desarrollo !!" + Console.RESET);
          Thread.sleep(1000);
          System.exit(0);
     }
}

case class TestMainDamas();