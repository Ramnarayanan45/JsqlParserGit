package in.parser.impls;

import in.parser.queryparser.ConditionMapping;
import in.parser.queryparser.QueryLayer;
import in.parser.queryparser.RestrictTablesColumns;
import net.sf.jsqlparser.expression.Alias;
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
        if (smt instanceof Select select && select.getPlainSelect() != null) {
            visit(select.getPlainSelect(), null);
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

        if (plainSelect.getFromItem() != null && fv != null) {
            plainSelect.getFromItem().accept(fv, context);
        }

        if (plainSelect.getSelectItems() != null) {
            for (SelectItem<? extends Expression> i : plainSelect.getSelectItems()) {
                i.accept(sv, context);
            }
        }

        if (plainSelect.getWhere() != null) {
            plainSelect.getWhere().accept(exv, context);
        }

        if (plainSelect.getJoins() != null) {
            String currentLeft = resolveFromItemName(plainSelect.getFromItem(), false);
            for (Join join : plainSelect.getJoins()) {
                String right = resolveFromItemName(join.getRightItem(), false);
                if (currentLayer != null) {
                    currentLayer.add("Joins", currentLeft + " " + resolveJoinType(join) + " " + right);
                    if (join.getOnExpressions() != null) {
                        for (Expression onExpression : join.getOnExpressions()) {
                            currentLayer.add("Join_Conditions", onExpression.toString());
                        }
                    }
                }
                if (join.getRightItem() != null) {
                    join.getRightItem().accept(fv, context);
                }
                currentLeft = right;
            }
        }

        if (plainSelect.getGroupBy() != null && plainSelect.getGroupBy().getGroupByExpressionList() != null) {
            ExpressionVisitorImpl groupExpr =new ExpressionVisitorImpl(restrictTablesColumns, conditionMapping);
            GroupByVisitorImpl groupByVisitor = new GroupByVisitorImpl(groupExpr);
            plainSelect.getGroupBy().accept(groupByVisitor, context);
        }

        if (plainSelect.getOrderByElements() != null) {
            ExpressionVisitorImpl orderExpr =new ExpressionVisitorImpl(restrictTablesColumns, conditionMapping);
            OrderByVisitorImpl orderByVisitor = new OrderByVisitorImpl(orderExpr);
            for (OrderByElement ob : plainSelect.getOrderByElements()) {
                ob.accept(orderByVisitor, context);
            }
        }

        if (plainSelect.getHaving() != null && currentLayer != null) {
            ExpressionVisitorImpl havingExpr = new ExpressionVisitorImpl(restrictTablesColumns, conditionMapping);
            plainSelect.getHaving().accept(havingExpr, context);
            currentLayer.add("HavingCriteria", plainSelect.getHaving().toString());
        }

        if (plainSelect.getLimit() != null && currentLayer != null) {
            currentLayer.add("Limits", plainSelect.getLimit().toString());
        }

        if (plainSelect.getOffset() != null && currentLayer != null) {
            currentLayer.add("Offsets", plainSelect.getOffset().toString());
        }

        return currentLayer;
    }

    @Override
    public <S> QueryLayer visit(ParenthesedSelect parenthesedSelect, S context) {
        QueryLayer currentLayer = (QueryLayer) context;
        if(parenthesedSelect.getAlias()!=null && currentLayer != null) {
            Alias al = parenthesedSelect.getAlias();
            currentLayer.add("Aliases", al.toString());
        }
        if (parenthesedSelect.getSelect() != null) {
            parenthesedSelect.getSelect().accept(this, context);
        }
        return currentLayer;
    }

    @Override
    public <S> QueryLayer visit(FromQuery fromQuery, S context) {
        return null;
    }

    @Override
    public <S> QueryLayer visit(SetOperationList setOpList, S context) {
        QueryLayer parentLayer=(QueryLayer)context;
        QueryLayer subLayer=new QueryLayer();

        if(parentLayer!=null){
            parentLayer.subLayers.add(subLayer);
        }
        if (setOpList != null && setOpList.getSelects() != null) {
            for (Select select : setOpList.getSelects()) {
                if (select != null) {
                    select.accept(this, subLayer);
                }
            }
        }
        return subLayer;
    }

    @Override
    public <S> QueryLayer visit(WithItem<?> withItem, S context) {
        return null;
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
