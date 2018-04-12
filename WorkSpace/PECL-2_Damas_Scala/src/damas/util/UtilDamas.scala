package damas.util

/**
 * Imports
 */
import java.net.URL
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
}