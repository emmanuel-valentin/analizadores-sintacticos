class Token(
  private val type: TokenType? = null,
  private val lexeme: String = "",
  internal val position: Int = 0,
) {

  override fun equals(other: Any?): Boolean {
    if (other !is Token) {
      return false
    }

    return this.type == other.type
  }

  override fun toString(): String {
    return "$type $lexeme"
  }

    override fun hashCode(): Int {
    var result = type?.hashCode() ?: 0
    result = 31 * result + lexeme.hashCode()
    return result
  }
}
