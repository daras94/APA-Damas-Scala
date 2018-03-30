package damas.util
import java.net.URL
import javax.sound.sampled._
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
}