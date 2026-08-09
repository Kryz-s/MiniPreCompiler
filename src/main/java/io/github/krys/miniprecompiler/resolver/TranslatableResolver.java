package io.github.krys.miniprecompiler.resolver;

import io.github.krys.miniprecompiler.MiniPreCompiler;
import io.github.krys.miniprecompiler.api.ComponentResolver;
import io.github.krys.miniprecompiler.component.LiteralPreComponent;
import io.github.krys.miniprecompiler.component.TranslatablePreComponent;
import io.github.krys.miniprecompiler.model.Arguments;
import io.github.krys.miniprecompiler.model.PreComponent;
import io.github.krys.miniprecompiler.model.PreComponentTree;
import net.kyori.adventure.text.format.Style;

public final class TranslatableResolver implements ComponentResolver {
    @Override
    public PreComponent resolve(String name, Arguments args, PreComponent[] children, Style inheritedStyle, MiniPreCompiler compiler) {
        String key = args.get(0);
        if (key == null) return new LiteralPreComponent("<" + name + ">", inheritedStyle);

        // FIX: El array debe tener tamaño args.size() - 1 (todos menos la key)
        // y el bucle debe iterar hasta args.size() (inclusive del último índice).
        PreComponentTree[] tArgs = new PreComponentTree[args.size() - 1];
        for (int i = 1; i < args.size(); i++) {
            tArgs[i - 1] = compiler.compile(args.get(i));
        }
        return new TranslatablePreComponent(key, tArgs, inheritedStyle);
    }

    @Override
    public String[] names() {
        return new String[]{"lang", "translate", "tr"};
    }
}
