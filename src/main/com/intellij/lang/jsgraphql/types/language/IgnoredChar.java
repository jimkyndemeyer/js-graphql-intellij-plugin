package com.intellij.lang.jsgraphql.types.language;

import com.intellij.lang.jsgraphql.types.PublicApi;

import java.io.Serializable;
import java.util.Objects;

@PublicApi
public class IgnoredChar implements Serializable {

    public enum IgnoredCharKind {
        SPACE, COMMA, TAB, CR, LF, OTHER
    }

    private final String value;
    private final IgnoredCharKind kind;
    private final SourceLocation sourceLocation;


    public IgnoredChar(String value, IgnoredCharKind kind, SourceLocation sourceLocation) {
        this.value = value;
        this.kind = kind;
        this.sourceLocation = sourceLocation;
    }

    public String getValue() {
        return value;
    }

    public IgnoredCharKind getKind() {
        return kind;
    }

    public SourceLocation getSourceLocation() {
        return sourceLocation;
    }

    @Override
    public String toString() {
        return "IgnoredChar{" +
            "value='" + value + '\'' +
            ", kind=" + kind +
            ", sourceLocation=" + sourceLocation +
            '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        IgnoredChar that = (IgnoredChar) o;
        return Objects.equals(value, that.value) &&
            kind == that.kind &&
            Objects.equals(sourceLocation, that.sourceLocation);
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = 31 * result + Objects.hashCode(value);
        result = 31 * result + Objects.hashCode(kind);
        result = 31 * result + Objects.hashCode(sourceLocation);
        return result;
    }
}