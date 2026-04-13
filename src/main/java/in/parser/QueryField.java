package in.parser;
import java.util.*;

public class QueryField {

    String name;
    List<String> values = new ArrayList<>();

    public QueryField(String name) {
        this.name = name;
    }

    public void add(String value) {
        if (value != null) {
            values.add(value);
        }
    }

    public String getName() {
        return name;
    }

    public List<String> getValues() {
        return values;
    }

}
