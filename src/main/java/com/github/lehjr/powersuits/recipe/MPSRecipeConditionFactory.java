package com.github.lehjr.powersuits.recipe;

import com.github.lehjr.powersuits.config.MPSSettings;
import com.github.lehjr.powersuits.constants.MPSConstants;
import com.google.gson.JsonObject;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.conditions.ICondition;
import net.minecraftforge.common.crafting.conditions.IConditionSerializer;

public class MPSRecipeConditionFactory implements ICondition {
    static final ResourceLocation NAME = new ResourceLocation(MPSConstants.MOD_ID, "flag");

    String conditionName;

    public MPSRecipeConditionFactory(String conditionName) {
        this.conditionName = conditionName;
    }

    @Override
    public ResourceLocation getID() {
        return NAME;
    }

    @Override
    public boolean test() {
        switch (conditionName) {
            // Vanilla reciples only as fallback
            case "vanilla_recipes_enabled": {
                return (MPSSettings.useVanillaRecipes());
            }
        }
        return false;
    }

    public static class Serializer implements IConditionSerializer<MPSRecipeConditionFactory> {

        public static final Serializer INSTANCE = new Serializer();

        @Override
        public void write(JsonObject json, MPSRecipeConditionFactory value) {
            // Don't think anything else needs to be added here, as this is now working

//            System.out.println("json: " + json.toString());
//            System.out.println("value: " + value.conditionName);
//            json.addProperty("condition", value.conditionName);
        }

        @Override
        public MPSRecipeConditionFactory read(JsonObject json) {
            return new MPSRecipeConditionFactory(JSONUtils.getString(json, "flag"));
        }

        @Override
        public ResourceLocation getID() {
            return MPSRecipeConditionFactory.NAME;
        }
    }
}