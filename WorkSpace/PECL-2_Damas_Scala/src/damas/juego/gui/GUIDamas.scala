package damas.juego.gui
package damas.juego
/**
 * @import
 */
import scala.swing._
import scala.swing.event._
import javax.swing.JOptionPane
import javax.swing.WindowConstants
/**
 * @author Daniel
 */
//clase para dibujar el tablero
class dibujarTablero(val tablero: List[Int]) extends Component {
  
  
}
//creamos un evento para obtener las coordenadas de la jugada a realizar
case class dibujarTableroEvento(x: Int, y: Int) extends Event 
//Ventana para mostrar el tablero de juego
class mostrarTablero(val turno: String,val tablero: List[Int]) extends Frame { 
   
   title = "Damas BOM for Scala - "; //titulo de la ventana
   preferredSize = new Dimension(300, 300); //tamaño de la ventana
   val boton = Button("Nueva Partida") { 
     nuevoJuego()
   }
   val labelTurno = new Label
   labelTurno.text = "Turno del %s".format(turno)
   val canvas = new dibujarTablero(tablero)
   
   //configuración del contenido de la ventana 
   contents = new BoxPanel(Orientation.Vertical) {
    contents += labelTurno
    contents += canvas
    contents += Swing.VStrut(10)
    contents += boton
    border = Swing.EmptyBorder(10, 10, 10, 10)
   }
   
   peer.setLocationRelativeTo(null) //centrar la ventana
   peer.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE) //seleccionar la operación de cierre de ventana
   
   //escuchar eventos 
   listenTo(canvas)
   reactions += {
    case dibujarTableroEvento(x,y) => 
      //Tablero.realizarJugada(x,y)
      actualizarTablero()
    }
  
  def actualizarTablero(){
    labelTurno.text = "Turno del %s".format(turno)
    canvas.repaint()
  }
  
  def nuevoJuego() {
    //Tablero.restart()
    actualizarTablero()
  }
}
//Ventana para realizar la configuracion de juego
class configuracionJuego(val turno: String) extends Frame { 
  
    title = "Configuración de la partida"; //título de la ventana
    preferredSize = new Dimension(300, 300); //tamaño de la ventana
    val dificultad = new ComboBox(List("Nivel 1","Nivel 2","Nivel 3","Nivel 4","Nivel 5"))//comboBox para la dificultad
    val label1 = new Label("Dificultad del juego");//label para la dificultad
    val filasColumnas = new RadioButton("8x8")//radioButton1
    val filasColumnas2 = new RadioButton("16x16")//radioButton2
    val filasColumnas3 = new RadioButton("32x32")//radioButton3
    filasColumnas.selected = true//por defecto queda seleccionado el primer radio button
    val filasColumnasGroup = new ButtonGroup(filasColumnas,filasColumnas2,filasColumnas3)//agrupamos los botones en un grupo para que solo
    //pueda seleccionarse uno
    val boton = new Button {
      text = "Comenzar Partida"//botón de comienzo de partida
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
     listenTo(filasColumnas)
     listenTo(filasColumnas2)
     listenTo(filasColumnas3)
     
     //obtener valores
     val dificultadSelected = dificultad.selection.item
     val tamaño = filasColumnasGroup.selected
     
     //reacciones a esos eventos 
     reactions += {
     case ButtonClicked(`boton`) => {
         //var tablero = generarTablero(columnas,filas,dificultad)
         //val mostrarTablero = new mostrarTablero(turno,tablero)
         //mostrarTablero.visible = true
         //this.visible = false  
      }
     case SelectionChanged(`dificultad`) => {
        val d = dificultad.selection.item match{
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
     case WindowClosing (_) => {
       this.close()
       GUIDamas.open()
     }
    }
}
//Ventana para mostrar las estadísticas del jugador (victorias, derrotas y empates). Opción para reseterarlas
class estadisticasJuego extends Frame {  
    title = "Estadísticas del jugador"; //Titulo de la ventana
    preferredSize= new Dimension(240, 180); //Tamaño de la ventana
    
   
}
//Ventana principal
object GUIDamas extends Frame {   
     /**
      * Propiedades de la interface.
      */
     title = "Damas BOM for Scala"; //titulo de la ventana
     preferredSize= new Dimension(240, 160); //tamaño de la ventana
     
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
     case WindowClosing (`GUIDamas`) => {
         val opcion = JOptionPane.showConfirmDialog(null,"¿Salir del juego y guardar la partida?")
         if (opcion == 0){
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

 
