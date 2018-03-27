import damas.juego._
import damas.gui._
import scala.sys.process._
/**
 * @author david
 * 
 * Declaracion de Paquete Damas.
 */
package object damas {
     /**
      * Declaracion de constantes para todo el apquete.
      */
     val Tab = Tablero;     //Inicializacion del Tablero.
     val Gui = GUIDamas;      
     
     /**
      * Declaracion de funciones del TIPO UTIL
      */
     def clear(): Unit = (if (System.getProperty("os.name").contains("Windows")) "cls" else "clear").!;
}