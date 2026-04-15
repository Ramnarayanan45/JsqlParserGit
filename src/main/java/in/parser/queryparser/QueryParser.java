package in.parser.queryparser;

import in.parser.impls.*;
import in.parser.util.Input;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.Statement;
import java.util.*;

public class QueryParser {

    SelectVisitorImpl mv;
    List<String> list = new ArrayList<>();

    public static void main(String[] args) {
        QueryParser queryParser = new QueryParser();
        queryParser.queryData();
    }

    public void getChoices() {
        int choice = Input.getChoice("Parsing Query \n1 -> Layer by layer\n2 -> Inner query", 1, 2);
        if (choice == 1){
            queryData();
        }
        else{
            queryGetter();
        }
    }

    public void queryGetter() {
        int index = getParts();
        if (index == 0) {
            getAllList();
        }
        else {
            System.out.println("\n" + list.get(index - 1));
        }

        label:
        while (true) {
            String choice = getChoice();
            switch (choice) {
                case "1":
                    queryData();
                    break;
                case "2":
                    int in = getParts();
                    if (in == 0) {
                        getAllList();
                    }
                    else {
                        System.out.println("\n" + list.get(in - 1));
                    }
                    break;
                case "3":
                    break label;
                default:
                    System.out.println("Please enter correct option");
                    break;
            }
        }
    }

    public void getAllList() {
        for (String s : list) {
            System.out.println(s);
        }
    }

    public String getChoice() {
        return Input.getLine("Select \n1 -> Write Query\n2 -> Get List\n3 -> exit");
    }

    public int getParts() {
        return Input.getChoice("Select \n0 -> All List\n1 -> Tables\n2 -> Joins\n3 -> Columns\n4 -> Conditions\n5 -> Functions\n6 -> FunctionColumns" +
                "\n7 -> GroupBy Columns\n8 -> GroupBy Criteria\n9 -> OrderBy Columns\n10 -> Sub Tables\n11 -> Sub Joins\n12 -> Sub Columns" +
                "\n13 -> Sub Functions\n14 -> Sub FunctionColumns\n15 -> Having Criteria\n16 -> Aliases\n" +
                "17 -> Sub Aliases\nEnter Option -> ", 0, list.size());
    }

    public void controller() {
        String query = getInput();
        Statement smt = getStatement(query);
        mv = new SelectVisitorImpl(smt);
    }

    public String getInput() {
        return Input.getLine("Enter the Select Query");
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

        Statement statement = getStatement(getInput());
        QueryLayer root = new QueryLayer();
        ExpressionVisitorImpl expr = new ExpressionVisitorImpl();
        SelectItemVisitorImpl selItem = new SelectItemVisitorImpl(expr);
        SelectVisitorImpl sel = new SelectVisitorImpl(null, selItem, expr);
        FromItemVisitorImpl from = new FromItemVisitorImpl(sel);
        sel = new SelectVisitorImpl(from, selItem, expr);
        StatementVisitorImpl stmt = new StatementVisitorImpl(sel);
        statement.accept(stmt, root);
        printLayer(root, 1);
    }

    public void printLayer(QueryLayer layer, int level) {
        System.out.println("\nLEVEL " + level);

        for (QueryNode<?> node : layer.getNodes()) {
            System.out.println(node.getCategory() + " -> " + node.getName());
        }

        for (QueryLayer sub : layer.subLayers) {
            printLayer(sub, level + 1);
        }
    }
}
//      System.out.println("\nLevel " + level);
//        for (QueryField field : layer.getFields()) {
//         System.out.println(field.getName() + " -> " + field.getValues());
//     }
//      for (QueryLayer sub : layer.subLayers) {
//      printLayer(sub, level + 1);
//     }