package damas.juego.gui;
// Import.
import scala.swing._;
import scala.swing.event._;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;
import java.awt.Color;
import java.awt.ComponentOrientation;
import damas.juego.Tablero;
import damas.util.Persistencia;
import damas.util.Stats;
import java.awt.Button
import javax.swing.plaf.MenuItemUI
import damas.util.ColorTextArea;


/**
 * @author david
 * 
 * Clase Que contrulle la ventana encargada de generra la ventana de juego del
 * Tablero.
 */
class MostrarTablero (val tablero:List[ Int ], val turno: Int, val nivel: Int, val dificultad: Int, val fichas: (Int, Int), val evento: String, val modo: Boolean, val score: (Int, Int), val tam: Int) extends Frame {

     /**
      * Declaricones globales del frame.
      */
     this.title          = "Damas BOM for Scala" //titulo de la ventana
     this.resizable      = false;
     this.background     = Color.DARK_GRAY;
     
     /**
      * Declaracion de elementos del frame.
      */
     private var tab     = new Tuple8(tablero, turno, nivel, dificultad, fichas, evento, modo, score);
     private val turne   = new Label(if (turno == 0) "■" else "●", null, Alignment.Center)
     private val nFichJ1 = new Label(tab._5._1.toString(), null, Alignment.Center);
     private val nFichJ2 = new Label(tab._5._2.toString(), null, Alignment.Center);
     private val puntos  = new Label((if (turno == 0) tab._8._1 else tab._8._2).toString(), null, Alignment.Center) 
     private val dif     = new Label((dificultad + 1).toString(), null, Alignment.Center);
     private val level   = new Label((nivel + 1).toString(), null, Alignment.Center);
     private val event   = new TextArea(null, 20, 25) {
          editable   = false;
          opaque     = false;
          lineWrap   = true;
          background = Color.darkGray;
          foreground = Color.BLUE;
          border = Swing.EmptyBorder(10, 10, 10, 10);
     };
         
