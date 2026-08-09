package io.github.krys.miniprecompiler.resolver;

import io.github.krys.miniprecompiler.api.ComponentResolver;
import io.github.krys.miniprecompiler.component.GroupPreComponent;
import io.github.krys.miniprecompiler.component.LiteralPreComponent;
import io.github.krys.miniprecompiler.model.Arguments;
import io.github.krys.miniprecompiler.model.PreComponent;
import io.github.krys.miniprecompiler.MiniPreCompiler;
import net.kyori.adventure.text.format.Style;

public final class ResetResolver implements ComponentResolver {
    @Override
    public PreComponent resolve(String name, Arguments args, PreComponent[] children, Style inheritedStyle, MiniPreCompiler compiler) {
        if (children.length == 0) {
            return new LiteralPreComponent("", Style.empty());
        }
        if (children.length == 1) {
            children[0].setStyle(Style.empty());
            return children[0];
        }
        GroupPreComponent group = new GroupPreComponent(children);
        group.setStyle(Style.empty());
        return group;
    }

    // FIX: <reset> envuelve contenido, NO es self-closing.
    // Debe resetear el estilo de todo lo que contiene.
    @Override
    public boolean isSelfClosing() {
        return false;
    }

    @Override
    public String[] names() {
        return new String[]{"reset"};
    }
}
