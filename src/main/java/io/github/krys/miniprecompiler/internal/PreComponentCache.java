package io.github.krys.miniprecompiler.internal;

import net.kyori.adventure.text.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Deprecated
public final class PreComponentCache {
    private static final Map<String, Component> staticCache = new ConcurrentHashMap<>();

    public static Component getOrCompute(String key, java.util.function.Supplier<Component> supplier) {
        return staticCache.computeIfAbsent(key, k -> supplier.get());
    }

    public static void clear() {
        staticCache.clear();
    }
}