     /**
       * configuración del contenido de la ventana
       */
     contents = new BoxPanel(Orientation.Vertical) {
          contents += new MenuBar {  
               contents += Swing.HStrut(10);
               contents += new Menu("A Menu") {      
                    contents += new MenuItem("An item");   
                    contents += new MenuItem("Hola");
                    contents += new Separator;
                    contents += new CheckMenuItem("Check me");
               }
               contents += new MenuItem(new Action("Guardar y Salir") {
                    def apply {
                         val tab = new Tuple8(tablero, turno, nivel, dificultad, fichas, evento, modo, score)
                         Persistencia.savePlayDamas(tab._1, tab._7, tab._3, tab._2, tab._4, tab._5, tab._8);
                         close
                         GUIDamas.open()
                    }
               });
               pack;
               visible = true;
               componentOrientation = ComponentOrientation.LEFT_TO_RIGHT;
          }
          contents += new BoxPanel(Orientation.Horizontal) {
               contents += new BoxPanel(Orientation.Vertical) {
                    contents += new DibujarTablero(tab._1, tam) {
                         contents += Swing.VStrut(5);
                         reactions += {
                              case  DibujarTableroEvento(x, y, dir) => {
                                   if (dir != -1) {
                                        val tab_aux = runSetMov(x, y, dir, modo);
                                        if (tab_aux._1 != Nil) {
                                             turne.text   = if (tab_aux._2 == 0) "■" else "●";
                                             nFichJ1.text = (tab_aux._5._1.toString());
                                             nFichJ2.text = (tab_aux._5._2.toString());
                                             puntos.text  = (if (tab_aux._2 == 0) tab_aux._8._1 else tab_aux._8._2).toString();
                                             this.updateTablero(tab_aux._1);
                                             tab = tab_aux;
                                        } 
                                        event.text = tab_aux._6; 
                                   }
                              }
                         }
                    };
                    border = Swing.EmptyBorder(0, 0, 0, 10);
                    preferredSize = tam match { // Tamaño del Canbas.
                         case 8  => new Dimension(425, 425);
                         case 16 => new Dimension(620, 620);
                         case 32 => new Dimension(1020, 1020);
                    }
               };
               contents += new BoxPanel(Orientation.Vertical) {
                    preferredSize = tam match { // Tamaño del Canbas.
                         case 8  => new Dimension(350, 420);
                         case 16 => new Dimension(350, 620);
                         case 32 => new Dimension(350, 1020);
                    }
                    contents += new BoxPanel(Orientation.NoOrientation) {
                         border = Swing.CompoundBorder(Swing.TitledBorder(Swing.EtchedBorder, "Informacion de partida: "), Swing.EmptyBorder(5,5,5,10))
                         contents += Swing.VStrut(10);
                         contents += new GridPanel(6, 2) {
                              contents += new Label(" - Turno de: ", null, Alignment.Left)
                              contents += turne
                              contents += new Label(" - Nº Fichas Jugador 1: ", null, Alignment.Left);
                              contents += nFichJ1;
                              contents += new Label(" - Nº Fichas Jugador 2: ", null, Alignment.Left);
                              contents += nFichJ2;
                              contents += new Label(" - Puntuacion: ", null, Alignment.Left);
                              contents += puntos;    
                              contents += new Label(" - Dificultad: ", null, Alignment.Left);
                              contents += dif; 
                              contents += new Label(" - Nivel: ", null, Alignment.Left);
                              contents += level; 
                         }
                    }
                    contents += new BoxPanel(Orientation.NoOrientation) {
                         border = Swing.CompoundBorder(Swing.TitledBorder(Swing.EtchedBorder, "Eventos: "), Swing.EmptyBorder(5,5,5,10));
                         contents += event;
                    }
               }
               contents += Swing.VStrut(10);
               border = Swing.EmptyBorder(10, 10, 10, 10);    
          }
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
     
     /**
      * Eventos Calcula.
      */
     private def runSetMov(x:Int, y:Int, dir:Int, modo_game:Boolean):(List[Int], Int, Int, Int, (Int, Int), String, Boolean, (Int, Int))  = {
          val tab_new = Tablero.damasPlayBom(tab._1, (y, x, dir), tab._2, (0, 0), modo_game);
          if (!tab_new._3) {
               val dimension = (Math.sqrt(tab_new._4.length).toInt);
               val fichXJug = Tablero.numFichasXjugadorInCurse(tab_new._4, 0, (0, 0));
               if (tab_new._2) {
                    Stats.setVictorias(Stats.getVictorias() + 1)
                    val sms_game = (if (tam == 32) {
                         GUIDamas.open()
                         "ENHORABUENA: HAS COMPLETADO EL JUEGO: ";
                    } else {                                      
                         val dif = dificultad + (if (dificultad < 4) 2 else 0);
                         val tableroNuevo = Tablero.generarTablero((dimension * 2), (dimension * 2), (dimension * 2), dif);
                         val new_nivel = new MostrarTablero(tableroNuevo, turno, (nivel + 1), dificultad * 2, fichXJug, new String, modo, (0, 0), tam * 2)
                         new_nivel.visible = true
                         "HAS COMPLETADO EL NIVEL";
                    });
                    JOptionPane.showMessageDialog(null, sms_game + tab_new._5, "Comprobar Partida", JOptionPane.INFORMATION_MESSAGE);
                    this.clone();
               } else if (tab_new._1) {
                    Stats.setEmpates(Stats.getEmpates() + 1);
                    val dif = dificultad + (if (dificultad < 4) 2 else 0);
                    val opcion = JOptionPane.showMessageDialog(null, "HAS EMPATADO", "Comprobar Partida", JOptionPane.INFORMATION_MESSAGE);
                    val tableroNuevo = Tablero.generarTablero((dimension * 2), (dimension * 2), (dimension * 2), dif);
                    val fichXJugInit = Tablero.numFichasXjugadorInit(Math.sqrt(tableroNuevo.length).toInt);
                    val newVentana = new MostrarTablero(tableroNuevo, turno, (nivel + 1), dificultad, (fichXJugInit, fichXJugInit), new String, modo, (0, 0), tam);
                    newVentana.visible = true; 
               }
               return (tab_new._4, tab._2 + (if (tab._2 == 0) 1 else -1), nivel, dificultad, fichXJug, tab_new._5, modo, (tab_new._6._1, tab_new._6._2));
          } else {
               return (Nil, 0, 0, 0, (0, 0), new String, false, (0, 0));
          }
     }
}