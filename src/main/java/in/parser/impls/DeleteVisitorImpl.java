package in.parser.impls;

import in.parser.queryparser.QueryLayer;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.statement.delete.Delete;
import net.sf.jsqlparser.schema.Table;

public class DeleteVisitorImpl {

    ExpressionVisitorImpl exprVisitor = new ExpressionVisitorImpl();

    public QueryLayer handle(Delete delete, QueryLayer layer) {

        if (layer == null) return null;

        Table table = delete.getTable();
        if (table != null) {
            layer.add("Table", table.getName());
        }

        Expression where = delete.getWhere();
        if (where != null) {
            layer.add("Where", where.toString());
            where.accept(exprVisitor, layer);
        }

        if (delete.getUsingList() != null) {
            delete.getUsingList().forEach(t ->
                layer.add("UsingTables", t.toString())
            );
        }
        return layer;
    }
}