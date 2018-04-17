package damas.util;

// Imports
import damas.util._;
import scala.xml._;
import java.util.Calendar;
import java.net.URL;
import scala.xml.dtd.{DocType, PublicID};
import java.io.File;
import java.io.OutputStreamWriter;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.FileReader;
import java.io.FileNotFoundException;
/**
 * @author david
 */
object Persistencia {
     
     /**
      * Declaracion de variables GLobales.
      */
     val id_root = Text(Calendar.getInstance().getTime().toLocaleString());
     //val doctype = DocType("html", PublicID("-//W3C//DTD XHTML 1.0 Strict//EN", "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd"), Nil);
     
     /**
      * Almacenamos la partida en curso generando un Xml para su proxima
      * apertura y continuacion de la partida.
      */
     def savePlayDamas(tablero:List[Int], mode_game:Boolean, nivel:Int, turno:Int, dificultad:Int, num_fichas:(Int, Int)): Unit = {    
          val dimension = Math.sqrt(tablero.length).toInt;
          val atb_tab_n = new UnprefixedAttribute("dim-Y", Text(Math.sqrt(tablero.length).toInt.toString()), new UnprefixedAttribute("dim-X", Text(Math.sqrt(tablero.length).toInt.toString()), Null));
          val node_tab  = this.generateChild(Elem(null, "tablero", atb_tab_n, TopScope), tablero, 0, 0);
          val num_fich  = Elem(null, "fichasXJugador", new UnprefixedAttribute("jugador-2", Text(num_fichas._2.toString()), new UnprefixedAttribute("jugador-1", Text(num_fichas._1.toString()), Null)), TopScope, node_tab);
          val turne     = Elem(null, "turno", Null, TopScope, Text(turno.toString()));
          val level     = Elem(null, "nivel", Null, TopScope, Text(nivel.toString()));
          val play      = Elem(null, "play", new UnprefixedAttribute("dificultad", Text(dificultad.toString()), new UnprefixedAttribute("ia", Text(mode_game.toString()), Null)), TopScope, level, turne, num_fich);
          val fecha     = Elem(null, "fecha", Null, TopScope, id_root)
          this.writeXml(Elem(null, "damas", Null, TopScope, fecha, play));
     }
     
     
     /**
      * Geerrar elementos que contienen los nodos de las filas del tablero
      */
     private def generateChild(n:Elem, tablero:List[Int], row:Int, col:Int): Elem = {
          if (row <= Math.sqrt(tablero.length).toInt - 1) {
               def generateCol(col:Int): UnprefixedAttribute = {
                    return new UnprefixedAttribute(("col-" + col), Text(tablero(row * (Math.sqrt(tablero.length).toInt) + col).toString()), (if (col < Math.sqrt(tablero.length).toInt - 1) {
                         generateCol(col + 1) 
                    } else Null));
               }
               val row_n = Elem(null, ("row-" + row), generateCol(col), TopScope);
               val node  = (n match {
                    case Elem(prefix, label, attribs, scope, child @ _*) => Elem(prefix, label, attribs, scope, child ++ row_n : _*);
               });
               this.generateChild(node.copy(node.prefix, node.label, node.attributes, node.scope, true, node.child), tablero, (row + 1), 0);
          } else {
               return n;
          }               
     }
     
     /**
      * Escribimos en el fichero de xml para salvar la partida.
      */
     private def writeXml(node:Node): Unit = {
          val folder = new File(Setting.getSavePath())
          try {
               if (folder.exists()) {
                    val out:OutputStreamWriter = new OutputStreamWriter(new FileOutputStream(new File(folder.getPath() + "/" + id_root + ".xml")));
                    XML.write(out, XML.loadString(new PrettyPrinter(512, 4).format(node, TopScope)),"UTF-8", true, null, MinimizeMode.Always);
                    out.close();
               } else {
                    if(folder.mkdir()) {
                         this.writeXml(node);
                    } else {
                         println(" - " + Console.RED + "ERROR" + Console.RESET + ": Error al generar el directorio no se puden guardar las partidas.");
                    }
               }
          } catch {
               case t: Exception => t.printStackTrace() // TODO: handle error
          }
     }
     
     /*
      * Se encarga de guarda la partida savada para poder continuar el juego.
      */
     def changePlay(path:String): (List[Int], Int, Int, Int, (Int, Int), String, Boolean) = {
          val xml = this.loadFilePlay(path, null);
          val GetPlay:(List[Int], Int, Int, Int, (Int, Int), String, Boolean) = if (xml != null) {
               /**
                * Estraemos la informacion de la partida desde el XML con XPATH.
                */
               val (ia, dificultad)       = ((xml \\ "damas" \\ "play" \ "@ia").text,                                     (xml \\ "damas" \\ "play" \ "@dificultad").text);
               val (nivel, turno)         = ((xml \\ "damas" \\ "play" \ "nivel").text,                                   (xml \\ "damas" \\ "play" \ "turno").text);
               val (numFichJ1, numFichJ2) = ((xml \\ "damas" \\ "play" \\ "fichasXJugador" \ "@jugador-1").text,          (xml \\ "damas" \\ "play" \\ "fichasXJugador" \ "@jugador-1").text);
               val (dim_X, dim_Y)         = ((xml \\ "damas" \\ "play" \\ "fichasXJugador" \\ "tablero" \ "@dim-X").text, (xml \\ "damas" \\ "play" \\ "fichasXJugador" \\ "tablero" \ "@dim-Y").text);
               def generateRowTab(col:Int, row:Int): List[Int] = (xml \\ "play" \\ "fichasXJugador" \\ "tablero" \\ ("row-" + row) \ ("@col-" + col)) match {
                    case e:Node ⇒ e.text.toInt :: generateRowTab(col + 1, row)
                    case _ ⇒ (if (row < dim_Y.toInt) generateRowTab(0, (row + 1)) else Nil);
               };
               
               new Tuple7(generateRowTab(0, 0), turno.toInt, nivel.toInt, dificultad.toInt, (numFichJ1.toInt, numFichJ2.toInt), new String, ia.toBoolean);
          } else {
               new Tuple7(Nil, 0, 0, 0, (0, 0), new String, false);
          }
          return GetPlay
     }
         
     private def loadFilePlay(path:String, xml:Elem): Elem = {
          try {
               if (xml == null) {
                    val file = new File(Setting.getSavePath().toString());
                    if (file.exists()) {
                         return XML.loadFile(file.getPath() + "/" + path);
                    } 
               }
          } catch {
                 case t: FileNotFoundException => 
                      println(" - " + Console.RED + "ERROR" + Console.RESET + ": La partida que intenta cargar no existe.");
                      t.printStackTrace() // TODO: handle error
          }
          return xml;      
     }
     
     /*private  def loadTablero(): List[Int] = {
       
     }*/
  
}