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
import net.daporkchop.realcoords.RealCoords;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

/**
 * @author DaPorkchop_
 */
public class CoordsProvider extends CoordsImpl implements ICapabilitySerializable<NBTTagList> {
    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        return capability == RealCoords.COORDS_CAPABILITY;
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        if (capability == RealCoords.COORDS_CAPABILITY) {
            return RealCoords.COORDS_CAPABILITY.cast(this);
        }
        return null;
    }

    @Override
    public NBTTagList serializeNBT() {
        NBTTagList list = new NBTTagList();
        this.forEachEnabled(type -> list.appendTag(new NBTTagString(type.name())));
        return list;
    }

    @Override
    public void deserializeNBT(NBTTagList nbt) {
        this.clear();
        nbt.forEach(entry -> {
            try {
                this.set(CoordType.valueOf(((NBTTagString) entry).getString()).ordinal());
            } catch (IllegalArgumentException e) {
                //invalid value, possibly a coordinate type was removed?
            }
        });
    }
}
