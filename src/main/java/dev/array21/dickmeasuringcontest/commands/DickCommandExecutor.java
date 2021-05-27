package dev.array21.dickmeasuringcontest.commands;

import java.util.Random;

import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.exceptions.ErrorResponseException;
import nl.thedutchmc.netherlandsbot.commands.CommandData;
import nl.thedutchmc.netherlandsbot.commands.ModuleCommandListener;

public class DickCommandExecutor extends ModuleCommandListener {
	
	@Override
	public void onCommand(CommandData commandData) {
		String[] arguments = commandData.getArgumentsRaw();
		String username, discriminator;
		long id;
		if(arguments.length > 0) {
			String arg1 = arguments[0];
			if(arg1.length() < 4) {
				System.out.println(arg1);
				commandData.getChannel().sendMessage("The argument you provided is not valid.").queue();
				return;
			}
			
			String idSubstr = arg1.substring(3).replaceAll(">", "").strip();
			if(!idSubstr.chars().allMatch(Character::isDigit)) {
				System.out.println(idSubstr);
				commandData.getChannel().sendMessage("The argument you provided is not valid.").queue();
				return;
			}
			
			long idLong = Long.valueOf(idSubstr);
			User u; 
			try {
				u = commandData.getGuild().retrieveMemberById(idLong).complete().getUser();
			} catch(ErrorResponseException e) {
				switch(e.getErrorResponse()) {
				case UNKNOWN_USER:
				case UNKNOWN_MEMBER:
					commandData.getChannel().sendMessage("Couldn't find user.").queue();
					break;
				default:
					break;
				}
				
				return;
			}
			username = u.getName();
			discriminator = u.getDiscriminator();
			id = idLong;
		} else {
			User u = commandData.getMember().getUser();
			username = u.getName();
			discriminator = u.getDiscriminator();
			id = u.getIdLong();
		}

		long usernameLong = mapStringToLong(username);
		long discriminatorLong = mapStringToLong(discriminator);
		
		Random r = new Random(usernameLong + discriminatorLong + id);
		int a = r.nextInt(17);
		
		String dickStr = "8";
		for(int i = 0; i < a; i++) {
			dickStr += "=";
		}
		
		dickStr += "D";
		
		String message = String.format("**%s's size:**\n"
				+ dickStr, username);
		
		commandData.getChannel().sendMessage(message).queue();
	}
	
	private long mapStringToLong(String a) {
		long returnValue = 0;
		for(char c : a.toCharArray()) {
			returnValue += (int) c;
		}
		
		return returnValue;
	}
}
