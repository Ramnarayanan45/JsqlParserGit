package in.parser.reference;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class QueryReferenceModel {
    private final List<TableReference> tables = new ArrayList<>();
    private final List<ColumnReference> columns = new ArrayList<>();
    private final List<JoinReference> joins = new ArrayList<>();
    private final List<ObjectLinkReference> links = new ArrayList<>();

    public void addTable(TableReference tableReference) {
        if (tableReference != null) {
            tables.add(tableReference);
        }
    }

    public void addColumn(ColumnReference columnReference) {
        if (columnReference != null) {
            columns.add(columnReference);
        }
    }

    public void addJoin(JoinReference joinReference) {
        if (joinReference != null) {
            joins.add(joinReference);
        }
    }

    public void addLink(ObjectLinkReference linkReference) {
        if (linkReference != null) {
            links.add(linkReference);
        }
    }

    public List<TableReference> getTables() {
        return Collections.unmodifiableList(tables);
    }

    public List<ColumnReference> getColumns() {
        return Collections.unmodifiableList(columns);
    }

    public List<JoinReference> getJoins() {
        return Collections.unmodifiableList(joins);
    }

    public List<ObjectLinkReference> getLinks() {
        return Collections.unmodifiableList(links);
    }
}
