package com.evoslab.cookielicious.datagen;

import com.evoslab.cookielicious.common.core.Cookielicious;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.ModelProvider;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Objects;

/**
 * Modified copy-paste of the SMDatagenUtil class in SullysMod, taken with permission.
 *
 * @author Uraneptus
 */
public class CDatagenUtil {

    public static final String LAYER_0 = "layer0";
    public static final String GENERATED = "item/generated";
    public static final String HANDHELD = "item/handheld";

    public static String name(Block block) {
        return Objects.requireNonNull(ForgeRegistries.BLOCKS.getKey(block)).getPath();
    }

    public static String name(Item item) {
        return Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(item)).getPath();
    }

    public static ResourceLocation modBlockLocation(String path) {
        return Cookielicious.rl(ModelProvider.BLOCK_FOLDER + "/" + path);
    }

    public static ResourceLocation modItemLocation(String path) {
        return Cookielicious.rl(ModelProvider.ITEM_FOLDER + "/" + path);
    }

    public static ResourceLocation vanillaBlockLocation(String path) {
        return ResourceLocation.withDefaultNamespace(ModelProvider.BLOCK_FOLDER + "/" + path);
    }

    public static ResourceLocation vanillaItemLocation(String path) {
        return ResourceLocation.withDefaultNamespace(ModelProvider.ITEM_FOLDER + "/" + path);
    }
}
