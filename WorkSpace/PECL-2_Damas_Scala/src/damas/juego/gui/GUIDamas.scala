package damas.juego.gui
/**
  * @import
  */
import scala.swing._
import scala.swing.event._
import javax.swing.JOptionPane
import javax.swing.WindowConstants
import java.awt.{Graphics2D,Color}
import java.awt.{Color,Graphics2D,BasicStroke}
import java.awt.geom._
/**
  * @author Daniel
  */
//objeto para guardar las estadisticas del jugador
object stats{
    var NUM_VICTORIAS = 3
    var NUM_DERROTAS = 5
    var NUM_EMPATES = 6
}
//clase para generar el tablero segun dificultad y tamaño
class TableroGUI(val tamaño: Int){
  
   //val player = 1
   val grid = 
   Array(0, 1, 0, 1, 0, 1, 0, 1,
			   1, 0, 1, 0, 1, 0, 1, 0,
			   0, 1, 0, 1, 0, 1, 0, 1,
			   0, 0, 0, 0, 0, 0, 0, 0,
			   0, 0, 0, 0, 0, 0, 0, 0,
			   2, 0, 2, 0, 2, 0, 2, 0,
			   0, 2, 0, 2, 0, 2, 0, 2,
			   2, 0, 2, 0, 2, 0, 2, 0)

  def apply(x: Int, y: Int): Int = grid(tamaño * y + x)
  
 /* def realizarJugada(x: Int, y: Int) {
    if (this(x, y) == 0) {
      grid(tamaño * y + x) = player
      player = 3 - player
    }
  }
  def restart() {
    for (i <- 0 until 9)
      grid(i) = 0
    player = 1
  }
  */
}
//clase para dibujar el tablero
class dibujarTablero(val tablero: TableroGUI, val tamaño: Int) extends Component {
   
  preferredSize = new Dimension(520, 520)
  
  listenTo(mouse.clicks)
  reactions += {
    case MouseClicked(_, p, _, _, _) => mouseClick(p.x, p.y)
  }

  // returns squareSide, x0, y0, wid
  def squareGeometry: (Int, Int, Int, Int) = {
    val d = size
    val squareSide = d.height min d.width
    val x0 = (d.width - squareSide)/tamaño-1
    val y0 = (d.height - squareSide)/tamaño-1
    (squareSide, x0, y0, squareSide/tamaño)
  }

  def mouseClick(x: Int, y: Int) {
    val (squareSide, x0, y0, wid) = squareGeometry
    if (x0 <= x && x < x0 + squareSide && y0 <= y && y < y0 + squareSide) {
      val col = (x - x0) / wid
      val row = (y - y0) / wid
      publish(dibujarTableroEvento(col, row))
    }
  }
  
  override def paintComponent(g : Graphics2D) {
    val d = size
    g.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING,java.awt.RenderingHints.VALUE_ANTIALIAS_ON)
    g.setColor(Color.WHITE);
    g.fillRect(0,0, d.width, d.height);
    val squareSide = d.height min d.width
    val wid = squareSide / tamaño
    val x0 = (d.width - squareSide)/tamaño-1
    val y0 = (d.height - squareSide)/tamaño-1
    g.setColor(Color.BLACK)
    //líneas verticales
    for (x <- 1 to tamaño-1)
      g.draw(new Line2D.Double(x0 + x * wid, y0, x0 + x * wid, y0 + squareSide))
    //líneas horizontales
    for (y <- 1 to tamaño-1)
      g.draw(new Line2D.Double(x0, y0 + y * wid, x0 + squareSide, y0 + y * wid))
      g.setStroke(new BasicStroke(3f))
    for (x <- 0 until tamaño) {
      for (y <- 0 until tamaño) {
	      tablero(x, y) match {
	        case 1 =>
	          g.setColor(colores(0))
	          g.draw(new Ellipse2D.Double(x0 + x * wid + 10, y0 + y * wid + 10, wid - 20, wid - 20))
	        case 2 =>
	          g.setColor(colores(2))
	          val x1 = x0 + x * wid + 10
	          val y1 = y0 + y * wid + 10
	          g.draw(new Line2D.Double(x1, y1, x1 + wid - 20, y1 + wid - 20))
	          g.draw(new Line2D.Double(x1, y1 + wid - 20, x1 + wid - 20, y1))
	        case _ => 
	      }
      }
    }
  }
  
  def colores(n: Int): Color = {
    n match {
      case 0 => Color.red
      case 1 => Color.green
      case 2 => Color.blue
      case 3 => Color.yellow
      case 4 => Color.pink
      case 5 => Color.cyan
      case _ => Color.white 
    }
  }
}
//creamos un evento para obtener las coordenadas de la jugada a realizar
case class dibujarTableroEvento(x: Int, y: Int) extends Event
//Ventana para mostrar el tablero de juego
class mostrarTablero(var turno: String, val tablero: TableroGUI, val tamaño: Int) extends Frame {

