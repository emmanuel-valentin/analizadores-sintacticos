import java.util.Stack

class Parser(private val tokens: List<Token>) {

  private val select = Token(TokenType.SELECT, "select")
  private val from = Token(TokenType.FROM, "from")
  private val distinct = Token(TokenType.DISTINCT, "distinct")
  private val asterisk = Token(TokenType.ASTERISK, "*")
  private val comma = Token(TokenType.COMMA, ",")
  private val dot = Token(TokenType.DOT, ".")
  private val identifier = Token(TokenType.IDENTIFIER, "")
  private val eof = Token(TokenType.EOF, "")
  private val epsilon = Token(TokenType.EPSILON, "")

  private val parsingTable: Map<String, Map<Token, MutableList<*>>>

  init {
    parsingTable = mapOf(
      "Q" to mapOf(
        select to mutableListOf(select, "D", from, "T")
      ),
      "D" to mapOf(
        distinct to mutableListOf(distinct, "P"),
        asterisk to mutableListOf("P"),
        identifier to mutableListOf("P")
      ),
      "P" to mapOf(
        asterisk to mutableListOf(asterisk),
        identifier to mutableListOf("A")
      ),
      "A" to mapOf(
        identifier to mutableListOf("A2", "A1")
      ),
      "A1" to mapOf(
        comma to mutableListOf(comma, "A"),
        from to mutableListOf(epsilon)
      ),
      "A2" to mapOf(
        identifier to mutableListOf(identifier, "A3")
      ),
      "A3" to mapOf(
        from to mutableListOf(epsilon),
        comma to mutableListOf(epsilon),
        dot to mutableListOf(dot, identifier),
      ),
      "T" to mapOf(
        identifier to mutableListOf("T2", "T1")
      ),
      "T1" to mapOf(
        comma to mutableListOf(comma, "T"),
        eof to mutableListOf(epsilon)
      ),
      "T2" to mapOf(
        identifier to mutableListOf(identifier, "T3")
      ),
      "T3" to mapOf(
        comma to mutableListOf(epsilon),
        identifier to mutableListOf(identifier),
        eof to mutableListOf(epsilon)
      )
    )
  }


 fun parse() {
    var i = 0
    var lookahead = tokens[i]
    val symbolStack = Stack<Any>()
    symbolStack.push(eof)
    symbolStack.push("Q")

    var topValueStack = symbolStack.peek()

    while (symbolStack.isNotEmpty()) {

      if (lookahead == topValueStack) {
        symbolStack.pop()
        if (++i >= tokens.size) break
        lookahead = tokens[i]
      }
      else if (topValueStack is Token) {
        error(lookahead.position, "Consulta no válida")
        return
      }
      else if (getProductions(topValueStack, lookahead).isNullOrEmpty()) {
        error(lookahead.position, "Consulta no válida")
        return
      }
      else {
        val productions = getProductions(topValueStack, lookahead)?.toMutableList()
        symbolStack.pop()
        while (!productions.isNullOrEmpty()) {
          val lastProduction = productions.last()

          if (lastProduction != epsilon) {
            symbolStack.push(lastProduction)
          }

          productions.remove(lastProduction)
        }
      }

      topValueStack = symbolStack.peek()
    }
    println("Consulta válida")
  }

  private fun getProductions(topValueStack: Any, lookahead: Token)
    = parsingTable[topValueStack]?.entries?.find { (entry) -> lookahead == entry }?.value
}

