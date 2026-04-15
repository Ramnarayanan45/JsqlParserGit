package in.parser.genericreference;

import java.util.Objects;

public class GenericRefEdge {
    String id;
    String fromNodeId;
    String toNodeId;
    RefEdgeType type;
    String label;

    public GenericRefEdge(String id, String fromNodeId, String toNodeId, RefEdgeType type, String label) {
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

    public RefEdgeType getType() {
        return type;
    }

    public String getLabel() {
        return label;
    }

    @Override
    public String toString() {
        return "GenericRefEdge{" +
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
        if (!(o instanceof GenericRefEdge that)) return false;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
