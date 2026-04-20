package in.parser.impls;

import in.parser.queryparser.QueryLayer;
import net.sf.jsqlparser.statement.merge.*;


public class MergeOperationVisitorImpl implements MergeOperationVisitor<QueryLayer> {
    @Override
    public  <S> QueryLayer visit(MergeDelete mergeDelete, S context) {
        return (QueryLayer)context;
    }

    @Override
    public <S> QueryLayer visit(MergeUpdate mergeUpdate,S context) {
        return (QueryLayer)context;
    }

    @Override
    public <S> QueryLayer visit(MergeInsert mergeInsert,S context) {
        return (QueryLayer)context;
    }
}
