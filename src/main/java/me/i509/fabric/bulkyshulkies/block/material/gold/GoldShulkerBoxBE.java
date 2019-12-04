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

package me.i509.fabric.bulkyshulkies.block.material.gold;

import org.jetbrains.annotations.Nullable;

import net.minecraft.util.DyeColor;

import me.i509.fabric.bulkyshulkies.api.block.material.AbstractMaterialBasedShulkerBoxBlockEntity;
import me.i509.fabric.bulkyshulkies.registry.ShulkerBlockEntities;

public class GoldShulkerBoxBE extends AbstractMaterialBasedShulkerBoxBlockEntity {
	public GoldShulkerBoxBE(@Nullable DyeColor color) {
		super(ShulkerBlockEntities.GOLD_SHULKER_BOX, 63, color);
	}

	public GoldShulkerBoxBE() {
		this(null);
	}
}