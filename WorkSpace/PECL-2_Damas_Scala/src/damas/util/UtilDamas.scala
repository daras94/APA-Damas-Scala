package damas.util

/**
  * Imports
  */
import java.net.URL;
import java.awt.GraphicsEnvironment;
import java.awt.Font;
import java.awt.FontFormatException
import java.io.File;
import java.io.IOException;
import javax.sound.sampled._
import scala.sys.process._
/**
 * @author david
 */
object UtilDamas {

<<<<<<< HEAD
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
=======
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
>>>>>>> 284378426960799c1f58a4bed7c4f4c9c115377f
}