package io.github.krys.miniprecompiler.component;

import io.github.krys.miniprecompiler.model.PreComponent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.pointer.Pointered;

public final class InsertingPreComponent extends PreComponent {
  private final String insertion;

  public InsertingPreComponent(String insertion, PreComponent[] children) {
    this.insertion = insertion;
    this.children = children;
  }

  @Override
  public Component render(Pointered pointered) {
    var builder = Component.text()
      .insertion(insertion);
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
    return new InsertingPreComponent(insertion, copied);
  }
}
