package io.github.krys.miniprecompiler.api;

import io.github.krys.miniprecompiler.model.Arguments;
import io.github.krys.miniprecompiler.model.PreComponent;
import io.github.krys.miniprecompiler.MiniPreCompiler;
import net.kyori.adventure.text.format.Style;

public interface ComponentResolver {

  PreComponent resolve(
    String name,
    Arguments args,
    PreComponent[] children,
    Style inheritedStyle,
    MiniPreCompiler compiler
  );

  String[] names();

  default int priority() {
    return 0;
  }

  default boolean isSelfClosing() {
    return false;
  }
}