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

package me.i509.fabric.bulkyshulkies;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import com.google.common.reflect.TypeToken;
import ninja.leaping.configurate.ConfigurationOptions;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import ninja.leaping.configurate.objectmapping.DefaultObjectMapperFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.block.Block;
import net.minecraft.block.ShulkerBoxBlock;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.InvalidIdentifierException;
import net.minecraft.util.registry.Registry;

import net.fabricmc.loader.api.FabricLoader;

import me.i509.fabric.bulkyshulkies.api.block.base.BasicShulkerBlock;
import me.i509.fabric.bulkyshulkies.block.ender.EnderSlabBlock;
import me.i509.fabric.bulkyshulkies.config.MainConfig;

public class BulkyShulkies {
	private static final Logger LOGGER = LogManager.getLogger(BulkyShulkies.class);

	private static final BulkyShulkies INSTANCE;
	private static List<Predicate<ItemStack>> disallowedItems = new ArrayList<>();

	private MainConfig mainConf;
	private ConfigurationLoader<CommentedConfigurationNode> mainConfLoader;

	private BulkyShulkies() throws IOException {
		BulkyShulkies.disallowedItems.add((stack) -> Block.getBlockFromItem(stack.getItem()) instanceof ShulkerBoxBlock);
		BulkyShulkies.disallowedItems.add((stack) -> Block.getBlockFromItem(stack.getItem()) instanceof BasicShulkerBlock);

		Path configLocation = FabricLoader.getInstance().getConfigDir().resolve("bulkyshulkies");
		Path configFile = configLocation.resolve("bulkyshulkies.conf");

		if (!Files.exists(configLocation)) {
			Files.createDirectories(configLocation);
		}

		if (!Files.exists(configFile)) {
			Files.createFile(configFile);
		}

		try {
			this.mainConfLoader = HoconConfigurationLoader.builder()
					.setPath(configFile).build();

			CommentedConfigurationNode mainConfigRoot = this.mainConfLoader.load(ConfigurationOptions.defaults().setHeader(MainConfig.HEADER)
					.withObjectMapperFactory(DefaultObjectMapperFactory.getInstance()).withShouldCopyDefaults(true));

			//noinspection UnstableApiUsage
			this.mainConf = mainConfigRoot.getValue(TypeToken.of(MainConfig.class), new MainConfig());
			this.mainConfLoader.save(mainConfigRoot);
		} catch (Exception e) {
			e.printStackTrace(); // Well some invalid syntax
			return;
		}

		for (String id : this.mainConf.getNotAllowedInShulkers()) {
			try {
				Identifier identifier = new Identifier(id);
				Optional<Item> item = Registry.ITEM.getOrEmpty(identifier);

				if (!item.isPresent()) {
					this.getLogger().error("Tried to register item: " + identifier.toString() + " to the shulker box blacklist, but it is not present within the ITEM registry.");
					continue;
				}

				BulkyShulkies.addDisallowedShulkerItem((stack) -> Registry.ITEM.getId(stack.getItem()).equals(identifier));
			} catch (InvalidIdentifierException e) {
				this.getLogger().error("Item: " + id + " is not a valid identifier, so it failed to be registered into the shulker box blacklist.", e);
			}
		}
	}

	public static BulkyShulkies getInstance() {
		return BulkyShulkies.INSTANCE;
	}

	public static Identifier id(String path) {
		return new Identifier(BulkyShulkiesMod.MODID, path);
	}

	public Logger getLogger() {
		return BulkyShulkies.LOGGER;
	}

	public MainConfig getConfig() {
		return this.mainConf;
	}

	public static void addDisallowedShulkerItem(Predicate<ItemStack> predicate) {
		BulkyShulkies.disallowedItems.add(predicate);
	}

	public boolean canInsertItem(ItemStack stack) {
		if (stack.getItem() instanceof BlockItem && ((BlockItem) stack.getItem()).getBlock() instanceof EnderSlabBlock) {
			return true;
		}

		for (Predicate<ItemStack> disallowedItems : BulkyShulkies.disallowedItems) {
			if (disallowedItems.test(stack)) {
				return false;
			}
		}

		return true;
	}

	static {
		BulkyShulkies instance;

		try {
			instance = new BulkyShulkies();
		} catch (IOException e) {
			e.printStackTrace();
			instance = null;
		}

		INSTANCE = instance;
	}
}
