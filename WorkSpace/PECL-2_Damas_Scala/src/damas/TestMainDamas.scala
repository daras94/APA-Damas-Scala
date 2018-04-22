package damas

import damas.util.Setting

//import java.awt._;

/**
  * @author david.
  * 
  * Declaracion de Objeto.
  */
object TestMainDamas /*extends App*/ {
     
     val str = new StringBuilder
     
     /**
       *  Declaracion del MAIN.
       */
     def main(args: Array[ String ]): Unit = {
          Util.clear(); str.clear(); str.append("\n");                                                     // Limpiamos el prompt. y Vaciamos el StringBuilder. 
          Util.printtextArt("💣 Damas BOM !!!", "© 2017 - 2018");
          str.append("\n ").append("┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
          str.append("\n ").append(String.format("┃   %s                                                             %-47s ┃", Console.CYAN, Console.RESET));
          str.append("\n ").append(String.format("┃ ❈ %s MENU Start UP, Damas BOM                                    %-47s ┃", Console.CYAN, Console.RESET));
          str.append("\n ").append(String.format("┃   %s-----------------------------------------------------------  %-47s ┃", Console.CYAN, Console.RESET));
          str.append("\n ").append("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛").append("\n");
          str.append("\n ").append(String.format(" %-10s 1%s) - %s", Console.MAGENTA, Console.RESET, "Iniciar partida GUI Damas BOM."));
          str.append("\n ").append(String.format(" %-10s 2%s) - %s", Console.MAGENTA, Console.RESET, "Iniciar partida Shell Damas BOM.")).append("\n");
          str.append("\n ").append("┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
          str.append("\n ").append(String.format("┃ ❈ %s NOTA %s: %-96s ┃", Console.RED, Console.RESET, "Seleccione la forma grafica de ejecucion del juego"));
          str.append("\n ").append("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛"); 
          str.append("\n ").append("  ❈ Seleccione una opcion(" + Console.GREEN + "Pulse 0 para salir" + Console.RESET + "): ");
          print(str)
          val opc:String = Console.in.readLine();
          opc match {
               case "1" ⇒ 
                    Util.clipSoundEfect("arcade_echo.wav", 0);             // Efecto de sonido del Inicio.
                    Gui.InitGUI();                                         // Inicializa la interfaces Grafica.
               case "2" ⇒ 
                    Util.clipSoundEfect("arcade_echo.wav", 0);             // Efecto de sonido del Inicio.
                    Cmd.initShell();                                       // Inicializa la interfaces Shell.
               case _ ⇒
                    if (opc != "0") {
                         println("\n - " + Console.RED + "ERROR: " + Console.RESET + "La opcion de juego seleccionda no es valida.");
                         Thread.sleep(1000);
                    }
          }
          if (opc != "0") this.main(args);   // Nos llamamos de nuevo de forma recursiba.
          println(Console.GREEN + "\n - Has salido del juego amigo espero que te guste Scala ya que este juego lo patrocinan Linux OS y Mac OS \n   orgullosos desarrollo !!" + Console.RESET);
          Thread.sleep(1000); 
          System.exit(0);
     }
}
