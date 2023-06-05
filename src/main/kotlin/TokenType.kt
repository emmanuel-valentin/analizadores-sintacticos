enum class TokenType {
  // Identificadores
  IDENTIFIER,

  // Palabaras reservadas
  SELECT,
  FROM,
  DISTINCT,

  // Carácteres
  COMMA,
  DOT,
  ASTERISK,

  // Final de cadena
  EOF,
  EPSILON,
}