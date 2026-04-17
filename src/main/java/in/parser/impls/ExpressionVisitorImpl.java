package in.parser.impls;

import in.parser.queryparser.ConditionMapping;
import in.parser.queryparser.QueryLayer;
import in.parser.queryparser.RestrictTablesColumns;
import net.sf.jsqlparser.expression.*;
import net.sf.jsqlparser.expression.operators.arithmetic.*;
import net.sf.jsqlparser.expression.operators.conditional.*;
import net.sf.jsqlparser.expression.operators.relational.*;
import net.sf.jsqlparser.schema.*;
import net.sf.jsqlparser.statement.piped.FromQuery;
import net.sf.jsqlparser.statement.select.*;
import java.util.*;

public class ExpressionVisitorImpl implements ExpressionVisitor<QueryLayer> {

    RestrictTablesColumns restrictTablesColumns;
    ConditionMapping conditionMapping;

    public ExpressionVisitorImpl(RestrictTablesColumns restrictTablesColumns, ConditionMapping conditionMapping) {
        this.restrictTablesColumns = restrictTablesColumns;
        this.conditionMapping = conditionMapping;
    }

    public String getColumnName(Column column) {
        if (column == null) {
            return "";
        }
        String columnName = column.getColumnName();
        Table table = column.getTable();
        if (table != null && table.getName() != null && !table.getName().isBlank()) {
            return table.getName() + "." + columnName;
        }
        return columnName;
    }

    @Override
    public <S> QueryLayer visit(BitwiseRightShift bitwiseRightShift, S context) {
        return null;
    }

    @Override
    public <S> QueryLayer visit(BitwiseLeftShift bitwiseLeftShift, S context) {
        return null;
    }

    @Override
    public <S> QueryLayer visit(NullValue nullValue, S context) {
        return null;
    }

    @Override
    public <S> QueryLayer visit(Function function, S context) {

        QueryLayer layer = (QueryLayer) context;
        if (function.getParameters() != null) {
            function.getParameters().accept(this, context);
        }
        if (layer != null) {
            layer.add("Functions", function.getName());
            layer.add("Function_Columns", function.toString());
        }
        return layer;
    }

    @Override
    public <S> QueryLayer visit(SignedExpression signedExpression, S context) {
        return null;
    }

    @Override
    public <S> QueryLayer visit(JdbcParameter jdbcParameter, S context) {
        return null;
    }

    @Override
    public <S> QueryLayer visit(JdbcNamedParameter jdbcNamedParameter, S context) {
        return null;
    }

    @Override
    public <S> QueryLayer visit(DoubleValue doubleValue, S context) {
        return null;
    }

    @Override
    public <S> QueryLayer visit(LongValue longValue, S context) {
        QueryLayer layer = (QueryLayer) context;
        if (layer != null) {
            layer.add("Condition Value", new JdbcParameter());
        }
        return layer;
    }

    @Override
    public <S> QueryLayer visit(HexValue hexValue, S context) {
        return null;
    }

    @Override
    public <S> QueryLayer visit(DateValue dateValue, S context) {
        QueryLayer layer = (QueryLayer) context;
        if (layer != null) {
            layer.add("Condition Value", new JdbcParameter());
        }
        return layer;
    }

    @Override
    public <S> QueryLayer visit(TimeValue timeValue, S context) {
        QueryLayer layer = (QueryLayer) context;
        if (layer != null) {
            layer.add("Condition Value", new JdbcParameter());
        }
        return layer;
    }

    @Override
    public <S> QueryLayer visit(TimestampValue timestampValue, S context) {
        QueryLayer layer = (QueryLayer) context;
        if (layer != null) {
            layer.add("Condition Value", new JdbcParameter());
        }
        return layer;
    }

    @Override
    public <S> QueryLayer visit(StringValue stringValue, S context) {
        QueryLayer layer = (QueryLayer) context;
        if (layer != null) {
            layer.add("Condition Value", new JdbcParameter());
        }
        return layer;
    }

    @Override
    public <S> QueryLayer visit(BooleanValue booleanValue, S context) {
        return null;
    }

    @Override
    public <S> QueryLayer visit(Addition addition, S context) {
        addition.getLeftExpression().accept(this, context);
        addition.getRightExpression().accept(this, context);
        return (QueryLayer) context;
    }

