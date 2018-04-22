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
//clase para dibujar el tablero
class dibujarTablero(tablero: List[ Int ], val tamaño: Int) extends Component {

     listenTo(mouse.clicks)
     reactions += {
          case MouseClicked(_, p, _, _, _) => mouseClick(p.x, p.y);
     }

     def apply(x: Int, y: Int): Int = tablero(tamaño * y + x)

     def squareGeometry: (Int, Int, Int, Int) = {
          val d = tamaño match {
               case 8  => new Dimension(450, 450) //tamaño de la ventana
               case 16 => new Dimension(600, 600)
               case 32 => new Dimension(800, 800)
          };
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
          val d = tamaño match {
               case 8  => new Dimension(450, 450) //tamaño de la ventana
               case 16 => new Dimension(600, 600)
               case 32 => new Dimension(800, 800)
          };
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
                    val damas = apply(x, y)
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

/**
 * Ventana para mostrar el tablero de juego
 */
class mostrarTablero(val tablero: List[ Int ], val turno: Int, val nivel: Int, val dificultad: Int, val fichas: (Int, Int), val evento: String, val modo: Boolean, val score: (Int, Int), val tam: Int) extends Frame {

     /**
      * Declaricones globales del frame.
      */
     this.title = "Damas BOM for Scala" //titulo de la ventana
     this.resizable_=(false);
     private var (x0, y0) = (0, 0);
     private var tab      = new Tuple8(tablero, turno, nivel, dificultad, fichas, evento, modo, score);
     private val turne    = new Label(" - Turno: " + (if (turno == 0) "■" else "●"));
     private val nFichJ1  = new Label(" - Nº Fichas Jugador 2: " + tab._5._1.toString());
     private val nFichJ2  = new Label(" - Nº Fichas Jugador 2: " + tab._5._2.toString());
     private val puntos   = new Label(" - Puntuacion: " + (if (turno == 0) tab._8._1 else tab._8._2).toString());
     private val event    = new Label
         
     /**
       * configuración del contenido de la ventana
       */
     contents = new BoxPanel(Orientation.Horizontal) {
          contents += new BoxPanel(Orientation.Vertical) {
               contents += new dibujarTablero(tab._1, tam) {
                    contents += Swing.VStrut(5);
                    reactions += {
                         case  dibujarTableroEvento(x, y) => {
                              if ((x0 == 0) && (y0 == 0)) {
                                   x0 = x;
                                   y0 = y;
                              } else {
                                   if ((x0 != x) && (y0 != y)) {
                                        val dir = (if ((x > x0) && (y > y0)) 21 else if (x > x0 && y < y0) 11 else if (x < x0 && y < y0) 10 else 20);
                                        val tab_new = Tablero.damasPlayBom(tab._1, (y0, x0, dir), (if(tab._2 == 0) 0 else 1), (0, 0), false);
                                        if (!tab_new._3) {
                                             val dimension = (Math.sqrt(tab_new._4.length).toInt);
                                             val fichXJug = Tablero.numFichasXjugadorInCurse(tab_new._4, 0, (0, 0));
                                             if (tab_new._2) {
                                                  Stats.setVictorias(Stats.getVictorias() + 1)
                                                  val sms_game = (if (tam == 32) {
                                                       GUIDamas.open()
                                                       "ENHORABUENA: HAS COMPLETADO EL JUEGO: ";
                                                  } else {                                             
                                                       val tableroNuevo = Tablero.generarTablero((dimension * 2), (dimension * 2), (dimension * 2), (dificultad * 2));
                                                       val new_nivel = new mostrarTablero(tableroNuevo, turno, 0, dificultad * 2, fichXJug, new String, modo, (0, 0), tam * 2)
                                                       new_nivel.visible = true
                                                       "HAS COMPLETADO EL NIVEL";
                                                  });
                                                  JOptionPane.showMessageDialog(null, sms_game + tab_new._5, "Comprobar Partida", JOptionPane.INFORMATION_MESSAGE);
                                                  this.clone();
                                             } else if (tab_new._1) {
                                                  Stats.setEmpates(Stats.getEmpates() + 1)
                                                  val opcion = JOptionPane.showMessageDialog(null, "HAS EMPATADO", "Comprobar Partida", JOptionPane.INFORMATION_MESSAGE);
                                                  val tableroNuevo = Tablero.generarTablero((dimension * 2), (dimension * 2), (dimension * 2), (dificultad * 2));
                                                  val fichXJugInit = Tablero.numFichasXjugadorInit(Math.sqrt(tableroNuevo.length).toInt);
                                                  val newVentana = new mostrarTablero(tableroNuevo, turno, 0, dificultad, (fichXJugInit, fichXJugInit), new String, modo, (0, 0), tam);
                                                  newVentana.visible = true;
                                                  
                                             };
                                             tab = new Tuple8(tab_new._4, tab._2 + (if (turno == 0) 1 else -1), nivel, dificultad, fichXJug, tab_new._5, modo, tab_new._6);
                                             turne.text   = " - Turno: " + (if (tab._2 == 0) "■" else "●");
                                             nFichJ1.text = " - Nº Fichas Jugador 1: " + (tab._5._1.toString());
                                             nFichJ2.text = " - Nº Fichas Jugador 2: " + (tab._5._2.toString());
                                             puntos.text  = " - Puntuacion: " + (if (tab._2 == 0) tab._8._1 else tab._8._2).toString();
                                             /*contents    += new dibujarTablero(tab_new._4, tam);
                                             contents.drop(0);*/
                                        }
                                        //event.text = (tab_new._5.substring(tab_new._5.indexOf(Console.RESET + "[0m")));
                                        x0 = 0;
                                        y0 = 0;
                                   } 
                              }   
                         }
                    }
               };
               border = Swing.EmptyBorder(0, 0, 0, 10);
               preferredSize = tam match { // Tamaño del Canbas.
                    case 8  => new Dimension(455, 455);
                    case 16 => new Dimension(605, 605);
                    case 32 => new Dimension(805, 805);
               }
          };
          contents += new BoxPanel(Orientation.Vertical) {
               contents += new BoxPanel(Orientation.Vertical) {
                    border = Swing.CompoundBorder(Swing.TitledBorder(Swing.EtchedBorder, "Informacion de partida: "), Swing.EmptyBorder(5,5,5,10))
                    contents +=  turne
                    contents +=  nFichJ1; 
                    contents +=  nFichJ2; 
                    contents += new Label(" - Dificultad: " + (dificultad + 1));
                    contents += new Label(" - Nivel: " + (nivel + 1));
                    contents +=  puntos; 
                    contents += Swing.VStrut(10)
               }
               contents += new BoxPanel(Orientation.Vertical) {
                    border = Swing.CompoundBorder(Swing.TitledBorder(Swing.EtchedBorder, "Eventos: "), Swing.EmptyBorder(5,5,5,10));
                    contents += Swing.VStrut(10);
                    contents += event;
               }
               contents += new BoxPanel(Orientation.Vertical) {
                    contents += Swing.VStrut(10);
                    contents += new Button() {     //botón de comienzo de partida
                         text = "Guardar y salir";   
                         action = Action(text) { 
                              val tab = new Tuple8(tablero, turno, nivel, dificultad, fichas, evento, modo, score)
                              Persistencia.savePlayDamas(tab._1, tab._7, tab._3, tab._2, tab._4, tab._5, tab._8);
                              close
                              GUIDamas.open()
                         }
                    }
               }
          }
          contents += Swing.VStrut(10);
          border = Swing.EmptyBorder(10, 10, 10, 10);
     };
     peer.setLocationRelativeTo(null) //centrar la ventana
     peer.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE) //seleccionar la operación de cierre de ventana
     //escuchar eventos
     reactions += {
          case WindowClosing(_) => {
               this.close()
               GUIDamas.open()
          };
     }    
     
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
     val tamaño = Setting.getDimTablero()
     val filas = 8
     val columnas = 8

     //escuchar eventos
     listenTo(dificultad)
     listenTo(boton)

     //reacciones a esos eventos
     reactions += {
          case ButtonClicked(`boton`) => {
               val grid = Tablero.generarTablero(filas, columnas, tamaño, dif)
               val fichXJug = Tablero.numFichasXjugadorInit(Math.sqrt(grid.length).toInt);
               val mostrarTablero = new mostrarTablero(grid, turno, 0, dif, (fichXJug, fichXJug), new String, modo, (0, 0), tamaño)
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
               val mostrarTablero = new mostrarTablero(tablero._1, tablero._2, tablero._3, tablero._4, tablero._5, tablero._6, tablero._7, tablero._8, Setting.getDimTablero())
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