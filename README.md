# delegate

## Getting Started
//todo
## Usage
```scala
import org.glavo.delegate._

class StringProperty {
  var value: String = "" 
}

class C {
  @delegate[String]("name")  
  val nameProperty: StringProperty = new StringProperty()
}

val c = new C

assert(c.name == "")

c.name = "Glavo"
assert(c.name == "Glavo")
assert(c.nameProperty.value == "Glavo")
```