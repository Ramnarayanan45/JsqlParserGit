package in.parser.genericreference;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class GenericReferenceModel {
    private final Map<String, GenericRefNode<?>> nodes = new LinkedHashMap<>();
    private final List<GenericRefEdge> edges = new ArrayList<>();

    public void addNode(GenericRefNode<?> node) {
        if (node != null) {
            nodes.put(node.getId(), node);
        }
    }

    public void addEdge(GenericRefEdge edge) {
        if (edge != null) {
            edges.add(edge);
        }
    }

    public GenericRefNode<?> getNode(String nodeId) {
        return nodes.get(nodeId);
    }

    public Collection<GenericRefNode<?>> getNodes() {
        return Collections.unmodifiableCollection(nodes.values());
    }

    public List<GenericRefEdge> getEdges() {
        return Collections.unmodifiableList(edges);
    }

    public List<GenericRefEdge> getOutgoingEdges(String nodeId) {
        List<GenericRefEdge> result = new ArrayList<>();
        for (GenericRefEdge edge : edges) {
            if (edge.getFromNodeId().equals(nodeId)) {
                result.add(edge);
            }
        }
        return result;
    }
}
