package in.parser.impls;

import in.parser.queryparser.QueryLayer;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.select.*;

public class IntoTableVisitorImpl implements IntoTableVisitor<QueryLayer> {

    @Override
    public <S> QueryLayer visit(Table tableName, S context) {
        QueryLayer layer=(QueryLayer)context;
        layer.add("Tables",tableName.getName());
        return layer;
    }
}
