package in.parser.genericreference;

public enum RefEdgeType {
    HAS_COLUMN,
    BELONGS_TO_TABLE,
    JOIN_LEFT_TABLE,
    JOIN_RIGHT_TABLE,
    JOIN_LEFT_COLUMN,
    JOIN_RIGHT_COLUMN,
    LINK_SOURCE,
    LINK_TARGET,
    REFERENCES,
    DERIVED_FROM
}