    @Override
    public <S> QueryLayer visit(Division division, S context) {
        division.getLeftExpression().accept(this, context);
        division.getRightExpression().accept(this, context);
        return (QueryLayer) context;
    }

    @Override
    public <S> QueryLayer visit(IntegerDivision integerDivision, S context) {
        return null;
    }

    @Override
    public <S> QueryLayer visit(Multiplication multiplication, S context) {
        multiplication.getLeftExpression().accept(this, context);
        multiplication.getRightExpression().accept(this, context);
        return (QueryLayer) context;
    }

    @Override
    public <S> QueryLayer visit(Subtraction subtraction, S context) {
        subtraction.getLeftExpression().accept(this, context);
        subtraction.getRightExpression().accept(this, context);
        return (QueryLayer) context;
    }

    @Override
    public <S> QueryLayer visit(AndExpression andExpression, S context) {
        andExpression.getLeftExpression().accept(this, context);
        andExpression.getRightExpression().accept(this, context);
        return (QueryLayer) context;
    }

    @Override
    public <S> QueryLayer visit(OrExpression orExpression, S context) {
        orExpression.getLeftExpression().accept(this, context);
        orExpression.getRightExpression().accept(this, context);
        return (QueryLayer) context;
    }

    @Override
    public <S> QueryLayer visit(XorExpression xorExpression, S context) {
        return null;
    }

    @Override
    public <S> QueryLayer visit(Between between, S context) {
        QueryLayer layer = (QueryLayer) context;
        Expression left = between.getLeftExpression();
//        Expression right=;
        if (left instanceof Column column) {
            String columnName = getColumnName(column);
            ExpressionVisitorAdapterImpl expr = new ExpressionVisitorAdapterImpl();
//            right.accept(expr);
            List<Object> val = new ArrayList<>();
            val.add(expr.getValue());
            conditionMapping.addCondition(columnName, val);
//                between.setRightExpression(new JdbcParameter());
        }
        if (layer != null) {
            layer.add("Conditions", between.toString());
        }
//        if ( != null) {
        between.getLeftExpression().accept(this, context);
//        }
        if (between.getBetweenExpressionStart() != null) {
            between.getBetweenExpressionStart().accept(this, context);
        }
        if (between.getBetweenExpressionEnd() != null) {
            between.getBetweenExpressionEnd().accept(this, context);
        }
        return layer;
    }

    @Override
    public <S> QueryLayer visit(OverlapsCondition overlapsCondition, S context) {
        return null;
    }

    @Override
    public <S> QueryLayer visit(EqualsTo equalsTo, S context) {

        QueryLayer layer = (QueryLayer) context;
        Expression left = equalsTo.getLeftExpression();
        Expression right = equalsTo.getRightExpression();
        if (left instanceof Column column) {
            String columnName = getColumnName(column);

            if (right instanceof LongValue ||
                    right instanceof DoubleValue ||
                    right instanceof StringValue ||
                    right instanceof DateValue ||
                    right instanceof TimeValue ||
                    right instanceof BooleanValue ||
                    right instanceof TimestampValue || right instanceof NullValue) {

                ExpressionVisitorAdapterImpl expr = new ExpressionVisitorAdapterImpl();
                right.accept(expr);

                List<Object> val = new ArrayList<>();
                val.add(expr.getValue());

                conditionMapping.addCondition(columnName, val);

                equalsTo.setRightExpression(new JdbcParameter());

            } else if (right instanceof Select) {
                right.accept(this, context);

            } else {
                right.accept(this, context);
            }
        }

        if (layer != null) {
            layer.add("Conditions", equalsTo.toString());
        }

        if (left != null) {
            left.accept(this, context);
        }
        if (right != null) {
            right.accept(this, context);
        }
        return layer;
    }

    @Override
    public <S> QueryLayer visit(GreaterThan greaterThan, S context) {

        QueryLayer layer = (QueryLayer) context;
        Expression left = greaterThan.getLeftExpression();
        Expression right = greaterThan.getRightExpression();
        if (left instanceof Column || left instanceof Function) {
            String columnName = left.toString();
            ExpressionVisitorAdapterImpl expr = new ExpressionVisitorAdapterImpl();
            right.accept(expr);
            List<Object> val = new ArrayList<>();
            val.add(expr.getValue());
            conditionMapping.addCondition(columnName, val);
            greaterThan.setRightExpression(new JdbcParameter());
        }

        if (layer != null) {
            layer.add("Conditions", greaterThan.toString());
        }

        return layer;
    }

