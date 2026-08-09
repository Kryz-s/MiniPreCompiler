package io.github.krys.miniprecompiler.component;

import io.github.krys.miniprecompiler.model.PreComponent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.pointer.Pointered;

import java.util.function.Function;

public final class HoverPreComponent extends PreComponent {
  private final Function<Pointered, HoverEvent<?>> eventFunction;

  public HoverPreComponent(Function<Pointered, HoverEvent<?>> eventFunction, PreComponent[] children) {
    this.eventFunction = eventFunction;
    this.children = children;
  }

  @Override
  public Component render(Pointered pointered) {
    TextComponent.Builder builder = Component.text()
      .style(computedStyle)
      .hoverEvent(eventFunction.apply(pointered));
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
    return new HoverPreComponent(eventFunction, copied);
  }
}