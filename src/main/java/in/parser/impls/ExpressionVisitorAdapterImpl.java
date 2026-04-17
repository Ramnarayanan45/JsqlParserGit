package in.parser.impls;

import net.sf.jsqlparser.expression.*;

public class ExpressionVisitorAdapterImpl extends ExpressionVisitorAdapter<Object> {

    Object value;

    public Object getValue() {
        return value;
    }

    @Override
    public <S> Object visit(StringValue stringValue,S context) {
        value = stringValue.getValue();
        return value;
    }

    @Override
    public <S> Object visit(LongValue longValue,S context) {
        value = longValue.getValue();
        return value;
    }

    @Override
    public <S> Object visit(DoubleValue doubleValue,S context) {
        value = doubleValue.getValue();
        return value;
    }
}