    @Override
    public <S> QueryLayer visit(GreaterThanEquals greaterThanEquals, S context) {
        QueryLayer layer = (QueryLayer) context;
        Expression left=greaterThanEquals.getLeftExpression();
        Expression right=greaterThanEquals.getRightExpression();
        if (left instanceof Column column) {
            String columnName = getColumnName(column);

            if (right instanceof LongValue ||
                    right instanceof DoubleValue ||
                    right instanceof StringValue ||
                    right instanceof DateValue ||
                    right instanceof TimeValue ||
                    right instanceof TimestampValue) {

                ExpressionVisitorAdapterImpl expr = new ExpressionVisitorAdapterImpl();
                right.accept(expr);

                List<Object> val = new ArrayList<>();
                val.add(expr.getValue());

                conditionMapping.addCondition(columnName, val);

                greaterThanEquals.setRightExpression(new JdbcParameter());

            }
            else if (right instanceof Select) {
                right.accept(this, context);

            }
            else {
                right.accept(this, context);
            }
        }
        if (layer != null) {
            layer.add("Conditions", greaterThanEquals.toString());
        }
        if (left!= null) {
            left.accept(this, context);
        }
        if (right != null) {
           right.accept(this, context);
        }
        return layer;
    }

//    @Override
//    public <S> QueryLayer visit(InExpression inExpression, S context) {
//        QueryLayer layer = (QueryLayer) context;
//
//        Expression left = inExpression.getLeftExpression();
//        Expression right = inExpression.getRightExpression();
//        if (left instanceof Column column) {
//            String columnName = getColumnName(column);
//            ExpressionVisitorAdapterImpl expr = new ExpressionVisitorAdapterImpl();
//            right.accept(expr);
//            List<Object> val=new ArrayList<>();
//            val.add(expr.getValue());
//            conditionMapping.addCondition(columnName, val);
//            inExpression.setRightExpression(new JdbcParameter());
//        }
//        if (layer != null) {
//            layer.add("Conditions", inExpression.toString());
//        }
//        return layer;
//    }
    @Override
    public <S> QueryLayer visit(InExpression inExpression, S context) {
        QueryLayer layer = (QueryLayer) context;

        Expression left = inExpression.getLeftExpression();
        Expression right = inExpression.getRightExpression();

        if (left instanceof Column column) {
            String columnName = getColumnName(column);

            if (right instanceof Select) {
                right.accept(this, context);

            }
            else if (right instanceof ExpressionList<?>) {
                ExpressionVisitorAdapterImpl expr = new ExpressionVisitorAdapterImpl();
                right.accept(expr);

                List<Object> val = new ArrayList<>();
                val.add(expr.getValue());

                conditionMapping.addCondition(columnName, val);

                inExpression.setRightExpression(new JdbcParameter());
            }
        }
        if (layer != null) {
            layer.add("Conditions", inExpression.toString());
        }
        return layer;
    }

    @Override
    public <S> QueryLayer visit(IncludesExpression includesExpression, S context) {
        return null;
    }

    @Override
    public <S> QueryLayer visit(ExcludesExpression excludesExpression, S context) {
        return null;
    }

    @Override
    public <S> QueryLayer visit(FullTextSearch fullTextSearch, S context) {
        QueryLayer layer = (QueryLayer) context;

        Expression matchColumns = fullTextSearch.getMatchColumns();
        Expression against = fullTextSearch.getAgainstValue();

        if (against instanceof StringValue) {
            ExpressionVisitorAdapterImpl expr = new ExpressionVisitorAdapterImpl();
            against.accept(expr);

            Object value = expr.getValue();

            if (matchColumns instanceof ExpressionList<?> list) {
                Set<String> set=new HashSet<>();
                for (Expression exp : list) {
                    if (exp instanceof Column column) {
                        String columnName = getColumnName(column);
                        if(set.add(columnName)) {
                            conditionMapping.addCondition(columnName, List.of(value));
                        }
                    }
                }
            }
        }

        fullTextSearch.setAgainstValue(new JdbcParameter());

        if (matchColumns != null) matchColumns.accept(this, context);
        if (against != null) against.accept(this, context);

        return layer;
    }

