package in.parser.impls;

import in.parser.queryparser.*;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.operators.relational.ExpressionList;
import net.sf.jsqlparser.schema.*;
import net.sf.jsqlparser.statement.select.*;

import net.sf.jsqlparser.expression.operators.relational.ParenthesedExpressionList;

public class InsertVisitorImpl {
    SelectVisitorImpl sv;

    public InsertVisitorImpl(SelectVisitorImpl sv){
        this.sv=sv;
    }

    public <S> QueryLayer visit(ExpressionList<Column> columns, Values values, S context) {
        QueryLayer layer = (QueryLayer) context;

        if (columns != null) {
            ExpressionList<?> val=values.getExpressions();
            System.out.println(columns.get(0));
            for (int i = 0; i< columns.size(); i++) {
                layer.add("Columns", columns.get(i).getColumnName());
                layer.add("Values",val.get(i));
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
