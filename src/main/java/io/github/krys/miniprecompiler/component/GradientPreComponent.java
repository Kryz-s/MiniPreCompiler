package io.github.krys.miniprecompiler.component;

import io.github.krys.miniprecompiler.model.PreComponent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.pointer.Pointered;

import java.util.ArrayList;
import java.util.List;

public final class GradientPreComponent extends PreComponent {
  private final TextColor[] colors;
  private final float phase;

  public GradientPreComponent(TextColor[] colors, float phase, PreComponent[] children) {
    this.colors = colors;
    this.phase = phase;
    this.children = children;
  }

  @Override
  public Component render(Pointered pointered) {
    List<Component> flattened = new ArrayList<>();
    collectTextComponents(children, flattened, pointered);
    if (flattened.isEmpty()) {
      return Component.empty().style(computedStyle);
    }
    if (colors.length == 1) {
      TextComponent.Builder builder = Component.text()
        .style(computedStyle)
        .color(colors[0]);
      for (Component c : flattened) {
        builder.append(c);
      }
      return builder.build();
    }
    TextComponent.Builder result = Component.text().style(computedStyle);
    int totalLength = 0;
    for (Component c : flattened) {
      totalLength += contentLength(c);
    }
    if (totalLength == 0) {
      for (Component c : flattened) {
        result.append(c);
      }
      return result.build();
    }
    int index = 0;
    for (Component c : flattened) {
      int len = contentLength(c);
      if (len == 0) {
        result.append(c);
        continue;
      }
      TextComponent.Builder segment = Component.text();
      String text = ((net.kyori.adventure.text.TextComponent) c).content();
      for (int i = 0; i < text.length(); i++) {

        float position = (index + i) / (float) Math.max(1, totalLength - 1);

        float adjusted = (position + phase) % 1.0f;
        if (adjusted < 0) adjusted += 1.0f;

        if (adjusted == 0.0f && (position + phase) > 0.0f) {
          adjusted = 1.0f;
        }

        TextColor color = interpolate(adjusted);
        segment.append(Component.text(String.valueOf(text.charAt(i)), c.style().color(color)));
      }
      index += len;
      result.append(segment.build());
    }
    return result.build();
  }

  private void collectTextComponents(PreComponent[] comps, List<Component> out, Pointered pointered) {
    if (comps == null) return;
    for (PreComponent comp : comps) {
      Component rendered = comp.render(pointered);
      if (rendered instanceof TextComponent) {
        out.add(rendered);
      }
    }
  }

  private int contentLength(Component c) {
    if (c instanceof TextComponent tc) {
      return tc.content().length();
    }
    return 0;
  }

  private TextColor interpolate(float position) {
    if (colors.length == 1) return colors[0];
    float scaled = position * (colors.length - 1);
    int index = (int) scaled;
    if (index >= colors.length - 1) return colors[colors.length - 1];
    float local = scaled - index;
    return leap(colors[index], colors[index + 1], local);
  }

  private TextColor leap(TextColor a, TextColor b, float t) {
    int r = (int) (a.red() + (b.red() - a.red()) * t);
    int g = (int) (a.green() + (b.green() - a.green()) * t);
    int bl = (int) (a.blue() + (b.blue() - a.blue()) * t);
    return TextColor.color(r, g, bl);
  }

  @Override
  public PreComponent deepCopy() {
    PreComponent[] copied = new PreComponent[children.length];
    for (int i = 0; i < children.length; i++) {
      copied[i] = children[i].deepCopy();
    }
    return new GradientPreComponent(colors.clone(), phase, copied);
  }
}
