package in.parser.graphreference;

import java.util.Objects;

public class GraphEdge {
    private final String id;
    private final String fromNodeId;
    private final String toNodeId;
    private final GraphEdgeType type;
    private final String label;

    public GraphEdge(String id, String fromNodeId, String toNodeId, GraphEdgeType type, String label) {
        this.id = id;
        this.fromNodeId = fromNodeId;
        this.toNodeId = toNodeId;
        this.type = type;
        this.label = label;
    }

    public String getId() {
        return id;
    }

    public String getFromNodeId() {
        return fromNodeId;
    }

    public String getToNodeId() {
        return toNodeId;
    }

    public GraphEdgeType getType() {
        return type;
    }

    public String getLabel() {
        return label;
    }

    @Override
    public String toString() {
        return "GraphEdge{" +
                "id='" + id + '\'' +
                ", fromNodeId='" + fromNodeId + '\'' +
                ", toNodeId='" + toNodeId + '\'' +
                ", type=" + type +
                ", label='" + label + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GraphEdge)) return false;
        GraphEdge graphEdge = (GraphEdge) o;
        return Objects.equals(id, graphEdge.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
