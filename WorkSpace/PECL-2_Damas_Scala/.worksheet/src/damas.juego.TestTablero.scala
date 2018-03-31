package damas.juego

object TestTablero {;import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(97); 

     val tablero1 = Tablero.generarTablero(8, 8, 8, 1);System.out.println("""tablero1  : List[Int] = """ + $show(tablero1 ));$skip(82); 
                                 
     
     Tablero.echoTablero(tablero1, 0, 0);}
                                                  
}
