package com.yukihitoho.sclsp.parsing

import com.yukihitoho.sclsp.ast
import scala.language.implicitConversions
import scala.util.parsing.input

object Implicits {
  implicit def positionToPosition(position: input.Position): ast.Position = ast.Position(position.line, position.column)
}
