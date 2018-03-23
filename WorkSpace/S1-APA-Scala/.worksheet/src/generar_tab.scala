object generar_tab {;import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(235); 

	/*
	 * Funcion que se encarga de generar el tablero de acuerdo a las dimensiones de juego pasadas.
	 */
  def create_tab(n: Int): List[Int] = n match {
    case 0 => Nil;
    case _ => 0 :: create_tab(n - 1);
  };System.out.println("""create_tab: (n: Int)List[Int]""");$skip(31); 
  
  val tab = create_tab(81);;System.out.println("""tab  : List[Int] = """ + $show(tab ))}
                                                  
  
}
