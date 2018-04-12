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
import scala.io.Source
import java.io._
import damas.juego._

/**
 * @author Daniel
 */
//objeto para guardar las estadisticas del jugador
object stats {
<<<<<<< HEAD
  var NUM_VICTORIAS = 3
  var NUM_DERROTAS = 5
  var NUM_EMPATES = 6
}
//clase para generar el tablero segun dificultad y tamaño
class TableroGUI(grid: List[Int]) {

  def getTablero = grid
  def apply(x: Int, y: Int, tamaño: Int) = grid(tamaño * y + x)
=======
     var NUM_VICTORIAS = 3
     var NUM_DERROTAS = 5
     var NUM_EMPATES = 6
}
//clase para generar el tablero segun dificultad y tamaño
class TableroGUI(grid: List[ Int ]) {

     def getTablero = grid
     def apply(x: Int, y: Int, tamaño: Int) = grid(tamaño * y + x)
>>>>>>> 284378426960799c1f58a4bed7c4f4c9c115377f

}
//clase para dibujar el tablero
class dibujarTablero(val tablero: TableroGUI, val tamaño: Int) extends Component {

<<<<<<< HEAD
  preferredSize = new Dimension(520, 520)
  var direccion = 0

  listenTo(mouse.clicks)
  reactions += {
    case MouseClicked(_, p, _, _, _) => mouseClick(p.x, p.y)
  }

