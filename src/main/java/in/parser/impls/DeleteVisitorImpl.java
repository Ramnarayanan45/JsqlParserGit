package in.parser.impls;

import in.parser.queryparser.ConditionMapping;
import in.parser.queryparser.QueryLayer;
import in.parser.queryparser.RestrictConfig;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.statement.delete.Delete;
import net.sf.jsqlparser.schema.Table;

public class DeleteVisitorImpl {
    RestrictConfig restrictConfig = new RestrictConfig();
    ConditionMapping conditionMapping;
    ExpressionVisitorImpl exprVisitor = new ExpressionVisitorImpl(restrictConfig,conditionMapping);

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