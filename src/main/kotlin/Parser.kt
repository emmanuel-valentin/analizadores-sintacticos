import java.util.*

class Parser(private val tokens: List<Token>) {
  // Símbolos terminales
  private val id = Token(TokenType.IDENTIFIER, "")
  private val select = Token(TokenType.SELECT, "select")
  private val from = Token(TokenType.FROM, "from")
  private val distinct = Token(TokenType.DISTINCT, "distinct")
  private val comma = Token(TokenType.COMMA, ",")
  private val dot = Token(TokenType.DOT, ".")
  private val asterisk = Token(TokenType.ASTERISK, "*")
  private val eof = Token(TokenType.EOF, "")
  private val epsilon = Token(TokenType.EPSILON, "")

    // Simbolos no terminales
  private val Q = NonTerminalSymbol.Q
  private val D = NonTerminalSymbol.D
  private val P = NonTerminalSymbol.P
  private val A = NonTerminalSymbol.A
  private val A1 = NonTerminalSymbol.A1
  private val A2 = NonTerminalSymbol.A2
  private val A3 = NonTerminalSymbol.A3
  private val T = NonTerminalSymbol.T
  private val T1 = NonTerminalSymbol.T1
  private val T2 = NonTerminalSymbol.T2
  private val T3 = NonTerminalSymbol.T3

// Tipo de acciones para la tabla de acción
  private val shift = ActionType.SHIFT
  private val reduce = ActionType.REDUCE
  private val accept = ActionType.ACCEPT

  /**
   * La estructura de la gramática se encuentra de la siguiente manera:
   * Map<Int, Map<NonTerminalSymbol, List<NonTerminalSymbol or Token>>>
   *
   * - La llave del tipo entero dentro del diccionario sirve para identificar las producciones de las reducciones dentro
   * del algoritmo del analizador sintáctico.
   * - La llave dentro del segundo mapa, de tipo NonTerminalSymbol que se encuentra anidado, sirve para identificar el
   * lado izquierdo de la gramática
   * - La lista de valores representa las producciones de la gramática
   */

     private val grammar = mapOf(
    1 to mapOf(Q to listOf(select, D, from, T)),
    2 to mapOf(D to listOf(distinct, P)),
    3 to mapOf(D to listOf(P)),
    4 to mapOf(P to listOf(asterisk)),
    5 to mapOf(P to listOf(A)),
    6 to mapOf(A to listOf(A2, A1)),
    7 to mapOf(A1 to listOf(comma, A)),
    8 to mapOf(A1 to listOf()),
    9 to mapOf(A2 to listOf(id, A3)),
    10 to mapOf(A3 to listOf(dot, id)),
    11 to mapOf(A3 to listOf()),
    12 to mapOf(T to listOf(T2, T1)),
    13 to mapOf(T1 to listOf(comma, T)),
    14 to mapOf(T1 to listOf()),
    15 to mapOf(T2 to listOf(id, T3)),
    16 to mapOf(T3 to listOf(id)),
    17 to mapOf(T3 to listOf())
  )

  // Tablas de análisis sintáctico
  private val actionTable = mapOf(
    0 to mapOf(select to Pair(shift, 2)),
    1 to mapOf(eof to Pair(accept, null)),
    2 to mapOf(
      distinct to Pair(shift, 4),
      asterisk to Pair(shift, 6),
      id to Pair(shift, 9)
    ),
    3 to mapOf(from to Pair(shift, 10)),
    4 to mapOf(
      asterisk to Pair(shift, 6),
      id to Pair(shift, 9),
    ),
    5 to mapOf(from to Pair(reduce, 3)),
    6 to mapOf(from to Pair(reduce, 4)),
    7 to mapOf(from to Pair(reduce, 5)),
    8 to mapOf(
      from to Pair(reduce, 8),
      comma to Pair(shift, 13)
    ),
    9 to mapOf(
      from to Pair(reduce, 11),
      comma to Pair(reduce, 11),
      dot to Pair(shift, 15)
    ),
    10 to mapOf(id to Pair(shift, 22)),
    11 to mapOf(from to Pair(reduce, 2)),
    12 to mapOf(from to Pair(reduce, 6)),
    13 to mapOf(id to Pair(shift, 9)),
    14 to mapOf(
      from to Pair(reduce, 9),
      comma to Pair(reduce, 9)
    ),
    15 to mapOf(id to Pair(shift, 17)),
    16 to mapOf(eof to Pair(reduce, 1)),
    17 to mapOf(
      from to Pair(reduce, 10),
      comma to Pair(reduce, 10),
    ),
    18 to mapOf(
      comma to Pair(shift, 20),
      eof to Pair(reduce, 14)
    ),
    19 to mapOf(eof to Pair(reduce, 12)),
    20 to mapOf(id to Pair(shift, 22)),
    21 to mapOf(from to Pair(reduce, 7)),
    22 to mapOf(
      comma to Pair(reduce, 17),
      id to Pair(shift, 24),
      eof to Pair(reduce, 17)
    ),
    23 to mapOf(
      comma to Pair(reduce, 15),
      eof to Pair(reduce, 15)
    ),
    24 to mapOf(
      comma to Pair(reduce, 16),
      eof to Pair(reduce, 16)
    ),
    25 to mapOf(
      eof to Pair(reduce, 13)
    )
  )

  private val gotoTable = mapOf(
    0 to mapOf(Q to 1),
    2 to mapOf(
      D to 3,
      P to 5,
      A to 7,
      A2 to 8,
    ),
    4 to mapOf(
      P to 11,
      A to 7,
      A2 to 8,
    ),
    8 to mapOf(A1 to 12),
    9 to mapOf(A3 to 14),
    10 to mapOf(
      T to 16,
      T2 to 18,
    ),
    13 to mapOf(
      A to 21,
      A2 to 8,
    ),
    18 to mapOf(T1 to 19),
    20 to mapOf(
      T to 25,
      T2 to 18,
    ),
    22 to mapOf(T3 to 23),
  )
