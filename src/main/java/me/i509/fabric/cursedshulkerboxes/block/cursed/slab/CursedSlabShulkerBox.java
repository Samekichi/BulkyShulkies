/*
 * MIT License
 *
 * Copyright (c) 2019 i509VCB
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

package me.i509.fabric.cursedshulkerboxes.block.cursed.slab;

import org.jetbrains.annotations.Nullable;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DyeColor;
import net.minecraft.world.BlockView;

import me.i509.fabric.cursedshulkerboxes.api.block.slab.AbstractCursedShulkerSlabBlock;
import me.i509.fabric.cursedshulkerboxes.registry.ShulkerBlocks;

public class CursedSlabShulkerBox extends AbstractCursedShulkerSlabBlock {
	public CursedSlabShulkerBox(Settings settings, @Nullable DyeColor color) {
		super(settings, 18, color);
	}

	@Override
	public BlockEntity createBlockEntity(BlockView blockView) {
		return new CursedSlabShulkerBoxBE(this.getColor());
	}

	@Override
	public ItemStack getItemStack(@Nullable DyeColor color) {
		if (color == null) {
			return new ItemStack(ShulkerBlocks.SLAB_SHULKER_BOX);
		}

		switch (color) {
		case WHITE:
			return new ItemStack(ShulkerBlocks.WHITE_SLAB_SHULKER_BOX);
		case ORANGE:
			return new ItemStack(ShulkerBlocks.ORANGE_SLAB_SHULKER_BOX);
		case MAGENTA:
			return new ItemStack(ShulkerBlocks.MAGENTA_SLAB_SHULKER_BOX);
		case LIGHT_BLUE:
			return new ItemStack(ShulkerBlocks.LIGHT_BLUE_SLAB_SHULKER_BOX);
		case YELLOW:
			return new ItemStack(ShulkerBlocks.YELLOW_SLAB_SHULKER_BOX);
		case LIME:
			return new ItemStack(ShulkerBlocks.LIME_SLAB_SHULKER_BOX);
		case PINK:
			return new ItemStack(ShulkerBlocks.PINK_SLAB_SHULKER_BOX);
		case GRAY:
			return new ItemStack(ShulkerBlocks.GRAY_SLAB_SHULKER_BOX);
		case LIGHT_GRAY:
			return new ItemStack(ShulkerBlocks.LIGHT_GRAY_SLAB_SHULKER_BOX);
		case CYAN:
			return new ItemStack(ShulkerBlocks.CYAN_SLAB_SHULKER_BOX);
		case PURPLE:
		default:
			return new ItemStack(ShulkerBlocks.PURPLE_SLAB_SHULKER_BOX);
		case BLUE:
			return new ItemStack(ShulkerBlocks.BLUE_SLAB_SHULKER_BOX);
		case BROWN:
			return new ItemStack(ShulkerBlocks.BROWN_SLAB_SHULKER_BOX);
		case GREEN:
			return new ItemStack(ShulkerBlocks.GREEN_SLAB_SHULKER_BOX);
		case RED:
			return new ItemStack(ShulkerBlocks.RED_SLAB_SHULKER_BOX);
		case BLACK:
			return new ItemStack(ShulkerBlocks.BLACK_SLAB_SHULKER_BOX);
		}
	}
}