    @Override
    public <S> QueryLayer visit(IsNullExpression isNullExpression, S context) {
        QueryLayer layer = (QueryLayer) context;

        if (layer != null) {
            layer.add("Conditions", isNullExpression.toString());
        }
        return layer;
    }

    @Override
    public <S> QueryLayer visit(IsBooleanExpression isBooleanExpression, S context) {
        return null;
    }

    @Override
    public <S> QueryLayer visit(IsUnknownExpression isUnknownExpression, S context) {
        return null;
    }

    @Override
    public <S> QueryLayer visit(LikeExpression likeExpression, S context) {
        QueryLayer layer = (QueryLayer) context;
        Expression left=likeExpression.getLeftExpression();
        Expression right=likeExpression.getRightExpression();
        if (layer != null) {
            if (left instanceof Column column) {
                String columnName = getColumnName(column);
                ExpressionVisitorAdapterImpl expr = new ExpressionVisitorAdapterImpl();
                right.accept(expr);

                List<Object> val=new ArrayList<>();
                val.add(expr.getValue());
                conditionMapping.addCondition(columnName, val);
                likeExpression.setRightExpression(new JdbcParameter());
            }
            layer.add("Conditions", likeExpression.toString());
        }
        if (likeExpression.getLeftExpression() != null) {
            likeExpression.getLeftExpression().accept(this, context);
        }
        if (likeExpression.getRightExpression() != null) {
            likeExpression.getRightExpression().accept(this, context);
        }
        return layer;
    }

    @Override
    public <S> QueryLayer visit(MinorThan minorThan, S context) {
        QueryLayer layer = (QueryLayer) context;
        Expression left=minorThan.getLeftExpression();
        Expression right=minorThan.getRightExpression();
        if (left instanceof Column || left instanceof Function) {
            String columnName = left.toString();
            ExpressionVisitorAdapterImpl expr = new ExpressionVisitorAdapterImpl();
            right.accept(expr);
            List<Object> val=new ArrayList<>();
            val.add(expr.getValue());
            conditionMapping.addCondition(columnName, val);
            minorThan.setRightExpression(new JdbcParameter());
        }
        if (layer != null) {
            layer.add("Conditions", minorThan.toString());
        }
        if (left != null) {
            left.accept(this, context);
        }
        if (right != null) {
           right.accept(this, context);
        }
        return layer;
    }

    @Override
    public <S> QueryLayer visit(MinorThanEquals minorThanEquals, S context) {
        QueryLayer layer = (QueryLayer) context;
        Expression left=minorThanEquals.getLeftExpression();
        Expression right=minorThanEquals.getRightExpression();
        if (left instanceof Column column) {
            String columnName = getColumnName(column);
            ExpressionVisitorAdapterImpl expr = new ExpressionVisitorAdapterImpl();
            right.accept(expr);

            List<Object> val=new ArrayList<>();
            val.add(expr.getValue());
            conditionMapping.addCondition(columnName, val);
            minorThanEquals.setRightExpression(new JdbcParameter());
        }
        if (layer != null) {
            layer.add("Conditions", minorThanEquals.toString());
        }
        if (left != null) {
           left.accept(this, context);
        }
        if (right != null) {
            right.accept(this, context);
        }
        return layer;
    }

    @Override
    public <S> QueryLayer visit(NotEqualsTo notEqualsTo, S context) {
        QueryLayer layer = (QueryLayer) context;
        Expression left=notEqualsTo.getLeftExpression();
        Expression right=notEqualsTo.getRightExpression();
        if (left instanceof Column column) {
            String columnName = getColumnName(column);

            if (right instanceof LongValue || right instanceof DoubleValue || right instanceof StringValue ||
            right instanceof DateValue ||right instanceof TimeValue || right instanceof TimestampValue || right instanceof NullValue) {

                ExpressionVisitorAdapterImpl expr = new ExpressionVisitorAdapterImpl();
                right.accept(expr);
                List<Object> val = new ArrayList<>();
                val.add(expr.getValue());
                conditionMapping.addCondition(columnName, val);

                notEqualsTo.setRightExpression(new JdbcParameter());

            } else if (right instanceof Select) {
                right.accept(this, context);

            } else {
                right.accept(this, context);
            }
        }
        if (layer != null) {
            layer.add("Conditions", notEqualsTo.toString());
        }
        if (notEqualsTo.getLeftExpression() != null) {
            notEqualsTo.getLeftExpression().accept(this, context);
        }
        if (notEqualsTo.getRightExpression() != null) {
            notEqualsTo.getRightExpression().accept(this, context);
        }
        return layer;
    }

