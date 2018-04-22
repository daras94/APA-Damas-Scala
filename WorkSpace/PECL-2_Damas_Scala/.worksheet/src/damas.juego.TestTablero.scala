package damas.juego

import java.awt.image.BufferedImage;
import java.awt._;
import javax.imageio.ImageIO;

object TestTablero {;import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(184); 

     val tablero1 = Tablero.generarTablero(8, 8, 8, 0);System.out.println("""tablero1  : List[Int] = """ + $show(tablero1 ));$skip(884); 

                                                  
     //Tablero.numFichasXjugadorInCurse(tablero1, 0, (0, 0));
                                                  
     def printtextArt(textART:String, height:Int, width:Int): Unit = {
          val image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
          try {
               image.getGraphics().setFont(new Font("Dialog", Font.PLAIN, 24));
               val graphics2D = image.createGraphics();
               graphics2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
               graphics2D.drawString(textART, 6, 24);
               ImageIO.write(image, "png", new java.io.File("text.png"));
               generateTextArt(image, height, width, 0, 0);
          } catch {
               case ex: java.io.IOException ⇒ ex.getMessage();
          }
     };System.out.println("""printtextArt: (textART: String, height: Int, width: Int)Unit""");$skip(579); 
     
     def generateTextArt(art:BufferedImage, height:Int, width:Int, row:Int, col:Int): Unit = {
          if (row < height) {
               val sb = new StringBuilder();
               if (col < width) {
                    sb.append(if (art.getRGB(col, row) == -16777216) /*" " else if (art.getRGB(col, row) == -1)*/ "■" else "●");
                    generateTextArt(art, height, width, row, (col + 1));
               }
               if (!sb.toString().trim().isEmpty()) println(sb);
               generateTextArt(art, height, width, (row + 1), 0);
          }
     };System.out.println("""generateTextArt: (art: java.awt.image.BufferedImage, height: Int, width: Int, row: Int, col: Int)Unit""");$skip(46); 
    
    
     printtextArt("Hola", 144, 32);}

}
