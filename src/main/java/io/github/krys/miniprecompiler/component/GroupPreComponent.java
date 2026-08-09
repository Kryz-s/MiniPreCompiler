package io.github.krys.miniprecompiler.component;

import io.github.krys.miniprecompiler.model.PreComponent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.pointer.Pointered;

public final class GroupPreComponent extends PreComponent {

  public GroupPreComponent(PreComponent[] children) {
    this.children = children;
  }

  @Override
  public Component render(Pointered pointered) {
    if (children == null || children.length == 0) {
      return Component.empty().style(computedStyle != null ? computedStyle : Style.empty());
    }
    if (children.length == 1) {
      Component child = children[0].render(pointered);
      // FIX: Si el grupo tiene un estilo propio, debe aplicarse al hijo.
      // En Adventure, envolvemos en un builder para fusionar estilos.
      if (computedStyle != null && !computedStyle.equals(Style.empty())) {
        return Component.text().style(computedStyle).append(child).build();
      }
      return child;
    }
    net.kyori.adventure.text.TextComponent.Builder builder = Component.text()
      .style(computedStyle != null ? computedStyle : Style.empty());
    for (PreComponent child : children) {
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
    return new GroupPreComponent(copied);
  }
}
