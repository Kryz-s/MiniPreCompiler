package io.github.krys.miniprecompiler.model;

import io.github.krys.miniprecompiler.api.PreComponentVisitor;
import io.github.krys.miniprecompiler.component.LiteralPreComponent;
import net.kyori.adventure.pointer.Pointered;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.Style;

public record PreComponentTree(PreComponent root) {
    public static final PreComponentTree EMPTY = new PreComponentTree(new LiteralPreComponent("", Style.empty()));

    @Override
    public PreComponent root() {
        return root;
    }

    public Component render(Pointered pointered) {
        return root.render(pointered);
    }

    public Component render(Pointered pointered, PreComponent extra) {
        Component base = root.render(pointered);
        Component appended = extra.render(pointered);
        return Component.text().append(base).append(appended).build();
    }

    public Component render(Pointered pointered, PreComponent[] extras) {
        TextComponent.Builder builder = Component.text().append(root.render(pointered));
        for (PreComponent extra : extras) {
            builder.append(extra.render(pointered));
        }
        return builder.build();
    }

    public PreComponentTree copy() {
        return new PreComponentTree(root.deepCopy());
    }

    public void visit(PreComponentVisitor visitor) {
        visitDepthFirst(root, visitor);
    }

    private void visitDepthFirst(PreComponent node, PreComponentVisitor visitor) {
        visitor.visit(node);
        PreComponent[] childArray = node.getChildren();
        for (PreComponent child : childArray) {
            visitDepthFirst(child, visitor);
        }
    }
}