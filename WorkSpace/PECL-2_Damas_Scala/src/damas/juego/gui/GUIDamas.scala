package damas.juego.gui
/**
  * @import
  */
import scala.swing._
import scala.swing.event._
import javax.swing.JOptionPane
import javax.swing.WindowConstants
import java.awt.{ Graphics2D, Color }
import java.awt.{ Color, Graphics2D, BasicStroke }
import java.awt.geom._;
import damas.juego._
import damas.util._
import damas.util.Stats
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.io.File;

/**
  * @author Daniel
  */
object Tab {
     def getTab(grid: (List[ Int ], Int, Int, Int, (Int, Int), String, Boolean, (Int, Int))) = grid
}
object TableroGUI {
     // def getTablero(grid: List[Int]) = grid
     def apply(x: Int, y: Int, tamaño: Int, tablero: List[ Int ]) = tablero(tamaño * y + x)

}

//Ventana para realizar la configuracion de juego dhadjd
class configuracionJuego(val turno: Int, val modo: Boolean) extends Frame {

     title = "Configuración de la partida"; //título de la ventana
     preferredSize = new Dimension(300, 200); //tamaño de la ventana
     val dificultad = new ComboBox(List("Basico", "Facil", "Medio", "Experto", "Avanzado", "Legendario")) //comboBox para la dificultad
     val label1 = new Label("Dificultad del juego"); //label para la dificultad

     //pueda seleccionarse uno
     val boton = new Button {
          text = "Comenzar Partida" //botón de comienzo de partida
     }

     //configuración del contenido de la ventana
     contents = new BoxPanel(Orientation.Vertical) {
          contents += label1
          contents += dificultad
          contents += Swing.VStrut(5)
          contents += Swing.VStrut(5)
          contents += boton
          contents += Swing.VStrut(5)
          border = Swing.EmptyBorder(30, 30, 10, 30)
     }

     peer.setLocationRelativeTo(null) //centrar la ventana
     peer.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE) //seleccionar la operación de cierre de ventana
     val dif = Setting.getDificultad()
     val tam = Setting.getDimTablero()

     //escuchar eventos
     listenTo(dificultad)
     listenTo(boton)

     //reacciones a esos eventos
     reactions += {
          case ButtonClicked(`boton`) => {
               val grid = Tablero.generarTablero(tam, tam, tam, dif)
               val fichXJug = Tablero.numFichasXjugadorInit(Math.sqrt(grid.length).toInt);
               val mostrarTablero = new MostrarTablero(grid, turno, 0, dif, (fichXJug, fichXJug), new String, modo, (0, 0), tam)
               UtilDamas.clipSoundEfect("start_up.wav", 0);
               mostrarTablero.visible = true
               this.visible = false
          }
          case SelectionChanged(`dificultad`) => {
               dificultad.selection.item match {
                    case "Basico"     => Setting.setDificultad(-1)
                    case "Facil"      => Setting.setDificultad(0)
                    case "Medio"      => Setting.setDificultad(1)
                    case "Experto"    => Setting.setDificultad(2)
                    case "Avanzado"   => Setting.setDificultad(3)
                    case "Legendario" => Setting.setDificultad(4)
               }
          }
          case WindowClosing(_) => {
               this.close()
               GUIDamas.open()
          }
     }
}
//Ventana para mostrar las estadísticas del jugador (victorias, derrotas y empates). Opción para reseterarlas
class estadisticasJuego extends Frame {

     title = "Estadísticas del jugador"; //Titulo de la ventana
     preferredSize = new Dimension(220, 180); //Tamaño de la ventana
     val labelV = new Label
     val labelD = new Label
     val labelE = new Label
     val boton = new Button {
          text = "Reiniciar estadísticas"
     }

     labelV.text = "Número de victorias: %d".format(Stats.getVictorias())
     labelE.text = "Número de empates: %d".format(Stats.getEmpates())

     contents = new BoxPanel(Orientation.Vertical) {
          contents += labelV
          contents += labelD
          contents += labelE
          contents += Swing.VStrut(10)
          contents += boton
          border = Swing.EmptyBorder(30, 30, 10, 30)
     }
     peer.setLocationRelativeTo(null) //centrar la ventana
     peer.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE) //seleccionar la operación de cierre de ventana

     listenTo(boton)

     reactions += {
          case ButtonClicked(`boton`) =>
               Stats.setVictorias(0)
               Stats.setEmpates(0)
               labelV.text = "Número de victorias: %d".format(Stats.getVictorias())
               labelE.text = "Número de empates: %d".format(Stats.getEmpates())
               contents = new BoxPanel(Orientation.Vertical) {
                    contents += labelV
                    contents += labelD
                    contents += labelE
                    contents += Swing.VStrut(10)
                    contents += boton
                    border = Swing.EmptyBorder(30, 30, 10, 30)
               }
          case WindowClosing(_) => {
               this.close()
               GUIDamas.open()
          }
     }
}
//Ventana para cargar la partida
class partidasGuardadas(partidas: List[ File ]) extends Frame {

