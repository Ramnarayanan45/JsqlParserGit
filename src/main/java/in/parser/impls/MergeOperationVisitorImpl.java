package in.parser.impls;

import in.parser.queryparser.QueryLayer;
import net.sf.jsqlparser.statement.merge.*;


public class MergeOperationVisitorImpl implements MergeOperationVisitor<QueryLayer> {
    @Override
    public  <S> QueryLayer visit(MergeDelete mergeDelete, S context) {
        QueryLayer layer=(QueryLayer)context;
        return layer;
    }

    @Override
    public <S> QueryLayer visit(MergeUpdate mergeUpdate,S context) {
        QueryLayer layer=(QueryLayer)context;
        return layer;
    }

    @Override
    public <S> QueryLayer visit(MergeInsert mergeInsert,S context) {
        QueryLayer layer=(QueryLayer)context;
        return layer;
    }
}
