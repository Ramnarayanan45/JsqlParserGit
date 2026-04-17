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

    @Override
    public <QueryLayer> Object visit(TimeValue timeValue,QueryLayer context){
        value = timeValue.getValue();
        return value;
    }

    @Override
    public <QueryLayer> Object visit(TimestampValue timestampValue,QueryLayer context){
        value=timestampValue.getValue();
        return value;
    }

    @Override
    public <QueryLayer> Object visit(BooleanValue  booleanValue,QueryLayer context){
        value=booleanValue.getValue();
        return value;
    }

    @Override
    public <QueryLayer> Object visit(DateValue dateValue,QueryLayer context){
        value=dateValue.getValue();
        return value;
    }

    @Override
    public <QueryLayer> Object visit(AllValue allValue,QueryLayer context){
        value=allValue.toString();
        return value;
    }

}