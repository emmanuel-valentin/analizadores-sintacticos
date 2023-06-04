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
