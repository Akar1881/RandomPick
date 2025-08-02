package com.randompick;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;
import java.util.stream.Collectors;

public class RandomPick extends JavaPlugin implements Listener {

    private final Random random = new Random();
    private List<Material> allowedMaterials = new ArrayList<>();

    // Config options
    private int dropChance;
    private int enchantChance;
    private int maxEnchants;
    private boolean allowEnchantedBooks;
    private int enchantedBookChance;

    private String msgReceiveDrop;
    private String msgReceiveEnchanted;
    private String msgReceiveBook;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        loadConfigValues();
        loadAllowedMaterials();

        Bukkit.getPluginManager().registerEvents(this, this);

        getLogger().info("RandomPick enabled! Allowed items count: " + allowedMaterials.size());
    }

    private void loadConfigValues() {
        FileConfiguration config = getConfig();

        dropChance = config.getInt("drop-chance", 100);
        enchantChance = config.getInt("enchant-chance", 30);
        maxEnchants = config.getInt("max-enchants", 3);
        allowEnchantedBooks = config.getBoolean("allow-enchanted-books", true);
        enchantedBookChance = config.getInt("enchanted-book-chance", 10);

        msgReceiveDrop = config.getString("messages.receive-drop", "You received a random drop!");
        msgReceiveEnchanted = config.getString("messages.receive-enchanted-drop", "Lucky! You got an enchanted item!");
        msgReceiveBook = config.getString("messages.receive-enchanted-book", "You found a mysterious enchanted book!");
    }

    private void loadAllowedMaterials() {
        List<String> unwanted = getConfig().getStringList("unwanted-items");
        allowedMaterials = Arrays.stream(Material.values())
                .filter(m -> !unwanted.contains(m.name()))
                .collect(Collectors.toList());

        if (allowedMaterials.isEmpty()) {
            getLogger().warning("No allowed materials found! Please check your config.yml.");
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if (allowedMaterials.isEmpty()) return;

        // Roll chance to replace drop
        if (random.nextInt(100) >= dropChance) return; // No replacement this time

        event.setDropItems(false); // Cancel normal drops

        ItemStack dropItem;

        // Check if we should drop enchanted book instead of normal item
        if (allowEnchantedBooks && random.nextInt(100) < enchantedBookChance) {
            dropItem = createRandomEnchantedBook();
            event.getPlayer().sendMessage(msgReceiveBook);
        } else {
            // Pick random normal item
            Material mat = allowedMaterials.get(random.nextInt(allowedMaterials.size()));
            dropItem = new ItemStack(mat, 1);

            // Possibly add enchantments if item can be enchanted
            if (random.nextInt(100) < enchantChance && canBeEnchanted(mat)) {
                addRandomEnchantments(dropItem);
                event.getPlayer().sendMessage(msgReceiveEnchanted);
            } else {
                event.getPlayer().sendMessage(msgReceiveDrop);
            }
        }

        // Drop item in world at broken block location
        event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(), dropItem);
    }

    private boolean canBeEnchanted(Material material) {
        // Check common enchantable items (tools, armor, weapons)
        switch (material) {
            case WOODEN_SWORD, STONE_SWORD, IRON_SWORD, DIAMOND_SWORD, NETHERITE_SWORD,
                 WOODEN_PICKAXE, STONE_PICKAXE, IRON_PICKAXE, DIAMOND_PICKAXE, NETHERITE_PICKAXE,
                 WOODEN_AXE, STONE_AXE, IRON_AXE, DIAMOND_AXE, NETHERITE_AXE,
                 WOODEN_SHOVEL, STONE_SHOVEL, IRON_SHOVEL, DIAMOND_SHOVEL, NETHERITE_SHOVEL,
                 WOODEN_HOE, STONE_HOE, IRON_HOE, DIAMOND_HOE, NETHERITE_HOE,
                 LEATHER_HELMET, CHAINMAIL_HELMET, IRON_HELMET, DIAMOND_HELMET, NETHERITE_HELMET,
                 LEATHER_CHESTPLATE, CHAINMAIL_CHESTPLATE, IRON_CHESTPLATE, DIAMOND_CHESTPLATE, NETHERITE_CHESTPLATE,
                 LEATHER_LEGGINGS, CHAINMAIL_LEGGINGS, IRON_LEGGINGS, DIAMOND_LEGGINGS, NETHERITE_LEGGINGS,
                 LEATHER_BOOTS, CHAINMAIL_BOOTS, IRON_BOOTS, DIAMOND_BOOTS, NETHERITE_BOOTS,
                 BOW, CROSSBOW, TRIDENT, FISHING_ROD, SHIELD -> {
                return true;
            }
            default -> {
                return false;
            }
        }
    }

    private void addRandomEnchantments(ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return;

        // Get all enchantments that can be applied to the item
        List<Enchantment> possibleEnchants = Arrays.stream(Enchantment.values())
                .filter(Objects::nonNull)
                .filter(enchant -> enchant.canEnchantItem(item))
                .collect(Collectors.toList());

        if (possibleEnchants.isEmpty()) return;

        int enchantsCount = 1 + random.nextInt(maxEnchants); // at least 1 enchant

        for (int i = 0; i < enchantsCount; i++) {
            Enchantment enchant = possibleEnchants.get(random.nextInt(possibleEnchants.size()));
            int level = 1 + random.nextInt(enchant.getMaxLevel());

            meta.addEnchant(enchant, level, true);
        }

        item.setItemMeta(meta);
    }

    private ItemStack createRandomEnchantedBook() {
        ItemStack book = new ItemStack(Material.ENCHANTED_BOOK);
        EnchantmentStorageMeta meta = (EnchantmentStorageMeta) book.getItemMeta();

        if (meta == null) return book;

        List<Enchantment> enchantments = Arrays.stream(Enchantment.values())
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        int enchantsCount = 1 + random.nextInt(maxEnchants);

        for (int i = 0; i < enchantsCount; i++) {
            Enchantment enchant = enchantments.get(random.nextInt(enchantments.size()));
            int level = 1 + random.nextInt(enchant.getMaxLevel());

            meta.addStoredEnchant(enchant, level, true);
        }

        book.setItemMeta(meta);
        return book;
    }
}