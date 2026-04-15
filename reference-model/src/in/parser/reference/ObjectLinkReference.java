package in.parser.reference;

import java.util.Objects;

public class ObjectLinkReference {
    private final String sourceObjectId;
    private final String targetObjectId;
    private final String linkType;
    private final String details;

    public ObjectLinkReference(String sourceObjectId, String targetObjectId, String linkType, String details) {
        this.sourceObjectId = sourceObjectId;
        this.targetObjectId = targetObjectId;
        this.linkType = linkType;
        this.details = details;
    }

    public String getSourceObjectId() {
        return sourceObjectId;
    }

    public String getTargetObjectId() {
        return targetObjectId;
    }

    public String getLinkType() {
        return linkType;
    }

    public String getDetails() {
        return details;
    }

    @Override
    public String toString() {
        return "ObjectLinkReference{" +
                "sourceObjectId='" + sourceObjectId + '\'' +
                ", targetObjectId='" + targetObjectId + '\'' +
                ", linkType='" + linkType + '\'' +
                ", details='" + details + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ObjectLinkReference)) return false;
        ObjectLinkReference that = (ObjectLinkReference) o;
        return Objects.equals(sourceObjectId, that.sourceObjectId) &&
                Objects.equals(targetObjectId, that.targetObjectId) &&
                Objects.equals(linkType, that.linkType) &&
                Objects.equals(details, that.details);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sourceObjectId, targetObjectId, linkType, details);
    }
}
