package in.parser.reference;

import java.util.Objects;

public class ColumnReference {
    private final String columnName;
    private final TableReference table;

    public ColumnReference(String columnName, TableReference table) {
        this.columnName = columnName;
        this.table = table;
    }

    public String getColumnName() {
        return columnName;
    }

    public TableReference getTable() {
        return table;
    }

    public String getFullyQualifiedName() {
        if (table == null) {
            return columnName;
        }
        return table.getResolvedName() + "." + columnName;
    }

    @Override
    public String toString() {
        return "ColumnReference{" +
                "columnName='" + columnName + '\'' +
                ", table=" + table +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ColumnReference)) return false;
        ColumnReference that = (ColumnReference) o;
        return Objects.equals(columnName, that.columnName) && Objects.equals(table, that.table);
    }

    @Override
    public int hashCode() {
        return Objects.hash(columnName, table);
    }
}
