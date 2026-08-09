package io.github.krys.miniprecompiler.resolver;

import io.github.krys.miniprecompiler.MiniPreCompiler;
import io.github.krys.miniprecompiler.api.ComponentResolver;
import io.github.krys.miniprecompiler.component.ClickPreComponent;
import io.github.krys.miniprecompiler.component.LiteralPreComponent;
import io.github.krys.miniprecompiler.model.Arguments;
import io.github.krys.miniprecompiler.model.PreComponent;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.format.Style;

public final class ClickResolver implements ComponentResolver {
    @Override
    public PreComponent resolve(String name, Arguments args, PreComponent[] children, Style inheritedStyle, MiniPreCompiler compiler) {
        String actionName = args.get(0, "open_url");
        String rawValue = args.get(1, "");
        ClickEvent event = resolveClickEvent(actionName, rawValue);
        if (children.length == 0) {
            return new LiteralPreComponent("", inheritedStyle);
        }
        return new ClickPreComponent(event, children);
    }

    private ClickEvent resolveClickEvent(String actionName, String rawValue) {
        ClickEvent.Action action = resolveAction(actionName);
        ClickEvent.Payload normalizedValue = normalizeValue(action, rawValue);
        return ClickEvent.clickEvent(action, normalizedValue);
    }

    private ClickEvent.Action resolveAction(String name) {
        ClickEvent.Action registered = ClickEvent.Action.NAMES.value(name);
        if (registered != null) return registered;
        return switch (name) {
            case "run_command" -> ClickEvent.Action.RUN_COMMAND;
            case "suggest_command" -> ClickEvent.Action.SUGGEST_COMMAND;
            case "open_file" -> ClickEvent.Action.OPEN_FILE;
            case "copy_to_clipboard" -> ClickEvent.Action.COPY_TO_CLIPBOARD;
            case "change_page" -> ClickEvent.Action.CHANGE_PAGE;
            default -> ClickEvent.Action.OPEN_URL;
        };
    }

    private ClickEvent.Payload normalizeValue(ClickEvent.Action action, String raw) {
        if (action == ClickEvent.Action.CHANGE_PAGE) {
            return ClickEvent.Payload.integer(parseInt(raw));
        }
        return ClickEvent.Payload.string(raw);
    }

    private int parseInt(String raw) {
        try {
            return Integer.parseInt(raw);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    // FIX: <click> envuelve contenido, NO es self-closing.
    // Si isSelfClosing=true, flattenSelfClosing separa el tag de sus hijos
    // y el texto pierde el evento de click.
    @Override
    public boolean isSelfClosing() {
        return false;
    }

    @Override
    public String[] names() {
        return new String[]{"click"};
    }
}