    @Override
    public <S> QueryLayer visit(DoubleAnd doubleAnd, S context) {
        return null;
    }

    @Override
    public <S> QueryLayer visit(Contains contains, S context) {
        return null;
    }

    @Override
    public <S> QueryLayer visit(ContainedBy containedBy, S context) {
        return null;
    }

    @Override
    public <S> QueryLayer visit(ParenthesedSelect select, S context) {
        return visit(select.getSelect(), context);
    }

    @Override
    public <S> QueryLayer visit(Column column, S context) {

        QueryLayer layer = (QueryLayer) context;
        String colName = column.getColumnName();
        String tableName = null;
        if (column.getTable() != null) {
            tableName = column.getTable().getName();
        }
        if (tableName == null || tableName.isBlank()) {

            if (restrictTablesColumns.getCurrentTables().size() == 1) {
                tableName = restrictTablesColumns.getCurrentTables().get(0);
            }
        }
        else {
            tableName = restrictTablesColumns.resolveTable(tableName);
        }
        String table=tableName;
        boolean isRestricted = restrictTablesColumns.getColumns().stream().anyMatch(s -> s.getColumnName().equalsIgnoreCase(colName) && s.getTableName().equalsIgnoreCase(table));
        String fullName = tableName + "." + colName;
        if (layer != null) {
            if (isRestricted) {
                layer.add("RestrictColumns", fullName);
            }
            else {
                layer.add("Columns", fullName);
            }
        }

        return layer;
    }

    @Override
    public <S> QueryLayer visit(CaseExpression caseExpression, S context) {
        if (caseExpression.getSwitchExpression() != null) {
            caseExpression.getSwitchExpression().accept(this, context);
        }

        if (caseExpression.getWhenClauses() != null) {
            for (WhenClause whenClause : caseExpression.getWhenClauses()) {
                whenClause.accept(this, context);
            }
        }

        if (caseExpression.getElseExpression() != null) {
            caseExpression.getElseExpression().accept(this, context);
        }
        return (QueryLayer) context;
    }

    @Override
    public <S> QueryLayer visit(WhenClause whenClause, S context) {
        if (whenClause.getWhenExpression() != null) {
            whenClause.getWhenExpression().accept(this, context);
        }

        if (whenClause.getThenExpression() != null) {
            whenClause.getThenExpression().accept(this, context);
        }
        return (QueryLayer) context;
    }

    @Override
    public <S> QueryLayer visit(ExistsExpression existsExpression, S context) {
        QueryLayer layer=(QueryLayer)context;

        if(layer!=null){
            layer.add("Exists",existsExpression.toString());
        }

        if(existsExpression.getRightExpression()!=null){
            existsExpression.getRightExpression().accept(this,context);
        }

        return layer;
    }

    @Override
    public <S> QueryLayer visit(MemberOfExpression memberOfExpression, S context) {
        return null;
    }

    @Override
    public <S> QueryLayer visit(AnyComparisonExpression anyComparisonExpression, S context) {
        return null;
    }

    @Override
    public <S> QueryLayer visit(Matches matches, S context) {
        QueryLayer layer = (QueryLayer) context;

        Expression left = matches.getLeftExpression();
        Expression right = matches.getRightExpression();
        if (right instanceof StringValue) {
            ExpressionVisitorAdapterImpl expr = new ExpressionVisitorAdapterImpl();
            right.accept(expr);
            List<Object> val = new ArrayList<>();
            val.add(expr.getValue());
            if (left instanceof ExpressionList<?> list) {
                for (Expression exp : list) {
                    if (exp instanceof Column column) {
                        String columnName = getColumnName(column);
                        conditionMapping.addCondition(columnName, val);
                    }
                }
            }
        }
        matches.setRightExpression(new JdbcParameter());
        if (left != null) {
            left.accept(this, context);
        }
        if (right != null){
            right.accept(this, context);
        }
        return layer;
    }

    @Override
    public <S> QueryLayer visit(BitwiseAnd bitwiseAnd, S context) {
        return null;
    }

    @Override
    public <S> QueryLayer visit(BitwiseOr bitwiseOr, S context) {
        return null;
    }

