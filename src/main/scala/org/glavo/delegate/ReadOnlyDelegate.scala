package org.glavo.delegate

import scala.annotation.StaticAnnotation
import scala.language.experimental.macros
import scala.reflect.macros.whitebox

class ReadOnlyDelegate extends StaticAnnotation {
  def macroTransform(annottees: Any*): Any = macro ReadOnlyDelegate.impl
}

object ReadOnlyDelegate {
  def impl(c: whitebox.Context)(annottees: c.Expr[Any]*): c.Expr[Any] = {
    import c.universe._

    val ans = c.prefix.tree match {
      case q"new $_(${Ident(name)}: $tpe)" =>
        annottees.head.tree match {
          case q"$mods val $delegateName: $delegateTpe = $initExpr" =>
            q"""
               $mods val $delegateName: $delegateTpe = $initExpr
               def ${name.toTermName}: $tpe = $delegateName.value
             """
          case q"$mods var $delegateName: $delegateTpe = $initExpr" =>
            q"""
               $mods var $delegateName: $delegateTpe = $initExpr
               def ${name.toTermName}: $tpe = $delegateName.value
             """
          case _ =>
            c.abort(c.enclosingPosition, "Annotation is only supported on define properties")
        }
      case _ =>
        c.abort(c.enclosingPosition, "Incorrect annotation arg!")
    }

    c.Expr(ans)
  }
}
