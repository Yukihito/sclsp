package com.yukihitoho.sclsp.parsing

import scala.util.parsing.combinator._

trait Parser extends JavaTokenParsers {
  import Implicits._
  import ParsingError._
  private def specialChars = """\+\*\-\/\#\$\!\%\&\=\~\@\?\<\>\:\;"""
  private def symbol: Parser[String] = s"""[a-zA-Z_$specialChars][a-zA-Z0-9_$specialChars]*""".r
  private def list: Parser[NodeList] = positioned("(" ~> rep(elem) <~ ")" ^^ NodeList)
  private def dotted: Parser[NodePair] = positioned("(" ~> elem ~ "." ~ elem <~ ")" ^^ { case car ~ "." ~ cdr => NodePair(car, cdr) })
  private def atom: Parser[Node] = positioned(
      stringLiteral ^^ (s => StringLiteral(s.slice(1, s.length - 1)))
    | floatingPointNumber ^^ (v => NumberLiteral(v.toDouble))
    | symbol ^^ (v => Symbol(v))
  )
  private def quoted: Parser[NodeList] = positioned("'" ~> elem ^^ (v => NodeList(List(Symbol("quote"), v))))
  private def elem: Parser[Node] = positioned(
      list
    | dotted
    | atom
    | quoted
  )

  def parseToNode(src: String): Either[InvalidSyntax, Node] = parseAll(elem, src) match {
    case Success(result, _) => Right(result)
    case noSuccess: NoSuccess => Left(InvalidSyntax(noSuccess.msg, noSuccess.next.pos))
  }
}

object Parser extends Parser
