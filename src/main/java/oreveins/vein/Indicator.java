package oreveins.vein;

import java.util.List;
import java.util.Random;
import javax.annotation.Nonnull;

import net.minecraft.block.state.IBlockState;

import com.typesafe.config.Config;
import oreveins.util.ConfigHelper;

public class Indicator
{
    public final int maxDepth;
    public final float chance;
    public final boolean ignoreVegetation;
    public final boolean ignoreLiquids;
    public final boolean hasUnderCondition;

    private final List<IBlockState> states;
    private final List<IBlockState> underStates;

    Indicator(Config config) throws IllegalArgumentException
    {
        this.states = ConfigHelper.getBlockStateList(config, "blocks");
        this.maxDepth = ConfigHelper.getValue(config, "max_depth", 32);
        this.chance = 1f / (float) ConfigHelper.getValue(config, "rarity", 10);
        this.ignoreVegetation = ConfigHelper.getBoolean(config, "ignore_vegetation", true);
        this.ignoreLiquids = ConfigHelper.getBoolean(config, "ignore_liquids", false);

        this.hasUnderCondition = config.hasPath("blocks_under");
        this.underStates = hasUnderCondition ? ConfigHelper.getBlockStateList(config, "blocks_under") : null;
    }

    @Nonnull
    public IBlockState getStateToGenerate(Random random)
    {
        return states.get(random.nextInt(states.size()));
    }

    public boolean validUnderState(IBlockState state)
    {
        return underStates.contains(state);
    }
}
