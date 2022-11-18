package mc.server.survival.libraries;

import mc.server.Logger;
import mc.server.survival.files.Main;
import mc.server.survival.libraries.chemistry.Chemistries;
import mc.server.survival.libraries.chemistry.ChemistryDrug;
import mc.server.survival.managers.FileManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Map;

public class RecipeLib
{
	private RecipeLib() {}

	static RecipeLib instance = new RecipeLib();

	public static RecipeLib getInstance()
	{
		return instance;
	}

	private static int loadedRecipes = 0;

	public void reloadRecipes()
	{
		if (((ArrayList<String>) FileManager.getInstance().recipes().get("recipes.enabled")).contains("1"))
		{
			Bukkit.removeRecipe(new NamespacedKey(Main.getInstance(), "1"));
			createRecipe(structureRecipe(toItemStack(Material.SADDLE), "1",
					Map.of('L', Material.LEATHER,
							'S', Material.STRING,
							'I', Material.IRON_INGOT,
							'0', Material.AIR),
					"LLL", "S0S", "I0I"));
		}
		else
			Bukkit.removeRecipe(new NamespacedKey(Main.getInstance(), "1"));

		if (((ArrayList<String>) FileManager.getInstance().recipes().get("recipes.enabled")).contains("2"))
		{
			Bukkit.removeRecipe(new NamespacedKey(Main.getInstance(), "2"));
			createRecipe(structureRecipe(toItemStack(Material.NAME_TAG), "2",
					Map.of('P', Material.PAPER,
							'S', Material.STRING,
							'I', Material.IRON_INGOT),
					"SIS", "PPP", "SIS"));
		}
		else
			Bukkit.removeRecipe(new NamespacedKey(Main.getInstance(), "2"));

		if (((ArrayList<String>) FileManager.getInstance().recipes().get("recipes.enabled")).contains("3"))
		{
			Bukkit.removeRecipe(new NamespacedKey(Main.getInstance(), "3"));
			createRecipe(structureRecipe(toItemStack(Material.IRON_HORSE_ARMOR), "3",
					Map.of('D', Material.IRON_INGOT,
							'0', Material.AIR,
							'S', Material.SADDLE),
					"00D", "DSD", "D00"));
		}
		else
			Bukkit.removeRecipe(new NamespacedKey(Main.getInstance(), "3"));

		if (((ArrayList<String>) FileManager.getInstance().recipes().get("recipes.enabled")).contains("4"))
		{
			Bukkit.removeRecipe(new NamespacedKey(Main.getInstance(), "4"));
			createRecipe(structureRecipe(toItemStack(Material.GOLDEN_HORSE_ARMOR), "4",
					Map.of('D', Material.GOLD_INGOT,
							'0', Material.AIR,
							'S', Material.SADDLE),
					"00D", "DSD", "D00"));
		}
		else
			Bukkit.removeRecipe(new NamespacedKey(Main.getInstance(), "4"));

		if (((ArrayList<String>) FileManager.getInstance().recipes().get("recipes.enabled")).contains("5"))
		{
			Bukkit.removeRecipe(new NamespacedKey(Main.getInstance(), "5"));
			createRecipe(structureRecipe(toItemStack(Material.DIAMOND_HORSE_ARMOR), "5",
					Map.of('D', Material.DIAMOND,
							'0', Material.AIR,
							'S', Material.SADDLE),
					"00D", "DSD", "D00"));
		}
		else
			Bukkit.removeRecipe(new NamespacedKey(Main.getInstance(), "5"));

		if (((ArrayList<String>) FileManager.getInstance().recipes().get("recipes.enabled")).contains("6"))
		{
			Bukkit.removeRecipe(new NamespacedKey(Main.getInstance(), "6"));
			createRecipe(structureRecipe(toItemStack(Material.LEATHER), "6", Map.of('R', Material.ROTTEN_FLESH), "RRR", "RRR", "RRR"));
		}
		else
			Bukkit.removeRecipe(new NamespacedKey(Main.getInstance(), "6"));

		if (((ArrayList<String>) FileManager.getInstance().recipes().get("recipes.enabled")).contains("7"))
		{
			Bukkit.removeRecipe(new NamespacedKey(Main.getInstance(), "7"));
			createRecipe(structureRecipe(toItemStack(Material.SPECTRAL_ARROW), "7",
					Map.of('0', Material.AIR,
							'F', Material.FLINT,
							'B', Material.BLAZE_ROD,
							'P', Material.FEATHER),
					"0F0", "0B0", "0P0"));
		}
		else
			Bukkit.removeRecipe(new NamespacedKey(Main.getInstance(), "7"));

		if (((ArrayList<String>) FileManager.getInstance().recipes().get("recipes.enabled")).contains("8"))
		{
			Bukkit.removeRecipe(new NamespacedKey(Main.getInstance(), "8"));
			createRecipe(structureRecipe(toItemStack(Material.EXPERIENCE_BOTTLE), "8",
					Map.of('G', Material.GLOWSTONE_DUST,
							'I', Material.IRON_NUGGET,
							'B', Material.GLASS_BOTTLE,
							'0', Material.AIR),
					"0G0", "IBI", "0G0"));
		}
		else
			Bukkit.removeRecipe(new NamespacedKey(Main.getInstance(), "8"));

		if (((ArrayList<String>) FileManager.getInstance().recipes().get("recipes.enabled")).contains("9"))
		{
			Bukkit.removeRecipe(new NamespacedKey(Main.getInstance(), "9"));
			createRecipe(structureRecipe(toItemStack(Material.BUNDLE), "9",
					Map.of('S', Material.STRING,
							'L', Material.LEATHER,
							'C', Material.CHEST),
					"LSL", "LCL", "LLL"));
		}
		else
			Bukkit.removeRecipe(new NamespacedKey(Main.getInstance(), "9"));

		if (((ArrayList<String>) FileManager.getInstance().recipes().get("recipes.enabled")).contains("10"))
		{
			Bukkit.removeRecipe(new NamespacedKey(Main.getInstance(), "10"));
			createRecipe(structureRecipe(ChemistryDrug.getDrug(Chemistries.piwo), "10", Map.of('W', Material.WHEAT, 'B', Material.GLASS_BOTTLE), "WWW", "WBW", "WWW"));
		}
		else
			Bukkit.removeRecipe(new NamespacedKey(Main.getInstance(), "10"));

		if (((ArrayList<String>) FileManager.getInstance().recipes().get("recipes.enabled")).contains("11"))
		{
			Bukkit.removeRecipe(new NamespacedKey(Main.getInstance(), "11"));
			createRecipe(structureRecipe(ChemistryDrug.getDrug(Chemistries.wino), "11", Map.of('A', Material.AIR, 'S', Material.SWEET_BERRIES, 'V', Material.SUGAR, 'B', Material.GLASS_BOTTLE), "ASA", "VBV", "ASA"));
		}
		else
			Bukkit.removeRecipe(new NamespacedKey(Main.getInstance(), "11"));

		if (((ArrayList<String>) FileManager.getInstance().recipes().get("recipes.enabled")).contains("12"))
		{
			Bukkit.removeRecipe(new NamespacedKey(Main.getInstance(), "12"));
			createRecipe(structureRecipe(ChemistryDrug.getDrug(Chemistries.bimber), "12", Map.of('W', Material.HAY_BLOCK, 'A', Material.APPLE, 'G', Material.GOLDEN_APPLE, 'B', Material.GLASS_BOTTLE), "WGW", "GBG", "AGA"));
		}
		else
			Bukkit.removeRecipe(new NamespacedKey(Main.getInstance(), "12"));

		if (((ArrayList<String>) FileManager.getInstance().recipes().get("recipes.enabled")).contains("13"))
		{
			Bukkit.removeRecipe(new NamespacedKey(Main.getInstance(), "13"));
			createRecipe(structureRecipe(new ItemLib().get("aether:crystal_of_keeping"), "13",
					Map.of('A', Material.AIR,
							'G', Material.GOLD_BLOCK,
							'E', Material.EMERALD_BLOCK,
							'H', new RecipeChoice.ExactChoice(new ItemLib().get("aether:angel_heart"))),
					"AGA", "EHE", "AGA"));
		}
		else
			Bukkit.removeRecipe(new NamespacedKey(Main.getInstance(), "13"));
		
		Main.getInstance().getAdapter().setCraftings(loadedRecipes);
		loadedRecipes = 0;
	}

	public static ItemStack toItemStack(final Material material)
	{
		return new ItemStack(material, 1);
	}

	private static void createRecipe(ShapedRecipe recipe)
	{
		Bukkit.addRecipe(recipe); loadedRecipes++;
	}

	public static ShapedRecipe structureRecipe(final ItemStack itemToRecipe, final String ID, @NotNull Map ingredients, String... shape)
	{
		final NamespacedKey key = new NamespacedKey(Main.getInstance(), ID);
		ShapedRecipe recipe = new ShapedRecipe(key, itemToRecipe);

		try
		{
			recipe.shape(shape);

			for (String keySet : shape)
				for (char keyMap : keySet.toCharArray())
				{
					final Object recipeChoice = ingredients.get(keyMap);
					final boolean isMaterial = recipeChoice instanceof Material;

					if (isMaterial) recipe.setIngredient(keyMap, (Material) ingredients.get(keyMap));
					else recipe.setIngredient(keyMap, (RecipeChoice) ingredients.get(keyMap));
				}
		}
		catch (Exception e) { Logger.log("&cWystapil problem podczas tworzenia receptury."); }

		return recipe;
	}
}