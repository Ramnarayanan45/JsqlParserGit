# Reference Model (Standalone)

This folder is intentionally separate from the main parser source.

Classes:
- `TableReference` -> table name and alias
- `ColumnReference` -> column with its table reference
- `JoinReference` -> join type with left/right table + column references
- `ObjectLinkReference` -> generic link between any two objects
- `QueryReferenceModel` -> container for tables, columns, joins, links
- `ReferenceModelExample` -> sample builder usage

Graph package:
- `in.parser.graphreference.GraphNodeType` -> node kinds (`TABLE`, `COLUMN`, `JOIN`, `LINK`)
- `in.parser.graphreference.GraphEdgeType` -> typed directional relationships
- `in.parser.graphreference.GraphNode` -> graph node with attributes
- `in.parser.graphreference.GraphEdge` -> graph edge with type + label
- `in.parser.graphreference.QueryGraph` -> graph store and traversal accessors
- `in.parser.graphreference.QueryGraphBuilder` -> fluent graph builder
- `in.parser.graphreference.QueryGraphExample` -> sample graph for join/link modeling
- `in.parser.graphreference.GraphTraversalHelper` -> common filtered traversal helpers

Generic package:
- `in.parser.genericreference.RefNodeType` -> generic node categories
- `in.parser.genericreference.RefEdgeType` -> generic edge categories
- `in.parser.genericreference.GenericRefNode<T>` -> generic node (typed value + metadata)
- `in.parser.genericreference.GenericRefEdge` -> relationship edge between nodes
- `in.parser.genericreference.GenericReferenceModel` -> node/edge container with traversal helper
- `in.parser.genericreference.GenericReferenceBuilder` -> fluent builder for table/column/join/link
- `in.parser.genericreference.GenericReferenceExample` -> sample compatible model instance
