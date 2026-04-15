package in.parser.impls;

import in.parser.queryparser.QueryLayer;
import net.sf.jsqlparser.statement.select.*;

public class OrderByVisitorImpl implements OrderByVisitor<QueryLayer> {
    ExpressionVisitorImpl expressionVisitor;

    public OrderByVisitorImpl(ExpressionVisitorImpl expressionVisitor) {
        this.expressionVisitor = expressionVisitor;
    }

    @Override
    public QueryLayer visit(OrderByElement orderBy, Object context) {
        QueryLayer layer = (QueryLayer) context;
        if (layer == null || orderBy == null) {
            return layer;
        }
        layer.add("OrderByColumn",orderBy.toString());
        if(orderBy.isAsc()){
            layer.add("OrderByOrder", "Asc");
        }
        else{
            layer.add("OrderByOrder", "Desc");
        }

        if (orderBy.getExpression() != null) {
            orderBy.getExpression().accept(expressionVisitor, context);
        }
        return layer;
    }
}
