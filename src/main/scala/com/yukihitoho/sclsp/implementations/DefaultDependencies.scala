package com.yukihitoho.sclsp.implementations

import com.yukihitoho.sclsp.evaluator.{EnvironmentFactory, Evaluator, Module, StackTrace}
import com.yukihitoho.sclsp.modules.prelude.Prelude
import com.yukihitoho.sclsp.parsing.Parser

trait DefaultDependencies {
  protected val parser: Parser = Parser
  protected val evaluator: Evaluator = new Evaluator {
    override val stackTrace: StackTrace = new DefaultStackTrace
  }
  protected val modules: Seq[Module] = Seq(Prelude)
  protected val environmentFactory: EnvironmentFactory = DefaultEnvironmentFactory
}
