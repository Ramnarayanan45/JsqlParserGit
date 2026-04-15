package in.parser.impls;

import in.parser.queryparser.QueryLayer;
import net.sf.jsqlparser.statement.select.*;

public class PivotVisitorImpl implements PivotVisitor<QueryLayer> {
    @Override
    public <S> QueryLayer visit(Pivot pivot, S context) {
        QueryLayer layer=(QueryLayer)context;
        layer.add("Pivot on columns",pivot.getForColumns());
        return layer;
    }

    @Override
    public <S> QueryLayer visit(PivotXml pivotXml, S context) {
        QueryLayer layer=(QueryLayer)context;
        layer.add("Pivot Select",pivotXml.getInSelect());
        return layer;
    }

    @Override
    public <S> QueryLayer visit(UnPivot unpivot, S context) {
        QueryLayer layer=(QueryLayer)context;
        layer.add("UnPivot",unpivot.getUnPivotClause());
        return layer;
    }
}
