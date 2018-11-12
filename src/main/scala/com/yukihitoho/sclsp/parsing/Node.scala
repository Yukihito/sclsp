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
  def toAST: ASTNode
}

case class NodeList(children: List[Node], fileName: String) extends Node {
  override def toAST: ASTNode =
    children.foldRight[ASTNode](ASTSymbol("#nil", Position(pos.line, pos.column, fileName))) {(car, cdr) =>
      ASTNodePair(car.toAST, cdr, Position(pos.line, pos.column, fileName))
    }
}

case class NodePair(car: Node, cdr: Node, fileName: String) extends Node {
  override def toAST: ASTNode = ASTNodePair(car.toAST, cdr.toAST, Position(pos.line, pos.column, fileName))
}

case class Symbol(value: String, fileName: String) extends Node {
  override def toAST: ASTNode = ASTSymbol(value, Position(pos.line, pos.column, fileName))
}

case class StringLiteral(value: String, fileName: String) extends Node {
  override def toAST: ASTNode = ASTStringLiteral(value, Position(pos.line, pos.column, fileName))
}

case class NumberLiteral(value: Double, fileName: String) extends Node {
  override def toAST: ASTNode = ASTNumberLiteral(value, Position(pos.line, pos.column, fileName))
}
