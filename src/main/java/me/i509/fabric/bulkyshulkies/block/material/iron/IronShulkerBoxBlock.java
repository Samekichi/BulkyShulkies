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

package me.i509.fabric.bulkyshulkies.block.material.iron;

import org.checkerframework.checker.nullness.qual.Nullable;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.ItemConvertible;
import net.minecraft.util.DyeColor;
import net.minecraft.world.BlockView;

import me.i509.fabric.bulkyshulkies.api.block.colored.Facing1x1ColoredInventoryShulkerBoxBlock;
import me.i509.fabric.bulkyshulkies.block.ShulkerBoxConstants;
import me.i509.fabric.bulkyshulkies.registry.ShulkerBlocks;

public class IronShulkerBoxBlock extends Facing1x1ColoredInventoryShulkerBoxBlock {
	public IronShulkerBoxBlock(Settings settings, @Nullable DyeColor color) {
		super(settings, ShulkerBoxConstants.IRON_SLOT_COUNT, color);
	}

	@Override
	public BlockEntity createBlockEntity(BlockView blockView) {
		return new IronShulkerBoxBlockEntity(this.getColor());
	}

	@Override
	public ItemConvertible getItem(@Nullable DyeColor color) {
		if (color == null) {
			return ShulkerBlocks.IRON_SHULKER_BOX;
		}

		switch (color) {
		case WHITE:
			return ShulkerBlocks.WHITE_IRON_SHULKER_BOX;
		case ORANGE:
			return ShulkerBlocks.ORANGE_IRON_SHULKER_BOX;
		case MAGENTA:
			return ShulkerBlocks.MAGENTA_IRON_SHULKER_BOX;
		case LIGHT_BLUE:
			return ShulkerBlocks.LIGHT_BLUE_IRON_SHULKER_BOX;
		case YELLOW:
			return ShulkerBlocks.YELLOW_IRON_SHULKER_BOX;
		case LIME:
			return ShulkerBlocks.LIME_IRON_SHULKER_BOX;
		case PINK:
			return ShulkerBlocks.PINK_IRON_SHULKER_BOX;
		case GRAY:
			return ShulkerBlocks.GRAY_IRON_SHULKER_BOX;
		case LIGHT_GRAY:
			return ShulkerBlocks.LIGHT_GRAY_IRON_SHULKER_BOX;
		case CYAN:
			return ShulkerBlocks.CYAN_IRON_SHULKER_BOX;
		case PURPLE:
		default:
			return ShulkerBlocks.PURPLE_IRON_SHULKER_BOX;
		case BLUE:
			return ShulkerBlocks.BLUE_IRON_SHULKER_BOX;
		case BROWN:
			return ShulkerBlocks.BROWN_IRON_SHULKER_BOX;
		case GREEN:
			return ShulkerBlocks.GREEN_IRON_SHULKER_BOX;
		case RED:
			return ShulkerBlocks.RED_IRON_SHULKER_BOX;
		case BLACK:
			return ShulkerBlocks.BLACK_IRON_SHULKER_BOX;
		}
	}
}
