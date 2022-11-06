package oceanicvoid.thedeeperdark.systems.quests;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import net.minecraft.advancements.Advancement;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.packs.resources.Resource;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import oceanicvoid.thedeeperdark.TheDeeperDark;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class QuestHandler {
	private static Map<ResourceLocation, Resource> questResourceMap;
	private static MinecraftServer mcServer;

	@SubscribeEvent
	public static void onServerStarting(ServerStartingEvent event) {
		mcServer = event.getServer();
		questResourceMap = mcServer.getResourceManager().listResources("advancements", (rl) -> rl.getNamespace().equals(TheDeeperDark.MODID));
	}

	public static ResourceLocation getQuestDisplay(String questid) {
		return new ResourceLocation(TheDeeperDark.MODID, "advancements/" + questid + ".json");
	}

	// change to return string
	public static void getQuestName(String questid) {
		ResourceLocation advLoc = getQuestDisplay(questid);
		try {
			InputStream jsonData = questResourceMap.get(advLoc).open();
			JsonElement parsed = JsonParser.parseReader(new InputStreamReader(jsonData));
			String data = parsed.getAsJsonObject().get("quest_name").getAsString();
			System.out.println(data);

		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
