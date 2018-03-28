package damas.juego.shell
import scala.sys.process._

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
          
     }
}