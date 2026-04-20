package in.parser.impls;

import in.parser.queryparser.*;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.operators.relational.ExpressionList;
import net.sf.jsqlparser.schema.*;
import net.sf.jsqlparser.statement.select.*;


public class InsertVisitorImpl {
    SelectFromVisitorImpl sv;
    RestrictTablesColumns restrictTablesColumns;

    public InsertVisitorImpl(SelectFromVisitorImpl sv,RestrictTablesColumns restrictTablesColumns){
        this.sv=sv;
        this.restrictTablesColumns=restrictTablesColumns;
    }

    public <S> QueryLayer visit(ExpressionList<Column> columns, Values values, S context) {
        QueryLayer layer = (QueryLayer) context;

        if (columns != null) {
            int j=columns.size();
            ExpressionList<?> val=values.getExpressions();
            for (Column column : columns) {
                if (!restrictTablesColumns.getColumns().stream().anyMatch(s -> s.getColumnName().equalsIgnoreCase(column.getColumnName()))) {
                    String col = column.getColumnName();
                    layer.add("Columns", col);
                }
                else{
                    layer.add("RestrictColumns",column.getColumnName());
                }
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
        if (!restrictTablesColumns.getTables().stream().anyMatch(s -> s.equalsIgnoreCase(tableName.getName()))) {
            layer.add("Tables", tableName.getName());
            if (tableName.getAlias() != null) {
                layer.add("Aliases", tableName.getAlias().getName());
            }
        }
        else{
            layer.add("RestrictTables",tableName.getName());
        }        return layer;
    }

    public <S> QueryLayer visit(ExpressionList<Column> column, S context){
        QueryLayer layer=(QueryLayer)context;
        for (Column col : column) {
            if (!restrictTablesColumns.getColumns().stream().anyMatch(s -> s.getColumnName().equalsIgnoreCase(col.getColumnName()))) {
                String columnName = col.getColumnName();
                layer.add("Columns", columnName);
            }
            else{
                layer.add("RestrictColumns",col.getColumnName());
            }
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
        QueryLayer parent = (QueryLayer) context;
        QueryLayer subLayer = new QueryLayer();
        select.accept((SelectVisitor) sv, subLayer);
        parent.subLayers.add(subLayer);
        return parent;
    }
}
