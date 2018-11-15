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
    variables.foreach(newEnvironment.define)
    newEnvironment
  }

  def find(name: String): Option[Variable] =
    variables.find(name).fold {
      for {
        base <- base
        variable <- base.find(name)
      } yield variable
    }(Some(_))

  def define(variable: Variable): Environment = {
    variables.store(variable)
    this
  }

  def set(variable: Variable): Option[Environment] =
    variables.find(variable.name) match {
      case Some(_) =>
        variables.store(variable)
        Some(this)
      case None =>
        for {
          base <- base
          updatedEnvironment <- base.set(variable)
        } yield updatedEnvironment
    }

  def store(modules: Seq[Module]): Environment = {
    for {
      module <- modules
      variable <- module.variableFactories.map(_.create())
    } yield define(variable)
    this
  }
}
