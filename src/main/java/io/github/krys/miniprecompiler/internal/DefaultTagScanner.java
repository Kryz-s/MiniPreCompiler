package io.github.krys.miniprecompiler.internal;

import io.github.krys.miniprecompiler.api.TagScanner;
import io.github.krys.miniprecompiler.model.TagNode;

import java.util.*;

public final class DefaultTagScanner implements TagScanner {

  @Override
  public List<TagNode> scan(String input) {
    List<TagNode> roots = new ArrayList<>();
    Deque<OpenTag> stack = new ArrayDeque<>();
    StringBuilder textBuffer = new StringBuilder();
    int i = 0;
    while (i < input.length()) {
      char c = input.charAt(i);
      if (c == '<') {
        flushText(textBuffer, stack, roots);
        int end = findTagEnd(input, i);
        if (end == -1) {
          textBuffer.append(c);
          i++;
          continue;
        }
        String content = input.substring(i + 1, end);
        i = end + 1;
        boolean isClosing = content.startsWith("/");
        boolean isSelfClosing = content.endsWith("/");
        if (isSelfClosing) {
          content = content.substring(0, content.length() - 1);
        }
        if (isClosing) {
          String tagName = content.substring(1);
          tagName = normalizeClosingTag(tagName);
          closeUpTo(tagName, stack, roots);
        } else {
          String[] parts = splitTag(content);
//          System.out.println("parts= " + Arrays.toString(parts));
          String tagName = normalizeTagName(parts[0]);
          String rawArgs = parts.length > 1 ? parts[1] : "";
//          System.out.println("rawArgs= " + rawArgs);
          if (isSelfClosing) {
            TagNode selfClosing = new TagNode(tagName, rawArgs, "", List.of(), false, true);
            if (stack.isEmpty()) {
              roots.add(selfClosing);
            } else {
              stack.peek().addChild(selfClosing);
            }
          } else {
            stack.push(new OpenTag(tagName, rawArgs));
          }
        }
      } else if (c == '\\' && i + 1 < input.length() && input.charAt(i + 1) == '<') {
        textBuffer.append('<');
        i += 2;
      } else {
        textBuffer.append(c);
        i++;
      }
    }
    flushText(textBuffer, stack, roots);
    while (!stack.isEmpty()) {
      OpenTag tag = stack.pop();
      TagNode node = tag.build();
      if (stack.isEmpty()) {
        roots.add(node);
      } else {
        stack.peek().addChild(node);
      }
    }
    return roots;
  }

  private String normalizeTagName(String name) {
//    System.out.println(name);
    if (name.startsWith("#")) {
      return "color";
    }
    return name;
  }

  private String normalizeClosingTag(String name) {
    if (name.startsWith("#")) {
      return "color";
    }
    return name;
  }

  private void flushText(StringBuilder buffer, Deque<OpenTag> stack, List<TagNode> roots) {
    if (buffer.isEmpty()) return;
    String text = buffer.toString();
    buffer.setLength(0);
    TagNode textNode = new TagNode("", "", text, List.of(), false, false);
    if (stack.isEmpty()) {
      roots.add(textNode);
    } else {
      stack.peek().addChild(textNode);
    }
  }

  private void closeUpTo(String tagName, Deque<OpenTag> stack, List<TagNode> roots) {
    Deque<OpenTag> popped = new ArrayDeque<>();
    while (!stack.isEmpty()) {
      OpenTag tag = stack.pop();
      popped.push(tag);
      if (tag.tagName.equals(tagName)) {
        break;
      }
    }
    while (!popped.isEmpty()) {
      OpenTag tag = popped.pop();
      TagNode node = tag.build();
      if (popped.isEmpty()) {
        if (stack.isEmpty()) {
          roots.add(node);
        } else {
          stack.peek().addChild(node);
        }
      } else {
        popped.peek().addChild(node);
      }
    }
  }

  private int findTagEnd(String input, int start) {
    boolean inQuotes = false;
    for (int i = start + 1; i < input.length(); i++) {
      char c = input.charAt(i);
      if (c == '\'') {
        inQuotes = !inQuotes;
      } else if (c == '>' && !inQuotes) {
        return i;
      }
    }
    return -1;
  }

  private String[] splitTag(String content) {
//    System.out.println("splitTag= " +  content);
    if (content.startsWith("#")) {
      return new String[]{"color", content};
    }
    int colon = content.indexOf(':');
    if (colon == -1) {
      return new String[]{content};
    }
    return new String[]{content.substring(0, colon), content.substring(colon + 1)};
  }

  private static final class OpenTag {
    final String tagName;
    final String rawArgs;
    final List<TagNode> children = new ArrayList<>();

    OpenTag(String tagName, String rawArgs) {
      this.tagName = tagName;
      this.rawArgs = rawArgs;
    }

    void addChild(TagNode child) {
      children.add(child);
    }

    TagNode build() {
      return new TagNode(tagName, rawArgs, "", children, false, false);
    }
  }
}