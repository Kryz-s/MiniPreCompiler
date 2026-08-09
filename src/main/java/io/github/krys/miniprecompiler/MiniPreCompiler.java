package io.github.krys.miniprecompiler;

import io.github.krys.miniprecompiler.api.ComponentResolver;
import io.github.krys.miniprecompiler.api.TagScanner;
import io.github.krys.miniprecompiler.component.ColorPreComponent;
import io.github.krys.miniprecompiler.internal.DefaultTagScanner;
import io.github.krys.miniprecompiler.model.Arguments;
import io.github.krys.miniprecompiler.model.PreComponent;
import io.github.krys.miniprecompiler.model.PreComponentTree;
import io.github.krys.miniprecompiler.model.TagNode;
import io.github.krys.miniprecompiler.component.GroupPreComponent;
import io.github.krys.miniprecompiler.component.LiteralPreComponent;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;

import java.util.ArrayList;
import java.util.List;

public class MiniPreCompiler {

  private final ResolverRegistry registry;
  private final TagScanner scanner;

  public MiniPreCompiler(ResolverRegistry registry) {
    this.registry = registry;
    this.scanner = new DefaultTagScanner();
  }

  public PreComponentTree compile(String miniMessageString) {
    if (miniMessageString == null) return PreComponentTree.EMPTY;
    List<TagNode> roots = scanner.scan(miniMessageString);
    roots = flattenSelfClosing(roots);
    List<PreComponent> components = new ArrayList<>();
    for (TagNode root : roots) {
      components.add(resolveStyled(buildStyledNode(root, Style.empty())));
    }
    PreComponent rootComponent;
    if (components.size() == 1) {
      rootComponent = components.get(0);
    } else {
      rootComponent = new GroupPreComponent(components.toArray(new PreComponent[0]));
    }
    return new PreComponentTree(rootComponent);
  }

  public PreComponentTree compile(String miniMessageString, ComponentResolver... additionalResolvers) {
    ResolverRegistry extended = registry;
    for (ComponentResolver resolver : additionalResolvers) {
      extended = extended.register(resolver);
    }
    return new MiniPreCompiler(extended).compile(miniMessageString);
  }

  private List<TagNode> flattenSelfClosing(List<TagNode> nodes) {
    List<TagNode> result = new ArrayList<>();
    for (TagNode node : nodes) {
      ComponentResolver resolver = registry.resolve(node.tagName());
      boolean isSelfClosing = resolver != null && resolver.isSelfClosing();
      List<TagNode> flattenedChildren = flattenSelfClosing(node.children());

      if (isSelfClosing && !flattenedChildren.isEmpty()) {
        result.add(new TagNode(
          node.tagName(),
          node.rawArgs(),
          node.textContent(),
          List.of(),
          false,
          true
        ));
        result.addAll(flattenedChildren);
      } else {
        result.add(new TagNode(
          node.tagName(),
          node.rawArgs(),
          node.textContent(),
          flattenedChildren,
          node.isClosingTag(),
          node.isSelfClosing()
        ));
      }
    }
    return result;
  }

  private StyledNode buildStyledNode(TagNode node, Style computed) {
    Style childInherited = "reset".equals(node.tagName()) ? Style.empty() : computed;

    List<StyledNode> styledChildren = new ArrayList<>();
    for (TagNode child : node.children()) {
      styledChildren.add(buildStyledNode(child, childInherited));
    }
    return new StyledNode(node, computed, styledChildren);
  }

  private PreComponent resolveStyled(StyledNode styled) {
    TagNode node = styled.node;
    PreComponent[] childComponents = new PreComponent[styled.children.size()];
    for (int i = 0; i < styled.children.size(); i++) {
      childComponents[i] = resolveStyled(styled.children.get(i));
    }
    if (node.tagName().isEmpty()) {
      return new LiteralPreComponent(node.textContent(), styled.computedStyle);
    }
    ComponentResolver resolver = registry.resolve(node.tagName());
    if (resolver == null) {
      return new LiteralPreComponent("<" + node.tagName() + ">", styled.computedStyle);
    }
    return resolver.resolve(node.tagName(), Arguments.parse(node.rawArgs()), childComponents, styled.computedStyle, this);
  }

  private record StyledNode(TagNode node, Style computedStyle, List<StyledNode> children) {
  }
}
