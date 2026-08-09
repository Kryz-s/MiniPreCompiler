package io.github.krys.miniprecompiler;

import io.github.krys.miniprecompiler.api.ComponentResolver;
import io.github.krys.miniprecompiler.resolver.*;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public final class ResolverRegistry {

  private final Map<String, ComponentResolver> resolvers;

  private ResolverRegistry(Map<String, ComponentResolver> resolvers) {
    this.resolvers = resolvers;
  }

  public static ResolverRegistry create() {
    return new ResolverRegistry(new HashMap<>());
  }

  public static ResolverRegistry withDefaults() {
    ResolverRegistry registry = create();
    for (NamedTextColor color : NamedTextColor.NAMES.values()) {
     registry.register(new ColorResolver(color.toString(), color));
    }
    registry
      .register(new ColorResolver("grey", NamedTextColor.GRAY))
      .register(new ColorResolver("dark_grey", NamedTextColor.DARK_GRAY))
      .register(new ColorResolver("color", null))
      .register(new StyleResolver(TextDecoration.BOLD, "bold", "b", "!bold", "!b"))
      .register(new StyleResolver(TextDecoration.ITALIC, "italic", "i", "em"))
      .register(new StyleResolver(TextDecoration.UNDERLINED, "underlined", "!underlined", "u", "!u"))
      .register(new StyleResolver(TextDecoration.STRIKETHROUGH, "strikethrough", "!strikethrough", "st", "!st"))
      .register(new StyleResolver(TextDecoration.OBFUSCATED, "obfuscated", "!obfuscated", "obf", "!obf"))
      .register(new ResetResolver())
      .register(new HoverResolver())
      .register(new ClickResolver())
      .register(new GradientResolver())
      .register(new RainbowResolver())
      .register(new KeybindResolver())
      .register(new TranslatableResolver())
      .register(new TranslatableFallbackResolver())
      .register(new ShadowResolver())
      .register(new InsertResolver())
      .register(new NewlineResolver())
      .register(new SpriteResolver())
      .register(new PlayerHeadResolver())
      .register(new PrideResolver());
    return registry;
  }

  public ResolverRegistry register(ComponentResolver resolver) {
    for (String name : resolver.names()) {
      ComponentResolver existing = resolvers.get(name);
      if (existing == null || resolver.priority() > existing.priority()) {
        resolvers.put(name, resolver);
      }
    }
    return this;
  }

  public ResolverRegistry registerAll(Collection<ComponentResolver> resolvers) {
    for (ComponentResolver resolver : resolvers) {
      register(resolver);
    }
    return this;
  }

  public ResolverRegistry unregister(String tagName) {
    resolvers.remove(tagName);
    return this;
  }

  public @Nullable ComponentResolver resolve(String tagName) {
    return resolvers.get(tagName);
  }
}