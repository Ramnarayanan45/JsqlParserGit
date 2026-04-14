package in.parser.impls;

import in.parser.*;
import net.sf.jsqlparser.expression.Alias;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.piped.FromQuery;
import net.sf.jsqlparser.statement.select.*;
import java.util.*;

public class SelectVisitorImpl implements SelectVisitor<QueryLayer> {

    FromItemVisitorImpl fv;
    SelectItemVisitorImpl sv;
    ExpressionVisitorImpl exv;

    public SelectVisitorImpl(Statement smt) {
        if (smt instanceof Select select && select.getPlainSelect() != null) {
            visit(select.getPlainSelect(), null);
        }
    }

    public SelectVisitorImpl(FromItemVisitorImpl fromVisitor, SelectItemVisitorImpl selectItemVisitor, ExpressionVisitorImpl exprVisitor) {
        this.fv = fromVisitor;
        this.sv = selectItemVisitor;
        this.exv = exprVisitor;
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
            ExpressionVisitorImpl whereExpr = new ExpressionVisitorImpl(true, false, false);
            plainSelect.getWhere().accept(whereExpr, context);
        }

        if (plainSelect.getJoins() != null) {
            for (Join join : plainSelect.getJoins()) {
                if (currentLayer != null) {
                    currentLayer.add("Joins", join.toString());
                }
                if (join.getRightItem() != null) {
                    join.getRightItem().accept(fv, context);
                }
            }
        }

        if (plainSelect.getGroupBy() != null &&
                plainSelect.getGroupBy().getGroupByExpressionList() != null) {

            if (currentLayer != null) {
                currentLayer.add("GroupByCriteria", plainSelect.getGroupBy().toString());
            }
            ExpressionVisitorImpl groupExpr = new ExpressionVisitorImpl(false, true, false);

            for (Object obj : plainSelect.getGroupBy().getGroupByExpressionList()) {
                if (obj instanceof Expression exp) {
                    exp.accept(groupExpr, context);
                }
            }
        }

        if (plainSelect.getOrderByElements() != null) {
            ExpressionVisitorImpl orderExpr = new ExpressionVisitorImpl(false, false, true);

            for (OrderByElement ob : plainSelect.getOrderByElements()) {
                if (ob.getExpression() != null) {
                    ob.getExpression().accept(orderExpr, context);
                }
            }
        }

        if (plainSelect.getHaving() != null && currentLayer != null) {
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
