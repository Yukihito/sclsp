package com.yukihitoho.sclsp.parsing

import com.yukihitoho.sclsp.ast.Position

trait ParsingError

object ParsingError {
  case class InvalidSyntax(msg: String, position: Position) extends ParsingError
}
