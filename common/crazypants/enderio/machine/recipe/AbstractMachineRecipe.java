package crazypants.enderio.machine.recipe;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.ItemStack;
import crazypants.enderio.machine.IMachineRecipe;
import crazypants.enderio.machine.MachineRecipeInput;

public abstract class AbstractMachineRecipe implements IMachineRecipe {

  @Override
  public float getEnergyRequired(MachineRecipeInput... inputs) {
    if(inputs == null || inputs.length <= 0) {
      return 0;
    }
    IRecipe recipe = getRecipeForInputs(inputs);
    return recipe == null ? 0 : recipe.getEnergyRequired();
  }

  public abstract IRecipe getRecipeForInputs(MachineRecipeInput[] inputs);

  @Override
  public MachineRecipeInput[] getQuantitiesConsumed(MachineRecipeInput[] inputs) {
    MachineRecipeInput[] res = new MachineRecipeInput[inputs.length];
    int i = 0;
    for (MachineRecipeInput input : inputs) {
      ItemStack used = input.item.copy();
      used.stackSize = 1;
      MachineRecipeInput ri = new MachineRecipeInput(input.slotNumber, used);
      res[i] = ri;
      i++;
    }
    return res;
  }

  @Override
  public float getExperianceForOutput(ItemStack output) {
    return 0;
  }

  @Override
  public boolean isRecipe(MachineRecipeInput... inputs) {
    if(inputs == null || inputs.length <= 0) {
      return false;
    }
    IRecipe recipe = getRecipeForInputs(inputs);
    return recipe != null;
  }

  @Override
  public ItemStack[] getCompletedResult(float chance, MachineRecipeInput... inputs) {
    if(inputs == null || inputs.length <= 0) {
      return new ItemStack[0];
    }
    IRecipe recipe = getRecipeForInputs(inputs);
    if(recipe == null) {
      return new ItemStack[0];
    }
    RecipeOutput[] outputs = recipe.getOutputs();
    if(outputs == null) {
      return new ItemStack[0];
    }
    List<ItemStack> result = new ArrayList<ItemStack>();
    for (RecipeOutput output : outputs) {
      if(output.getChance() >= chance) {
        result.add(output.getOutput());
      }
    }
    return result.toArray(new ItemStack[result.size()]);

  }

}
