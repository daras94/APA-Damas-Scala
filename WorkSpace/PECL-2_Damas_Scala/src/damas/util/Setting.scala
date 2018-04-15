package damas.util;

import scala.collection.immutable;
import java.util.Properties;
import java.io.InputStream;
import java.io.File;
import java.io.OutputStream;
import java.io.FileOutputStream;
import java.io.FileInputStream;

/**
 * @author david
 */
object Setting {
     
     /**
      * Establece la dificultad del 
      */
     def setSound(enable:Boolean): Unit = {
          val props:Properties = this.loadPropertis();
          props.setProperty("damas.sound", enable.toString());
          this.saveChange(props);
          props.clear();
     }
     
     /**
      * Recperamos el valor de la configuracion de sonido del la aplicacion.
      */
     def getSound(): Boolean = {
          return this.loadPropertis().getProperty("damas.sound").toBoolean;
     }
     
     /**
      * Recupera el equvalente a la dificultad actualmente configurada en el
      * sistemas de arcade de damas.
      */
     def getStatusSound(): String = {
          val sound:Boolean = (this.loadPropertis().getProperty("damas.sound").toBoolean); 
          if (sound) {
               return "\u2007\u2007ENABLE\u2007\u2007";
          }
          return "\u2007DISABLED\u2007";
     }
     
     /**
      * Establece las nuevas dimensiones del tablero de juego pre establecidas.
      */
     def setDimTablero(dimension:Int): Unit = {
          val props:Properties = this.loadPropertis();
          props.setProperty("damas.juego.tablero.dimension", dimension.toString());
          this.saveChange(props);
          props.clear();
     }
     
     /**
      * Recperamos el valor de la configuracion de la dimension del tablero.
      */
     def getDimTablero(): Int = {
          return this.loadPropertis().getProperty("damas.juego.tablero.dimension").toInt;
     }
     
     
     def getStatusDimension(): String = {
          val dimension:Int = (this.loadPropertis().getProperty("damas.juego.tablero.dimension").toInt); 
          return (dimension + "x" + dimension).toString();
     }
     
     /**
      * Establecel nivel de dificultad del juego.
      */
     def setDificultad(dificult:Int): Unit = {
          val props:Properties = this.loadPropertis();
          props.setProperty("damas.juego.dificultad", dificult.toString());
          this.saveChange(props);
          props.clear();
     }
     
     /**
      * Recperamos el valor de la configuracion de la dificultad del juego
      */
     def getDificultad(): Int = {
          return this.loadPropertis().getProperty("damas.juego.dificultad").toInt;
     }
     
     /**
      * Recupera el equvalente a la dificultad actualmente configurada en el
      * sistemas de arcade de damas.
      */
     def getStatusDificultad(): String = (this.loadPropertis().getProperty("damas.juego.dificultad").toInt + 1) match {
          case 0 ⇒ " Basico  ";    case 1 ⇒ "  Facil   "; 
          case 2 ⇒ "  Medio  ";    case 3 ⇒ " Avanzado "; 
          case 4 ⇒ " Experto ";    case 5 ⇒ "Legendario"; 
     }
     
     
     /**
      * Recperamos el valor de la ruta de almacenamiento de las partidas
      * salvadas durante el juego.
      */
     def getSavePath(): String = {
          return this.loadPropertis().getProperty("damas.path.save.play");
     }
     
     /**
      * Restablece la configuracion de fabrica del juego.
      */
     def restoreConf(): Unit = {
          try {
               val fileCof = new File("conf/config.properties");
               if (fileCof.exists()) {
                    fileCof.delete();
               }
          } catch {
               case t: Throwable => t.printStackTrace() // TODO: handle error
          }
     }
     
     /**
      * Lemeos la configuraciones desde el fichero de configuracion de propertis
      * enbebido en la aplicacion.
      */
     private def loadPropertis(): Properties = {
          val props = new Properties();
          try {
               val is:InputStream = new FileInputStream(new File("conf/config.properties"));
               props.load(is);
               is.close();
          } catch {
               case t: Exception => 
                    try {
                         val is:InputStream = getClass().getResourceAsStream("/config.properties");
                         props.load(is);
                         is.close();
                    } catch {
                         case t:Exception => t.printStackTrace() // TODO: handle error
                    }
          }
          return props;
     }
     
     /**
      * 
      */
     private def saveChange(props:Properties): Unit = {
          val folder = new File("./conf")
          try {
               if (folder.exists()) {
                    val out:OutputStream = new FileOutputStream(new File("conf/config.properties"));
                    props.store(out, "######################################################################################### \n" +
                                     "#                  ---> { Propertis de configuracion del juego } <----                   # \n" +
                                     "########################################################################################## \n");
                    out.close();
               } else {
                    if(folder.mkdir()) {
                         this.saveChange(props);
                    } else {
                         println(" - " + Console.RED + "ERROR" + Console.RESET + ": Error al generar el directorio no se puden guardar los cambios.");
                    }
               }
          } catch {
               case t: Exception => t.printStackTrace() // TODO: handle error
          }
     }
}
