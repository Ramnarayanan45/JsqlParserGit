package in.parser.queryparser;

import in.parser.exceptions.QueryExceptionHandler;
import in.parser.exceptions.QueryParsingException;
import in.parser.impls.*;
import in.parser.util.Input;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.Statement;

import java.util.*;

public class QueryParser {

    RestrictionConfiguration restrictionConfiguration =new RestrictionConfiguration();
    ParamGenerator paramGenerator = new ParamGenerator();
    Statement statement;

    public static void main(String[] args) {
        QueryParser queryParser = new QueryParser();
        ConfigLoader configLoader=new ConfigLoader(queryParser.restrictionConfiguration);
        configLoader.loadConfig();
        queryParser.queryData();
    }

    public String getQuery() {
        return Input.getLine("Enter the Query");
    }

    public Statement getStatement(String query) {
        try {
            return CCJSqlParserUtil.parse(query);
        }
        catch (JSQLParserException e) {
            throw new QueryParsingException("Failed to parse the SQL query. Syntax might be invalid.", e);
        }
    }

    public void queryData() {

        restrictionConfiguration.clearCurrentTables();
        try {
            statement = getStatement(getQuery());
            QueryLayer root = new QueryLayer();
            ExpressionVisitorImpl expr = new ExpressionVisitorImpl(restrictionConfiguration, paramGenerator);
            SelectItemVisitorImpl selItem = new SelectItemVisitorImpl(expr);
            SelectFromVisitorImpl sel = new SelectFromVisitorImpl(selItem, restrictionConfiguration, expr, paramGenerator);
            StatementVisitorImpl stmt = new StatementVisitorImpl(sel, restrictionConfiguration, expr);
            statement.accept(stmt, root);
            if (hasRestriction(root)) {
                System.err.println("Access Denied: Restricted table/column used.");
            } else {
                printLayer(root, 1);
                printValues();
            }
        }
        catch(Exception e){
            QueryExceptionHandler.handle(e);
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
        List<ConditionMapping<?>> values= paramGenerator.getParamList();
            System.out.print("\nQuery : ");
            System.out.println(statement);
            System.out.println("\nValues : ");
            for (ConditionMapping<?> c : values) {
                System.out.println(c.getColumnName() + " -> " + c.getValue());
        }
    }
}