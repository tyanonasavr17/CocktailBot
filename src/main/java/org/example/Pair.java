package org.example;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.With;

import java.util.Collection;

@With
@Getter
@EqualsAndHashCode
@AllArgsConstructor
public class Pair<F, S> {
    private final F first;
    private final S second;

    public static <F, S> Pair<F, S> of(F first, S second) {
        return new Pair<>(first, second);
    }

    public boolean firstNonNull() {
        return first != null;
    }

    public boolean secondNonNull() {
        return second != null;
    }

    public boolean bothNonNull() {
        return firstNonNull() && secondNonNull();
    }

    public boolean anyNonNull() {
        return firstNonNull() || secondNonNull();
    }

    public boolean isEmpty() {
        return !anyNonNull();
    }

    public boolean firstIsEmpty() {
        return isEmpty(first);
    }

    public boolean secondIsEmpty() {
        return isEmpty(second);
    }

    private static boolean isEmpty(Object o) {
        boolean isEmpty = true;
        if (o instanceof Collection) {
            isEmpty = ((Collection) o).isEmpty();
        } else if (o instanceof String) {
            isEmpty = ((String) o).trim().isEmpty();
        } else if (o instanceof JsonNode) {
            isEmpty = ((JsonNode) o).isEmpty();
        }
        return isEmpty;
    }
}
