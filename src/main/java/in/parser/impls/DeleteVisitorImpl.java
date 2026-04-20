package in.parser.impls;

import in.parser.queryparser.ParamGenerator;
import in.parser.queryparser.QueryLayer;
import in.parser.queryparser.RestrictionConfiguration;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.statement.delete.Delete;
import net.sf.jsqlparser.schema.Table;

public class DeleteVisitorImpl {
    RestrictionConfiguration restrictionConfiguration;
    ParamGenerator paramGenerator;
    ExpressionVisitorImpl exprVisitor;

    public DeleteVisitorImpl(ExpressionVisitorImpl expressionVisitor){
        this.exprVisitor=expressionVisitor;
        this.restrictionConfiguration =exprVisitor.restrictionConfiguration;
        this.paramGenerator=expressionVisitor.paramGenerator;
    }

    public QueryLayer handle(Delete delete, QueryLayer layer) {

        if (layer == null) return null;

        Table table = delete.getTable();
        if (table != null) {
            layer.add("Table", table.getName());
        }

        Expression where = delete.getWhere();
        if (where != null) {
            where.accept(exprVisitor, layer);
        }

        return layer;
    }
}