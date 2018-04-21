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
import java.awt.geom._
import java.io._
import damas.juego._
import damas.util._
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
/**
  * <<<<<<< HEAD
  * @author Daniel
  */

object Tab {

     def getTab(grid: (List[ Int ], Int, Int, Int, (Int, Int), String, Boolean, (Int, Int))) = grid

}
object TableroGUI {

     // def getTablero(grid: List[Int]) = grid
     def apply(x: Int, y: Int, tamaño: Int, tablero: List[ Int ]) = tablero(tamaño * y + x)

}
//clase para dibujar el tablero
class dibujarTablero(tablero: List[ Int ], val tamaño: Int) extends Component {

     tamaño match {
          case 8  => preferredSize = new Dimension(300, 400) //tamaño de la ventana
          case 16 => preferredSize = new Dimension(700, 500)
          case 32 => preferredSize = new Dimension(900, 700)
     }

     listenTo(mouse.clicks)
     reactions += {
          case MouseClicked(_, p, _, _, _) => mouseClick(p.x, p.y)
     }

     def squareGeometry: (Int, Int, Int, Int) = {
          val d = size
          val squareSide = d.height min d.width
          val x0 = (d.width - squareSide) / tamaño - 1
          val y0 = (d.height - squareSide) / tamaño - 1
          (squareSide, x0, y0, squareSide / tamaño)
     }

     def mouseClick(x: Int, y: Int) {
          val (squareSide, x0, y0, wid) = squareGeometry
          if (x0 <= x && x < x0 + squareSide && y0 <= y && y < y0 + squareSide) {
               val col = (x - x0) / wid
               val row = (y - y0) / wid
               publish(dibujarTableroEvento(col, row))
          }
     }

     override def paintComponent(g: Graphics2D) {
          val d = size
          g.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING, java.awt.RenderingHints.VALUE_ANTIALIAS_ON)
          g.setColor(Color.BLACK);
          g.fillRect(0, 0, d.width, d.height);
          val squareSide = d.height min d.width
          val wid = squareSide / tamaño
          val x0 = (d.width - squareSide) / tamaño - 1
          val y0 = (d.height - squareSide) / tamaño - 1
          //líneas verticales
          for (x <- 1 to tamaño - 1) {
               g.setColor(Color.WHITE)
               g.draw(new Line2D.Double(x0 + x * wid, y0, x0 + x * wid, y0 + squareSide))
          }
          //líneas horizontales
          for (y <- 1 to tamaño - 1) {
               g.setColor(Color.WHITE)
               g.draw(new Line2D.Double(x0, y0 + y * wid, x0 + squareSide, y0 + y * wid))
               g.setStroke(new BasicStroke(3f))
          }
          for (x <- 0 until tamaño) {
               for (y <- 0 until tamaño) {
                    var damas = TableroGUI.apply(x, y, tamaño, tablero)
                    damas match {
                         case 32 => //ficha J1
                              g.setColor(colores(0))
                              g.fill(new Rectangle2D.Double(x0 + x * wid + 10, y0 + y * wid + 10, wid - 20, wid - 20))
                         case 21 => //ficha J2
                              g.setColor(colores(1))
                              g.fill(new Ellipse2D.Double(x0 + x * wid + 10, y0 + y * wid + 10, wid - 20, wid - 20))
                         case 30 => //dama J1
                              g.setColor(Color.ORANGE)
                              g.draw(new Rectangle2D.Double(x0 + x * wid + 10, y0 + y * wid + 10, wid - 20, wid - 20))
                         case 28 => //dama J2
                              g.setColor(Color.ORANGE)
                              g.draw(new Ellipse2D.Double(x0 + x * wid + 10, y0 + y * wid + 10, wid - 20, wid - 20))
                         case _ => //Bombas
                              if (damas % 10 >= 2 && damas != 10) {
                                   g.setColor(colores(damas % 10))
                                   if ((damas - (damas % 10)) / 10 == 3) {
                                        g.draw(new Rectangle2D.Double(x0 + x * wid + 10, y0 + y * wid + 10, wid - 20, wid - 20))
                                   }
                                   else {
                                        g.draw(new Ellipse2D.Double(x0 + x * wid + 10, y0 + y * wid + 10, wid - 20, wid - 20))
                                   }
                                   val x1 = x0 + x * wid + 10
                                   val y1 = y0 + y * wid + 10
                                   g.draw(new Line2D.Double(x1, y1, x1 + wid - 20, y1 + wid - 20))
                                   g.draw(new Line2D.Double(x1, y1 + wid - 20, x1 + wid - 20, y1))
                              }
                    }
               }
          }
     }

     def colores(n: Int): Color = {
          n match {
               case 0 => Color.red
               case 1 => Color.cyan
               case 2 => Color.green
               case 3 => Color.yellow
               case 4 => Color.magenta
               case 5 => Color.blue
               case _ => Color.white
          }
     }
}

/**
  * creamos un evento para obtener las coordenadas de la jugada a realizar
  */
case class dibujarTableroEvento(x: Int, y: Int) extends Event
//Ventana para mostrar el tablero de juego
class mostrarTablero(var turno: Int, val tablero: List[ Int ], val tamaño: Int) extends Frame {

