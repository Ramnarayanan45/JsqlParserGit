package in.parser.impls;

import in.parser.queryparser.QueryLayer;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.Statement;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class ReproductionTest {

    @Test
    public void testInsertSelect() throws JSQLParserException {
        String query = "INSERT INTO target (a, b) SELECT c1, c2 FROM source;";
        Statement statement = CCJSqlParserUtil.parse(query);

        QueryLayer root = new QueryLayer();
        ExpressionVisitorImpl expr = new ExpressionVisitorImpl();
        SelectItemVisitorImpl selItem = new SelectItemVisitorImpl(expr);
        SelectVisitorImpl sel = new SelectVisitorImpl(null, selItem, expr);
        FromItemVisitorImpl from = new FromItemVisitorImpl(sel);
        sel = new SelectVisitorImpl(from, selItem, expr);
        StatementVisitorImpl stmt = new StatementVisitorImpl(sel);

        try {
            statement.accept(stmt, root);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
}
