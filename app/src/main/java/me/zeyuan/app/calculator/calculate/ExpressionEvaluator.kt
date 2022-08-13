package me.zeyuan.app.calculator.calculate

import org.javia.arity.Symbols
import org.javia.arity.SyntaxException
import org.javia.arity.Util
import kotlin.math.max

private const val MAX_DIGITS = 12

/**
 * A [Double] has at least 17 significant digits, we show the first [.MAX_DIGITS]
 * and use the remaining digits as guard digits to hide floating point precision errors.
 */
private val ROUNDING_DIGITS = max(17 - MAX_DIGITS, 0)

class ExpressionEvaluator {
    private val symbols = Symbols()

    fun evaluate(expression: String): String {
        var expr = expression
        // remove any trailing operators
        while (expr.isNotEmpty() && "+-×÷".indexOf(expr[expr.length - 1]) != -1) {
            expr = expr.substring(0, expr.length - 1)
        }

        if (expr.isEmpty() || expr.toDoubleOrNull() != null) {
            return ""
        }

        try {
            val result = symbols.eval(expr)
            if (result.isNaN()) return "不是数字"
            // The arity library uses floating point arithmetic when evaluating the expression leading to precision errors in the result.
            // The method doubleToString hides these errors; So rounding the result by dropping N digits of precision.
            return Util.doubleToString(result, MAX_DIGITS, ROUNDING_DIGITS)
        } catch (e: SyntaxException) {
            return "错误"
        }
    }
}