package org.karatachi.expression.function;

import java.util.Collections;
import java.util.List;

import org.karatachi.expression.AbstractFunction;
import org.karatachi.expression.IEnvironment;
import org.karatachi.expression.ast.Expression;
import org.karatachi.expression.ast.Literal;

public class IfNaN extends AbstractFunction {
    public static final String FUNCTION_NAME = "ifnan";

    public IfNaN() {
        super(FUNCTION_NAME);
    }

    @Override
    public double evaluate(IEnvironment env, List<Double> args) {
        for (Double arg : args) {
            if (!Double.isNaN(arg)) {
                return arg;
            }
        }
        return Double.NaN;
    }

    @Override
    public List<String> getInternallyUsedVariables() {
        return Collections.emptyList();
    }

    @Override
    public String represent(IEnvironment env, List<String> args,
            List<Double> values) {
        for (int i = 0; i < values.size(); ++i) {
            if (!Double.isNaN(values.get(i))) {
                return "(" + args.get(i) + ")";
            }
        }
        return "NaN";
    }

    @Override
    public Expression extract(IEnvironment env, List<Expression> args,
            List<Double> values) {
        for (int i = 0; i < values.size(); ++i) {
            if (!Double.isNaN(values.get(i))) {
                return args.get(i);
            }
        }
        return Literal.NaN;
    }
}
