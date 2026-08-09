package io.github.krys.miniprecompiler.api;

import io.github.krys.miniprecompiler.model.PreComponent;

public interface PreComponentVisitor {
    void visit(PreComponent component);
}