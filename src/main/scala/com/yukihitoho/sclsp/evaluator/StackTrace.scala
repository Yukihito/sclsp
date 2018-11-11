package com.yukihitoho.sclsp.evaluator

trait StackTrace {
  protected def push(call: Call): Unit
  protected def pop(): Option[Call]
  def toList: List[Call]
  def trace[T](call: Call)(f: => T): T = {
    push(call)
    val result = f
    pop()
    result
  }
}

case class Call(caller: Value, callee: CallableValue)
