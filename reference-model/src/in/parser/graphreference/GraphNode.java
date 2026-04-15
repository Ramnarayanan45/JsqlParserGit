package in.parser.graphreference;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class GraphNode {
    private final String id;
    private final GraphNodeType type;
    private final Map<String, String> attributes = new HashMap<>();

    public GraphNode(String id, GraphNodeType type) {
        this.id = id;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public GraphNodeType getType() {
        return type;
    }

    public void setAttribute(String key, String value) {
        if (key != null && value != null) {
            attributes.put(key, value);
        }
    }

    public String getAttribute(String key) {
        return attributes.get(key);
    }

    public Map<String, String> getAttributes() {
        return Collections.unmodifiableMap(attributes);
    }

    @Override
    public String toString() {
        return "GraphNode{" +
                "id='" + id + '\'' +
                ", type=" + type +
                ", attributes=" + attributes +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GraphNode)) return false;
        GraphNode graphNode = (GraphNode) o;
        return Objects.equals(id, graphNode.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
