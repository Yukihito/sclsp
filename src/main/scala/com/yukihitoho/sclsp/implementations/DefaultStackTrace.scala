package com.yukihitoho.sclsp.implementations

import com.yukihitoho.sclsp.evaluator.{Call, StackTrace}

class DefaultStackTrace extends StackTrace {
  private var stack: List[Call] = List()

  override protected def push(call: Call): Unit = {
    stack = call :: stack
  }

  override protected def pop(): Option[Call] = {
    val head = stack.headOption
    stack = stack.tail
    head
  }

  override def toList: List[Call] = stack
}

