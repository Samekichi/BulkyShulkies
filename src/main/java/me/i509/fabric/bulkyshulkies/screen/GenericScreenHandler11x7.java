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

package me.i509.fabric.bulkyshulkies.screen;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.Slot;

import me.i509.fabric.bulkyshulkies.BulkyShulkies;
import me.i509.fabric.bulkyshulkies.api.SlotFactory;

public class GenericScreenHandler11x7 extends ScreenHandler {
	private final Inventory inventory;

	public GenericScreenHandler11x7(ScreenHandlerType<GenericScreenHandler11x7> type, int syncId, PlayerInventory playerInventory, SlotFactory slotFactory) {
		this(type, syncId, playerInventory, new SimpleInventory(77), slotFactory);
	}

	public GenericScreenHandler11x7(ScreenHandlerType<GenericScreenHandler11x7> type, int syncId, PlayerInventory playerInventory, Inventory inventory, SlotFactory slotFactory) {
		super(type, syncId);
		this.inventory = inventory;
		this.inventory.onOpen(playerInventory.player);

		for (int row = 0; row < 7; ++row) {
			for (int column = 0; column < 11; ++column) {
				this.addSlot(slotFactory.create(inventory, column + (row * 11), (column * 18) + 5, 18 + (row * 18)));
			}
		}

		for (int row = 0; row < 3; ++row) {
			for (int column = 0; column < 9; ++column) {
				this.addSlot(new Slot(playerInventory, column + row * 9 + 9, 23 + (column * 18), 112 + row * 18 + 45));
			}
		}

		for (int column = 0; column < 9; ++column) {
			this.addSlot(new Slot(playerInventory, column, 23 + (column * 18), 170 + 45));
		}
	}

	@Override
	public boolean canUse(PlayerEntity player) {
		return this.inventory.canPlayerUse(player);
	}

	@Override
	public ItemStack transferSlot(PlayerEntity player, int invSlot) {
		ItemStack itemStack = ItemStack.EMPTY;
		Slot slot = this.slots.get(invSlot);

		if (slot != null && slot.hasStack()) {
			ItemStack itemStack2 = slot.getStack();
			itemStack = itemStack2.copy();

			if (invSlot < 77) {
				if (!this.insertItem(itemStack2, 7 * 9, this.slots.size(), true)) {
					return ItemStack.EMPTY;
				}
			} else if (!this.insertItem(itemStack2, 0, 77, false)) {
				return ItemStack.EMPTY;
			}

			if (itemStack2.isEmpty()) {
				slot.setStack(ItemStack.EMPTY);
			} else {
				slot.markDirty();
			}
		}

		return itemStack;
	}

	@Override
	public boolean canInsertIntoSlot(ItemStack stack, Slot slot) {
		// TODO: Make generic
		return BulkyShulkies.getInstance().canInsertItem(stack);
	}

	@Override
	public void close(PlayerEntity player) {
		super.close(player);
		this.inventory.onClose(player);
	}

	public Inventory getInventory() {
		return this.inventory;
	}
}
