package bleach.hack.utils;

import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.AmbientEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.WaterMobEntity;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.text.TextFormatting;

public class EntityUtils {

	public static List<Entity> getLoadedEntities() {
		return Minecraft.getInstance().world.getEntitiesWithinAABBExcludingEntity(
				null, new AxisAlignedBB(
						Minecraft.getInstance().player.posX - 128, 0,
						Minecraft.getInstance().player.posZ - 128,
						Minecraft.getInstance().player.posX + 128, 256,
						Minecraft.getInstance().player.posZ + 128));
	}
	
	public static boolean isAnimal(Entity e) {
		return e instanceof AnimalEntity || e instanceof AmbientEntity
		|| e instanceof WaterMobEntity;
	}

	public static void setGlowing(Entity entity, TextFormatting color, String teamName) {
		Minecraft mc = Minecraft.getInstance();
		ScorePlayerTeam team = mc.world.getScoreboard().getTeamNames().contains(teamName) ?
				mc.world.getScoreboard().getTeam(teamName) :
				mc.world.getScoreboard().createTeam(teamName);
        
		mc.world.getScoreboard().addPlayerToTeam(entity.getScoreboardName(), team);
		mc.world.getScoreboard().getTeam(teamName).setColor(color);
		
		entity.setGlowing(true);
	}
	
}