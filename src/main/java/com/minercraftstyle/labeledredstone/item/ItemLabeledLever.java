package com.minercraftstyle.labeledredstone.item;

import com.minercraftstyle.labeledredstone.block.BlockStandingLLever;
import com.minercraftstyle.labeledredstone.block.BlockWallLLever;
import com.minercraftstyle.labeledredstone.init.ModBlocks;
import com.minercraftstyle.labeledredstone.tileentity.TELabeledLever;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class ItemLabeledLever extends ItemLR
{
    public ItemLabeledLever()
    {
        super.setUnlocalizedName("labeledLever");
        this.maxStackSize = 16;
    }

    public boolean onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ)
    {
        if (!worldIn.getBlockState(pos).getBlock().getMaterial().isSolid())
        {
            return false;
        }
        else
        {
            pos = pos.offset(side);

            if (!playerIn.func_175151_a(pos, side, stack))
            {
                return false;
            }
            else if (!ModBlocks.standing_llever.canPlaceBlockAt(worldIn, pos))
            {
                return false;
            }
            else if (worldIn.isRemote)
            {
                return true;
            }
            else
            {
                if (side == EnumFacing.UP || side == EnumFacing.DOWN)
                {
                    int i = MathHelper.floor_double((double) ((playerIn.rotationYaw + 180.0F) * 16.0F / 360.0F) + 0.5D) & 15;
                    worldIn.setBlockState(pos, ModBlocks.standing_llever.getDefaultState().withProperty(BlockStandingLLever.ROTATION_PROP, Integer.valueOf(i)).withProperty(BlockStandingLLever.FACING_PROP, BlockStandingLLever.EnumOrientation.getState(side, playerIn.func_174811_aO(), playerIn.isSneaking())), 3);
                }
                else
                {
                    worldIn.setBlockState(pos, ModBlocks.wall_llever.getDefaultState().withProperty(BlockWallLLever.FACING_PROP, side), 3);
                }

                --stack.stackSize;
                TileEntity tileEntity = worldIn.getTileEntity(pos);

                if (tileEntity instanceof TELabeledLever && !ItemBlock.setTileEntityNBT(worldIn, pos, stack))
                {
                    //packet handling and gui loading
                }

                return true;
            }
        }
    }
}
