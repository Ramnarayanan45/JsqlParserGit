package in.parser.impls;

import in.parser.queryparser.ConditionMapping;
import in.parser.queryparser.QueryLayer;
import in.parser.queryparser.RestrictTablesColumns;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.piped.FromQuery;
import net.sf.jsqlparser.statement.select.*;


public class SelectVisitorImpl implements SelectVisitor<QueryLayer> {

    FromItemVisitorImpl fv;
    SelectItemVisitorImpl sv;
    ExpressionVisitorImpl exv;
    RestrictTablesColumns restrictTablesColumns;
    ConditionMapping conditionMapping;


    public SelectVisitorImpl(Statement smt) {
        if (smt instanceof Select select) {
            select.accept(this, null);
        }
    }

    public SelectVisitorImpl(FromItemVisitorImpl fromVisitor, SelectItemVisitorImpl selectItemVisitor, ExpressionVisitorImpl exprVisitor) {
        this.fv = fromVisitor;
        this.sv = selectItemVisitor;
        this.exv = exprVisitor;
        restrictTablesColumns=exv.restrictTablesColumns;
        conditionMapping= exv.conditionMapping;
    }

    public String resolveFromItemName(FromItem item, boolean useAliasIfPresent) {
        if (item instanceof Table table) {
            if (useAliasIfPresent && table.getAlias() != null && table.getAlias().getName() != null && !table.getAlias().getName().isBlank()){
                return table.getAlias().getName();
            }
            return table.getName();
        }
        if (item instanceof ParenthesedSelect parenthesedSelect && parenthesedSelect.getAlias() != null && parenthesedSelect.getAlias().getName() != null && !parenthesedSelect.getAlias().getName().isBlank()) {
            return parenthesedSelect.getAlias().getName();
        }
        return ""+item;
    }

    public String resolveJoinType(Join join) {
        if (join.isLeft()) {
            return "LEFT JOIN";
        }
        if (join.isRight()) {
            return "RIGHT JOIN";
        }
        if (join.isFull()) {
            return "FULL JOIN";
        }
        if (join.isInnerJoin()) {
            return "INNER JOIN";
        }
        if (join.isCross()) {
            return "CROSS JOIN";
        }
        return "JOIN";
    }

    @Override
    public <S> QueryLayer visit(PlainSelect plainSelect, S context) {
        QueryLayer currentLayer = (QueryLayer) context;
        ExpressionVisitorImpl localExprVisitor = this.exv;
        if (plainSelect.getFromItem() != null && fv != null) {
            plainSelect.getFromItem().accept(fv, context);
        }
        if (plainSelect.getSelectItems() != null) {
            for (SelectItem<? extends Expression> i : plainSelect.getSelectItems()) {
                i.accept(sv, context);
            }
        }
        if (plainSelect.getWhere() != null) {
            plainSelect.getWhere().accept(localExprVisitor, context);
        }
        if (plainSelect.getJoins() != null) {
            for (Join join : plainSelect.getJoins()) {
                if (join.getOnExpressions() != null) {
                    for (Expression onExpression : join.getOnExpressions()) {
                        onExpression.accept(localExprVisitor, context);
                    }
                }
            }
        }
        if (plainSelect.getHaving() != null) {
            plainSelect.getHaving().accept(localExprVisitor, context);
        }
        return currentLayer;
    }

    @Override
    public <S> QueryLayer visit(ParenthesedSelect parenthesedSelect, S context) {

        QueryLayer layer = (QueryLayer) context;
        if (parenthesedSelect.getAlias() != null && layer != null) {
            layer.add("Aliases", parenthesedSelect.getAlias().getName());
        }
        if (parenthesedSelect.getSelect() != null) {
            parenthesedSelect.getSelect().accept(new StatementVisitorImpl(this, restrictTablesColumns,exv), context);
        }
        return layer;
    }

    @Override
    public <S> QueryLayer visit(FromQuery fromQuery, S context) {
        return null;
    }

    @Override
    public <S> QueryLayer visit(SetOperationList setOpList, S context) {

        QueryLayer parentLayer = (QueryLayer) context;
        QueryLayer subLayer = new QueryLayer();

        if (parentLayer != null) {
            parentLayer.subLayers.add(subLayer);
        }

        if (setOpList.getSelects() != null) {
            for (Select select : setOpList.getSelects()) {
                if (select != null) {
                    select.accept(
                            new StatementVisitorImpl(this, restrictTablesColumns,exv),
                            subLayer
                    );
                }
            }
        }

        return subLayer;
    }

    @Override
    public <S> QueryLayer visit(WithItem<?> withItem, S context) {

        QueryLayer parentLayer = (QueryLayer) context;
        QueryLayer cteLayer = new QueryLayer();

        if (parentLayer != null) {
            parentLayer.subLayers.add(cteLayer);
        }

        if (withItem.getAlias() != null) {
            cteLayer.add("CTE", withItem.getAlias().getName());
        }

        if (withItem.getSelect() != null) {
            withItem.getSelect().accept(new StatementVisitorImpl(this, restrictTablesColumns, exv), cteLayer);
        }

        return cteLayer;
    }
    @Override
    public <S> QueryLayer visit(Values values, S context) {
        return null;
    }

    @Override
    public <S> QueryLayer visit(LateralSubSelect lateralSubSelect, S context) {
        return null;
    }

    @Override
    public <S> QueryLayer visit(TableStatement tableStatement, S context) {
        return null;
    }

}
