package com.lance5057.extradelight.util;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonObject;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.fluids.FluidStack;

public class FluidIngredientCompat {
    private final Fluid fluid;
    private final int amount;
    private final FluidStack cachedStack;
    private TagKey<Fluid> tag;

    private FluidIngredientCompat(Fluid fluid, int amount) {
        this.fluid = fluid;
        this.amount = amount;
        this.cachedStack = new FluidStack(fluid, amount);
    }

    private FluidIngredientCompat(TagKey<Fluid> tag, int amount) {
        this.tag = tag;
        this.fluid = null;
        this.amount = amount;
        this.cachedStack = null;
    }

    public static FluidIngredientCompat fromFluid(Fluid fluid, int amount) {
        return new FluidIngredientCompat(fluid, amount);
    }

    public static FluidIngredientCompat fromFluidStack(FluidStack stack) {
        FluidIngredientCompat compat = new FluidIngredientCompat(stack.getFluid(), stack.getAmount());
        return compat;
    }

    public static FluidIngredientCompat fromTag(TagKey<Fluid> tag, int amount) {
        return new FluidIngredientCompat(tag, amount);
    }

    public List<FluidStack> getMatchingFluidStacks() {
        List<FluidStack> list = new ArrayList<>();
        if (cachedStack != null) {
            list.add(cachedStack);
        }
        return list;
    }

    public boolean test(FluidStack stack) {
        if (fluid != null) {
            return stack.getFluid() == fluid;
        }
        return false;
    }

    public Fluid getFluid() {
        return fluid;
    }

    public int getAmount() {
        return amount;
    }

    public int getRequiredAmount() {
        return amount;
    }

    public FluidStack getFluidStack() {
        return cachedStack;
    }

    public JsonObject serialize() {
        JsonObject json = new JsonObject();
        if (tag != null) {
            json.addProperty("tag", tag.location().toString());
        } else if (fluid != null) {
            json.addProperty("fluid", ForgeRegistries.FLUIDS.getKey(fluid).toString());
        }
        json.addProperty("amount", amount);
        return json;
    }
}