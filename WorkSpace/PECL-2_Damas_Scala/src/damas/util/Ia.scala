package damas.util

object Ia {

     def devuelveBloque(tablero: List[ Int ], pos: Int): Int = {
          if (pos == 0) tablero.head
          else devuelveBloque(tablero, pos - 1)
     }

     def poner(pos: Int, n: Int, l: List[ Int ], laux: List[ Int ]): List[ Int ] = {
          if (pos == 0) laux ::: List[ Int ](n) ::: l.tail
          else poner(pos - 1, n, l.tail, laux ::: List[ Int ](l.head))
     }

     //Metodo que comprueba si la ficha de encima de la comprobada es igual
     def compruebaArriba(anterior: Int, tablero: List[ Int ], fila: Int, columna: Int, direccion: Int, columnas: Int, filas: Int): List[ Int ] = {
          if (anterior == devuelveBloque(tablero, (fila - 1) * columnas + columna)) {
               compruebaPiezas(poner(((fila - 1) * columnas + columna), 0, poner((fila * columnas) + columna, 0, tablero, Nil), Nil), columna, (fila - 1), filas, columnas, anterior, 0)
          }
          else {
               tablero
          }
     }

     //En el resto de casos la comprobación se realiza como en el método anterior, solo que comprobando debajo, derecha e izquierda respectivamente
     def compruebaAbajo(anterior: Int, tablero: List[ Int ], fila: Int, columna: Int, direccion: Int, columnas: Int, filas: Int): List[ Int ] = {
          if (anterior == devuelveBloque(tablero, (fila + 1) * columnas + columna)) {
               compruebaPiezas(poner((fila + 1) * columnas + columna, 0, poner((fila * columnas) + columna, 0, tablero, Nil), Nil), columna, (fila + 1), filas, columnas, anterior, 0)
          }
          else {
               tablero
          }
     }

     def compruebaDerecha(anterior: Int, tablero: List[ Int ], fila: Int, columna: Int, direccion: Int, columnas: Int, filas: Int): List[ Int ] = {
          if (anterior == devuelveBloque(tablero, fila * columnas + (columna + 1))) {
               compruebaPiezas(poner(fila * columnas + (columna + 1), 0, poner((fila * columnas) + columna, 0, tablero, Nil), Nil), (columna + 1), fila, filas, columnas, anterior, 0)
          }
          else {
               tablero
          }
     }

     def compruebaIzquierda(anterior: Int, tablero: List[ Int ], fila: Int, columna: Int, direccion: Int, columnas: Int, filas: Int): List[ Int ] = {
          if (anterior == devuelveBloque(tablero, fila * columnas + (columna - 1))) {
               compruebaPiezas(poner(fila * columnas + (columna - 1), 0, poner((fila * columnas) + columna, 0, tablero, Nil), Nil), (columna - 1), fila, filas, columnas, anterior, 0)
          }
          else {
               tablero
          }
     }