    @Override
    public <S> QueryLayer visit(BitwiseXor bitwiseXor, S context) {
        return null;
    }

    @Override
    public <S> QueryLayer visit(CastExpression castExpression, S context) {
        return null;
    }

    @Override
    public <S> QueryLayer visit(Modulo modulo, S context) {
        return null;
    }

    @Override
    public <S> QueryLayer visit(AnalyticExpression analyticExpression, S context) {
        return null;
    }

    @Override
    public <S> QueryLayer visit(ExtractExpression extractExpression, S context) {
        return null;
    }

    @Override
    public <S> QueryLayer visit(IntervalExpression intervalExpression, S context) {
        return null;
    }

    @Override
    public <S> QueryLayer visit(OracleHierarchicalExpression hierarchicalExpression, S context) {
        return null;
    }

    @Override
    public <S> QueryLayer visit(RegExpMatchOperator regExpMatchOperator, S context) {
        return null;
    }

    @Override
    public <S> QueryLayer visit(JsonExpression jsonExpression, S context) {
        return null;
    }

    @Override
    public <S> QueryLayer visit(JsonOperator jsonOperator, S context) {
        return null;
    }

    @Override
    public <S> QueryLayer visit(UserVariable userVariable, S context) {
        return null;
    }

    @Override
    public <S> QueryLayer visit(NumericBind numericBind, S context) {
        return null;
    }

    @Override
    public <S> QueryLayer visit(KeepExpression keepExpression, S context) {
        return null;
    }

    @Override
    public <S> QueryLayer visit(MySQLGroupConcat groupConcat, S context) {
        return null;
    }

    @Override
    public <S> QueryLayer visit(ExpressionList<? extends Expression> expressionList, S context) {
        if (expressionList != null) {
            for (Expression expression : expressionList) {
                if (expression != null) {
                    expression.accept(this, context);
                }
            }
        }
        return null;
    }

    @Override
    public <S> QueryLayer visit(RowConstructor<? extends Expression> rowConstructor, S context) {
        return null;
    }

    @Override
    public <S> QueryLayer visit(RowGetExpression rowGetExpression, S context) {
        return null;
    }

    @Override
    public <S> QueryLayer visit(OracleHint hint, S context) {
        return null;
    }

    @Override
    public <S> QueryLayer visit(TimeKeyExpression timeKeyExpression, S context) {
        return null;
    }

    @Override
    public <S> QueryLayer visit(DateTimeLiteralExpression dateTimeLiteralExpression, S context) {
        return null;
    }

    @Override
    public <S> QueryLayer visit(NotExpression notExpression, S context) {
        QueryLayer layer = (QueryLayer) context;
        Expression right=notExpression.getExpression();
        String columnName=right.toString();
            if (right instanceof LongValue || right instanceof DoubleValue || right instanceof StringValue ||
                    right instanceof DateValue ||right instanceof TimeValue || right instanceof TimestampValue || right instanceof NullValue) {

                ExpressionVisitorAdapterImpl expr = new ExpressionVisitorAdapterImpl();
                right.accept(expr);
                List<Object> val = new ArrayList<>();
                val.add(expr.getValue());
                conditionMapping.addCondition(columnName, val);

                notExpression.setExpression(new JdbcParameter());

            } else if (right instanceof Select) {
                right.accept(this, context);

            } else {
                right.accept(this, context);
            }

        return layer;
    }

    @Override
    public <S> QueryLayer visit(NextValExpression nextValExpression, S context) {
        return null;
    }

    @Override
    public <S> QueryLayer visit(CollateExpression collateExpression, S context) {
        return null;
    }

    @Override
    public <S> QueryLayer visit(SimilarToExpression similarToExpression, S context) {
        return null;
    }

    @Override
    public <S> QueryLayer visit(ArrayExpression arrayExpression, S context) {
        return null;
    }

    @Override
    public <S> QueryLayer visit(ArrayConstructor arrayConstructor, S context) {
        return null;
    }

    @Override
    public <S> QueryLayer visit(VariableAssignment variableAssignment, S context) {
        return null;
    }

    @Override
    public <S> QueryLayer visit(XMLSerializeExpr xmlSerializeExpr, S context) {
        return null;
    }

    @Override
    public <S> QueryLayer visit(TimezoneExpression timezoneExpression, S context) {
        return null;
    }

