package com.yukihitoho.sclsp.interpreter

import com.yukihitoho.sclsp.evaluator.{NilValue, NumberValue, PairValue, StringValue}
import org.scalatest._

class LambdaOperatorSpec extends FlatSpec with Matchers {
  // scalastyle:off
  "The lambda operator" should "construct compound procedure" in new WithInterpreter {
    interpreter.interpret("((lambda (x y) (- x y)) 1 2)") should be (Right(NumberValue(-1)))
  }

  it should "make a lexical closure" in new WithInterpreter {
    val src: String = """
      |(begin
      |  (define x 1)
      |  (define y 2)
      |  (define z 4)
      |  (define f (lambda (x) (begin
      |      (define y z)
      |      (cons x (cons y #nil)))))
      |  (cons x (cons y (f 3))))
    """.stripMargin
    interpreter.interpret(src).right.map(_.asInstanceOf[PairValue].toList.get) should be (
      Right(List(NumberValue(1), NumberValue(2), NumberValue(3), NumberValue(4))))
  }

  it should "make a nested lexical closure" in new WithInterpreter {
    val src: String =
      """
        |(begin
        | (define ChatApp (lambda ()
        |   (begin
        |     (define messages ())
        |     (lambda (command comment)
        |       (if (eq? command "chat")
        |         (set! messages (cons comment messages))
        |         messages)))))
        |  (define app (ChatApp))
        |  (app "chat" "hello!")
        |  (app "show" #nil)
        |)
      """.stripMargin
    interpreter.interpret(src) should be (Right(PairValue(StringValue("hello!"), NilValue, None)))
  }

  it should "be able to construct recursive function" in new WithInterpreter {
    val src: String =
      """
        |(begin
        |  (define factorial
        |    (lambda (n)
        |      (if (eq? n 1)
        |        1
        |        (* n (factorial (- n 1))))))
        |  (factorial 5))
      """.stripMargin
    interpreter.interpret(src) should be (Right(NumberValue(120)))
  }

  it should "be able to construct zero arity procedure" in new WithInterpreter {
    val src: String =
      """
        |(begin
        |  (define f (lambda () 1))
        |  (f))
      """.stripMargin

    interpreter.interpret(src) should be (Right(NumberValue(1)))
  }
  // scalastyle:on
}
