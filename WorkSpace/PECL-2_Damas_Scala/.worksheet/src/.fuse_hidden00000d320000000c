package damas.juego

object TestTablero {;import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(97); 

     val tablero1 = Tablero.generarTablero(8, 8, 8, 0);System.out.println("""tablero1  : List[Int] = """ + $show(tablero1 ));$skip(703); 
     def insertMovimiento(tablero: List[Int], actual:Int, victima:Int, value:Int, cont:Int): List[Int] = {
          if (cont < tablero.length) {
               val value_aux = (if (cont == actual) 10 else if (cont == victima) value else tablero(cont));
               if ((actual, victima) != (0, 0)) {
                    val (act, vic) = ((if (cont == actual) 0 else actual), (if (cont == victima) 0 else victima));
                    value_aux :: insertMovimiento(tablero, act, vic, value, (cont + 1));
               } else {
                    value_aux :: insertMovimiento(tablero, actual, victima, value, (cont + 1));
               }
          } else {
               Nil;
          }
     };System.out.println("""insertMovimiento: (tablero: List[Int], actual: Int, victima: Int, value: Int, cont: Int)List[Int]""");$skip(112); val res$0 = 

                                                  
     Tablero.numFichasXjugadorInCurse(tablero1, 0, (0, 0));;System.out.println("""res0: (Int, Int) = """ + $show(res$0))}

}