     title = "Damas BOM for Scala" //titulo de la ventana
     this.resizable_=(true)
     tamaño match {
          case 8  => preferredSize = new Dimension(600, 300) //tamaño de la ventana
          case 16 => preferredSize = new Dimension(1200, 600)
          case 32 => preferredSize = new Dimension(1400, 800)
     }
     val labelTurno = new Label
     val numfichasJ1 = new Label
     val numfichasJ2 = new Label
     val evento = new Label
     val score = new Label
     val numfichas = Tablero.numFichasXjugadorInCurse(tablero, 0, (0, 0))
     labelTurno.text = " ❈❈❈❈❈ Turno del %s".format(turno) + " ❈❈❈❈❈"
     numfichasJ1.text = " ❈❈❈❈❈ Fichas Jugador 1: " + numfichas._1.toString() + " ❈❈❈❈❈"
     numfichasJ2.text = " ❈❈❈❈❈ Fichas Jugador 2: " + numfichas._2.toString() + " ❈❈❈❈❈"
     evento.text = " ❈ Evento: "
     val canvas = new dibujarTablero(tablero, tamaño)
     var x0, y0 = 0
     var direccion = 0
     /**
       * configuración del contenido de la ventana
       */
     contents = new BoxPanel(Orientation.Horizontal) {
          contents += canvas
          contents += new BoxPanel(Orientation.Vertical) {
               labelTurno.verticalTextPosition_=(Alignment.Center)
               contents += labelTurno
               contents += numfichasJ1
               contents += numfichasJ2
               contents += evento
               contents += score
          }
          contents += Swing.VStrut(10)
          border = Swing.EmptyBorder(10, 10, 10, 10)
     }

     peer.setLocationRelativeTo(null) //centrar la ventana
     peer.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE) //seleccionar la operación de cierre de ventana

     //escuchar eventos
     listenTo(canvas)
     reactions += {
          case dibujarTableroEvento(x, y) =>
               println("dibujarTableroEvento en " + x + " " + y)
               if (x == x0 && y == y0) {
                    x0 = 0
                    y0 = 0
                    println("Error: no puede hacerse doble click sobre una ficha")
               }
               else {
                    if (x0 == 0 && y0 == 0) {
                         x0 = x
                         y0 = y
                    }
                    else {
                         if (x > x0 && y > y0) {
                              direccion = 21
                              println("dibujarTableroEvento en " + x + " " + y + " " + direccion)
                         }
                         else if (x > x0 && y < y0) {
                              direccion = 11
                              println("dibujarTableroEvento en " + x + " " + y + " " + direccion)
                         }
                         else if (x < x0 && y < y0) {
                              direccion = 10
                              println("dibujarTableroEvento en " + x + " " + y + " " + direccion)
                         }
                         else if (x > x0 && y > y0) {
                              direccion = 20
                              println("dibujarTableroEvento en " + x + " " + y + "" + direccion)
                         }
                         /*var t = turno match {
                              case "Jugador"   | "Jugador 1" => 0
                              case "Jugador 2" | "Maquina"   => 1
                         }*/

                         var nuevoTablero = Tablero.damasPlayBom(tablero, (y0, x0, direccion), turno, (0, 0), false)
                         evento.text = "Evento: " + nuevoTablero._5
                         /*if (nuevoTablero._2) {
                              actualizarTablero(nuevoTablero._4, true)
                              evento.text = "Evento: " + tuple._3
                              score.text = "❈ J1: " + nuevoTablero._6._1 + "J2: " + nuevoTablero._6._2
                         } else if (tuple._1) {
                         	stats.NUM_EMPATES = stats.NUM_EMPATES + 1
                         	val opcion = JOptionPane.showMessageDialog(null, "HAS EMPATADO", "Comprobar Partida", JOptionPane.INFORMATION_MESSAGE)
                         	val tableroNuevo = Tablero.generarTablero(column, row, tamaño, dificultad)
                         	val newVentana = new mostrarTablero(turno, tableroNuevo, tamaño)
                         	this.close
                         	newVentana.visible = true
                         } else {
                         	actualizarTablero(nuevoTablero._4, false)
                         }*/
                    }
               }

          case WindowClosing(_) => {
               this.close()
               //  GUIDamas.getTablero(tablero)
               GUIDamas.open()
          }
     }

     def actualizarTablero(tablero: List[ Int ], ganado: Boolean, dificultad: Int) {
          /*turno match {
               case "Jugador" =>
                    turno = "Máquina"
                    labelTurno.text = "Turno de la %s".format(turno)
               case "Jugador 1" =>
                    turno = "Jugador 2"
                    labelTurno.text = "Turno del %s".format(turno)
               case "Jugador 2" =>
                    turno = "Jugador 1"
                    labelTurno.text = "Turno del %s".format(turno)
               case "Máquina" =>
                    turno = "Jugador"
                    labelTurno.text = "Turno del %s".format(turno)
          }*/
          if (ganado) {
               //stats.NUM_VICTORIAS = stats.NUM_VICTORIAS + 1
               if (tamaño == 32) {
                    val opcion = JOptionPane.showMessageDialog(null, "ENHORABUENA: HAS COMPLETADO EL JUEGO", "Comprobar Partdida", JOptionPane.INFORMATION_MESSAGE)
                    GUIDamas.open()
                    this.close
               }
               else {
                    val dimension = (Math.sqrt(tablero.length).toInt) * 2
                    val opcion = JOptionPane.showMessageDialog(null, "HAS COMPLETADO EL NIVEL", "Comprobar Partida", JOptionPane.INFORMATION_MESSAGE)
                    val tableroNuevo = Tablero.generarTablero(dimension, dimension, dimension, dificultad * 2)
                    val newVentana = new mostrarTablero(turno, tableroNuevo, dimension)
                    this.close
                    newVentana.visible = true
               }
          }
          else {
               val opcion = JOptionPane.showMessageDialog(null, "HAS PERDIDO", "Comprobar Partida", JOptionPane.INFORMATION_MESSAGE)
               //stats.setDerrotas(stats.getDerrotas() + 1)
               //val tableroNuevo = new TableroGUI(tablero)
               //val newVentana = new mostrarTablero(turno, tableroNuevo, tamaño)
               //this.close
               //newVentana.visible = true
          }
     }
}
//Ventana para realizar la configuracion de juego dhadjd
class configuracionJuego(val turno: Int, val modo: Boolean) extends Frame {
     title = "Configuración de la partida"; //título de la ventana
     preferredSize = new Dimension(300, 200); //tamaño de la ventana
     val dificultad = new ComboBox(List("Nivel 1", "Nivel 2", "Nivel 3", "Nivel 4", "Nivel 5")) //comboBox para la dificultad
     val label1 = new Label("Dificultad del juego"); //label para la dificultad
     val rboton = new RadioButton("Sonido")

