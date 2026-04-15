package in.parser.genericreference;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public class GenericRefNode<T> {
    private final String id;
    private final RefNodeType type;
    private final T value;
    private final Map<String, Object> metadata;

    public GenericRefNode(String id, RefNodeType type, T value) {
        this(id, type, value, null);
    }

    public GenericRefNode(String id, RefNodeType type, T value, Map<String, Object> metadata) {
        this.id = id;
        this.type = type;
        this.value = value;
        if (metadata == null || metadata.isEmpty()) {
            this.metadata = Collections.emptyMap();
        } else {
            this.metadata = Collections.unmodifiableMap(new LinkedHashMap<>(metadata));
        }
    }

    public String getId() {
        return id;
    }

    public RefNodeType getType() {
        return type;
    }

    public T getValue() {
        return value;
    }

    public Map<String, Object> getMetadata() {
        return metadata;
    }

    @Override
    public String toString() {
        return "GenericRefNode{" +
                "id='" + id + '\'' +
                ", type=" + type +
                ", value=" + value +
                ", metadata=" + metadata +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GenericRefNode<?> that)) return false;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
