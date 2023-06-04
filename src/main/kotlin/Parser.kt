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