  // returns squareSide, x0, y0, wid
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
        var damas = tablero(x, y, tamaño)
        damas match {
          case 32 =>
            g.setColor(colores(0))
            g.draw(new Rectangle2D.Double(x0 + x * wid + 10, y0 + y * wid + 10, wid - 20, wid - 20))
          case 21 =>
            g.setColor(colores(1))
            g.draw(new Ellipse2D.Double(x0 + x * wid + 10, y0 + y * wid + 10, wid - 20, wid - 20))
          case _ => //Bombas
            if (damas % 10 >= 2 && damas != 10) {
              g.setColor(colores(damas % 10))
              if ((damas - (damas % 10)) / 10 == 3) {
                g.draw(new Rectangle2D.Double(x0 + x * wid + 10, y0 + y * wid + 10, wid - 20, wid - 20))
              } else {
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
=======
     preferredSize = new Dimension(520, 520)
     var direccion = 0

     listenTo(mouse.clicks)
     reactions += {
          case MouseClicked(_, p, _, _, _) => mouseClick(p.x, p.y)
     }

     // returns squareSide, x0, y0, wid
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
                    var damas = tablero(x, y, tamaño)
                    damas match {
                         case 32 =>
                              g.setColor(colores(0))
                              g.draw(new Rectangle2D.Double(x0 + x * wid + 10, y0 + y * wid + 10, wid - 20, wid - 20))
                         case 21 =>
                              g.setColor(colores(1))
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
>>>>>>> 284378426960799c1f58a4bed7c4f4c9c115377f
}

/**
  * creamos un evento para obtener las coordenadas de la jugada a realizar
  */
case class dibujarTableroEvento(x: Int, y: Int) extends Event
//Ventana para mostrar el tablero de juego
class mostrarTablero(var turno: String, val tablero: TableroGUI, val tamaño: Int) extends Frame {

<<<<<<< HEAD
  title = "Damas BOM for Scala" //titulo de la ventana
  preferredSize = new Dimension(700, 700) //tamaño de la ventana
  val labelTurno = new Label
  labelTurno.text = "Turno del %s".format(turno)
  val canvas = new dibujarTablero(tablero, tamaño)
  var x0, y0 = 0
  var direccion = 0
  /**
   * configuración del contenido de la ventana
   */
  contents = new BoxPanel(Orientation.Vertical) {
    contents += labelTurno
    contents += canvas
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
      if (x0 == 0 && y0 == 0) {
        x0 = x
        y0 = y
      } else {
        if (x > x0 && y > y0) {
          direccion = 21
          println("dibujarTableroEvento en " + x + " " + y + "" + direccion)
        } else if (x > x0 && y < y0) {
          direccion = 11
          println("dibujarTableroEvento en " + x + " " + y + "" + direccion)
        } else if (x < x0 && y < y0) {
          direccion = 10
          println("dibujarTableroEvento en " + x + " " + y + "" + direccion)
        } else if (x > x0 && y > y0) {
          direccion = 20
          println("dibujarTableroEvento en " + x + " " + y + "" + direccion)
        }
        var t = turno match {
          case "Jugador" | "Jugador 1" => 0
          case "Jugador 2" | "Maquina" => 1
        }
        var nuevoTablero = Tablero.damasPlayBom(tablero.getTablero, (y0, x0, direccion), t)
        actualizarTablero(nuevoTablero._3)
      }
    case WindowClosing(_) => {
      this.close()
      // GUIDamas.getTablero(tablero)
      GUIDamas.open()
    }
  }

  def actualizarTablero(tablero: List[Int]) {
    turno match {
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
    }
    val tableroNuevo = new TableroGUI(tablero)
    val newVentana = new mostrarTablero(turno, tableroNuevo, tamaño)
    this.close
    newVentana.visible = true
  }
=======
     title = "Damas BOM for Scala" //titulo de la ventana
     preferredSize = new Dimension(700, 700) //tamaño de la ventana
     val labelTurno = new Label
     labelTurno.text = "Turno del %s".format(turno)
     val canvas = new dibujarTablero(tablero, tamaño)
     var x0, y0 = 0
     var direccion = 0
     /**
       * configuración del contenido de la ventana
       */
     contents = new BoxPanel(Orientation.Vertical) {
          contents += labelTurno
          contents += canvas
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
               if (x0 == 0 && y0 == 0) {
                    x0 = x
                    y0 = y
               }
               else {
                    if (x > x0 && y > y0) {
                         direccion = 21
                         println("dibujarTableroEvento en " + x + " " + y + "" + direccion)
                    }
                    else if (x > x0 && y < y0) {
                         direccion = 11
                         println("dibujarTableroEvento en " + x + " " + y + "" + direccion)
                    }
                    else if (x < x0 && y < y0) {
                         direccion = 10
                         println("dibujarTableroEvento en " + x + " " + y + "" + direccion)
                    }
                    else if (x > x0 && y > y0) {
                         direccion = 20
                         println("dibujarTableroEvento en " + x + " " + y + "" + direccion)
                    }
                    var t = turno match {
                         case "Jugador" | "Jugador 1" => 0
                         case "Jugador 2" | "Maquina" => 1
                    }
                    var nuevoTablero = Tablero.damasPlayBom(tablero.getTablero, (y0, x0, direccion), t)
                    actualizarTablero(nuevoTablero._3)
               }
          case WindowClosing(_) => {
               this.close()
               // GUIDamas.getTablero(tablero)
               GUIDamas.open()
          }
     }

     def actualizarTablero(tablero: List[ Int ]) {
          turno match {
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
          }
          val tableroNuevo = new TableroGUI(tablero)
          val newVentana = new mostrarTablero(turno, tableroNuevo, tamaño)
          this.close
          newVentana.visible = true
     }
>>>>>>> 284378426960799c1f58a4bed7c4f4c9c115377f
}
//Ventana para realizar la configuracion de juego dhadjd
class configuracionJuego(val turno: String) extends Frame {

<<<<<<< HEAD
  title = "Configuración de la partida"; //título de la ventana
  preferredSize = new Dimension(300, 200); //tamaño de la ventana
  val dificultad = new ComboBox(List("Nivel 1", "Nivel 2", "Nivel 3", "Nivel 4", "Nivel 5")) //comboBox para la dificultad
  val label1 = new Label("Dificultad del juego"); //label para la dificultad
  //pueda seleccionarse uno
  val boton = new Button {
    text = "Comenzar Partida" //botón de comienzo de partida
  }
=======
     title = "Configuración de la partida"; //título de la ventana
     preferredSize = new Dimension(300, 200); //tamaño de la ventana
     val dificultad = new ComboBox(List("Nivel 1", "Nivel 2", "Nivel 3", "Nivel 4", "Nivel 5")) //comboBox para la dificultad
     val label1 = new Label("Dificultad del juego"); //label para la dificultad
     //pueda seleccionarse uno
     val boton = new Button {
          text = "Comenzar Partida" //botón de comienzo de partida
     }
>>>>>>> 284378426960799c1f58a4bed7c4f4c9c115377f

  //configuración del contenido de la ventana
  contents = new BoxPanel(Orientation.Vertical) {
    contents += label1
    contents += dificultad
    contents += Swing.VStrut(5)
    contents += boton
    contents += Swing.VStrut(5)
    border = Swing.EmptyBorder(30, 30, 10, 30)
  }

<<<<<<< HEAD
  peer.setLocationRelativeTo(null) //centrar la ventana
  peer.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE) //seleccionar la operación de cierre de ventana
  var dif = 1 //por defecto el nivel de dificultad será 1
  var filas = 8 //por defecto el número de filas será 8
  var columnas = 8 //por defecto el número de columnas será 8

  //escuchar eventos
  listenTo(dificultad)
  listenTo(boton)

  //obtener valores
  val dificultadSelected = dificultad.selection.item

  //reacciones a esos eventos
  reactions += {
    case ButtonClicked(`boton`) => {
      val grid = Tablero.generarTablero(filas, columnas, filas, dif)
      val tablero = new TableroGUI(grid)
      val mostrarTablero = new mostrarTablero(turno, tablero, filas)
      mostrarTablero.visible = true
      this.visible = false
    }
    case SelectionChanged(`dificultad`) => {
      val d = dificultad.selection.item match {
        case "Nivel 2" => dif = 2
        case "Nivel 3" => dif = 3
        case "Nivel 4" => dif = 4
        case "Nivel 5" => dif = 5
      }
    }
    case WindowClosing(_) => {
      this.close()
      GUIDamas.open()
    }
  }
=======
     peer.setLocationRelativeTo(null) //centrar la ventana
     peer.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE) //seleccionar la operación de cierre de ventana
     var dif = 1 //por defecto el nivel de dificultad será 1
     var filas = 8 //por defecto el número de filas será 8
     var columnas = 8 //por defecto el número de columnas será 8

     //escuchar eventos
     listenTo(dificultad)
     listenTo(boton)

     //obtener valores
     val dificultadSelected = dificultad.selection.item

     //reacciones a esos eventos
     reactions += {
          case ButtonClicked(`boton`) => {
               val grid = Tablero.generarTablero(filas, columnas, filas, dif)
               val tablero = new TableroGUI(grid)
               val mostrarTablero = new mostrarTablero(turno, tablero, filas)
               mostrarTablero.visible = true
               this.visible = false
          }
          case SelectionChanged(`dificultad`) => {
               val d = dificultad.selection.item match {
                    case "Nivel 2" => dif = 2
                    case "Nivel 3" => dif = 3
                    case "Nivel 4" => dif = 4
                    case "Nivel 5" => dif = 5
               }
          }
          case WindowClosing(_) => {
               this.close()
               GUIDamas.open()
          }
     }
>>>>>>> 284378426960799c1f58a4bed7c4f4c9c115377f
}
//Ventana para mostrar las estadísticas del jugador (victorias, derrotas y empates). Opción para reseterarlas
class estadisticasJuego extends Frame {

<<<<<<< HEAD
  title = "Estadísticas del jugador"; //Titulo de la ventana
  preferredSize = new Dimension(220, 180); //Tamaño de la ventana
  val labelV = new Label
  val labelD = new Label
  val labelE = new Label
  val boton = new Button {
    text = "Reiniciar estadísticas"
  }

  labelV.text = "Número de victorias: %d".format(stats.NUM_VICTORIAS)
  labelD.text = "Número de derrotas: %d".format(stats.NUM_DERROTAS)
  labelE.text = "Número de empates: %d".format(stats.NUM_EMPATES)

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
      stats.NUM_VICTORIAS = 0
      stats.NUM_DERROTAS = 0
      stats.NUM_EMPATES = 0
      labelV.text = "Número de victorias: %d".format(stats.NUM_VICTORIAS)
      labelD.text = "Número de derrotas: %d".format(stats.NUM_DERROTAS)
      labelE.text = "Número de empates: %d".format(stats.NUM_EMPATES)
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
=======
     title = "Estadísticas del jugador"; //Titulo de la ventana
     preferredSize = new Dimension(220, 180); //Tamaño de la ventana
     val labelV = new Label
     val labelD = new Label
     val labelE = new Label
     val boton = new Button {
          text = "Reiniciar estadísticas"
     }

     labelV.text = "Número de victorias: %d".format(stats.NUM_VICTORIAS)
     labelD.text = "Número de derrotas: %d".format(stats.NUM_DERROTAS)
     labelE.text = "Número de empates: %d".format(stats.NUM_EMPATES)

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
               stats.NUM_VICTORIAS = 0
               stats.NUM_DERROTAS = 0
               stats.NUM_EMPATES = 0
               labelV.text = "Número de victorias: %d".format(stats.NUM_VICTORIAS)
               labelD.text = "Número de derrotas: %d".format(stats.NUM_DERROTAS)
               labelE.text = "Número de empates: %d".format(stats.NUM_EMPATES)
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
>>>>>>> 284378426960799c1f58a4bed7c4f4c9c115377f
}

//Ventana principal
object GUIDamas extends Frame {

  title = "Damas BOM for Scala"; //titulo de la ventana
  preferredSize = new Dimension(240, 160); //tamaño de la ventana

<<<<<<< HEAD
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

  var tablero = Array[String]() //para guardar la partida

  //configuracion del contenido de la ventana
  contents = new BoxPanel(Orientation.Vertical) {
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
      val turno = "Jugador 1"
      val configuracion = new configuracionJuego(turno)
      configuracion.visible = true
      this.visible = false
    case ButtonClicked(`boton2`) =>
      val turno = "Jugador"
      val configuracion = new configuracionJuego(turno)
      configuracion.visible = true
      this.visible = false
    case ButtonClicked(`boton3`) =>
      val stats = new estadisticasJuego()
      stats.visible = true
      this.visible = false
    case WindowClosing(`GUIDamas`) => {
      val opcion = JOptionPane.showConfirmDialog(null, "¿Salir del juego y guardar la partida?")
      if (opcion == 0) {
        //guardarPartida(tablero)
        this.close()
      }
    }
  }

  /*  def guardarPartida(t: TableroGUI){
=======
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

     var tablero = Array[ String ]() //para guardar la partida

     //configuracion del contenido de la ventana
     contents = new BoxPanel(Orientation.Vertical) {
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
               val turno = "Jugador 1"
               val configuracion = new configuracionJuego(turno)
               configuracion.visible = true
               this.visible = false
          case ButtonClicked(`boton2`) =>
               val turno = "Jugador"
               val configuracion = new configuracionJuego(turno)
               configuracion.visible = true
               this.visible = false
          case ButtonClicked(`boton3`) =>
               val stats = new estadisticasJuego()
               stats.visible = true
               this.visible = false
          case WindowClosing(`GUIDamas`) => {
               val opcion = JOptionPane.showConfirmDialog(null, "¿Salir del juego y guardar la partida?")
               if (opcion == 0) {
                    //guardarPartida(tablero)
                    this.close()
               }
          }
     }

     /*  def guardarPartida(t: TableroGUI){
>>>>>>> 284378426960799c1f58a4bed7c4f4c9c115377f
       val writer = new PrintWriter(new File("partida.txt"))
       writer.write(t.grid.toString())
       writer.close()
     }
     def cargarPartida() : Array[Int] = {
        var partidaCargada = Source.fromFile("partida.txt".toString())
        partidaCargada = partidaCargada.map(_.toInt)
        return partidaCargada
     }*/

<<<<<<< HEAD
  /*   def getTablero(t: TableroGUI){
=======
     /*   def getTablero(t: TableroGUI){
>>>>>>> 284378426960799c1f58a4bed7c4f4c9c115377f
        tablero=t.grid.toArray.toString()
     }
     * /
     */
<<<<<<< HEAD
  /**
   * Metodo que se encarga de inicializar la interface grafica,
   */
  def InitGUI(): Unit = {
    //cargarPartida
    val ui = this;
    ui.visible = true
  }
=======
     /**
       * Metodo que se encarga de inicializar la interface grafica,
       */
     def InitGUI(): Unit = {
          //cargarPartida
          val ui = this;
          ui.visible = true
     }
>>>>>>> 284378426960799c1f58a4bed7c4f4c9c115377f
}

