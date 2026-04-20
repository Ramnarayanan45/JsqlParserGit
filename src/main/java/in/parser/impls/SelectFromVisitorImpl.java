package in.parser.impls;

import in.parser.queryparser.ParamGenerator;
import in.parser.queryparser.QueryLayer;
import in.parser.queryparser.RestrictConfig;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.piped.FromQuery;
import net.sf.jsqlparser.statement.select.*;

public class SelectFromVisitorImpl implements SelectVisitor<QueryLayer>,FromItemVisitor<QueryLayer> {

    RestrictConfig restrictConfig;
    ExpressionVisitorImpl expressionVisitor;
    ParamGenerator paramGenerator;
    SelectItemVisitorImpl selectItemVisitor;

    public SelectFromVisitorImpl(SelectItemVisitorImpl selectItemVisitor, RestrictConfig restrictConfig, ExpressionVisitorImpl expressionVisitor, ParamGenerator paramGenerator){
        this.restrictConfig = restrictConfig;
        this.expressionVisitor=expressionVisitor;
        this.paramGenerator = paramGenerator;
        this.selectItemVisitor=selectItemVisitor;
    }

    @Override
    public <S> QueryLayer visit(Table tableName, S context) {
        QueryLayer layer = (QueryLayer) context;
        String actualTable = tableName.getName();
        restrictConfig.addCurrentTable(actualTable);
        if (tableName.getAlias() != null) {
            String alias = tableName.getAlias().getName();
            restrictConfig.addAlias(alias, actualTable);
            layer.add("Aliases", alias);
        }

        if ((restrictConfig.getTables().stream().anyMatch(s -> s.equalsIgnoreCase(actualTable))) ||
                (restrictConfig.getPrefixTables().stream().anyMatch(s -> actualTable.startsWith(s)))){
            layer.add("RestrictTables", actualTable);
        }
        else {
            layer.add("Tables", actualTable);
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
            parenthesedSelect.getSelect().accept(new StatementVisitorImpl(this, restrictConfig,expressionVisitor), context);
        }
        return layer;
    }

    @Override
    public <S> QueryLayer visit(PlainSelect plainSelect, S context) {
        QueryLayer currentLayer = (QueryLayer) context;
        ExpressionVisitorImpl localExprVisitor = this.expressionVisitor;
        if (plainSelect.getFromItem() != null) {
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
    public <S> QueryLayer visit(FromQuery fromQuery, S context) {
        return null;
    }

    @Override
    public <S> QueryLayer visit(SetOperationList setOperationList, S context) {
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
            withItem.getSelect().accept(new StatementVisitorImpl(this, restrictConfig, expressionVisitor), cteLayer);
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
