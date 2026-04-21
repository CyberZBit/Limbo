package me.cyber.utils;

import me.cyber.Limbo;
import net.minecraft.util.Identifier;

/*
    From:
    https://github.com/feytox/Etherology/blob/1.21/src/main/java/ru/feytox/etherology/util/misc/EIdentifier.java#L8
 */
public class IdentifierUtils {
    public static Identifier of(String path) {
        return Identifier.tryParse(Limbo.MOD_ID, path);
    }
}