     //pueda seleccionarse uno
     val boton = new Button {
          text = "Comenzar Partida" //botón de comienzo de partida
     }

     //configuración del contenido de la ventana
     contents = new BoxPanel(Orientation.Vertical) {
          contents += label1
          contents += dificultad
          contents += Swing.VStrut(5)
          contents += rboton
          contents += Swing.VStrut(5)
          contents += boton
          contents += Swing.VStrut(5)
          border = Swing.EmptyBorder(30, 30, 10, 30)
     }

     peer.setLocationRelativeTo(null) //centrar la ventana
     peer.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE); //seleccionar la operación de cierre de ventana
     val dif = Setting.getDificultad(); //por defecto el nivel de dificultad será 1
     val filas = 8 //por defecto el número de filas será 8
     val columnas = 8 //por defecto el número de columnas será 8

     //escuchar eventos
     listenTo(dificultad)
     listenTo(rboton)
     listenTo(boton)

     //obtener valores
     val dificultadSelected = dificultad.selection.item

     //reacciones a esos eventos
     reactions += {
          case ButtonClicked(`boton`) => {
               val grid = Tablero.generarTablero(filas, columnas, filas, dif)
               val mostrarTablero = new mostrarTablero(turno, grid, filas)
               UtilDamas.clipSoundEfect("arcade_echo.wav").start();
               mostrarTablero.visible = true
               this.visible = false
          }
          case ButtonClicked(`rboton`) => {
               if (Setting.getSound())
                    Setting.setSound(false)
               else
                    Setting.setSound(true)
          }
          case SelectionChanged(`dificultad`) => {
               val d = dificultad.selection.item match {
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

     /*labelV.text = "Número de victorias: %d".format(stats.getVictorias())
     labelD.text = "Número de derrotas: %d".format(stats.getDerrotas())
     labelE.text = "Número de empates: %d".format(stats.getEmpates())*/

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
               /*stats.setVictorias(0)
               stats.setDerrotas(0)
               stats.setEmpates(0)
               labelV.text = "Número de victorias: %d".format(stats.getVictorias())
               labelD.text = "Número de derrotas: %d".format(stats.getDerrotas())
               labelE.text = "Número de empates:
               %d".format(stats.getEmpates())*/
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
class partidasGuardadas(partidas: List[ File ]) extends Frame {
     title = "Partidas guardadas"; //titulo de la ventana
     preferredSize = new Dimension(400, 200); //tamaño de la ventana
     val label = new Label()
     val texto = new TextArea(1, 1)
     label.text = echoGetFileSaveGUI(partidas, 0)
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
               //val mostrarTablero = new mostrarTablero(turno, tablero._1, filas)
          }
          case WindowClosing(_) => {
               this.close()
               GUIDamas.open()
          }
     }

     def echoGetFileSaveGUI(listSave: List[ File ], cont: Int): String = {
          if (cont < listSave.length) {
               val f_file = listSave.apply(cont);
               val f_name = f_file.getName().substring(0, f_file.getName().indexOf(".xml"));
               val f_modif = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault).format(new Date(f_file.lastModified()));
               return String.format(" Partida [%s] \t\t Modificado: %s \t\n ", (cont + 1).toString(), f_name, f_modif).concat(this.echoGetFileSaveGUI(listSave, cont + 1));
          }
          return new String;
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

