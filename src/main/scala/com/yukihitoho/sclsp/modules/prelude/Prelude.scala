package com.yukihitoho.sclsp.modules.prelude

import com.yukihitoho.sclsp.evaluator.{Module, VariableFactory}

object Prelude extends Module {
  override def variableFactories: Seq[VariableFactory] = Seq(
    Quote,
    If,
    Lambda,
    Define,
    Begin,
    Addition,
    Subtraction,
    Multiplication,
    Division,
    And,
    Or,
    Not,
    `Eq?`,
    `Atom?`,
    Car,
    Cdr,
    Cons,
    Print,
    Exit
  )
}
