package damas.util

/**
 * @author daniel
 */
object Ia {

  def devuelvePieza(tablero: List[Int], pos: Int): Int = {
    if (pos == 0) tablero.head
    else devuelvePieza(tablero, pos - 1)
  }

  def colocarFicha(pos: Int, n: Int, l: List[Int], laux: List[Int]): List[Int] = {
    if (pos == 0) laux ::: List[Int](n) ::: l.tail
    else colocarFicha(pos - 1, n, l.tail, laux ::: List[Int](l.head))
  }

  //Metodo que comprueba si la ficha de encima de la comprobada es igual
  def compruebaFichaArriba(anterior: Int, tablero: List[Int], fila: Int, columna: Int, columnas: Int, filas: Int): List[Int] = {
    if (anterior == devuelvePieza(tablero, (fila - 1) * columnas + columna)) {
      compruebaPiezas(colocarFicha(((fila - 1) * columnas + columna), 0, colocarFicha((fila * columnas) + columna, 0, tablero, Nil), Nil), columna, (fila - 1), filas, columnas, anterior, 0)
    } else {
      tablero
    }
  }

  //En el resto de casos la comprobación se realiza como en el método anterior, solo que comprobando debajo, derecha e izquierda respectivamente
  def compruebaFichaAbajo(anterior: Int, tablero: List[Int], fila: Int, columna: Int, columnas: Int, filas: Int): List[Int] = {
    if (anterior == devuelvePieza(tablero, (fila + 1) * columnas + columna)) {
      compruebaPiezas(colocarFicha((fila + 1) * columnas + columna, 0, colocarFicha((fila * columnas) + columna, 0, tablero, Nil), Nil), columna, (fila + 1), filas, columnas, anterior, 0)
    } else {
      tablero
    }
  }

  def compruebaFichaDerecha(anterior: Int, tablero: List[Int], fila: Int, columna: Int, columnas: Int, filas: Int): List[Int] = {
    if (anterior == devuelvePieza(tablero, fila * columnas + (columna + 1))) {
      compruebaPiezas(colocarFicha(fila * columnas + (columna + 1), 0, colocarFicha((fila * columnas) + columna, 0, tablero, Nil), Nil), (columna + 1), fila, filas, columnas, anterior, 0)
    } else {
      tablero
    }
  }

  def compruebaFichaIzquierda(anterior: Int, tablero: List[Int], fila: Int, columna: Int, columnas: Int, filas: Int): List[Int] = {
    if (anterior == devuelvePieza(tablero, fila * columnas + (columna - 1))) {
      compruebaPiezas(colocarFicha(fila * columnas + (columna - 1), 0, colocarFicha((fila * columnas) + columna, 0, tablero, Nil), Nil), (columna - 1), fila, filas, columnas, anterior, 0)
    } else {
      tablero
    }
  }

  def compruebaPiezas(tablero: List[Int], columna: Int, fila: Int, direccion: Int, filas: Int, columnas: Int, anterior: Int): List[Int] = {
    if (fila == 10 && columna == 10 && direccion == 10) {
      compruebaFichaAbajo(anterior, compruebaFichaDerecha(anterior, tablero, fila, columna, columnas, filas), fila, columna, columnas, filas)
    } 
    else if (fila == 10 && columna == (columnas - 1) && direccion == 11) {
      compruebaFichaAbajo(anterior, compruebaFichaIzquierda(anterior, tablero, fila, columna, columnas, filas), fila, columna, columnas, filas)
    } //Inferior izquierda
    else if (fila == (filas - 1) && columna == 10 && direccion == 20) {
      compruebaFichaArriba(anterior, compruebaFichaDerecha(anterior, tablero, fila, columna, columnas, filas), fila, columna, columnas, filas)
    } //Inferior derecha
    else if (fila == (filas - 1) && columna == (columnas - 1) && direccion == 21) {
      compruebaFichaArriba(anterior, compruebaFichaIzquierda(anterior, tablero, fila, columna, columnas, filas), fila, columna, columnas, filas)
    } else if (fila == 10) {
      compruebaFichaAbajo(anterior, compruebaFichaIzquierda(anterior, compruebaFichaDerecha(anterior, tablero, fila, columna, columnas, filas), fila, columna, columnas, filas), fila, columna, columnas, filas)
    } else if (fila == (filas - 1)) {
      compruebaFichaArriba(anterior, compruebaFichaIzquierda(anterior, compruebaFichaDerecha(anterior, tablero, fila, columna, columnas, filas), fila, columna, columnas, filas), fila, columna, columnas, filas)
    } else if (columna == 10) {
      compruebaFichaAbajo(anterior, compruebaFichaArriba(anterior, compruebaFichaDerecha(anterior, tablero, fila, columna, columnas, filas), fila, columna, columnas, filas), fila, columna, columnas, filas)
    } else if (columna == (columnas - 1)) {
      compruebaFichaAbajo(anterior, compruebaFichaArriba(anterior, compruebaFichaIzquierda(anterior, tablero, fila, columna, columnas, filas), fila, columna, columnas, filas), fila, columna, columnas, filas)
    } else {
      compruebaFichaDerecha(anterior, compruebaFichaAbajo(anterior, compruebaFichaArriba(anterior, compruebaFichaIzquierda(anterior, tablero, fila, columna,
        columnas, filas), fila, columna, columnas, filas), fila, columna, columnas, filas),
        fila, columna, columnas, filas)
    }
  }

  def compruebarPuntuacion(tablero: List[Int]): Int = {

    tablero.head match {
      case 28 => return 8 + compruebarPuntuacion(tablero.tail) //dama
      case 21 => return 1 + compruebarPuntuacion(tablero.tail) //ficha normal
      case 10 => return 0 //vacio
      case _  => return (tablero.head % 10) + compruebarPuntuacion(tablero.tail) //bomba
    }

  }

  def jugadaOptima(tablero: List[Int], fila: Int, columna: Int, direccion: Int, filaoptima: Int, columnaoptima: Int, direccionOptima: Int, filas: Int, columnas: Int, puntuacion: Int): (Int, Int, Int) = {
    if (fila == (filas - 1) && columna == (columnas - 1)) {
      return (filaoptima + 1, columnaoptima + 1, direccionOptima + 1)
    } else if (columna == (columnas - 1)) {
      if (puntuacion < compruebarPuntuacion(compruebaPiezas(tablero, columna, fila, direccion, filas, columnas, devuelvePieza(tablero, ((fila * columnas) + columna))))) {
        jugadaOptima(tablero, fila + 1, 0, direccion, fila, columna, direccionOptima, filas, columnas, compruebarPuntuacion(compruebaPiezas(tablero, columna, fila, direccion, filas, columnas, devuelvePieza(tablero, ((fila * columnas) + columna)))))
      } else {
        jugadaOptima(tablero, fila + 1, 0, direccion, filaoptima, columnaoptima, direccionOptima, filas, columnas, puntuacion)
      }
    } else {
      if (puntuacion < compruebarPuntuacion(compruebaPiezas(tablero, columna, fila, direccion, filas, columnas, devuelvePieza(tablero, ((fila * columnas) + columna))))) {
        jugadaOptima(tablero, fila, columna + 1, direccion, fila, columna, direccionOptima, filas, columnas, compruebarPuntuacion(compruebaPiezas(tablero, columna, fila, direccion, filas, columnas, devuelvePieza(tablero, ((fila * columnas) + columna)))))
      } else {
        jugadaOptima(tablero, fila, columna + 1, direccion, filaoptima, columnaoptima, direccionOptima, filas, columnas, puntuacion)
      }
    }
  }
}