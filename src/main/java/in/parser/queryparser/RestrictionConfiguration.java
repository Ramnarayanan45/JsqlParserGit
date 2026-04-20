package in.parser.queryparser;
import java.util.*;

public class RestrictionConfiguration {

    List<String> prefixTables=new ArrayList<>();
    List<String> tables=new ArrayList<>();
    List<TableContext> columns=new ArrayList<>();
    Map<String, String> aliasToTable = new HashMap<>();
    List<String> currentTables = new ArrayList<>();

    public void addCurrentTable(String table) {
        currentTables.add(table);
    }

    public List<String> getCurrentTables() {
        return currentTables;
    }

    public void clearCurrentTables() {
        currentTables.clear();
    }

    public void addAlias(String alias, String table) {
        aliasToTable.put(alias, table);
    }

    public String resolveTable(String name) {
        return aliasToTable.getOrDefault(name, name);
    }

    public List<String> getTables(){
        return tables;
    }

    public List<TableContext> getColumns(){
        return columns;
    }

    public void setTableName(String table){
        tables.add(table);
    }

    public void setColumnName(TableContext column){
        columns.add(column);
    }

    public void setTablePrefixName(String prefixTable){
        prefixTables.add(prefixTable);
    }

    public List<String> getPrefixTables(){
        return prefixTables;
    }
}
