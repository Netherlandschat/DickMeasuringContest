package nl.thedutchmc.dickmeasuringcontest.commands;


import net.dv8tion.jda.api.entities.Member;
import nl.thedutchmc.netherlandsbot.commands.CommandData;
import nl.thedutchmc.netherlandsbot.commands.ModuleCommandListener;

public class DickCommandExecutor extends ModuleCommandListener {
	
	@Override
	public void onCommand(CommandData commandData) {
		Member target;
		if(commandData.getArguments().length != 0) {
			String potentialMention = commandData.getArgumentsRaw()[0];			
			
			Member m = commandData.getGuild().retrieveMemberById(potentialMention.substring(3).replace(">", "")).complete();
			if(m == null) {
				commandData.getChannel().sendMessage("I couldn't find that user!").queue();
				return;
			}
			
			target = m;
		} else {
			target = commandData.getMember();
		}
		
		//Get some values about the user
        long id = target.getIdLong();
        String username = target.getUser().getName();
        int disc = Integer.valueOf(target.getUser().getDiscriminator().replace("#", ""));
        
        //Funny bitshifting
		long shiftedRight = id >> 15;
		long shiftedLeft = id << 7;
		
		//Get a long out of the username
        long usrLong = Long.valueOf(stringToLong(username.substring(0, (username.length() > 5) ? 5 : username.length())));
				
        //Do some more math
		long shiftedAvg = Math.abs((shiftedRight + shiftedLeft) / 2);
		long dickLength = Math.abs((shiftedAvg * usrLong) >> 54) / 2 *disc;

		//More than 100 (just to check for 3 digits), get the first 2
		if(dickLength > 100) {
		    dickLength = Long.valueOf(String.valueOf(dickLength).substring(0, 2)) / 2;
		}
	
		//More than 25
		if(dickLength > 20) {
			dickLength /= 2;
			dickLength *= (disc /1000);
		}
		
		//Still more than 25, divide by the 1/1000th of disc
	    if(dickLength > 25) {
	        dickLength = dickLength / (disc /1000);
	    }
		
	    //Under 10
	    if(dickLength <= 5) {
	    	dickLength = (long) Math.round((1 + dickLength) * 2.5);
	    }
	    
	    if(dickLength == 0) {
	    	dickLength++;
	    }
	    		
		String dickStr = "8";
		for(int i = 0; i < dickLength; i++) {
			dickStr += "=";
		}
		
		dickStr += "D";
		
		String message = String.format("**%s's size:**\n"
				+ dickStr, target.getEffectiveName());
		
		commandData.getChannel().sendMessage(message).queue();
	}
	
   public String stringToLong(String string) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < string.length(); ++i) {
            int ch = (int) string.charAt(i);
            if (ch < 100) {
                if(ch<10) {
                	sb.append('0');
                }
                sb.append('0').append(ch);
            } else {
            	sb.append(ch);
            }
        }
        return sb.toString();
    }
}