     title = "Partidas guardadas"; //titulo de la ventana
     preferredSize = new Dimension(400, 200); //tamaño de la ventana
     val label = new Label()
     val texto = new TextArea
     label.text = echoGetFileSaveGUI(partidas, 0, "")
     val boton = new Button {
          text = "Cargar"
     }

     contents = new BoxPanel(Orientation.Vertical) {
          contents += label
          contents += Swing.VStrut(10)
          contents += texto
          contents += boton
          border = Swing.EmptyBorder(30, 30, 10, 30)
     }

     peer.setLocationRelativeTo(null) //centrar la ventana
     peer.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE) //seleccionar la operación de cierre de ventana

     listenTo(boton)

     //reacciones a esos eventos
     reactions += {
          case ButtonClicked(`boton`) => {
               val tablero = Persistencia.changePlay(partidas.apply(texto.text.toInt).getName)
               val mostrarTablero = new MostrarTablero(tablero._1, tablero._2, tablero._3, tablero._4, tablero._5, tablero._6, tablero._7, tablero._8, Setting.getDimTablero())
               mostrarTablero.visible = true
               this.close()
          }
          case WindowClosing(_) =>
               this.close()
               GUIDamas.open()
     }

     def echoGetFileSaveGUI(listSave: List[ File ], cont: Int, salida: String): String = {
          if (cont < listSave.length) {
               val f_file = listSave.apply(cont);
               val f_name = f_file.getName().substring(0, f_file.getName().indexOf(".xml"));
               val salida = String.format("Partida [%s] ", (cont + 1).toString(), f_name);
               echoGetFileSaveGUI(listSave, cont + 1, salida)
          }
          else {
               return salida
          }
     }
}
//Ventana principal
object GUIDamas extends Frame {
     title = "Damas BOM for Scala"; //titulo de la ventana
     preferredSize = new Dimension(240, 160); //tamaño de la ventana
     val label = new Label()
     //boton opcion1
     val boton1 = new Button {
          text = "Jugador VS Jugador"
     }
     //boton opcion2
     val boton2 = new Button {
          text = "Jugador VS Máquina"
     }
     //boton opcion3
     val boton3 = new Button {
          text = "Estadísticas"
     }

     //configuracion del contenido de la ventana
     contents = new BoxPanel(Orientation.Vertical) {
          contents += label
          contents += boton1
          contents += boton2
          contents += boton3
          border = Swing.EmptyBorder(30, 30, 10, 30)
     }

     peer.setLocationRelativeTo(null) //centrar la ventana
     peer.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE) //seleccionar la operación de cierre de ventana

     //escuchar eventos
     listenTo(boton1)
     listenTo(boton2)
     listenTo(boton3)

     //reacciones a esos eventos
     reactions += {
          case ButtonClicked(`boton1`) =>
               val turno = 0
               val modo = false
               val configuracion = new configuracionJuego(turno, modo)
               configuracion.visible = true
               this.visible = false
          case ButtonClicked(`boton2`) =>
               val turno = 0
               val modo = true
               val configuracion = new configuracionJuego(turno, modo)
               configuracion.visible = true
               this.visible = false
          case ButtonClicked(`boton3`) =>
               val stats = new estadisticasJuego()
               stats.visible = true
               this.visible = false
          case WindowClosing(`GUIDamas`) =>
               val opcion = JOptionPane.showConfirmDialog(null, "¿Salir del juego y guardar la partida?")
               if (opcion == 0) {
                    //Persistencia.savePlayDamas(tablero, mode_game, nivel, turno, dificultad, num_fichas:(Int, Int), puntuacion:(Int, Int))
                    this.close()
               }

               def getTablero(tab: (List[ Int ], Int, Int, Int, (Int, Int), String, Boolean, (Int, Int))): Unit = {
                    val tablero = tab
               }
     }
     /**
       * Metodo que se encarga de inicializar la interface grafica,
       */
     def InitGUI(): Unit = {
          val partidas = Persistencia.getListOfFiles()
          if (partidas == null) {
               val ui = this;
               ui.visible = true
          }
          else {
               val load = new partidasGuardadas(partidas)
               load.visible = true
               this.visible = false
          }
     }
}