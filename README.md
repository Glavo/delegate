# delegate

## Getting Started

If you're using SBT, add the following lines to your build file:

```sbt
resolvers ++=  Seq (
  Resolver.sonatypeRepo("releases"),
  "jitpack" at "https://jitpack.io"
)

addCompilerPlugin("org.scalamacros" % "paradise" % "2.1.0" cross CrossVersion.full)

libraryDependencies += "org.glavo" %% "delegate" % "0.2.0" 
```
## Usage
```scala
import org.glavo.delegate._

class StringProperty {
  var value: String = "" 
}

class C {
  @Delegate(name: String)
  val nameProperty: StringProperty = new StringProperty()
}

val c = new C

assert(c.name == "")

c.name = "Glavo"
assert(c.name == "Glavo")
assert(c.nameProperty.value == "Glavo")
```