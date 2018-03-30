package damas

/**
  * @author david.
  * 
  * Declaracion de Objero.
  */
object TestMainDamas {
     /**
       *  Declaracion del MAIN.
       */
     def main(args: Array[ String ]): Unit = {
<<<<<<< HEAD
          println("/****************************************************************************************/");
          println("/*\t\t\t ===> { " + Console.CYAN + " MENU Start UP, Damas BOM " + Console.RESET + "} <=== \t\t\t*/");
          println("/****************************************************************************************/");
          println("/*\t" + Console.MAGENTA + "1" + Console.RESET + ") - Iniciar partida GUI Damas BOM.  \t\t\t\t\t\t*/");
          println("/*\t" + Console.MAGENTA + "2" + Console.RESET + ") - Iniciar partida Shell Damas BOM.\t\t\t\t\t\t*/");
          println("/****************************************************************************************/");
          print(" - Seleccione una opcion de juego (" + Console.GREEN + "Pulse 0 para salir" + Console.RESET + "): ")
          val i = Console.in.readLine();
          (i: String) match {
               case "1" => Gui.InitGUI();                                    // Inicializa la interfaces Grafica.
               case "2" => Cmd.initShell();                                  // Inicializa la interfaces Shell.
               case _ =>
                    if (i == "0") {
                         println(Console.GREEN + "\n - Has Salido del juego Amigo espero que te guste Scala ya que este juego lo patrocinan Linux OS y Mac OS orgullosos OS de desarrollo !!" + Console.RESET);
                         Thread.sleep(1000);
                         System.exit(0); 
                    } else {
                         println(" - " + Console.RED + "ERROR: " + Console.RESET + "Opcion de juego seleccionda no es valida.")    
=======
          Cmd.clear();                                                       // Limpia el pront en la consola de eclipse puede fallar. 
          println("┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
          println("┃\t\t\t ===> { " + Console.CYAN + " MENU Start UP, Damas BOM " + Console.RESET + "} <=== \t\t\t ┃");
          println("┣━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┫");
          println("┃ " + Console.MAGENTA + "1" + Console.RESET + ") - Inciar paratida GUI Damas BOM.  \t\t\t\t\t\t\t ┃");
          println("┃ " + Console.MAGENTA + "2" + Console.RESET + ") - Inciar paratida Shell Damas BOM.\t\t\t\t\t\t\t ┃");
          println("┣━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┫");
          println("┃ - " + Console.RED + "NOTA" + Console.RESET + ": Selecione la forma grafica de ejecucion del juego. \t\t\t\t ┃");
          println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
          print(" ❈ Selecione una opcion(" + Console.GREEN + "Pulse 0 para salir" + Console.RESET + "): ")
          var opc:String = Console.in.readLine();
          opc match {
               case "1" ⇒ 
                    Util.clipSoundEfect("arcade_echo.wav").start();   // Efecto de sonido del Inicio.
                    Gui.InitGUI();                                    // Inicializa la interfaces Grafica.
               case "2" ⇒ 
                    Util.clipSoundEfect("arcade_echo.wav").start();   // Efecto de sonido del Inicio.
                    Cmd.initShell();                                  // Inicializa la interfaces Shell.
               case _ ⇒
                    if (opc != "0") {
                         println("\n - " + Console.RED + "ERROR: " + Console.RESET + "Opcion de juego selecionda no es Valida.");
                         Thread.sleep(1000);
>>>>>>> b0889cfc3d50e4b46b53affbfc037d5d10636201
                    }
          }
          if (opc != "0") this.main(args);                                  // Nos llamamos de nuevo de forma recursiba.
          println(Console.GREEN + "\n - Has Salido del juego Amigo espero que te guste Scala ya que este juego lo patrocinan Linux OS y Mac OS orgullosos OS de desarrollo !!" + Console.RESET);
          Thread.sleep(1000);
     }
}