package in.parser.queryparser;


public class ConditionMapping<T> {
    String tableName;
    String columnName;
    T value;

    public ConditionMapping(String columnName, T value){
        this.columnName=columnName;
        this.value=value;
    }

    public String getTableName(){
        return tableName;
    }

    public void setTableName(String tableName){
        this.tableName=tableName;
    }

    public String getColumnName(){
        return columnName;
    }

    public void setColumnName(String columnName){
        this.columnName=columnName;
    }

    public T getValue(){
        return value;
    }

    public void setValue(T value){
        this.value=value;
    }

}
