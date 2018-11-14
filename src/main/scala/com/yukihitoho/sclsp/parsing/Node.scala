package com.yukihitoho.sclsp.parsing

import com.yukihitoho.sclsp.ast.{
  Position,
  Node => ASTNode,
  NodePair => ASTNodePair,
  NumberLiteral => ASTNumberLiteral,
  StringLiteral => ASTStringLiteral,
  Symbol => ASTSymbol
}
import scala.util.parsing.input.Positional

trait Node extends Positional {
  def toAST(fileName: String): ASTNode
}

case class NodeList(children: List[Node]) extends Node {
  override def toAST(fileName: String): ASTNode =
    children.foldRight[ASTNode](ASTSymbol("#nil", Position(pos.line, pos.column, fileName))) {(car, cdr) =>
      ASTNodePair(car.toAST(fileName), cdr, Position(pos.line, pos.column, fileName))
    }
}

case class NodePair(car: Node, cdr: Node) extends Node {
  override def toAST(fileName: String): ASTNode = ASTNodePair(car.toAST(fileName), cdr.toAST(fileName), Position(pos.line, pos.column, fileName))
}

case class Symbol(value: String) extends Node {
  override def toAST(fileName: String): ASTNode = ASTSymbol(value, Position(pos.line, pos.column, fileName))
}

case class StringLiteral(value: String) extends Node {
  override def toAST(fileName: String): ASTNode = ASTStringLiteral(value, Position(pos.line, pos.column, fileName))
}

case class NumberLiteral(value: Double) extends Node {
  override def toAST(fileName: String): ASTNode = ASTNumberLiteral(value, Position(pos.line, pos.column, fileName))
}
