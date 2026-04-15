package in.parser.graphreference;

import java.util.ArrayList;
import java.util.List;

public final class GraphTraversalHelper {

    private GraphTraversalHelper() {
    }

    public static List<GraphNode> getNodesByType(QueryGraph graph, GraphNodeType type) {
        List<GraphNode> result = new ArrayList<>();
        for (GraphNode node : graph.getAllNodes()) {
            if (node.getType() == type) {
                result.add(node);
            }
        }
        return result;
    }

    public static List<GraphNode> getConnectedTargets(QueryGraph graph, String sourceNodeId, GraphEdgeType edgeType) {
        List<GraphNode> result = new ArrayList<>();
        for (GraphEdge edge : graph.getOutgoingEdges(sourceNodeId)) {
            if (edge.getType() == edgeType) {
                GraphNode target = graph.getNode(edge.getToNodeId());
                if (target != null) {
                    result.add(target);
                }
            }
        }
        return result;
    }
}
