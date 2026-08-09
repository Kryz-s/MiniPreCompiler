package io.github.krys.miniprecompiler.resolver;

import io.github.krys.miniprecompiler.MiniPreCompiler;
import io.github.krys.miniprecompiler.api.ComponentResolver;
import io.github.krys.miniprecompiler.component.GradientPreComponent;
import io.github.krys.miniprecompiler.model.Arguments;
import io.github.krys.miniprecompiler.model.PreComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.TextColor;

public final class GradientResolver implements ComponentResolver {
    @Override
    public PreComponent resolve(String name, Arguments args, PreComponent[] children, Style inheritedStyle, MiniPreCompiler compiler) {
        if (args.isEmpty()) {
            return new GradientPreComponent(new TextColor[]{NamedTextColor.WHITE, NamedTextColor.BLACK}, 0.0f, children);
        }
        TextColor[] colors = new TextColor[args.size()];
        for (int i = 0; i < args.size(); i++) {
            String raw = args.get(i);
            TextColor color = TextColor.fromHexString(raw);
            if (color == null) color = NamedTextColor.NAMES.value(raw);
            colors[i] = color != null ? color : NamedTextColor.WHITE;
        }
        return new GradientPreComponent(colors, 0.0f, children);
    }

    @Override
    public String[] names() {
        return new String[]{"gradient"};
    }
}