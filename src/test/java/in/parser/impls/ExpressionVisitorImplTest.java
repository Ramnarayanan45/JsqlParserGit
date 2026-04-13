package in.parser.impls;

import in.parser.QueryLayer;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.select.Select;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class ExpressionVisitorImplTest {

    @Test
    void collectsMetadataInsideCaseWhenExpression() throws Exception {
        QueryLayer root = new QueryLayer();
        Statement statement = CCJSqlParserUtil.parse(
                "SELECT CASE WHEN score > 10 THEN UPPER(name) ELSE 'low' END AS rating FROM student"
        );

        ExpressionVisitorImpl expr = new ExpressionVisitorImpl();
        SelectItemVisitorImpl selectItemVisitor = new SelectItemVisitorImpl(expr);
        SelectVisitorImpl selectVisitor = new SelectVisitorImpl(null, selectItemVisitor, expr);
        FromItemVisitorImpl fromItemVisitor = new FromItemVisitorImpl(selectVisitor);
        selectVisitor = new SelectVisitorImpl(fromItemVisitor, selectItemVisitor, expr);

        ((Select) statement).getPlainSelect().accept(selectVisitor, root);

        assertTrue(root.get("COLUMNS").contains("score"));
        assertTrue(root.get("FUNCTIONS").contains("UPPER"));
        assertTrue(root.get("FUNCTION_COLUMNS").contains("UPPER(name)"));
        assertTrue(root.get("CONDITIONS").contains("10"));
        assertTrue(root.get("CONDITIONS").contains("'low'") || root.get("CONDITIONS").contains("low"));
        assertTrue(root.get("ALIASES").contains("rating"));
        assertTrue(root.get("TABLES").contains("student"));
    }
}
