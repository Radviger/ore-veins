/*
 * Part of the Ore Veins Mod by alcatrazEscapee
 * Work under Copyright. Licensed under the GPL-3.0.
 * See the project LICENSE.md for more information.
 */

package com.alcatrazescapee.oreveins.api;

import java.util.Collection;
import java.util.List;
import java.util.Random;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;

import com.alcatrazescapee.oreveins.vein.Indicator;

/**
 * This is a template for a vein type. It represents a "type" value as specified in JSON.
 * See {@link IVein} for proper procedure on adding a new {@link IVeinType} class
 * Register vein types via the static initializer in {@link com.alcatrazescapee.oreveins.vein.VeinRegistry}
 *
 * @param <V> the vein implementation
 */
@ParametersAreNonnullByDefault
public interface IVeinType<V extends IVein<?>>
{
    /**
     * Gets the state to generate at a point.
     * Handled by {@link AbstractVeinType} using a weighted list
     *
     * @param rand A random to use in generation
     * @return A block state
     */
    @Nonnull
    IBlockState getStateToGenerate(Random rand);

    /**
     * Gets all possible ore states spawned by this vein.
     * Used for command vein searching / world stripping
     *
     * @return a collection of block states
     */
    @Nonnull
    Collection<IBlockState> getOreStates();

    /**
     * Gets the indicator for this vein type
     *
     * @return An IIndicator if it exists, or null if not
     */
    @Nullable
    Indicator getIndicator();

    /**
     * If the vein can generate on the previous state
     *
     * @param state the current state
     * @return if the vein can generate
     */
    boolean canGenerateIn(IBlockState state);

    /**
     * Is the vein in range of a vertical column with specific offsets
     * This should be a simple check for optimization purposes
     *
     * @param vein    The vein instance
     * @param xOffset The x offset
     * @param zOffset The y offset
     * @return if the vein can generate any blocks in this column
     */
    boolean inRange(V vein, int xOffset, int zOffset);

    /**
     * Gets the chance to generate at a specific location
     *
     * @param vein the vein instance
     * @param pos  the position
     * @return a chance: 0 = 0% chance, 1 = 100% chance
     */
    double getChanceToGenerate(V vein, BlockPos pos);

    /**
     * Check if the dimension is valid for this vein
     *
     * @param dimensionID a dimension ID
     * @return true if the dimension is valid
     */
    boolean matchesDimension(int dimensionID);

    /**
     * Check if the biome is valid for this vein
     *
     * @param biome a biome
     * @return true if the biome is valid
     */
    boolean matchesBiome(Biome biome);

    /**
     * Verify that the vein is a valid vein (after loading from JSON)
     *
     * @return true if the vein is valid
     */
    boolean isValid();

    /**
     * Gets the min Y which this vein can spawn at
     *
     * @return a Y position
     */
    int getMinY();

    /**
     * Gets the max Y which this vein can spawn at
     *
     * @return a Y position
     */
    int getMaxY();

    /**
     * Gets the max chunk radius that this vein needs to check
     *
     * @return a radius in chunks
     */
    int getChunkRadius();

    /**
     * Adds all veins from a specific chunk to the list
     *
     * @param veins  the list of all veins
     * @param chunkX the chunkX
     * @param chunkZ the chunkZ
     * @param rand   a random to use in generation
     */
    void addVeins(List<IVein> veins, int chunkX, int chunkZ, Random rand);

}