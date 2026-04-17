package com.cookiejar.cookielicious.common.event;

import com.cookiejar.cookielicious.common.core.registry.CookieliciousTriggers;
import com.cookiejar.cookielicious.common.tag.CItemTags;
import com.cookiejar.cookielicious.common.triggers.SimpleTypeTrigger;
import com.cookiejar.cookielicious.common.util.References;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.animal.Parrot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerListener;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.player.*;
import net.neoforged.neoforge.event.server.ServerStartedEvent;
import net.neoforged.neoforge.event.server.ServerStoppedEvent;

import java.util.ArrayList;
import java.util.List;

public class CEventsListener {

    private static final List<Item> AVAILABLE_COOKIES = new ArrayList<>();

    public static final String MOD_DATA_KEY = "CookieliciousData";
    public static final String COOKIES_OBTAINED_KEY = "CookiesObtained";
    public static final String HAS_BAKE_EM_ALL_KEY = "HasBakeEmAll";


    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void onEntityInteract(PlayerInteractEvent.EntityInteract event) {
        if (event.getTarget() instanceof Parrot parrot) {
            Player player = event.getEntity();
            ItemStack interactionItem = event.getEntity().getItemInHand(event.getHand());

            // ded
            if (interactionItem.is(CItemTags.CHOCOLATE_COOKIES)) {
                if (!player.getAbilities().instabuild) {
                    interactionItem.shrink(1);
                }
                parrot.addEffect(new MobEffectInstance(MobEffects.POISON, 900));

                if (player.isCreative() || !parrot.isInvulnerable()) {
                    parrot.hurt(parrot.damageSources().playerAttack(player), Float.MAX_VALUE);
                }
                if (player instanceof ServerPlayer serverPlayer) {
                    CookieliciousTriggers.SIMPLE_TYPE.get().trigger(serverPlayer, SimpleTypeTrigger.Type.POISON_PARROT);
                }
                // noinspection resource
                event.setCancellationResult(InteractionResult.sidedSuccess(player.level().isClientSide));
                event.setCanceled(true);
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void onPlayerOpenContainer(PlayerContainerEvent.Open event) {
        // Listen for possible new cookies to be obtained :eyes:
        if (event.getEntity() instanceof ServerPlayer serverPlayer) {
            event.getContainer().addSlotListener(createCookieListener(serverPlayer));
        }
    }

    @SubscribeEvent
    public void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        if (event.getEntity() instanceof ServerPlayer serverPlayer) {
            serverPlayer.inventoryMenu.addSlotListener(createCookieListener(serverPlayer));
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void onPickedUpItem(ItemEntityPickupEvent.Post event) {
        if (AVAILABLE_COOKIES.contains(event.getCurrentStack().getItem())) {
            if (event.getPlayer() instanceof ServerPlayer serverPlayer) {
                checkCookiesObtained(serverPlayer, event.getCurrentStack());
            }
        }
    }

    @SubscribeEvent
    public void onAdvancementProgress(AdvancementEvent.AdvancementProgressEvent event) {
        if (event.getProgressType() == AdvancementEvent.AdvancementProgressEvent.ProgressType.REVOKE
                && event.getAdvancement().id().equals(References.BAKE_EM_ALL_ADV)) {
            if (event.getEntity() instanceof ServerPlayer serverPlayer) {
                // Clear progress saved to persistent player data
                CompoundTag persistentData = serverPlayer.getPersistentData();
                CompoundTag modData = persistentData.getCompound(MOD_DATA_KEY);
                modData.putBoolean(HAS_BAKE_EM_ALL_KEY, false);
                modData.put(COOKIES_OBTAINED_KEY, new ListTag());
                persistentData.put(MOD_DATA_KEY, modData);
            }
        }
    }

    @SubscribeEvent
    public void onServerStarted(ServerStartedEvent event) {
        calculateAvailableCookies(event.getServer());
    }

    @SubscribeEvent
    public void onServerStopped(ServerStoppedEvent event) {
        AVAILABLE_COOKIES.clear();
    }

    /**
     * Looks through all crafting recipes that produce an item tagged as "forge:cookies",
     * and adds them to the {@link #AVAILABLE_COOKIES} list.<br>
     * This list is used when tracking what cookies the player has obtained.
     */
    private static void calculateAvailableCookies(MinecraftServer server) {
        List<RecipeHolder<CraftingRecipe>> allCraftingRecipes = server.getRecipeManager().getAllRecipesFor(RecipeType.CRAFTING);

        for (RecipeHolder<CraftingRecipe> holder : allCraftingRecipes) {
            CraftingRecipe recipe = holder.value();
            ItemStack result = recipe.getResultItem(server.registryAccess());

            if (result.is(CItemTags.COOKIES)) {
                if (!AVAILABLE_COOKIES.contains(result.getItem())) {
                    AVAILABLE_COOKIES.add(result.getItem());
                }
            }
        }
    }

    /**
     * Keeps track of every cookie type the player has ever obtained on the server.<br>
     * Grants the "Bake 'Em All" advancement if all cookies that have valid recipes have been obtained.
     */
    private static void checkCookiesObtained(ServerPlayer serverPlayer, ItemStack craftedItem) {
        CompoundTag persistentData = serverPlayer.getPersistentData();
        CompoundTag modData = persistentData.getCompound(MOD_DATA_KEY);

        // Don't bother checking further if advancement is already granted
        if (modData.getBoolean(HAS_BAKE_EM_ALL_KEY))
            return;

        ListTag listTag = modData.getList(COOKIES_OBTAINED_KEY, Tag.TAG_STRING);

        String itemId = BuiltInRegistries.ITEM.getKey(craftedItem.getItem()).toString();
        boolean exists = false;

        for (int i = 0; i < listTag.size(); i++) {
            String s = listTag.getString(i);

            if (s.equals(itemId)) {
                exists = true;
                break;
            }
        }

        if (!exists) {
            listTag.add(StringTag.valueOf(itemId));
        }
        modData.put(COOKIES_OBTAINED_KEY, listTag);
        persistentData.put(MOD_DATA_KEY, modData);

        List<Item> allCookiesCopy = new ArrayList<>(AVAILABLE_COOKIES);

        for (int i = 0; i < listTag.size(); i++) {
            ResourceLocation id = ResourceLocation.tryParse(listTag.getString(i));
            if (id == null) continue;

            Item item = BuiltInRegistries.ITEM.get(id);

            if (item == Items.AIR) continue;

            allCookiesCopy.remove(item);
        }

        // All cookies have been crafted, grant advancement
        if (allCookiesCopy.isEmpty()) {
            CookieliciousTriggers.SIMPLE_TYPE.get().trigger(serverPlayer, SimpleTypeTrigger.Type.BAKE_EM_ALL);
        }
    }

    /**
     * Simple container listener to check for obtained cookies.
     */
    private static ContainerListener createCookieListener(ServerPlayer serverPlayer) {
        return new ContainerListener() {
            final Player player = serverPlayer;

            @Override
            public void slotChanged(AbstractContainerMenu containerMenu, int slotId, ItemStack itemStack) {
                // Only care about changes made in the slots of the player inventory
                if (containerMenu.getSlot(slotId).container == player.getInventory()) {
                    if (AVAILABLE_COOKIES.contains(itemStack.getItem())) {
                        checkCookiesObtained((ServerPlayer) player, itemStack);
                    }
                }
            }

            @Override
            public void dataChanged(AbstractContainerMenu containerMenu, int dataId, int data) {
            }
        };
    }
}
