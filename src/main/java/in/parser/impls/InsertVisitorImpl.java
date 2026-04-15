package in.parser.impls;

import in.parser.queryparser.*;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.operators.relational.ExpressionList;
import net.sf.jsqlparser.schema.*;
import net.sf.jsqlparser.statement.select.*;


public class InsertVisitorImpl {
    SelectVisitorImpl sv;

    public InsertVisitorImpl(SelectVisitorImpl sv){
        this.sv=sv;
    }

    public <S> QueryLayer visit(ExpressionList<Column> columns, Values values, S context) {
        QueryLayer layer = (QueryLayer) context;

        if (columns != null) {
            int j=columns.size();
            ExpressionList<?> val=values.getExpressions();
            for (Column column : columns) {
                layer.add("Columns", column.getColumnName());
            }
            if(val.size()==j) {
                for (int i = 0; i < val.size(); i++) {
                    layer.add("Values", columns.get(i).getColumnName() + "." + val.get(i));
                }
            }
            else {
                for (Expression expression : val) {
                    String[] value = ("" + expression).replaceAll("[()]", "").split(", ");
                    for (int k = 0; k < value.length; k++) {
                        layer.add("Values", columns.get(k).getColumnName() + "." + value[k]);
                    }
                }
            }
        }
        return layer;
    }

    public <S> QueryLayer visit(Table tableName, S context){
        QueryLayer layer=(QueryLayer)context;
        layer.add("Tables",tableName.getName());
        return layer;
    }

    public <S> QueryLayer visit(ExpressionList<Column> column, S context){
        QueryLayer layer=(QueryLayer)context;
        for (Column col : column) {
            String columnName = col.getColumnName();
            layer.add("Columns", columnName);
        }
        return layer;
    }

    public <S> QueryLayer visit(Values value, S context){
        QueryLayer layer=(QueryLayer)context;
        for(Expression exp:value.getExpressions()) {
            layer.add("Values",exp);
        }
        return layer;
    }

    public <S> QueryLayer visit(Select select, S context){
        select.accept(sv, context);
        return (QueryLayer) context;
    }

}
