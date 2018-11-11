package com.yukihitoho.sclsp.evaluator

trait EnvironmentFactory {
  def create(base: Option[Environment]): Environment
}

trait Environment {
  protected def variables: VariablesRepository

  protected def environmentFactory: EnvironmentFactory

  protected def base: Option[Environment]

  def extend(variables: Seq[Variable]): Environment = {
    val newEnvironment = environmentFactory.create(Some(this))
    variables.foreach(newEnvironment.store)
    newEnvironment
  }

  def find(symbol: SymbolValue): Option[Variable] =
    variables.find(symbol).fold {
      for {
        base <- base
        variable <- base.find(symbol)
      } yield variable
    }(Some(_))

  def store(variable: Variable): Environment = {
    variables.store(variable)
    this
  }

  def store(modules: Seq[Module]): Environment = {
    for {
      module <- modules
      variable <- module.variableFactories.map(_.create())
    } yield store(variable)
    this
  }
}
