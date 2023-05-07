class Token(
  private val type: TokenType,
  private val lexeme: String,
  private val position: Int? = 0,
) {

  override fun equals(other: Any?): Boolean {
    if (other !is Token) {
      return false
    }

    return this.type == other.type
  }

  override fun toString(): String {
    return "$type $lexeme $position"
  }
}