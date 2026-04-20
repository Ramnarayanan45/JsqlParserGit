package in.parser.impls;

import in.parser.queryparser.ConditionMapping;
import in.parser.queryparser.QueryLayer;
import in.parser.queryparser.RestrictTablesColumns;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.piped.FromQuery;
import net.sf.jsqlparser.statement.select.*;

public class FromItemVisitorImpl implements FromItemVisitor<QueryLayer> {

    SelectVisitorImpl sv;
    RestrictTablesColumns restrictTablesColumns;
    ConditionMapping conditionMapping;

    public FromItemVisitorImpl(SelectVisitorImpl sv, RestrictTablesColumns restrictTablesColumns) {
        this.sv = sv;
        this.restrictTablesColumns=restrictTablesColumns;
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
            ExpressionVisitorImpl subExpr =new ExpressionVisitorImpl(restrictTablesColumns, conditionMapping);
            SelectItemVisitorImpl subSelItem = new SelectItemVisitorImpl(subExpr);
            SelectVisitorImpl subSelect = new SelectVisitorImpl(null, subSelItem, subExpr);
            FromItemVisitorImpl subFrom = new FromItemVisitorImpl(subSelect,restrictTablesColumns);
            subSelect = new SelectVisitorImpl(subFrom, subSelItem, subExpr);
//            selectBody.getSelect().accept(new StatementVisitorImpl(subSelect,restrictTablesColumns,subExpr), subLayer);
        }
        return subLayer;
    }

    @Override
    public <S> QueryLayer visit(LateralSubSelect lateralSubSelect, S context) {
        System.out.println(lateralSubSelect);
        return null;
    }

    @Override
    public <S> QueryLayer visit(TableFunction tableFunction, S context) {
        System.out.println(tableFunction);
        return null;
    }

    @Override
    public <S> QueryLayer visit(ParenthesedFromItem parenthesedFromItem, S context) {
        System.out.println(parenthesedFromItem);
        return null;
    }

    @Override
    public <S> QueryLayer visit(Values values, S context) {
        QueryLayer layer = (QueryLayer) context;

        if (values.getExpressions() != null) {
            for (Expression row : values.getExpressions()) {
                row.accept(new ExpressionVisitorImpl(restrictTablesColumns, conditionMapping), context);
            }
        }
        return layer;
    }

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
        System.out.println(tableStatement);
        return null;
    }


    @Override
    public <S> QueryLayer visit(FromQuery fromQuery, S context) {
        System.out.println(fromQuery);
        return null;
    }
}
