package io.github.krys.miniprecompiler.resolver;

import io.github.krys.miniprecompiler.MiniPreCompiler;
import io.github.krys.miniprecompiler.api.ComponentResolver;
import io.github.krys.miniprecompiler.component.LiteralPreComponent;
import io.github.krys.miniprecompiler.component.TranslatablePreComponent;
import io.github.krys.miniprecompiler.model.Arguments;
import io.github.krys.miniprecompiler.model.PreComponent;
import io.github.krys.miniprecompiler.model.PreComponentTree;
import net.kyori.adventure.text.format.Style;

public final class TranslatableFallbackResolver implements ComponentResolver {
    @Override
    public PreComponent resolve(String name, Arguments args, PreComponent[] children, Style inheritedStyle, MiniPreCompiler compiler) {
        String key = args.get(0);
        if (key == null) return new LiteralPreComponent("<" + name + ">", inheritedStyle);
        String fallback = args.get(1);

        // FIX: Array tamaño = args.size() - 2 (key + fallback excluidos)
        // Bucle hasta args.size() para no perder el último argumento.
        PreComponentTree[] tArgs = new PreComponentTree[args.size() - 2];
        for (int i = 2; i < args.size(); i++) {
            tArgs[i - 2] = compiler.compile(args.get(i));
        }
        return new TranslatablePreComponent(key, fallback, tArgs, inheritedStyle);
    }

    @Override
    public String[] names() {
        return new String[]{"lang_or", "translate_or", "tr_or"};
    }
}
