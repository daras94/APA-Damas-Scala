package damas.juego.gui
/**
 * @import
 */
import scala.swing._
/**
 * @author Daniel.
 */
object GUIDamas extends MainFrame {
     
     /**
      * Propidades de la interfaces.
      */
     title = "GUI Program #1";
     preferredSize = new Dimension(320, 240);
     contents = new Label("Here is the contents!");
     
     /**
      * Metodo Que se encarga de inicialiar la interfaces grafica,    
      */
     def InitGUI(): Unit = {
          val ui = this;
          ui.visible = true
          println("End of main function");
     }
}
