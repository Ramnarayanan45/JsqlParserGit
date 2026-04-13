import net.sf.jsqlparser.statement.StatementVisitor;
import java.lang.reflect.Method;

public class CheckInterface {
    public static void main(String[] args) {
        Method[] methods = StatementVisitor.class.getMethods();
        for (Method m : methods) {
            System.out.println(m.getName() + "(" + m.getParameterTypes()[0].getSimpleName() + ", " + m.getParameterTypes()[1].getSimpleName() + ") -> " + m.getReturnType().getSimpleName());
            break; // Just need one
        }
    }
}
