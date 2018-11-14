package com.yukihitoho.sclsp.ast

trait Node {
  def position: Position
}

case class NodePair(car: Node, cdr: Node, position: Position) extends Node

case class Symbol(value: String, position: Position) extends Node

case class StringLiteral(value: String, position: Position) extends Node

case class NumberLiteral(value: Double, position: Position) extends Node

case class BooleanLiteral(value: Boolean, position: Position) extends Node

case class NilLiteral(position: Position) extends Node

case class Position(line: Int, column: Int, fileName: String)
