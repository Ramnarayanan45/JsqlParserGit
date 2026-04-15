package in.parser.reference;

import java.util.Objects;

public class TableReference {
    private final String tableName;
    private final String tableAlias;

    public TableReference(String tableName, String tableAlias) {
        this.tableName = tableName;
        this.tableAlias = tableAlias;
    }

    public String getTableName() {
        return tableName;
    }

    public String getTableAlias() {
        return tableAlias;
    }

    public String getResolvedName() {
        return (tableAlias != null && !tableAlias.isBlank()) ? tableAlias : tableName;
    }

    @Override
    public String toString() {
        return "TableReference{" +
                "tableName='" + tableName + '\'' +
                ", tableAlias='" + tableAlias + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TableReference)) return false;
        TableReference that = (TableReference) o;
        return Objects.equals(tableName, that.tableName) && Objects.equals(tableAlias, that.tableAlias);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tableName, tableAlias);
    }
}
