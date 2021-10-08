package net.purpura.dimension;

import com.google.common.collect.ImmutableSet;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.village.PointOfInterestType;
import net.purpura.Purpura;

import java.util.Set;
import java.util.function.Predicate;

/**
 * Copyright (c) 08.10.2021
 * Developed by MrMarnic
 * GitHub: https://github.com/MrMarnic
 */
public class PurpuraPOI extends PointOfInterestType {
    public PurpuraPOI() {
        super("purpura_portal_poi", getBlockStates(Purpura.PURPURA_PORTAL.get()), 0, 1);
    }

    public static Set<BlockState> getBlockStates(Block p_221042_0_) {
        return ImmutableSet.copyOf(p_221042_0_.getStateDefinition().getPossibleStates());
    }
}
