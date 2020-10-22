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

import net.daporkchop.realcoords.capability.CoordsImpl;
import net.daporkchop.realcoords.capability.ICoords;
import net.daporkchop.realcoords.capability.NoopCapabilityStorage;
import net.daporkchop.realcoords.command.CoordsCommand;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.event.FMLServerStoppingEvent;
import net.minecraftforge.server.permission.DefaultPermissionLevel;
import net.minecraftforge.server.permission.PermissionAPI;
import org.apache.logging.log4j.Logger;

@Mod(modid = RealCoords.MODID,
        useMetadata = true,
        dependencies = "after:cubicchunks@[1.12.2-0.0.1106.0,)",
        acceptableRemoteVersions = "*")
public class RealCoords {
    public static final String MODID = "realcoords";

    public static final boolean CC = Loader.isModLoaded("cubicchunks");

    public static Logger LOGGER;

    @CapabilityInject(ICoords.class)
    public static final Capability<ICoords> COORDS_CAPABILITY = null;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        LOGGER = event.getModLog();

        CapabilityManager.INSTANCE.register(ICoords.class, new NoopCapabilityStorage<>(), CoordsImpl::new);
    }

    @EventHandler
    public void serverStarting(FMLServerStartingEvent event) {
        PermissionAPI.registerNode(MODID + ".command.coords", DefaultPermissionLevel.ALL, "Allows to run the /realcoords command");

        event.registerServerCommand(new CoordsCommand());
    }

    @EventHandler
    public void serverStopping(FMLServerStoppingEvent event) {
    }
}
