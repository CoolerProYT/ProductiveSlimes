package com.coolerpromc.productiveslimes.compat.jade;

import com.coolerpromc.productiveslimes.block.entity.FluidTankBlockEntity;
import com.coolerpromc.productiveslimes.entity.slime.BaseSlime;
import mcp.mobius.waila.api.*;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import java.util.List;

@WailaPlugin
public class JadePlugin implements IWailaPlugin {
    @Override
    public void register(IRegistrar iRegistrar) {
        iRegistrar.registerComponentProvider(EntityInfoProvider.INSTANCE, TooltipPosition.BODY, BaseSlime.class);
        iRegistrar.registerBlockDataProvider(FluidTankProvider.INSTANCE, FluidTankBlockEntity.class);
        iRegistrar.registerComponentProvider(FluidTankClientProvider.INSTANCE, TooltipPosition.BODY, FluidTankBlockEntity.class);
    }

    public enum EntityInfoProvider implements IEntityComponentProvider {
        INSTANCE;

        @Override
        public void appendBody(List<ITextComponent> tooltip, IEntityAccessor accessor, IPluginConfig config) {
            if (accessor.getEntity() instanceof BaseSlime) {
                BaseSlime slime = (BaseSlime) accessor.getEntity();
                int nextDrop = slime.getNextDropTime();
                tooltip.add(new TranslationTextComponent("tooltip.productiveslimes.next_drop", (int) Math.ceil(nextDrop / 20) + "s"));
            }
        }
    }

    public enum FluidTankClientProvider implements IComponentProvider{
        INSTANCE;

        @Override
        public void appendBody(List<ITextComponent> tooltip, IDataAccessor accessor, IPluginConfig config) {
            CompoundNBT data = accessor.getServerData();
            if (data.contains("fluid")) {
                CompoundNBT fluid = data.getCompound("fluid");
                tooltip.add(new TranslationTextComponent("fluid." + fluid.getString("FluidName").replace(':','.')).append(": " + fluid.getInt("Amount") + "mB"));
            }
        }
    }

    public enum FluidTankProvider implements IServerDataProvider<TileEntity>{
        INSTANCE;

        @Override
        public void appendServerData(CompoundNBT compoundNBT, ServerPlayerEntity serverPlayerEntity, World world, TileEntity tileEntity) {
            if (tileEntity instanceof FluidTankBlockEntity) {
                FluidTankBlockEntity fluidTankBlockEntity = (FluidTankBlockEntity) tileEntity;
                compoundNBT.put("fluid", fluidTankBlockEntity.getFluidStack().writeToNBT(new CompoundNBT()));
            }
        }
    }
}
