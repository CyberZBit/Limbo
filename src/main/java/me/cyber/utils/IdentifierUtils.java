package me.cyber.utils;

import me.cyber.Limbo;
import net.minecraft.util.Identifier;

public class IdentifierUtils {
    public static Identifier of(String path) {
        return Identifier.tryParse(Limbo.MOD_ID, path);
    }

    public static String strId(String id) {
        return Limbo.MOD_ID + ":" + id;
    }
}
