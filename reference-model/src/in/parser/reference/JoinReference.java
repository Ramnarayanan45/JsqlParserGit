package in.parser.reference;

import java.util.Objects;

public class JoinReference {
    private final String joinType;
    private final TableReference leftTable;
    private final ColumnReference leftColumn;
    private final TableReference rightTable;
    private final ColumnReference rightColumn;

    public JoinReference(
            String joinType,
            TableReference leftTable,
            ColumnReference leftColumn,
            TableReference rightTable,
            ColumnReference rightColumn
    ) {
        this.joinType = joinType;
        this.leftTable = leftTable;
        this.leftColumn = leftColumn;
        this.rightTable = rightTable;
        this.rightColumn = rightColumn;
    }

    public String getJoinType() {
        return joinType;
    }

    public TableReference getLeftTable() {
        return leftTable;
    }

    public ColumnReference getLeftColumn() {
        return leftColumn;
    }

    public TableReference getRightTable() {
        return rightTable;
    }

    public ColumnReference getRightColumn() {
        return rightColumn;
    }

    @Override
    public String toString() {
        return "JoinReference{" +
                "joinType='" + joinType + '\'' +
                ", leftTable=" + leftTable +
                ", leftColumn=" + leftColumn +
                ", rightTable=" + rightTable +
                ", rightColumn=" + rightColumn +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof JoinReference)) return false;
        JoinReference that = (JoinReference) o;
        return Objects.equals(joinType, that.joinType) &&
                Objects.equals(leftTable, that.leftTable) &&
                Objects.equals(leftColumn, that.leftColumn) &&
                Objects.equals(rightTable, that.rightTable) &&
                Objects.equals(rightColumn, that.rightColumn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(joinType, leftTable, leftColumn, rightTable, rightColumn);
    }
}
