package world.lemmy.rookissurvey.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import world.lemmy.rookissurvey.inventory.InventoryUtils;

public class surveyCommandExecutor implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player p){
            InventoryUtils.openSurveyInventory(p);
        }
        else{
            sender.sendMessage("You must be a player to use this command");
        }
        return true;
    }
}
