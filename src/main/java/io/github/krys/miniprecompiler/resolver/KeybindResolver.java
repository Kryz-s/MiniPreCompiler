package io.github.krys.miniprecompiler.resolver;

import io.github.krys.miniprecompiler.api.ComponentResolver;
import io.github.krys.miniprecompiler.component.KeybindPreComponent;
import io.github.krys.miniprecompiler.model.Arguments;
import io.github.krys.miniprecompiler.model.PreComponent;
import io.github.krys.miniprecompiler.MiniPreCompiler;
import net.kyori.adventure.text.format.Style;

public final class KeybindResolver implements ComponentResolver {
    @Override
    public PreComponent resolve(String name, Arguments args, PreComponent[] children, Style inheritedStyle, MiniPreCompiler compiler) {
        String keybind = args.get(0, "key.jump");
        return new KeybindPreComponent(keybind, inheritedStyle);
    }

    @Override
    public boolean isSelfClosing() {
        return true;
    }
    @Override
    public String[] names() {
        return new String[]{"key"};
    }
}