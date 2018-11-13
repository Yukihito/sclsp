package com.yukihitoho.sclsp.interpreter

import com.yukihitoho.sclsp.evaluator.Value
import com.yukihitoho.sclsp.implementations.DefaultDependencies

class SampleInterpreter extends Interpreter with DefaultDependencies {
  def interpret(src: String): Either[InterpretationError, Value] = interpret(src, "example.scm")
}

trait WithInterpreter {
  val interpreter = new SampleInterpreter
}
