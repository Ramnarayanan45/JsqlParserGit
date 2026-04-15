package in.parser.genericreference;

import java.util.*;

public class GenericReferenceBuilder {
    GenericReferenceModel model = new GenericReferenceModel();

    public GenericReferenceBuilder table(String id, String tableName, String alias) {
        Map<String, Object> metadata = new LinkedHashMap<>();
        metadata.put("tableName", tableName);
        if (alias != null && !alias.isBlank()) {
            metadata.put("tableAlias", alias);
        }
        model.addNode(new GenericRefNode<>(id, RefNodeType.TABLE, tableName, metadata));
        return this;
    }

    public GenericReferenceBuilder column(String id, String columnName, String tableNodeId, String usage) {
        Map<String, Object> metadata = new LinkedHashMap<>();
        metadata.put("columnName", columnName);
        metadata.put("tableRef", tableNodeId);
        metadata.put("usage", usage);
        model.addNode(new GenericRefNode<>(id, RefNodeType.COLUMN, columnName, metadata));
        model.addEdge(new GenericRefEdge("edge-has-column-" + tableNodeId + "-" + id, tableNodeId, id, RefEdgeType.HAS_COLUMN, "table has column"));
        model.addEdge(new GenericRefEdge("edge-belongs-to-" + id + "-" + tableNodeId, id, tableNodeId, RefEdgeType.BELONGS_TO_TABLE, "column belongs to table"));
        return this;
    }

    public GenericReferenceBuilder join(String joinId,String joinType,String leftTableId,String rightTableId,String leftColumnId,String rightColumnId,String onExpression) {
        Map<String, Object> metadata = new LinkedHashMap<>();
        metadata.put("joinType", joinType);
        metadata.put("onExpression", onExpression);
        model.addNode(new GenericRefNode<>(joinId, RefNodeType.JOIN, joinType, metadata));

        model.addEdge(new GenericRefEdge("edge-join-left-table-" + joinId, joinId, leftTableId, RefEdgeType.JOIN_LEFT_TABLE, "left table"));
        model.addEdge(new GenericRefEdge("edge-join-right-table-" + joinId, joinId, rightTableId, RefEdgeType.JOIN_RIGHT_TABLE, "right table"));
        model.addEdge(new GenericRefEdge("edge-join-left-column-" + joinId, joinId, leftColumnId, RefEdgeType.JOIN_LEFT_COLUMN, "left column"));
        model.addEdge(new GenericRefEdge("edge-join-right-column-" + joinId, joinId, rightColumnId, RefEdgeType.JOIN_RIGHT_COLUMN, "right column"));
        return this;
    }

    public GenericReferenceBuilder link(String linkId, String linkType, String sourceNodeId, String targetNodeId, String expression) {
        Map<String, Object> metadata = new LinkedHashMap<>();
        metadata.put("linkType", linkType);
        metadata.put("expression", expression);
        model.addNode(new GenericRefNode<>(linkId, RefNodeType.LINK, linkType, metadata));
        model.addEdge(new GenericRefEdge("edge-link-source-" + linkId, linkId, sourceNodeId, RefEdgeType.LINK_SOURCE, "source"));
        model.addEdge(new GenericRefEdge("edge-link-target-" + linkId, linkId, targetNodeId, RefEdgeType.LINK_TARGET, "target"));
        return this;
    }

    public GenericReferenceModel build() {
        return model;
    }
}
