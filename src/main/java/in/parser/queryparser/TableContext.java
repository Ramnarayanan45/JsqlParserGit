package in.parser.queryparser;

public class TableContext {
    String tableName;
    String columnName;
    String aliasName;

    public TableContext(String tableName, String columnName, String aliasName){
        this.tableName=tableName;
        this.columnName=columnName;
        this.aliasName=aliasName;
    }

    public String getTableName(){
        return tableName;
    }

    public String getColumnName(){
        return columnName;
    }

    public String getAliasName(){
        return aliasName;
    }

    public void setTableName(String tableName){
        this.tableName=tableName;
    }

    public void setColumnName(String columnName){
        this.columnName=columnName;
    }

    public void setAliasName(String aliasName){
        this.aliasName=aliasName;
    }

}
