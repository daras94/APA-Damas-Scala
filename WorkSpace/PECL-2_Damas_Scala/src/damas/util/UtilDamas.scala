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
     def clipSoundEfect(pathClipAudio: String): Unit = {
          val audioIn = AudioSystem.getAudioInputStream(new URL(pathClipAudio));
          val clip = AudioSystem.getClip;
          clip.open(audioIn);
          clip.start;
     }
}