package in.parser.impls;

import net.sf.jsqlparser.expression.*;

public class ExpressionVisitorAdapterImpl extends ExpressionVisitorAdapter<Object> {

    Object value;

    public Object getValue() {
        return value;
    }

    @Override
    public <QueryLayer> Object visit(StringValue stringValue,QueryLayer context) {
        value = stringValue.getValue();
        return value;
    }

    @Override
    public <QueryLayer> Object visit(LongValue longValue,QueryLayer context) {
        value = longValue.getValue();
        return value;
    }

    @Override
    public <QueryLayer> Object visit(DoubleValue doubleValue,QueryLayer context) {
        value = doubleValue.getValue();
        return value;
    }
}