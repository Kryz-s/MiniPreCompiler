package io.github.krys.miniprecompiler.component;

import io.github.krys.miniprecompiler.model.PreComponent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.pointer.Pointered;

public final class KeybindPreComponent extends PreComponent {
    private final String keybind;

    public KeybindPreComponent(String keybind, Style style) {
        this.keybind = keybind;
        this.computedStyle = style;
    }

    @Override
    public Component render(Pointered pointered) {
        return Component.keybind(keybind, computedStyle);
    }

    @Override
    public PreComponent deepCopy() {
        return new KeybindPreComponent(keybind, computedStyle);
    }
}