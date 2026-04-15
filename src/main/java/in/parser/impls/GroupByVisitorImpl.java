package in.parser.impls;

import in.parser.queryparser.QueryLayer;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.statement.select.*;

public class GroupByVisitorImpl implements GroupByVisitor<QueryLayer> {
    ExpressionVisitorImpl expressionVisitor;

    public GroupByVisitorImpl(ExpressionVisitorImpl expressionVisitor) {
        this.expressionVisitor = expressionVisitor;
    }

    @Override
    public <S> QueryLayer visit(GroupByElement groupBy, S context) {
        QueryLayer layer = (QueryLayer) context;
        if (layer == null || groupBy == null) {
            return layer;
        }
        layer.add("GroupByCriteria", groupBy.toString());
        if (groupBy.getGroupByExpressionList() != null) {
            for (Object groupByExpression : groupBy.getGroupByExpressionList()) {
                if (groupByExpression instanceof Expression expression) {
                    expression.accept(expressionVisitor, context);
                }
            }
        }
        return layer;
    }
}
