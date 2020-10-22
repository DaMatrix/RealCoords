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

package net.daporkchop.realcoords;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.text.TextComponentString;

/**
 * @author DaPorkchop_
 */
public enum CoordType {
    POPUP {
        @Override
        public void add(EntityPlayerMP player) {
        }

        @Override
        public void remove(EntityPlayerMP player) {
            player.sendStatusMessage(new TextComponentString(""), true);
        }

        @Override
        public void update(EntityPlayerMP player) {
            String msg = String.format(RCFormatting.popup, player.posX, player.posY, player.posZ);
            player.sendStatusMessage(new TextComponentString(msg), true);
        }
    };

    private static final CoordType[] VALUES = values();

    public static CoordType fromOrdinal(int ordinal) {
        return VALUES[ordinal];
    }

    public abstract void add(EntityPlayerMP player);

    public abstract void remove(EntityPlayerMP player);

    public abstract void update(EntityPlayerMP player);
}
