package damas.juego

object TestTablero {;import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(100); 

     val tablero1 = Tablero.generarTablero(32, 32, 32, 1);System.out.println("""tablero1  : List[Int] = """ + $show(tablero1 ));$skip(89); 
                                 
     
     print(Tablero.echoTablero(tablero1, 0, 0));}
}
