package org.glavo.delegate

import scala.annotation.{StaticAnnotation, compileTimeOnly}
import scala.language.experimental.macros
import scala.reflect.macros.whitebox

@compileTimeOnly("")
class delegate[T](val name: String) extends StaticAnnotation {
  def macroTransform(annottees: Any*): Any = macro delegate.impl
}

object delegate {
  def impl(c: whitebox.Context)(annottees: c.Expr[Any]*): c.Expr[Any] = {
    import c.universe._
    val q"new $_[$tpe]($name) " = c.prefix.tree
    val s = name.toString().substring(1, name.toString().length - 1)
    val rs = annottees.map(_.tree).toList match {
      case q"$mods val $delegateName: $delegateTpe = $initExpr" :: Nil =>
        q"$mods val $delegateName: $delegateTpe = $initExpr" ::
          q"def ${TermName(s)}: $tpe = $delegateName.value" ::
          q"def ${TermName(s + "_$eq")}(value: $tpe): Unit = this.$delegateName.value = value" ::
          Nil
      case q"$mods var $delegateName: $delegateTpe = $initExpr" :: Nil =>
        q"$mods var $delegateName: $delegateTpe = $initExpr" ::
          q"def ${TermName(s)}: $tpe = $delegateName.value" ::
          q"def ${TermName(s + "_$eq")}(value: $tpe): Unit = this.$delegateName.value = value" ::
          Nil
      case _ => c.abort(c.enclosingPosition, "Annotation is only supported on val define")
    }
    val r = q"..$rs"
    c.Expr[Any](r)
  }
}