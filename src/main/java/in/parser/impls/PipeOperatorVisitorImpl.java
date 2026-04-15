package in.parser.impls;

import in.parser.queryparser.QueryLayer;
import net.sf.jsqlparser.statement.piped.*;

public class PipeOperatorVisitorImpl implements PipeOperatorVisitor<QueryLayer,QueryLayer> {

    @Override
    public QueryLayer visit(AggregatePipeOperator aggregate, QueryLayer context) {
        context.add("Aggregate Columns",aggregate.getSelectItems());
        return context;
    }

    @Override
    public QueryLayer visit(AsPipeOperator as, QueryLayer context) {
        context.add("PipeOperator",as.toString());
        return context;
    }

    @Override
    public QueryLayer visit(CallPipeOperator call, QueryLayer context) {
        context.add("Call Pipe Ope",call.getTableFunction());
        return context;
    }

    @Override
    public QueryLayer visit(DropPipeOperator drop, QueryLayer context) {
        context.add("PipeDrop",drop.toString());
        return context;
    }

    @Override
    public QueryLayer visit(ExtendPipeOperator extend, QueryLayer context) {
        context.add("Extend Pipe",extend.toString());
        return context;
    }

    @Override
    public QueryLayer visit(JoinPipeOperator join, QueryLayer context) {
        context.add("Pivot Join",join.getJoin());
        return context;
    }

    @Override
    public QueryLayer visit(LimitPipeOperator limit, QueryLayer context) {
        context.add("Pivot Limit",limit.getLimitExpression());
        return context;
    }

    @Override
    public QueryLayer visit(OrderByPipeOperator orderBy, QueryLayer context) {
        context.add("Pivot Order",orderBy.getOrderByElements());
        return context;
    }

    @Override
    public QueryLayer visit(PivotPipeOperator pivot, QueryLayer context) {
        context.add("Pivot Columns",pivot.getPivotColumns());
        return context;
    }

    @Override
    public QueryLayer visit(RenamePipeOperator rename, QueryLayer context) {
        context.add("Renamed Select Items",rename.getSelectItems());
        return context;
    }

    @Override
    public QueryLayer visit(SelectPipeOperator select, QueryLayer context) {
        context.add("Select Items",select.getSelectItems());
        return context;
    }

    @Override
    public QueryLayer visit(SetPipeOperator set, QueryLayer context) {
        context.add("SetPipeOpe",set.getUpdateSets());
        return context;
    }

    @Override
    public QueryLayer visit(TableSamplePipeOperator tableSample, QueryLayer context) {
        context.add("Table Sample",tableSample);
        return context;
    }

    @Override
    public QueryLayer visit(SetOperationPipeOperator union, QueryLayer context) {
        context.add("Union",union.toString());
        return context;
    }

    @Override
    public QueryLayer visit(UnPivotPipeOperator unPivot, QueryLayer context) {
        context.add("Pivot Pipe",unPivot);
        return context;
    }

    @Override
    public QueryLayer visit(WherePipeOperator where, QueryLayer context) {
        context.add("Pipe Condition",where.getExpression());
        return context;
    }

    @Override
    public QueryLayer visit(WindowPipeOperator window, QueryLayer context) {
        context.add("Pipe Window",window.toString());
        return context;
    }
}
