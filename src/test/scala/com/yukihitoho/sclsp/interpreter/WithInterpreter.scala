package com.yukihitoho.sclsp.interpreter

import com.yukihitoho.sclsp.implementations.DefaultDependencies

class SampleInterpreter extends Interpreter with DefaultDependencies

trait WithInterpreter {
  val interpreter = new SampleInterpreter
}
