/*
 * Adapted from The MIT License (MIT)
 *
 * Copyright (c) 2020-2020 DaPorkchop_
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation
 * files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy,
 * modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software
 * is furnished to do so, subject to the following conditions:
 *
 * Any persons and/or organizations using this software must include the above copyright notice and this permission notice,
 * provide sufficient credit to the original authors of the project (IE: DaPorkchop_), as well as provide a link to the original project.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
 * OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS
 * BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */

package net.daporkchop.realcoords.capability;

import net.daporkchop.realcoords.CoordType;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.text.TextComponentString;

import java.util.BitSet;
import java.util.function.Consumer;

/**
 * @author DaPorkchop_
 */
public class CoordsImpl extends BitSet implements ICoords {
    @Override
    public boolean isEnabled(CoordType type) {
        return this.get(type.ordinal());
    }

    @Override
    public void setEnabled(CoordType type, boolean enabled, EntityPlayerMP player) {
        boolean old = this.get(type.ordinal());
        if (old != enabled) {
            this.set(type.ordinal(), enabled);
            if (enabled) {
                type.add(player);
            } else {
                type.remove(player);
            }
            player.sendMessage(new TextComponentString((enabled ? "§aEnabled" : "§cDisabled") + ' ' + type.name().toLowerCase() + " coordinate display."));
        }
    }

    @Override
    public void forEachEnabled(Consumer<CoordType> callback) {
        for (int i = this.nextSetBit(0); i >= 0; i = this.nextSetBit(i + 1)) {
            callback.accept(CoordType.fromOrdinal(i));
        }
    }
}