    @Override
    public <S> QueryLayer visit(JsonAggregateFunction jsonAggregateFunction, S context) {
        return null;
    }

    @Override
    public <S> QueryLayer visit(JsonFunction jsonFunction, S context) {
        return null;
    }

    @Override
    public <S> QueryLayer visit(ConnectByRootOperator connectByRootOperator, S context) {
        return null;
    }

    @Override
    public <S> QueryLayer visit(ConnectByPriorOperator connectByPriorOperator, S context) {
        return null;
    }

    @Override
    public <S> QueryLayer visit(OracleNamedFunctionParameter oracleNamedFunctionParameter, S context) {
        return null;
    }

    @Override
    public <S> QueryLayer visit(AllColumns allColumns, S context) {
        QueryLayer layer = (QueryLayer) context;
        if (layer != null) {
            layer.add("Columns", "*");
        }
        return layer;
    }

    @Override
    public <S> QueryLayer visit(FunctionAllColumns functionColumns, S context) {
        return null;
    }

    @Override
    public <S> QueryLayer visit(AllTableColumns allTableColumns, S context) {
        return null;
    }

    @Override
    public <S> QueryLayer visit(AllValue allValue, S context) {
        return null;
    }

    @Override
    public <S> QueryLayer visit(IsDistinctExpression isDistinctExpression, S context) {
        return null;
    }

    @Override
    public <S> QueryLayer visit(GeometryDistance geometryDistance, S context) {
        return null;
    }

    @Override
    public <S> QueryLayer visit(Select select, S context) {

        QueryLayer parentLayer = (QueryLayer) context;
        QueryLayer subLayer = new QueryLayer();

        if (parentLayer != null) {
            parentLayer.subLayers.add(subLayer);
        }
        restrictTablesColumns.clearCurrentTables();
        ExpressionVisitorImpl subExpr = new ExpressionVisitorImpl(restrictTablesColumns,conditionMapping);
        SelectItemVisitorImpl subSelItem = new SelectItemVisitorImpl(subExpr);
        SelectVisitorImpl subSelect = new SelectVisitorImpl(null, subSelItem, subExpr);
        FromItemVisitorImpl subFrom = new FromItemVisitorImpl(subSelect, restrictTablesColumns);
        subSelect = new SelectVisitorImpl(subFrom, subSelItem, subExpr);
        StatementVisitorImpl stmt = new StatementVisitorImpl(subSelect, restrictTablesColumns);
        select.accept(stmt, subLayer);
        return null;
    }

    @Override
    public <S> QueryLayer visit(TranscodingFunction transcodingFunction, S context) {
        return null;
    }

    @Override
    public <S> QueryLayer visit(TrimFunction trimFunction, S context) {
        QueryLayer layer=(QueryLayer)context;
        if(layer!=null){
            layer.add("Trim Function",trimFunction.toString());
        }
        if(trimFunction.getExpression()!=null){
            trimFunction.getExpression().accept(this,context);
        }
        return layer;
    }

    @Override
    public <S> QueryLayer visit(RangeExpression rangeExpression, S context) {
        QueryLayer layer = (QueryLayer) context;
        if (layer != null) {
            layer.add("Conditions", rangeExpression.toString());
        }
        if (rangeExpression.getStartExpression() != null) {
            rangeExpression.getStartExpression().accept(this, context);
        }
        if (rangeExpression.getEndExpression() != null) {
            rangeExpression.getEndExpression().accept(this, context);
        }
        return layer;
    }

    @Override
    public <S> QueryLayer visit(TSQLLeftJoin tsqlLeftJoin, S context) {
        QueryLayer layer = (QueryLayer) context;
        if (layer != null) {
            layer.add("Join_Conditions", tsqlLeftJoin.toString());
        }
        if (tsqlLeftJoin.getLeftExpression() != null) {
            tsqlLeftJoin.getLeftExpression().accept(this, context);
        }
        if (tsqlLeftJoin.getRightExpression() != null) {
            tsqlLeftJoin.getRightExpression().accept(this, context);
        }
        return layer;
    }

    @Override
    public <S> QueryLayer visit(TSQLRightJoin tsqlRightJoin, S context) {
        QueryLayer layer = (QueryLayer) context;
        if (layer != null) {
            layer.add("Join_Conditions", tsqlRightJoin.toString());
        }
        if (tsqlRightJoin.getLeftExpression() != null) {
            tsqlRightJoin.getLeftExpression().accept(this, context);
        }
        if (tsqlRightJoin.getRightExpression() != null) {
            tsqlRightJoin.getRightExpression().accept(this, context);
        }
        return layer;
    }

