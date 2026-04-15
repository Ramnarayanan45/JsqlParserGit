package in.parser.impls;

import in.parser.queryparser.QueryLayer;
import net.sf.jsqlparser.statement.piped.*;

public class PipeOperatorVisitorImpl implements PipeOperatorVisitor<QueryLayer,QueryLayer> {

    @Override
    public QueryLayer visit(AggregatePipeOperator aggregate, QueryLayer context) {
        QueryLayer layer=context;
        return layer;
    }

    @Override
    public QueryLayer visit(AsPipeOperator as, QueryLayer context) {
        QueryLayer layer=context;
        return layer;
    }

    @Override
    public QueryLayer visit(CallPipeOperator call, QueryLayer context) {
        QueryLayer layer=context;
        return layer;
    }

    @Override
    public QueryLayer visit(DropPipeOperator drop, QueryLayer context) {
        QueryLayer layer=context;
        return layer;
    }

    @Override
    public QueryLayer visit(ExtendPipeOperator extend, QueryLayer context) {
        QueryLayer layer=context;
        return layer;
    }

    @Override
    public QueryLayer visit(JoinPipeOperator join, QueryLayer context) {
        QueryLayer layer=context;
        return layer;
    }

    @Override
    public QueryLayer visit(LimitPipeOperator limit, QueryLayer context) {
        QueryLayer layer=context;
        return layer;
    }

    @Override
    public QueryLayer visit(OrderByPipeOperator orderBy, QueryLayer context) {
        QueryLayer layer=context;
        return layer;
    }

    @Override
    public QueryLayer visit(PivotPipeOperator pivot, QueryLayer context) {
        QueryLayer layer=context;
        return layer;
    }

    @Override
    public QueryLayer visit(RenamePipeOperator rename, QueryLayer context) {
        QueryLayer layer=context;
        return layer;
    }

    @Override
    public QueryLayer visit(SelectPipeOperator select, QueryLayer context) {
        QueryLayer layer=context;
        return layer;
    }

    @Override
    public QueryLayer visit(SetPipeOperator set, QueryLayer context) {
        QueryLayer layer=context;
        return layer;
    }

    @Override
    public QueryLayer visit(TableSamplePipeOperator tableSample, QueryLayer context) {
        QueryLayer layer=context;
        return layer;
    }

    @Override
    public QueryLayer visit(SetOperationPipeOperator union, QueryLayer context) {
        QueryLayer layer=context;
        return layer;
    }

    @Override
    public QueryLayer visit(UnPivotPipeOperator unPivot, QueryLayer context) {
        QueryLayer layer=context;
        return layer;
    }

    @Override
    public QueryLayer visit(WherePipeOperator where, QueryLayer context) {
        QueryLayer layer=context;
        return layer;
    }

    @Override
    public QueryLayer visit(WindowPipeOperator window, QueryLayer context) {
        QueryLayer layer=context;
        return layer;
    }
}
