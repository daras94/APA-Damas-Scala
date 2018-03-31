package damas.juego

object TestTablero {;import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(97); 

     val tablero1 = Tablero.generarTablero(8, 8, 8, 1);System.out.println("""tablero1  : List[Int] = """ + $show(tablero1 ));$skip(74); val res$0 = 

                                                  
     tablero1.length;System.out.println("""res0: Int = """ + $show(res$0));$skip(52); ;
     
     Tablero.imprimirTablero(tablero1, 0, 0);}
}
