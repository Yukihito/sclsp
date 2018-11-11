package com.yukihitoho.sclsp.parsing

import com.yukihitoho.sclsp.ast.{
  Node => ASTNode,
  NodePair => ASTNodePair,
  NumberLiteral => ASTNumberLiteral,
  StringLiteral => ASTStringLiteral,
  Symbol => ASTSymbol
}
import scala.util.parsing.input.Positional
import Implicits._

trait Node extends Positional {
  def toAST: ASTNode
}

case class NodeList(children: List[Node]) extends Node {
  override def toAST: ASTNode =
    children.foldRight[ASTNode](ASTSymbol("#nil", pos)) {(car, cdr) =>
      ASTNodePair(car.toAST, cdr, pos)
    }
}

case class NodePair(car: Node, cdr: Node) extends Node {
  override def toAST: ASTNode = ASTNodePair(car.toAST, cdr.toAST, pos)
}

case class Symbol(value: String) extends Node {
  override def toAST: ASTNode = ASTSymbol(value, pos)
}

case class StringLiteral(value: String) extends Node {
  override def toAST: ASTNode = ASTStringLiteral(value, pos)
}

case class NumberLiteral(value: Double) extends Node {
  override def toAST: ASTNode = ASTNumberLiteral(value, pos)
}
