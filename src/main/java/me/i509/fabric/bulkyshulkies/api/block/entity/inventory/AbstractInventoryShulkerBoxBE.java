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

package me.i509.fabric.bulkyshulkies.api.block.entity.inventory;

import org.checkerframework.checker.nullness.qual.Nullable;

import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.ShulkerBoxBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;

import me.i509.fabric.bulkyshulkies.BulkyShulkies;
import me.i509.fabric.bulkyshulkies.api.block.entity.BasicShulkerBlockEntity;

public abstract class AbstractInventoryShulkerBoxBE extends AbstractSidedInventoryBlockEntity implements BasicShulkerBlockEntity {
	private final DirectionProperty directionProperty;
	protected ShulkerBoxBlockEntity.AnimationStage animationStage = ShulkerBoxBlockEntity.AnimationStage.CLOSED;
	protected float animationProgress;
	protected float prevAnimationProgress;
	private int viewerCount;

	protected AbstractInventoryShulkerBoxBE(BlockEntityType<? extends AbstractInventoryShulkerBoxBE> blockEntityType,
											DirectionProperty directionProperty, int slots) {
		super(blockEntityType, slots);
		this.directionProperty = directionProperty;
	}

	@Override
	public ShulkerBoxBlockEntity.AnimationStage getAnimationStage() {
		return this.animationStage;
	}

	@Override
	public float getAnimationProgress(float delta) {
		return MathHelper.lerp(delta, this.prevAnimationProgress, this.animationProgress);
	}

	@Override
	public DirectionProperty getDirectionProperty() {
		return this.directionProperty;
	}

	@Override
	public void updateNeighborStates() {
		this.getCachedState().method_30101(this.getWorld(), this.getPos(), 3);
	}

	@Override
	public void updateAnimation() {
		this.prevAnimationProgress = this.animationProgress;
		switch (this.animationStage) {
		case CLOSED:
			this.animationProgress = 0.0F;
			break;
		case OPENING:
			this.animationProgress += 0.1F;

			if (this.animationProgress >= 1.0F) {
				this.pushEntities(this, this.directionProperty);
				this.animationStage = ShulkerBoxBlockEntity.AnimationStage.OPENED;
				this.animationProgress = 1.0F;
				this.updateNeighborStates();
			}

			break;
		case CLOSING:
			this.animationProgress -= 0.1F;

			if (this.animationProgress <= 0.0F) {
				this.animationStage = ShulkerBoxBlockEntity.AnimationStage.CLOSED;
				this.animationProgress = 0.0F;
				this.updateNeighborStates();
			}

			break;
		case OPENED:
			this.animationProgress = 1.0F;
		}
	}

	@Override
	public boolean onSyncedBlockEvent(int value, int interactorCount) {
		if (value == 1) {
			this.viewerCount = interactorCount;

			if (interactorCount == 0) {
				this.animationStage = ShulkerBoxBlockEntity.AnimationStage.CLOSING;
				this.updateNeighborStates();
			}

			if (interactorCount == 1) {
				this.animationStage = ShulkerBoxBlockEntity.AnimationStage.OPENING;
				this.updateNeighborStates();
			}

			return true;
		} else {
			return super.onSyncedBlockEvent(value, interactorCount);
		}
	}

	@Override
	public void onOpen(PlayerEntity playerEntity) {
		if (!playerEntity.isSpectator()) {
			if (this.viewerCount < 0) {
				this.viewerCount = 0;
			}

			++this.viewerCount;
			this.world.addSyncedBlockEvent(this.pos, this.getCachedState().getBlock(), 1, this.viewerCount);

			if (this.viewerCount == 1) {
				this.world.playSound(null, this.pos, SoundEvents.BLOCK_SHULKER_BOX_OPEN, SoundCategory.BLOCKS, 0.5F, this.world.random.nextFloat() * 0.1F + 0.9F);
			}
		}
	}

	@Override
	public void onClose(PlayerEntity playerEntity) {
		if (!playerEntity.isSpectator()) {
			--this.viewerCount;
			this.world.addSyncedBlockEvent(this.pos, this.getCachedState().getBlock(), 1, this.viewerCount);

			if (this.viewerCount <= 0) {
				this.world.playSound(null, this.pos, SoundEvents.BLOCK_SHULKER_BOX_CLOSE, SoundCategory.BLOCKS, 0.5F, this.world.random.nextFloat() * 0.1F + 0.9F);
			}
		}
	}

	@Override
	public boolean canExtract(int inventorySlot, ItemStack stack, Direction direction) {
		return true;
	}

	@Override
	public boolean canInsert(int inventorySlot, ItemStack stack, @Nullable Direction direction) {
		return BulkyShulkies.getInstance().canInsertItem(stack);
	}

	@Override
	protected ScreenHandler createScreenHandler(int syncId, PlayerInventory playerInventory) {
		return null; // TODO: ScreenHandler api
	}
}
