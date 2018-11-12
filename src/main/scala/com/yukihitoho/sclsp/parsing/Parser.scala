package com.yukihitoho.sclsp.parsing

import com.yukihitoho.sclsp.ast.Position

import scala.util.parsing.combinator._

trait Parser {
  import ParsingError._
  class Parsers(fileName: String) extends JavaTokenParsers {
    private def specialChars = """\+\*\-\/\#\$\!\%\&\=\~\@\?\<\>\:\;"""

    private def symbol: Parser[String] = s"""[a-zA-Z_$specialChars][a-zA-Z0-9_$specialChars]*""".r

    private def list: Parser[NodeList] = positioned("(" ~> rep(elem) <~ ")" ^^ (nodes => NodeList(nodes, fileName)))

    private def dotted: Parser[NodePair] = positioned("(" ~> elem ~ "." ~ elem <~ ")" ^^ { case car ~ "." ~ cdr => NodePair(car, cdr, fileName) })

    private def atom: Parser[Node] = positioned(
      stringLiteral ^^ (s => StringLiteral(s.slice(1, s.length - 1), fileName))
        | floatingPointNumber ^^ (v => NumberLiteral(v.toDouble, fileName))
        | symbol ^^ (v => Symbol(v, fileName))
    )

    private def quoted: Parser[NodeList] = positioned("'" ~> elem ^^ (v => NodeList(List(Symbol("quote", fileName), v), fileName)))

    private def elem: Parser[Node] = positioned(
      list
        | dotted
        | atom
        | quoted
    )

    def parseToNode(src: String): Either[InvalidSyntax, Node] = parseAll(elem, src) match {
      case Success(result, _) => Right(result)
      case noSuccess: NoSuccess => {
        val pos = noSuccess.next.pos
        Left(InvalidSyntax(noSuccess.msg, Position(pos.line, pos.column, fileName)))
      }
    }
  }

  def parseToNode(src: String, fileName: String): Either[InvalidSyntax, Node] = new Parsers(fileName).parseToNode(src)
}

object Parser extends Parser
