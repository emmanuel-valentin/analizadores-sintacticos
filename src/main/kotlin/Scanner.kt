class Scanner(private val source: String) {

  private val tokens: MutableList<Token> = ArrayList()
  private val keywords: MutableMap<String, TokenType> = HashMap()

  init {
    keywords["select"]   = TokenType.SELECT
    keywords["from"]     = TokenType.FROM
    keywords["distinct"] = TokenType.DISTINCT
  }

  fun scanTokens(): List<Token> {
    var state = 0
    var lexemeStart = 0
    var i = 0
    var character: Char?
    val lexeme = StringBuilder()

    while (i <= source.length) {
      character = if (i < source.length) source[i] else '\u00A0'

      when (state) {
        0 -> when {
          character == '*' -> addToken(TokenType.ASTERISK, character.toString(), i + 1)
          character == ',' -> addToken(TokenType.COMMA, character.toString(), i + 1)
          character == '.' -> addToken(TokenType.DOT, character.toString(), i + 1)
          character.isLetter() -> {
            state = 1; lexeme.append(character); lexemeStart = i
          }
        }

        1 -> {
          if (character.isLetterOrDigit()) {
            lexeme.append(character)
          } else {
            addToken(TokenType.IDENTIFIER, lexeme.toString(), lexemeStart + 1)
            state = 0; lexemeStart = 0; i--
            lexeme.delete(0, lexeme.length)
          }
        }
      }

      i++
    }

    addToken(TokenType.EOF, "", source.length)
    return tokens
  }

  private fun addToken(type: TokenType = TokenType.IDENTIFIER, lexeme: String = "", position: Int = 0) {
    if (type != TokenType.IDENTIFIER) {
      tokens.add(Token(type, lexeme, position))
      return
    }
    tokens.add(
      Token(
        keywords.getOrDefault(lexeme, TokenType.IDENTIFIER),
        lexeme,
        position
      )
    )
  }
}