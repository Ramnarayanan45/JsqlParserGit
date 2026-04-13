package in.parser.impls;

import in.parser.*;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.statement.select.*;

public class SelectItemVisitorImpl implements SelectItemVisitor<QueryLayer> {

    ExpressionVisitorImpl ev;

    public SelectItemVisitorImpl(ExpressionVisitorImpl ev) {
        this.ev = ev;
    }

    @Override
    public <S> QueryLayer visit(SelectItem<? extends Expression> selectItem, S context) {

        Expression expr = selectItem.getExpression();
        QueryLayer currentLayer = (QueryLayer) context;
        if (selectItem.getAlias() != null && currentLayer != null) {
            currentLayer.add("Aliases", selectItem.getAlias().getName());
        }

        if (expr != null) {
            expr.accept(ev, context);
        }

        return currentLayer;
    }

}
