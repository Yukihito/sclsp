# sclsp
The Lisp interpreter that can be embedded within a scala application.
    
# Features
- Can replace the default parser with your owns.
- Can add your own operators that is implemented by scala on the interpreter.
  - By default, available operators are in below.
  <details><summary>CLICK HERE TO EXPAND</summary>

    - lambda
    - car
    - cdr
    - cons
    - list
    - quote
    - \'
    - define
    - if
    - ==
    - <=
    - <
    - \>
    - \>=
    - \+
    - \-
    - \*
    - /
    - and
    - or
    - not
    - eq?
    - atom?
    - begin
    - exit
    - print
    - set!
    - while

    </details>
- Lexical closure.     
- If interpretation is stopped by an error, the result object provides reason, stack trace, and position in code.  

# Usage
- [Specs](https://github.com/Yukihito/sclsp/tree/master/src/test/scala/com/yukihitoho/sclsp/interpreter)
- [Sample codes](https://github.com/Yukihito/sclsp-samples)
  - This samples contain
    - A simple example of embedding sclsp in a scala application.
    - A sample of REPL implementation.
    - Implementing a JavaScript subset by replace the default sclsp parser with the JavaScript parser.

# Getting started
## Adding dependency in your project
Write this lines in your build.sbt.
```scala
resolvers += "Sclsp Maven Repository" at "http://yukihito.github.io/sclsp"
libraryDependencies += "com.yukihitoho" % "sclsp_2.12" % "0.1"
```

## Use interpreter in your code.
Import the DefaultInterpreter.
```scala
import com.yukihitoho.sclsp.implementations.DefaultInterpreter

```

Prepare a string that contains the source code.
```scala
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
```

Specify the source code and the file name to Interpreter#interpret.

```scala
println(DefaultInterpreter.interpret(src, "a-dummy-file-name.scm"))
```

Run your app, then you will see the output like below.
```
Right(NumberValue(120.0))
``` 

A simple sample code about this section is [here](https://github.com/Yukihito/sclsp-samples/blob/master/src/main/scala/com/yukihitoho/sclspsamples/simpleusage/Main.scala).


If you needs more examples, check [Specs](https://github.com/Yukihito/sclsp/tree/master/src/test/scala/com/yukihitoho/sclsp/interpreter) or [Sample codes](https://github.com/Yukihito/sclsp-samples). 
