/*package in.parser;

import java.util.*;

public class QueryLayer {

    public List<QueryField> fields = new ArrayList<>();
    public List<QueryLayer> subLayers = new ArrayList<>();

    public void add(String fieldName, String value) {

        if (value == null) {
            return;
        }

        QueryField field = findField(fieldName);
        if (field == null) {
            field = new QueryField(fieldName);
            fields.add(field);
        }

        field.add(value);

    }

    public QueryField findField(String name) {
        for (QueryField f : fields) {
            if (f.getName().equals(name)) {
                return f;
            }
        }
        return null;
    }
    public List<QueryField> getFields() {
        return fields;
    }
}*/
package in.parser;

import java.util.*;

public class QueryLayer {

    public List<QueryNode<?>> nodes = new ArrayList<>();
    public List<QueryLayer> subLayers = new ArrayList<>();

    public <T> void add(String category, T value) {
        if (value == null) {
            return;
        }
        nodes.add(new QueryNode<>(category, value));
    }

    public List<QueryNode<?>> getNodes() {
        return nodes;
    }

}