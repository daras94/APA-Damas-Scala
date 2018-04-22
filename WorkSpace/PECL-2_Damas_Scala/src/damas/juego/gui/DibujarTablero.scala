package damas.juego.gui;


// Imports.
import scala.swing._
import scala.swing.event._
import java.awt.{ Color, Graphics2D, BasicStroke, Rectangle};
import java.awt.geom._;
import javax.imageio.ImageIO;
import java.io.IOException;

/**
 * @author david
 */
class DibujarTablero (var tablero:List[Int], val tam:Int) extends Component {
     
     /**
      * Declaracion de Variables Globales.
      */
     private val d = getPreferredSize();
     private var xJ:Int = 0;
     private var yJ:Int = 0;

     listenTo(mouse.clicks)
     reactions += {
          case MouseClicked(_, p, _, _, _) => mouseClick(p.x, p.y);
     }
     
     def squareGeometry(): (Int, Int, Int, Int) = {
          val x0 = (d.width  - (d.height min d.width)) / (tam - 1);
          val y0 = (d.height - (d.height min d.width)) / (tam - 1);
          return ((d.height min d.width), x0, y0, (d.height min d.width) / tam)
     }

     def mouseClick(x: Int, y: Int) {
          val (squareSide, x0, y0, wid) = squareGeometry();
          if (x0 <= x && x < x0 + squareSide && y0 <= y && y < y0 + squareSide) {
               val col = (x - x0) / wid;
               val row = (y - y0) / wid;
               if ((xJ == 0) && (yJ == 0)) {
                    xJ = col; yJ = row;
               } else if ((xJ != col) && (yJ != row)) {
                    val dir = (if ((col > xJ) && (row > yJ)) 21 else if ((col > xJ) && (row < yJ)) 11 else if ((col < xJ) && (row < yJ)) 10 else 20);
                    publish(DibujarTableroEvento(xJ, yJ, dir));
                    this.repaint(); 
                    xJ = 0; yJ = 0;
               } else {
                    xJ = 0; yJ = 0;
                    publish(DibujarTableroEvento(xJ, yJ, -1));
               }
          }          
     }
     
     override def paintComponent(g: Graphics2D) {
          super.paintComponent(g);  
          g.setBackground(Color.BLACK);
          g.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING, java.awt.RenderingHints.VALUE_ANTIALIAS_ON);
          val wid = (d.height min d.width) / tam
          val x0  = (d.width  - (d.height min d.width))  / (tam - 1);
          val y0  = (d.height - ( d.height min d.width)) / (tam - 1);
          this.printTablero(0, 0, x0, y0, wid, g);
     }
     
     /**
      * Pintar Fichas y Tablero Recursivo.
      */
     private def printTablero(x:Int, y:Int, x0:Int, y0:Int, wid:Int, g: Graphics2D): Unit = {
          if (x < tam) {
               if (y < tam) {
                    val background = List(Color.BLACK, Color.DARK_GRAY).apply(if ((x + y) % 2 == 1) 0 else 1);
                    g.setColor(background);
                    g.fillRect((x0 + x * wid), (y0 + y * wid), (d.width / tam), (d.height / tam));
                    if ((tablero(y * tam + x) - (tablero(y * tam + x) % 10)) != 10) {
                         g.setColor(List(background, Color.decode("#8b0000"), Color.decode("#000064"), Color.CYAN, Color.GREEN, Color.YELLOW, Color.MAGENTA, Color.PINK, Color.ORANGE).apply(tablero(y * tam + x) % 10));
                         /*try {
                              val image = ImageIO.read(this.getClass().getResource("/Images/pacman.png"));
                              g.drawImage(image, (x0 + x * wid + 5), (y0 + y * wid + 5), d.width / 10, d.height / 10, null);
                         } catch {
                              case t: IOException => t.printStackTrace() // TODO: handle error
                         }*/
                         val figura = (if ((tablero(y * tam + x) - (tablero(y * tam + x) % 10)) / 10 == 3) {
                              new Rectangle2D.Double((x0 + x * wid + 10), (y0 + y * wid + 10), (wid - 20), (wid - 20));
                         } else {
                              new Ellipse2D.Double((x0 + x * wid + 10), (y0 + y * wid + 10), (wid - 20), (wid - 20));
                         });
                         (tablero(y * tam + x) % 10) match {
                              case 1 | 2 | 8 => g.fill(figura);
                              case _ => Nil;
                         }
                         g.draw(figura);
                         if (((tablero(y * tam + x) % 10) > 2) && (tablero(y * tam + x) % 10 != 8)) {
                              g.draw(new Line2D.Double((x0 + x * wid + 10), (y0 + y * wid + 10), ((x0 + x * wid + 10) + wid - 20), ((y0 + y * wid + 10) + wid - 20)));
                              g.draw(new Line2D.Double((x0 + x * wid + 10), ((y0 + y * wid + 10) + wid - 20), ((x0 + x * wid + 10) + wid - 20), (y0 + y * wid + 10)));
                         }
                    }
               }
               this.printTablero(x + (if (y == tam - 1) 1 else 0), (if (y == tam - 1) 0 else (y + 1)), x0, y0, wid, g)
          }
     }
     
     def getPreferredSize(): Dimension = tam match {
          case 8  => new Dimension(420, 420);
          case 16 => new Dimension(620, 620);
          case 32 => new Dimension(1020, 1020);
     }
     
     def updateTablero(tab:List[Int]): Unit = {
          this.tablero = tab;
     }
     
}

/**
  * creamos un evento para obtener las coordenadas de la jugada a realizar
  */
case class DibujarTableroEvento(x: Int, y: Int, dir:Int) extends Event;
