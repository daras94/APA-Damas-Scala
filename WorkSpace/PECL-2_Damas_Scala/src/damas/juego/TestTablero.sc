package damas.juego

import java.awt.image.BufferedImage;
import java.awt._;
import javax.imageio.ImageIO;

object TestTablero {

     val tablero1 = Tablero.generarTablero(8, 8, 8, 0)
                                                  //> tablero1  : List[Int] = List(10, 21, 10, 21, 10, 22, 10, 22, 22, 10, 21, 10,
                                                  //|  22, 10, 22, 10, 10, 21, 10, 22, 10, 22, 10, 22, 10, 10, 10, 10, 10, 10, 10,
                                                  //|  10, 10, 10, 10, 10, 10, 10, 10, 10, 33, 10, 33, 10, 33, 10, 32, 10, 10, 33,
                                                  //|  10, 32, 10, 33, 10, 32, 33, 10, 33, 10, 32, 10, 32, 10)

                                                  
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
     }                                            //> printtextArt: (textART: String, height: Int, width: Int)Unit
     
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
     }                                            //> generateTextArt: (art: java.awt.image.BufferedImage, height: Int, width: In
                                                  //| t, row: Int, col: Int)Unit
    
    
     printtextArt("Hola", 144, 32);               //> java.lang.StackOverflowError
                                                  //| 	at java.awt.image.DirectColorModel.getRGB(DirectColorModel.java:455)
                                                  //| 	at java.awt.image.DirectColorModel.getRGB(DirectColorModel.java:721)
                                                  //| 	at java.awt.image.BufferedImage.getRGB(BufferedImage.java:917)
                                                  //| 	at damas.juego.TestTablero$.generateTextArt$1(damas.juego.TestTablero.sc
                                                  //| ala:32)
                                                  //| 	at damas.juego.TestTablero$.generateTextArt$1(damas.juego.TestTablero.sc
                                                  //| ala:33)
                                                  //| 	at damas.juego.TestTablero$.generateTextArt$1(damas.juego.TestTablero.sc
                                                  //| ala:33)
                                                  //| 	at damas.juego.TestTablero$.generateTextArt$1(damas.juego.TestTablero.sc
                                                  //| ala:33)
                                                  //| 	at damas.juego.TestTablero$.generateTextArt$1(damas.juego.TestTablero.sc
                                                  //| ala:33)
                                                  //| 	at damas.juego.TestTablero$.generateTextArt$1(damas.juego.TestTablero.sc
                                                  //| ala:33)
                                                  //| 	at damas.juego.TestTablero$.generateTextArt$1(damas.juego.TestTablero.sc
                                                  //| ala:33)
                                                  //| 	at damas.juego.TestTablero$.generateTextArt$1(damas.juego.TestTablero.sc
                                                  //| ala:33)
                                                  //| 	at da
                                                  //| Output exceeds cutoff limit.

}