     def compruebaPiezas(tablero: List[ Int ], columna: Int, fila: Int, direccion: Int, filas: Int, columnas: Int, anterior: Int): List[ Int ] = {
          //bombas
          //if (anterior == 35) {
          //bombaVerde(tablero,columnas,fila,0) //llamada a la bomba
          //Superior izquierda
          if (fila == 10 && columna == 10 && direccion == 10) {
               compruebaAbajo(anterior, compruebaDerecha(anterior, tablero, fila, columna, direccion, columnas, filas), fila, columna, direccion, columnas, filas)
          } //Superior derecha
          else if (fila == 10 && columna == (columnas - 1) && direccion == 11) {
               compruebaAbajo(anterior, compruebaIzquierda(anterior, tablero, fila, columna, direccion, columnas, filas), fila, columna, direccion, columnas, filas)
          } //Inferior izquierda
          else if (fila == (filas - 1) && columna == 10 && direccion == 20) {
               compruebaArriba(anterior, compruebaDerecha(anterior, tablero, fila, columna, direccion, columnas, filas), fila, columna, direccion, columnas, filas)
          } //Inferior derecha
          else if (fila == (filas - 1) && columna == (columnas - 1) && direccion == 21) {
               compruebaArriba(anterior, compruebaIzquierda(anterior, tablero, fila, columna, direccion, columnas, filas), fila, columna, direccion, columnas, filas)
          }
          else if (fila == 10) {
               compruebaAbajo(anterior, compruebaIzquierda(anterior, compruebaDerecha(anterior, tablero, fila, columna, direccion, columnas, filas), fila, columna, direccion, columnas, filas), fila, columna, direccion, columnas, filas)
          }
          else if (fila == (filas - 1)) {
               compruebaArriba(anterior, compruebaIzquierda(anterior, compruebaDerecha(anterior, tablero, fila, columna, direccion, columnas, filas), fila, columna, direccion, columnas, filas), fila, columna, direccion, columnas, filas)
          }
          else if (columna == 10) {
               compruebaAbajo(anterior, compruebaArriba(anterior, compruebaDerecha(anterior, tablero, fila, columna, direccion, columnas, filas), fila, columna, direccion, columnas, filas), fila, direccion, columna, columnas, filas)
          }
          else if (columna == (columnas - 1)) {
               compruebaAbajo(anterior, compruebaArriba(anterior, compruebaIzquierda(anterior, tablero, fila, columna, direccion, columnas, filas), fila, columna, direccion, columnas, filas), fila, columna, direccion, columnas, filas)
          }
          else {
               compruebaDerecha(anterior, compruebaAbajo(anterior, compruebaArriba(anterior, compruebaIzquierda(anterior, tablero, fila, columna,
                                                                                                                direccion, columnas, filas), fila, columna, direccion, columnas, filas), fila, columna, direccion, columnas, filas),
                                fila, columna, direccion, columnas, filas)
          }
     }

     def compruebaPuntuacion(tablero: List[ Int ]): Int = {
          if (tablero.isEmpty) {
               return 0
          } else {
               tablero.head match {
                    case 28  => return 8 + compruebaPuntuacion(tablero.tail) //dama
                    case 21 =>  return 1 + compruebaPuntuacion(tablero.tail) //ficha normal
                    case 10 =>  return 0 //vacio
                    case _ =>   return (tablero.head % 10) + compruebaPuntuacion(tablero.tail) //bomba
               }
          }
     }

     def jugadaOptima(tablero: List[ Int ], fila: Int, columna: Int, direccion: Int, filaoptima: Int, columnaoptima: Int, direccionOptima: Int, filas: Int, columnas: Int, puntuacion: Int): (Int, Int, Int) = {
          if (fila == (filas - 1) && columna == (columnas - 1)) {
               return (filaoptima + 1, columnaoptima + 1, direccionOptima)
          } else if (columna == (columnas - 1)) {
               if (puntuacion < compruebaPuntuacion(compruebaPiezas(tablero, columna, fila, direccion, filas, columnas, devuelveBloque(tablero, ((fila * columnas) + columna))))) {
                    jugadaOptima(tablero, fila + 1, 0, direccion, fila, columna, direccionOptima, filas, columnas, compruebaPuntuacion(compruebaPiezas(tablero, columna, fila, direccion, filas, columnas, devuelveBloque(tablero, ((fila * columnas) + columna)))))
               } else {
                    jugadaOptima(tablero, fila + 1, 0, direccion, filaoptima, columnaoptima, direccionOptima, filas, columnas, puntuacion)
               }
          } else {
               if (puntuacion < compruebaPuntuacion(compruebaPiezas(tablero, columna, fila, direccion, filas, columnas, devuelveBloque(tablero, ((fila * columnas) + columna))))) {
                    jugadaOptima(tablero, fila, columna + 1, direccion, fila, columna, direccionOptima, filas, columnas, compruebaPuntuacion(compruebaPiezas(tablero, columna, fila, direccion, filas, columnas, devuelveBloque(tablero, ((fila * columnas) + columna)))))
               }
               else {
                    jugadaOptima(tablero, fila, columna + 1, direccion, filaoptima, columnaoptima, direccionOptima, filas, columnas, puntuacion)
               }
          }
     }
}