package net.purpura.armor;

import java.util.function.Supplier;

import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemTier;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.LazyValue;
import net.purpura.Purpura;

public enum PurpuraItemTier implements IItemTier {
    KUNZIT(4, 3031, 10.0F, 5.5F, 20, () -> {
        return Ingredient.of(Purpura.KUNZIT.get());
    }),
    TETRAEDIT(4, 2031, 9.0F, 4.0F, 15, () -> {
        return Ingredient.of(Purpura.TETRAEDIT.get());
    }),
    TETRAEDIT_KUNZIT(4, 2531, 9.5F, 4.5F, 17, () -> {
        return Ingredient.of(Purpura.KUNZIT.get()); /* TODO! */
    });

    private final int level;
    private final int uses;
    private final float speed;
    private final float damage;
    private final int enchantmentValue;
    private final LazyValue<Ingredient> repairIngredient;

     PurpuraItemTier(int p_i48458_3_, int p_i48458_4_, float p_i48458_5_, float p_i48458_6_, int p_i48458_7_, Supplier<Ingredient> p_i48458_8_) {
        this.level = p_i48458_3_;
        this.uses = p_i48458_4_;
        this.speed = p_i48458_5_;
        this.damage = p_i48458_6_;
        this.enchantmentValue = p_i48458_7_;
        this.repairIngredient = new LazyValue<>(p_i48458_8_);
    }

    public int getUses() {
        return this.uses;
    }

    public float getSpeed() {
        return this.speed;
    }

    public float getAttackDamageBonus() {
        return this.damage;
    }

    public int getLevel() {
        return this.level;
    }

    public int getEnchantmentValue() {
        return this.enchantmentValue;
    }

    public Ingredient getRepairIngredient() {
        return this.repairIngredient.get();
    }
}