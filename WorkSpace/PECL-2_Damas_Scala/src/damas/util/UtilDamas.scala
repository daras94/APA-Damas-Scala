package damas.util

/**
  * Imports
  */
import java.net.URL;
import java.awt.image.BufferedImage;
import java.awt._;
import javax.imageio.ImageIO;
import java.io.File
import java.io.IOException;
import javax.sound.sampled._
import scala.sys.process._
/**
  * @author david
  */
object UtilDamas {

     /**
       * Reproductor de efectos de sonido del Juego.
       *
       * 	- pathClipAudio = String a la ruta del recuso de audio.
       */
     def clipSoundEfect(pathClipAudio: String): Clip = {
          val path: URL = getClass().getResource("/sound/" + pathClipAudio);
          val audioIn = AudioSystem.getAudioInputStream(path);
          val clip = AudioSystem.getClip;
          clip.open(audioIn);
          return clip;
     }     

     /**
       * Fucion que borra el contenido del pront en funcion de S.O en el que se
       * ejecute la aplicacion.
       */
     def clear() = (if (System.getProperty("os.name").contains("Windows")) "cls".! else "clear".!);

     /**
       *
       */
     def loadFont(): Unit = {
          val fontUrl = getClass().getResource("/FreeSans.ttf");          // La fuente deve de ser menor que 35 KB
          val ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
          ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, fontUrl.openStream()));
     }
     
     /**
      * Metodo que tranfoma el texto introducido en un text ART para locual
      * pinta una imagen en 2D transforado en texto en pixeles que luego hace
      * corresponder con los carracteres especificados.
      */
     def printtextArt(textART:String, subText:String): Unit = {
          val image = new BufferedImage(textART.length()*10, textART.length()*10, BufferedImage.TYPE_INT_RGB);
          try { 
               val graphics2D = image.createGraphics();
               image.getGraphics.setFont(new Font("Sans", Font.PLAIN, 8));
               graphics2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
               graphics2D.drawString(textART, 6, 12);
               this.generateTextArt(image, textART.length()*9, textART.length()*7, textART.length()*7, new StringBuilder, 31, subText);
          } catch {
               case ex: java.io.IOException ⇒ Console.RED + "- ERROR: " + Console.RESET + ex.getMessage();
          }
     }
          
     /**
      * Metodo que se encarga de gegerar y pintar el text Art.
      */
     private def generateTextArt(art:BufferedImage, height:Int, width:Int, x:Int, sb:StringBuilder, cont:Int, sms:String): Unit = {
          if (width != 0) {
               if (height != 0) {
                    sb.append(if (art.getRGB(height, width) == -16777216) "\u0020" else if (art.getRGB(height, width) == -1) "\u25A2" else "\u25AE");
               } else {
                    if (!sb.toString().trim().isEmpty) {
                         sb.append("\n").reverse;     // Coreciones para evitar que muestre el text art espejado.
                    } else {
                         sb.delete(sb.indexOf("\n") + 1, sb.length);
                    }
               }
               this.generateTextArt(art, (height + (if (height == 0) x else -1)), (width + (if (height == 0) -1 else 0)), x, sb, (cont + 1), sms);
          } else{
               println(Console.BOLD + Console.CYAN + sb.reverse + Console.RESET);
               println(String.format("%s %110s %s", Console.BOLD + Console.MAGENTA, sms, Console.RESET));
          }
     }     
     
     
}