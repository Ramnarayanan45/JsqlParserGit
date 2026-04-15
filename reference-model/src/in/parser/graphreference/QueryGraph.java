package in.parser.graphreference;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class QueryGraph {
    private final Map<String, GraphNode> nodesById = new LinkedHashMap<>();
    private final List<GraphEdge> edges = new ArrayList<>();

    public void addNode(GraphNode node) {
        if (node != null) {
            nodesById.put(node.getId(), node);
        }
    }

    public void addEdge(GraphEdge edge) {
        if (edge != null) {
            edges.add(edge);
        }
    }

    public GraphNode getNode(String id) {
        return nodesById.get(id);
    }

    public Collection<GraphNode> getAllNodes() {
        return Collections.unmodifiableCollection(nodesById.values());
    }

    public List<GraphEdge> getAllEdges() {
        return Collections.unmodifiableList(edges);
    }

    public List<GraphEdge> getOutgoingEdges(String nodeId) {
        List<GraphEdge> result = new ArrayList<>();
        for (GraphEdge edge : edges) {
            if (edge.getFromNodeId().equals(nodeId)) {
                result.add(edge);
            }
        }
        return result;
    }

    public List<GraphEdge> getIncomingEdges(String nodeId) {
        List<GraphEdge> result = new ArrayList<>();
        for (GraphEdge edge : edges) {
            if (edge.getToNodeId().equals(nodeId)) {
                result.add(edge);
            }
        }
        return result;
    }

    public List<GraphNode> getNeighbors(String nodeId) {
        List<GraphNode> result = new ArrayList<>();
        for (GraphEdge edge : edges) {
            if (edge.getFromNodeId().equals(nodeId)) {
                GraphNode neighbor = getNode(edge.getToNodeId());
                if (neighbor != null) {
                    result.add(neighbor);
                }
            }
        }
        return result;
    }
}
