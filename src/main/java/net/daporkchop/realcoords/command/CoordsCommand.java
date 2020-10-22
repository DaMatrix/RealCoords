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

package net.daporkchop.realcoords.command;

import net.daporkchop.realcoords.CoordType;
import net.daporkchop.realcoords.RealCoords;
import net.daporkchop.realcoords.capability.ICoords;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.server.permission.PermissionAPI;

import java.util.Arrays;
import java.util.List;

/**
 * @author DaPorkchop_
 */
public class CoordsCommand extends CommandBase {
    public CoordsCommand() {
        super();
    }

    @Override
    public String getName() {
        return "realcoords";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "/realcoords";
    }

    @Override
    public List<String> getAliases() {
        return Arrays.asList(
                "coordinates",
                "coords",
                "coord",
                "xyz",
                "pos"
        );
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        Entity entity = sender.getCommandSenderEntity();
        if (!(entity instanceof EntityPlayerMP)) {
            sender.sendMessage(new TextComponentString("§cYou must be a player to use this command!"));
            return;
        }
        ICoords coords = entity.getCapability(RealCoords.COORDS_CAPABILITY, null);
        coords.setEnabled(CoordType.POPUP, !coords.isEnabled(CoordType.POPUP), (EntityPlayerMP) entity);
    }

    @Override
    public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
        if (sender instanceof EntityPlayer) {
            return PermissionAPI.hasPermission((EntityPlayer) sender, RealCoords.MODID + ".command.coords");
        } else {
            return super.checkPermission(server, sender);
        }
    }
}
