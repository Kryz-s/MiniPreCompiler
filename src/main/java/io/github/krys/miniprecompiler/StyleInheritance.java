package io.github.krys.miniprecompiler;

import net.kyori.adventure.text.format.Style;

public final class StyleInheritance {
    public static Style compute(Style ownStyle, Style inheritedStyle) {
        if (ownStyle == null || ownStyle.equals(Style.empty())) {
            return inheritedStyle != null ? inheritedStyle : Style.empty();
        }
        if (inheritedStyle == null || inheritedStyle.equals(Style.empty())) {
            return ownStyle;
        }
        return inheritedStyle.merge(ownStyle);
    }

    public static Style computeWithReset(Style ownStyle, Style inheritedStyle) {
        return ownStyle != null ? ownStyle : Style.empty();
    }
}