     title = "Damas BOM for Scala - "; //titulo de la ventana
     preferredSize = new Dimension(700, 700); //tamaño de la ventana
     val labelTurno = new Label
     labelTurno.text = "Turno del %s".format(turno)
     val canvas = new dibujarTablero(tablero,tamaño)

     //configuración del contenido de la ventana
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
          case dibujarTableroEvento(x,y) =>
               println("dibujarTableroEvento en " + x + " " + y)
               //Tablero.realizarJugada(x,y)
               actualizarTablero()
          case WindowClosing(_) => {
               this.close()
               GUIDamas.open()
          }   
     }

     def actualizarTablero() {
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
        canvas.repaint()
     }
}
//Ventana para realizar la configuracion de juego
class configuracionJuego(val turno: String) extends Frame {

     title = "Configuración de la partida"; //título de la ventana
     preferredSize = new Dimension(300, 300); //tamaño de la ventana
     val dificultad = new ComboBox(List("Nivel 1", "Nivel 2", "Nivel 3", "Nivel 4", "Nivel 5")) //comboBox para la dificultad
     val label1 = new Label("Dificultad del juego"); //label para la dificultad
     val filasColumnas = new RadioButton("8x8") //radioButton1
     val filasColumnas2 = new RadioButton("16x16") //radioButton2
     val filasColumnas3 = new RadioButton("32x32") //radioButton3
     filasColumnas.selected = true //por defecto queda seleccionado el primer radio button
     val filasColumnasGroup = new ButtonGroup(filasColumnas, filasColumnas2, filasColumnas3) //agrupamos los botones en un grupo para que solo
     //pueda seleccionarse uno
     val boton = new Button {
          text = "Comenzar Partida" //botón de comienzo de partida
     }

     //configuración del contenido de la ventana
     contents = new BoxPanel(Orientation.Vertical) {
          contents += label1
          contents += dificultad
          contents += Swing.VStrut(5)
          contents += new Label("Tamaño del tablero")
          contents += filasColumnas
          contents += filasColumnas2
          contents += filasColumnas3
          contents += Swing.VStrut(5)
          contents += boton
          contents += Swing.VStrut(5)
          border = Swing.EmptyBorder(30, 30, 10, 30)
     }

     peer.setLocationRelativeTo(null) //centrar la ventana
     peer.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE) //seleccionar la operación de cierre de ventana
     var dif = 1 //por defecto el nivel de dificultad será 1
     var filas = 8 //por defecto el número de filas será 8
     var columnas = 8 //por defecto el número de columnas será 8

     //escuchar eventos
     listenTo(dificultad)
     listenTo(boton)
     listenTo(filasColumnas)
     listenTo(filasColumnas2)
     listenTo(filasColumnas3)

     //obtener valores
     val dificultadSelected = dificultad.selection.item
     val tamaño = filasColumnasGroup.selected

     //reacciones a esos eventos
     reactions += {
          case ButtonClicked(`boton`) => {
               //val tablero = Tablero.generarTablero(columnas,filas,dificultad)
               val tableroGUI = new TableroGUI(8)
               val mostrarTablero = new mostrarTablero(turno,tableroGUI,8)
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
          case ButtonClicked(`filasColumnas2`) => {
               filas = 16
               columnas = 16
          }
          case ButtonClicked(`filasColumnas3`) => {
               filas = 32
               columnas = 32
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
}
//Ventana principal
object GUIDamas extends Frame {

     title = "Damas BOM for Scala"; //titulo de la ventana
     preferredSize = new Dimension(240, 160); //tamaño de la ventana

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
                    //guardar partida
                    sys.exit(0)
               }
          }
     }
     /**
       * Metodo que se encarga de inicializar la interface grafica,
     */
     def InitGUI(): Unit = {
          //cargar partida
          val ui = this;
          ui.visible = true
     }
}

 
