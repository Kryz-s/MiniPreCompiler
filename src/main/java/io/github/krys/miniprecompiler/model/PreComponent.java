package io.github.krys.miniprecompiler.model;

import net.kyori.adventure.pointer.Pointered;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.Style;

public abstract class PreComponent {

  protected PreComponent[] children;
  protected Style computedStyle = Style.empty();

  public abstract Component render(Pointered pointered);

  public abstract PreComponent deepCopy();

  public PreComponent setChildren(PreComponent[] children) {
    this.children = children;
    return this;
  }

  public PreComponent setStyle(Style style) {
    this.computedStyle = style;
    return this;
  }

  public PreComponent prependChild(PreComponent child) {
    if (children == null) {
      children = new PreComponent[]{child};
      return this;
    }
    PreComponent[] next = new PreComponent[children.length + 1];
    next[0] = child;
    System.arraycopy(children, 0, next, 1, children.length);
    children = next;
    return this;
  }

  public PreComponent appendChild(PreComponent child) {
    if (children == null) {
      children = new PreComponent[]{child};
      return this;
    }
    PreComponent[] next = new PreComponent[children.length + 1];
    System.arraycopy(children, 0, next, 0, children.length);
    next[children.length] = child;
    children = next;
    return this;
  }

  public PreComponent replaceChild(int index, PreComponent replacement) {
    if (children != null && index >= 0 && index < children.length) {
      children[index] = replacement;
    }
    return this;
  }

  public PreComponent[] getChildren() {
    return children != null ? children : new PreComponent[0];
  }

  public Style getComputedStyle() {
    return computedStyle != null ? computedStyle : Style.empty();
  }
}