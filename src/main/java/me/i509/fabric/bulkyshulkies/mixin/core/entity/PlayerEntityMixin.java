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

package me.i509.fabric.bulkyshulkies.mixin.core.entity;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundTag;

import net.fabricmc.fabric.api.util.NbtType;

import me.i509.fabric.bulkyshulkies.api.player.EnderSlabBridge;
import me.i509.fabric.bulkyshulkies.inventory.EnderSlabInventory;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin implements EnderSlabBridge {
	protected EnderSlabInventory bulky$enderSlabInventory = new EnderSlabInventory();

	@Override
	public EnderSlabInventory bridge$getEnderSlabInventory() {
		return bulky$enderSlabInventory;
	}

	@Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerAbilities;deserialize(Lnet/minecraft/nbt/CompoundTag;)V"), method = "readCustomDataFromTag")
	private void onReadCustomData(CompoundTag tag, CallbackInfo ci) {
		if (tag.contains("EnderSlabItems", 9)) {
			this.bulky$enderSlabInventory.readTags(tag.getList("EnderSlabItems", NbtType.COMPOUND));
		}
	}

	@Inject(at = @At("HEAD"), method = "writeCustomDataToTag")
	private void onWriteCustomData(CompoundTag tag, CallbackInfo ci) {
		tag.put("EnderSlabItems", this.bulky$enderSlabInventory.getTags());
	}
}
