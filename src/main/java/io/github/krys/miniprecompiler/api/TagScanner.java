package io.github.krys.miniprecompiler.api;

import io.github.krys.miniprecompiler.model.TagNode;
import java.util.List;

public interface TagScanner {

    List<TagNode> scan(String input);
}