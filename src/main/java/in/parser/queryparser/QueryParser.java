package in.parser.queryparser;

import in.parser.impls.*;
import in.parser.util.Input;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.Statement;
import java.io.*;
import java.util.*;

public class QueryParser {

    SelectVisitorImpl mv;
    List<String> list = new ArrayList<>();
    RestrictTablesColumns restrictTablesColumns=new RestrictTablesColumns();
    ConditionMapping conditionMapping = new ConditionMapping();
    Statement statement;

    public static void main(String[] args) {
        QueryParser queryParser = new QueryParser();
        queryParser.loadConfig();
        queryParser.queryData();
    }

//    public void restrictColumns(){
//        int choice=getPreference();
//        if(choice==1){
//            getRestrictedOption();
//        }
//        else{
//            queryData();
//        }
//    }
//
//    public void getRestrictedOption(){
//        int choice=Input.getChoice("1 -> Restrict Tables Only\n2 -> Restrict Columns Only\n3 -> Restricted Both Columns and Tables\n  -> ",1,3);
//        if (choice ==1){
//            getRestrictedTables();
//        }
//        else if(choice==2){
//            getRestrictedColumns();
//        }
//        else{
//            getRestrictedTables();
//            getRestrictedColumns();
//        }
//        queryData();
//    }
//
//    public void getRestrictedTables(){
//        while (true){
//            String table=Input.getLine("Enter the Table Name\nNote : Press enter to exit");
//            if(table.isEmpty()){
//                System.out.println("------------------------------------------------------");
//                break;
//            }
//            restrictTablesColumns.setTableName(table);
//        }
//    }
//
//    public void getRestrictedColumns(){
//        while (true){
//            String column=Input.getLine("Enter the Column Name\nNote : Press enter to exit");
//            if(column.isEmpty()){
//                System.out.println("------------------------------------------------------");
//                break;
//            }
//            String table=Input.getLine("Enter the Table Name");
//            TableName tableName=new TableName(table,column,"");
//            restrictTablesColumns.setColumnName(tableName);
//        }
//    }
//    public void controller() {
//        String query = getQuery();
//        Statement smt = getStatement(query);
//        mv = new SelectVisitorImpl(smt);
//    }
//
//    public void getChoices() {
//        int choice = Input.getChoice("Parsing Query \n1 -> Layer by layer\n2 -> Inner query\n  -> ", 1, 2);
//        if (choice == 1){
//            queryData();
//        }
//        else{
//            queryGetter();
//        }
//    }
//    public void queryGetter() {
//        int index = getParts();
//        if (index == 0) {
//            getAllList();
//        }
//        else {
//            System.out.println("\n" + list.get(index - 1));
//        }
//
//        label: while (true) {
//            String choice = getChoice();
//            switch (choice) {
//                case "1":
//                    queryData();
//                    break;
//                case "2":
//                    int in = getParts();
//                    if (in == 0) {
//                        getAllList();
//                    }
//                    else {
//                        System.out.println("\n" + list.get(in - 1));
//                    }
//                    break;
//                case "3":
//                    break label;
//                default:
//                    System.out.println("Please enter correct option");
//                    break;
//            }
//        }
//    }
//
//    public void getAllList() {
//        for (String s : list) {
//            System.out.println(s);
//        }
//    }
//
//    public String getChoice() {
//        return Input.getLine("Select \n1 -> Write Query\n2 -> Get List\n3 -> exit");
//    }
//
//    public int getParts() {
//        return Input.getChoice("Select \n0 -> All List\n1 -> Tables\n2 -> Joins\n3 -> Columns\n4 -> Conditions\n5 -> Functions\n6 -> FunctionColumns" +
//                "\n7 -> GroupBy Columns\n8 -> GroupBy Criteria\n9 -> OrderBy Columns\n10 -> Sub Tables\n11 -> Sub Joins\n12 -> Sub Columns" +
//                "\n13 -> Sub Functions\n14 -> Sub FunctionColumns\n15 -> Having Criteria\n16 -> Aliases\n" +
//                "17 -> Sub Aliases\nEnter Option -> ", 0, list.size());
//    }
//   public int getPreference(){
//        int choice=Input.getChoice("1 -> Restrict Tables/Columns\n2 -> Allow All Tables/Columns\n  -> ",1,2);
//        return choice;
//    }

    public String getQuery() {
        return Input.getLine("Enter the Query");
    }

    public Statement getStatement(String query) {
        try {
            return CCJSqlParserUtil.parse(query);
        }
        catch (JSQLParserException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public void queryData() {

        restrictTablesColumns.clearCurrentTables();
        statement = getStatement(getQuery());
        QueryLayer root = new QueryLayer();
        ExpressionVisitorImpl expr = new ExpressionVisitorImpl(restrictTablesColumns, conditionMapping);
        SelectItemVisitorImpl selItem = new SelectItemVisitorImpl(expr);
        SelectVisitorImpl sel = new SelectVisitorImpl(null, selItem, expr);
        FromItemVisitorImpl from = new FromItemVisitorImpl(sel, restrictTablesColumns);
        sel = new SelectVisitorImpl(from, selItem, expr);
        StatementVisitorImpl stmt = new StatementVisitorImpl(sel, restrictTablesColumns,expr);
        statement.accept(stmt, root);
        if (hasRestriction(root)) {
            System.err.println("Access Denied: Restricted table/column used.");
        }
        else {
            printLayer(root, 1);
            printValues();
        }
    }

    public boolean hasRestriction(QueryLayer layer) {

        for (QueryNode<?> node : layer.getNodes()) {
            if (node.getCategory().equals("RestrictTables") || node.getCategory().equals("RestrictColumns")) {
                return true;
            }
        }
        for (QueryLayer sub : layer.subLayers) {
            if (hasRestriction(sub)) {
                return true;
            }
        }
        return false;
    }

    public void loadConfig() {
        Properties props = new Properties();

        try (InputStream input = getClass().getClassLoader().getResourceAsStream("restriction-config.properties")) {
            if (input == null) {
                System.out.println("Config file not found in resources!");
                return;
            }
            props.load(input);
            String tables = props.getProperty("restricted.tables");
            if (tables != null) {
                for (String table : tables.split(",")) {
                    restrictTablesColumns.setTableName(table.trim());
                }
            }
            String columns = props.getProperty("restricted.columns");
            if (columns != null) {
                for (String col : columns.split(",")) {
                    String[] parts = col.split("\\.");
                    if (parts.length == 2) {
                        restrictTablesColumns.setColumnName(
                                new TableName(parts[0].trim(), parts[1].trim(), "")
                        );
                    }
                }
            }
            String prefixTables=props.getProperty("restricted.prefixTables");
            if(prefixTables!=null){
                for(String prefix:prefixTables.split(",")){
                    restrictTablesColumns.setTablePrefixName(prefix.trim());
                }
            }
        }
        catch (Exception e) {
            System.out.println("Error loading config: " + e.getMessage());
        }
    }

    public void printLayer(QueryLayer layer, int level) {
        System.out.println("\nLevel " + level);
        for (QueryNode<?> node : layer.getNodes()) {
                System.out.println(node.getCategory() + " -> " + node.getName());
        }
        for (QueryLayer sub : layer.subLayers) {
            printLayer(sub, level + 1);
        }
    }

    public void printValues(){
        List<ConditionClass<?>> values=conditionMapping.getConditionsList();
            System.out.print("\nQuery : ");
            System.out.println(statement);
            System.out.println("\nValues : ");
            for (ConditionClass<?> c : values) {
                System.out.println(c.getColumnName() + " -> " + c.getValue());
        }
    }
}