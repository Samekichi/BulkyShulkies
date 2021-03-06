/*
 * MIT License
 *
 * Copyright (c) 2019-2020 i509VCB
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package me.i509.fabric.bulkyshulkies.item;

import org.checkerframework.checker.nullness.qual.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.CauldronBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.stat.Stats;
import net.minecraft.util.ActionResult;

import me.i509.fabric.bulkyshulkies.api.block.AbstractShulkerBoxBlock;
import me.i509.fabric.bulkyshulkies.api.block.base.InventoryShulkerBoxBlock;
import me.i509.fabric.bulkyshulkies.api.block.colored.ColoredShulkerBoxBlock;

public class InventoryShulkerBlockItem extends BlockItem {
	public InventoryShulkerBlockItem(AbstractShulkerBoxBlock block, Settings settings) {
		super(block, settings);

		if (!(block instanceof InventoryShulkerBoxBlock)) {
			throw new IllegalArgumentException("InventoryShulkerBlockItem must implement InventoryShulkerBoxBlock");
		}
	}

	@Override
	public AbstractShulkerBoxBlock getBlock() {
		return (AbstractShulkerBoxBlock) super.getBlock();
	}

	// TODO: Implement this in a plain non-inventory shulker box?
	@Override
	public ActionResult useOnBlock(ItemUsageContext context) {
		if (context.getWorld().isClient()) { // Fail fast in a client world
			return super.useOnBlock(context);
		}

		@Nullable final PlayerEntity player = context.getPlayer();

		if (player != null) {
			final BlockState blockState = context.getWorld().getBlockState(context.getBlockPos());

			// Test if we are using a cauldron
			if (blockState.getBlock() instanceof CauldronBlock) { // TODO: Add a hook method to verify we have a cauldron for custom cauldrons
				final ItemStack handStack = player.getStackInHand(context.getHand());

				if (handStack.getItem() instanceof BlockItem) {
					final Block block = ((BlockItem) handStack.getItem()).getBlock();

					if (block instanceof ColoredShulkerBoxBlock) {
						// Test Cauldron water level
						// TODO: Get level from custom cauldron?
						final int level = blockState.get(CauldronBlock.LEVEL);

						final ItemStack newStack = ((ColoredShulkerBoxBlock) block).getItemStack(null); // Get the uncolored item

						if (handStack.hasTag()) {
							newStack.setTag(handStack.getTag().copy());
						}

						// Copied logic from cauldron
						player.setStackInHand(context.getHand(), newStack);
						// TODO: Custom level decrement?
						((CauldronBlock) blockState.getBlock()).setLevel(context.getWorld(), context.getBlockPos(), blockState, level - 1);
						player.incrementStat(Stats.CLEAN_SHULKER_BOX);

						return ActionResult.SUCCESS;
					}
				}
			}
		}

		return super.useOnBlock(context);
	}

	public InventoryShulkerBoxBlock getAsInventoryType() {
		return (InventoryShulkerBoxBlock) super.getBlock();
	}
}
