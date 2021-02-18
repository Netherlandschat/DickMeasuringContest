package nl.thedutchmc.dickmeasuringcontest;

import net.dv8tion.jda.api.requests.GatewayIntent;
import nl.thedutchmc.dickmeasuringcontest.commands.DickCommandExecutor;
import nl.thedutchmc.netherlandsbot.annotations.RegisterBotModule;
import nl.thedutchmc.netherlandsbot.modules.BotModule;

@RegisterBotModule(name = "DickMeasuringContest", version = "1.0.0", intents = { GatewayIntent.GUILD_MESSAGES })
public class DickMeasuringContest extends BotModule {

	@Override
	public void onLoad() {
		super.logInfo("Initializing...");
		
		super.registerCommandListener("$dick", new DickCommandExecutor());
	
		super.logInfo("Loading complete.");
	}
}
