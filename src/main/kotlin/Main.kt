var existErrors = false

fun main(args: Array<String>) {
  runPrompt()
}

fun runPrompt() {
  while (true) {
    print("> ")
    readlnOrNull()?.let {
      run(it)
    }
  }
}

fun run(source: String) {
  val scanner = Scanner(source)
  val tokens = scanner.scanTokens()
  val parser = Parser(tokens)
  parser.parse()
}

fun error(line: Int, message: String) {
  report(line, "", message)
}

fun report(line: Int, where: String, message: String) {
  println("[line $line] Error $where: $message")
  existErrors = true
}