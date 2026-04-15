package in.parser.graphreference;

public enum GraphEdgeType {
    TABLE_HAS_COLUMN,
    COLUMN_BELONGS_TO_TABLE,
    JOIN_LEFT_TABLE,
    JOIN_RIGHT_TABLE,
    JOIN_LEFT_COLUMN,
    JOIN_RIGHT_COLUMN,
    LINK_SOURCE,
    LINK_TARGET,
    RELATED_TO
}