    @Override
    public <S> QueryLayer visit(StructType structType, S context) {
        QueryLayer layer = (QueryLayer) context;
        if (layer != null) {
            layer.add("Functions", "STRUCT");
            layer.add("Function_Columns", structType.toString());
        }
        if (structType.getArguments() != null) {
            for (SelectItem<?> argument : structType.getArguments()) {
                if (argument.getExpression() != null) {
                    argument.getExpression().accept(this, context);
                }
            }
        }
        return layer;
    }

    @Override
    public <S> QueryLayer visit(LambdaExpression lambdaExpression, S context) {
        QueryLayer layer=(QueryLayer)context;
        if(layer!=null){
            layer.add("Lambda Expression",lambdaExpression.toString());
        }

        if(lambdaExpression.getExpression()!=null){
            lambdaExpression.getExpression().accept(this,context);
        }
        return layer;
    }

    @Override
    public <S> QueryLayer visit(HighExpression highExpression, S context) {
        QueryLayer layer = (QueryLayer) context;
        if (layer != null) {
            layer.add("Conditions", highExpression.toString());
        }
        if (highExpression.getExpression() != null) {
            highExpression.getExpression().accept(this, context);
        }
        return layer;
    }

    @Override
    public <S> QueryLayer visit(LowExpression lowExpression, S context) {
        QueryLayer layer = (QueryLayer) context;
        if (layer != null) {
            layer.add("Conditions", lowExpression.toString());
        }
        if (lowExpression.getExpression() != null) {
            lowExpression.getExpression().accept(this, context);
        }
        return layer;
    }

    @Override
    public <S> QueryLayer visit(Plus plus, S context) {
        QueryLayer layer=(QueryLayer)context;

        if(layer!=null){
            layer.add("Plus",plus.toString());
        }

        if(plus.getLeftExpression()!=null){
            plus.getLeftExpression().accept(this,context);
        }
        if(plus.getRightExpression()!=null){
            plus.getRightExpression().accept(this,context);
        }
        return layer;
    }

    @Override
    public <S> QueryLayer visit(PriorTo priorTo, S context) {

        QueryLayer layer=(QueryLayer)context;
        if(layer!=null){
            layer.add("PriorTo",priorTo.toString());
        }

        if(priorTo.getLeftExpression()!=null){
            priorTo.getLeftExpression().accept(this,context);
        }

        if(priorTo.getRightExpression()!=null){
            priorTo.getRightExpression().accept(this,context);
        }

        return layer;
    }

    @Override
    public <S> QueryLayer visit(Inverse inverse, S context) {
        QueryLayer layer=(QueryLayer)context;

        if(layer!=null){
            layer.add("Inverse",inverse.toString());
        }

        if(inverse.getExpression()!=null){
            inverse.getExpression().accept(this,context);
        }
        return layer;
    }

    @Override
    public <S> QueryLayer visit(CosineSimilarity cosineSimilarity, S context) {
        QueryLayer layer=(QueryLayer)context;

        if(layer!=null){
            layer.add("CosineSimilarity",cosineSimilarity.toString());
        }

        if(cosineSimilarity.getLeftExpression()!=null){
            cosineSimilarity.getLeftExpression().accept(this,context);
        }
        if(cosineSimilarity.getRightExpression()!=null){
            cosineSimilarity.getRightExpression().accept(this,context);
        }
        return layer;
    }

    @Override
    public <S> QueryLayer visit(FromQuery fromQuery, S context) {
        QueryLayer parentLayer=(QueryLayer)context;
        if(parentLayer!=null){
            parentLayer.add("FromQuery",fromQuery.toString());
        }
        return parentLayer;
    }

    @Override
    public <S> QueryLayer visit(Concat concat, S context) {
        QueryLayer parentLayer=(QueryLayer) context;
        if(parentLayer!=null){
          parentLayer.add("Concat",concat.toString());
        }
        if(concat.getLeftExpression()!=null){
            concat.getLeftExpression().accept(this,context);
        }
        if(concat.getRightExpression()!=null){
            concat.getRightExpression().accept(this,context);
        }
        return parentLayer;
    }
}

