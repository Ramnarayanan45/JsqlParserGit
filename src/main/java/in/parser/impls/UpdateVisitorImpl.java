package in.parser.impls;

import in.parser.queryparser.ParamGenerator;
import in.parser.queryparser.QueryLayer;
import in.parser.queryparser.RestrictionConfiguration;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.statement.update.*;
import java.util.*;

public class UpdateVisitorImpl {
    RestrictionConfiguration restrictionConfiguration;
    ParamGenerator paramGenerator;
    ExpressionVisitorImpl exprVisitor;

    public UpdateVisitorImpl(ExpressionVisitorImpl expressionVisitor){
        this.exprVisitor=expressionVisitor;
        paramGenerator=expressionVisitor.paramGenerator;
        restrictionConfiguration =expressionVisitor.restrictionConfiguration;
    }

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
                        layer.add("UpdateColumns", col.getColumnName());
                    }
                }
                if (set.getValues() != null) {
                    List<Column> col=set.getColumns();
                    int i=0;
                    for (Expression expr : set.getValues()) {
                        layer.add("UpdateValues", col.get(i++).getColumnName()+"."+expr.toString());
                    }
                }
            }
        }

        if (update.getWhere() != null) {
            update.getWhere().accept(exprVisitor, layer);
        }
        return layer;
    }
}