package in.parser.impls;

import in.parser.*;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.piped.FromQuery;
import net.sf.jsqlparser.statement.select.*;

public class FromItemVisitorImpl implements FromItemVisitor<QueryLayer> {

    SelectVisitorImpl sv;

    public FromItemVisitorImpl(SelectVisitorImpl sv) {
        this.sv = sv;
    }

    @Override
    public <S> QueryLayer visit(Table tableName, S context) {
        QueryLayer currentLayer = (QueryLayer) context;
        if (currentLayer != null) {
            currentLayer.add("Tables", tableName.getName());
            if (tableName.getAlias() != null) {
                currentLayer.add("Aliases", tableName.getAlias().getName());
            }
        }
        return currentLayer;
    }

    @Override
    public <S> QueryLayer visit(ParenthesedSelect selectBody, S context) {

        QueryLayer parentLayer = (QueryLayer) context;

        QueryLayer subLayer = new QueryLayer();
        if (parentLayer != null) {
            parentLayer.subLayers.add(subLayer);
        }

        if (selectBody.getAlias() != null) {
            subLayer.add("Aliases", selectBody.getAlias().getName());
        }

        if (selectBody.getSelect() != null) {
            ExpressionVisitorImpl subExpr = new ExpressionVisitorImpl();
            SelectItemVisitorImpl subSelItem = new SelectItemVisitorImpl(subExpr);
            SelectVisitorImpl subSelect = new SelectVisitorImpl(null, subSelItem, subExpr);
            FromItemVisitorImpl subFrom = new FromItemVisitorImpl(subSelect);
            subSelect = new SelectVisitorImpl(subFrom, subSelItem, subExpr);
            selectBody.getSelect().accept(new StatementVisitorImpl(subSelect), subLayer);
        }
        return subLayer;
    }

    @Override
    public <S> QueryLayer visit(LateralSubSelect lateralSubSelect, S context) {
        return null;
    }

    @Override
    public <S> QueryLayer visit(TableFunction tableFunction, S context) {
        return null;
    }

    @Override
    public <S> QueryLayer visit(ParenthesedFromItem parenthesedFromItem, S context) {
        return null;
    }

    @Override
    public <S> QueryLayer visit(Values values, S context) {
        return null;
    }

    /**
     *  Parses FROM subquery (PlainSelect) into a subquery layer.
     * Example: SELECT * FROM (SELECT id FROM users) u;
     */

    @Override
    public <S> QueryLayer visit(PlainSelect plainSelect, S context) {
        QueryLayer parentLayer = (QueryLayer) context;
        QueryLayer subLayer = new QueryLayer();
        if (parentLayer != null) {
            parentLayer.subLayers.add(subLayer);
        }

        if (sv != null) {
            sv.visit(plainSelect, subLayer);
        }

        return subLayer;
    }

    /**
     * Parses SetOperationList (UNION/INTERSECT/EXCEPT) queries into one logical subquery layer.
     * SELECT * FROM A UNION SELECT * FROM B;
     */

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
                    select.accept(sv, subLayer);
                }
            }
        }

        return subLayer;
    }

    @Override
    public <S> QueryLayer visit(TableStatement tableStatement, S context) {
        return null;
    }


    @Override
    public <S> QueryLayer visit(FromQuery fromQuery, S context) {
        return null;
    }
}
