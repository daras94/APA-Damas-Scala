package damas.util

import scala.collection.immutable;
import java.util.Properties;
import java.io.InputStream;
import java.io.File;
import java.io.OutputStream;
import java.io.FileOutputStream;
import java.io.FileInputStream;
/**
 * @author daniel
 */
object stats {
   
     /**
      * Establece las nuevas dimensiones del tablero de juego pre establecidas.
      */
     def setVictorias(victorias:Int): Unit = {
          val props:Properties = this.loadPropertis();
          props.setProperty("damas.victorias", victorias.toString());
          this.saveChange(props);
          props.clear();
     }
     def setEmpates(empates:Int): Unit = {
          val props:Properties = this.loadPropertis();
          props.setProperty("damas.empates", empates.toString());
          this.saveChange(props);
          props.clear();
     }
     
     /**
      * Recperamos el valor de la configuracion de la dimension del tablero.
      */
     def getVictorias(): Int = {
          return this.loadPropertis().getProperty("damas.victorias").toInt;
     }
     def getEmpates(): Int = {
          return this.loadPropertis().getProperty("damas.empates").toInt;
     }
    
     
     /**
      * Restablece la configuracion de fabrica del juego.
      */
     def restoreConf(): Unit = {
          try {
               val fileCof = new File("conf/stats.properties");
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
               val is:InputStream = new FileInputStream(new File("conf/stats.properties"));
               props.load(is);
               is.close();
          } catch {
               case t: Exception => 
                    try {
                         val is:InputStream = getClass().getResourceAsStream("/stats.properties");
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
                    val out:OutputStream = new FileOutputStream(new File("conf/stats.properties"));
                    props.store(out, "######################################################################################### \n" +
                                     "#                  ---> { Estadisticas del juego } <----                                # \n" +
                                     "######################################################################################### \n");
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