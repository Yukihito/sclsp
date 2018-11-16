package com.yukihitoho.sclsp.modules.prelude

import com.yukihitoho.sclsp.evaluator.{Module, VariableFactory}

object Prelude extends Module {
  override def variableFactories: Seq[VariableFactory] = Seq(
    Quote,
    If,
    Lambda,
    Define,
    `Set!`,
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
    `Op ==`,
    `Op !=`,
    `Op <`,
    `Op <=`,
    `Op >`,
    `Op >=`,
    Car,
    Cdr,
    Cons,
    Print,
    Exit
  )
}
