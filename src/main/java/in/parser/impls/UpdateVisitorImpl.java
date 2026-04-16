package in.parser.impls;

import in.parser.queryparser.QueryLayer;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.statement.update.Update;
import net.sf.jsqlparser.statement.update.UpdateSet;

public class UpdateVisitorImpl {

    ExpressionVisitorImpl exprVisitor = new ExpressionVisitorImpl();

    public QueryLayer handle(Update update, QueryLayer layer) {

        if (layer == null){
            return null;
        }

        if (update.getTable() != null){
            layer.add("Table", update.getTable().getName());
        }

        if (update.getUpdateSets() != null){
            for (UpdateSet set : update.getUpdateSets()) {

                if (set.getColumns() != null){
                    for (Column col : set.getColumns()){
                        layer.add("Update_Columns", col.getColumnName());
                    }
                }

                if (set.getValues() != null) {
                    for (Expression expr : set.getValues()) {
                        layer.add("Update_Values", expr.toString());
                        expr.accept(exprVisitor, layer);
                    }
                }
            }
        }

        if (update.getWhere() != null) {
            layer.add("Where", update.getWhere().toString());
            update.getWhere().accept(exprVisitor, layer);
        }

        return layer;
    }
}