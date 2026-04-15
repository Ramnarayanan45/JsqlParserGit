package in.parser.graphreference;

public class QueryGraphBuilder {

    private final QueryGraph graph = new QueryGraph();

    public QueryGraphBuilder table(String nodeId, String tableName, String alias) {
        GraphNode table = new GraphNode(nodeId, GraphNodeType.TABLE);
        table.setAttribute("tableName", tableName);
        table.setAttribute("alias", alias);
        graph.addNode(table);
        return this;
    }

    public QueryGraphBuilder column(String nodeId, String columnName, String tableNodeId) {
        GraphNode column = new GraphNode(nodeId, GraphNodeType.COLUMN);
        column.setAttribute("columnName", columnName);
        column.setAttribute("tableRef", tableNodeId);
        graph.addNode(column);
        graph.addEdge(new GraphEdge(
                "edge-table-has-column-" + tableNodeId + "-" + nodeId,
                tableNodeId,
                nodeId,
                GraphEdgeType.TABLE_HAS_COLUMN,
                "table has column"
        ));
        graph.addEdge(new GraphEdge(
                "edge-column-belongs-to-table-" + nodeId + "-" + tableNodeId,
                nodeId,
                tableNodeId,
                GraphEdgeType.COLUMN_BELONGS_TO_TABLE,
                "column belongs to table"
        ));
        return this;
    }

    public QueryGraphBuilder join(
            String joinNodeId,
            String joinType,
            String leftTableNodeId,
            String rightTableNodeId,
            String leftColumnNodeId,
            String rightColumnNodeId
    ) {
        GraphNode join = new GraphNode(joinNodeId, GraphNodeType.JOIN);
        join.setAttribute("joinType", joinType);
        graph.addNode(join);

        graph.addEdge(new GraphEdge("edge-join-left-table-" + joinNodeId, joinNodeId, leftTableNodeId, GraphEdgeType.JOIN_LEFT_TABLE, "left table"));
        graph.addEdge(new GraphEdge("edge-join-right-table-" + joinNodeId, joinNodeId, rightTableNodeId, GraphEdgeType.JOIN_RIGHT_TABLE, "right table"));
        graph.addEdge(new GraphEdge("edge-join-left-column-" + joinNodeId, joinNodeId, leftColumnNodeId, GraphEdgeType.JOIN_LEFT_COLUMN, "left column"));
        graph.addEdge(new GraphEdge("edge-join-right-column-" + joinNodeId, joinNodeId, rightColumnNodeId, GraphEdgeType.JOIN_RIGHT_COLUMN, "right column"));
        return this;
    }

    public QueryGraphBuilder link(String linkNodeId, String linkType, String sourceNodeId, String targetNodeId, String expression) {
        GraphNode link = new GraphNode(linkNodeId, GraphNodeType.LINK);
        link.setAttribute("linkType", linkType);
        link.setAttribute("expression", expression);
        graph.addNode(link);

        graph.addEdge(new GraphEdge("edge-link-source-" + linkNodeId, linkNodeId, sourceNodeId, GraphEdgeType.LINK_SOURCE, "source"));
        graph.addEdge(new GraphEdge("edge-link-target-" + linkNodeId, linkNodeId, targetNodeId, GraphEdgeType.LINK_TARGET, "target"));
        return this;
    }

    public QueryGraph build() {
        return graph;
    }
}
