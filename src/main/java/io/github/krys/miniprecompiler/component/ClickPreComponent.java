package io.github.krys.miniprecompiler.component;

import io.github.krys.miniprecompiler.model.PreComponent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.pointer.Pointered;

public final class ClickPreComponent extends PreComponent {
    private final ClickEvent event;

    public ClickPreComponent(ClickEvent event, PreComponent[] children) {
        this.event = event;
        this.children = children;
    }

    @Override
    public Component render(Pointered pointered) {
        net.kyori.adventure.text.TextComponent.Builder builder = Component.text()
          .style(computedStyle)
          .clickEvent(event);
        for (PreComponent child : getChildren()) {
            builder.append(child.render(pointered));
        }
        return builder.build();
    }

    @Override
    public PreComponent deepCopy() {
        PreComponent[] copied = new PreComponent[children.length];
        for (int i = 0; i < children.length; i++) {
            copied[i] = children[i].deepCopy();
        }
        return new ClickPreComponent(event, copied);
    }
}