package in.parser.impls;

import in.parser.queryparser.ConditionMapping;
import in.parser.queryparser.QueryLayer;
import in.parser.queryparser.RestrictTablesColumns;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.piped.FromQuery;
import net.sf.jsqlparser.statement.select.*;

public class SelectFromVisitorImpl implements SelectVisitor<QueryLayer>,FromItemVisitor<QueryLayer> {

    RestrictTablesColumns restrictTablesColumns;
    ExpressionVisitorImpl expressionVisitor;
    ConditionMapping conditionMapping;
    SelectItemVisitorImpl selectItemVisitor;

    public SelectFromVisitorImpl(SelectItemVisitorImpl selectItemVisitor,RestrictTablesColumns restrictTablesColumns, ExpressionVisitorImpl expressionVisitor,ConditionMapping conditionMapping){
        this.restrictTablesColumns=restrictTablesColumns;
        this.expressionVisitor=expressionVisitor;
        this.conditionMapping=conditionMapping;
        this.selectItemVisitor=selectItemVisitor;
    }

    @Override
    public <S> QueryLayer visit(Table tableName, S context) {
        QueryLayer layer = (QueryLayer) context;
        String actualTable = tableName.getName();
        restrictTablesColumns.addCurrentTable(actualTable);
        if (tableName.getAlias() != null) {
            String alias = tableName.getAlias().getName();
            restrictTablesColumns.addAlias(alias, actualTable);
            layer.add("Aliases", alias);
        }

        if ((!restrictTablesColumns.getTables().stream().anyMatch(s -> s.equalsIgnoreCase(actualTable))) &&
                (!restrictTablesColumns.getPrefixTables().stream().anyMatch(s -> actualTable.startsWith(s)))){
            layer.add("Tables", actualTable);
        }
        else {
            layer.add("RestrictTables", actualTable);
        }

        return layer;
    }

    @Override
    public <S> QueryLayer visit(TableFunction tableFunction, S context) {
        return null;
    }

    @Override
    public QueryLayer visit(ParenthesedFromItem parenthesedFromItem, Object context) {
        return null;
    }

    @Override
    public <S> QueryLayer visit(ParenthesedSelect parenthesedSelect, S context) {
        QueryLayer layer = (QueryLayer) context;
        if (parenthesedSelect.getAlias() != null && layer != null) {
            layer.add("Aliases", parenthesedSelect.getAlias().getName());
        }
        if (parenthesedSelect.getSelect() != null) {
            parenthesedSelect.getSelect().accept(new StatementVisitorImpl(this, restrictTablesColumns,expressionVisitor), context);
        }
        return layer;
    }

    @Override
    public QueryLayer visit(PlainSelect plainSelect, Object context) {
        QueryLayer currentLayer = (QueryLayer) context;
        ExpressionVisitorImpl localExprVisitor = this.expressionVisitor;
        if (plainSelect.getFromItem() != null && (FromItemVisitor)this != null) {
            plainSelect.getFromItem().accept(this, context);
        }
        if (plainSelect.getSelectItems() != null) {
            for (SelectItem<? extends Expression> i : plainSelect.getSelectItems()) {
                i.accept(selectItemVisitor, context);
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
    public QueryLayer visit(FromQuery fromQuery, Object context) {
        return null;
    }

    @Override
    public QueryLayer visit(SetOperationList setOperationList, Object context) {
        QueryLayer parentLayer=(QueryLayer)context;
        QueryLayer subLayer=new QueryLayer();

        if(parentLayer!=null){
            parentLayer.subLayers.add(subLayer);
        }

        if (setOperationList != null && setOperationList.getSelects() != null) {
            for (Select select : setOperationList.getSelects()) {
                if (select != null) {
                    select.accept((SelectVisitor) this, subLayer);
                }
            }
        }

        return subLayer;
    }

    @Override
    public QueryLayer visit(WithItem withItem, Object context) {
        QueryLayer parentLayer = (QueryLayer) context;
        QueryLayer cteLayer = new QueryLayer();

        if (parentLayer != null) {
            parentLayer.subLayers.add(cteLayer);
        }

        if (withItem.getAlias() != null) {
            cteLayer.add("CTE", withItem.getAlias().getName());
        }

        if (withItem.getSelect() != null) {
            withItem.getSelect().accept(new StatementVisitorImpl(this, restrictTablesColumns, expressionVisitor), cteLayer);
        }

        return cteLayer;
    }

    @Override
    public QueryLayer visit(Values values, Object context) {
        return null;
    }

    @Override
    public QueryLayer visit(LateralSubSelect lateralSubSelect, Object context) {
        return null;
    }

    @Override
    public QueryLayer visit(TableStatement tableStatement, Object context) {
        return null;
    }

    @Override
    public void visit(ParenthesedSelect parenthesedSelect) {
        SelectVisitor.super.visit(parenthesedSelect);
    }

    @Override
    public void visit(PlainSelect plainSelect) {
        SelectVisitor.super.visit(plainSelect);
    }

    @Override
    public void visit(SetOperationList setOpList) {
        SelectVisitor.super.visit(setOpList);
    }

    @Override
    public void visit(Values values) {
        SelectVisitor.super.visit(values);
    }

    @Override
    public void visit(LateralSubSelect lateralSubSelect) {
        SelectVisitor.super.visit(lateralSubSelect);
    }

    @Override
    public void visit(TableStatement tableStatement) {
        SelectVisitor.super.visit(tableStatement);
    }
}
