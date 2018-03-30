<<<<<<< HEAD
import damas.juego._
import damas.juego.gui._
import damas.juego.shell._

=======
import damas.juego._;
import damas.juego.gui._;
import damas.juego.shell._;
import damas.util._;
>>>>>>> b0889cfc3d50e4b46b53affbfc037d5d10636201
/**
 * @author david
 * 
 * Declaracion de Paquete Damas.
 */
package object damas {
     /**
      * Declaracion de constantes para todo el apquete.
      */
     val Cmd = ShellDamas;
     val Gui = GUIDamas;   
     val Util = UtilDamas;
}