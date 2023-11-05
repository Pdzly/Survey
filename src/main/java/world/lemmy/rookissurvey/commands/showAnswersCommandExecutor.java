package world.lemmy.rookissurvey.commands;

import me.kodysimpson.simpapi.exceptions.MenuManagerException;
import me.kodysimpson.simpapi.exceptions.MenuManagerNotSetupException;
import me.kodysimpson.simpapi.menu.MenuManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import world.lemmy.rookissurvey.menu.ListSurveyAnswers;

public class showAnswersCommandExecutor implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player p){
            try {
                MenuManager.openMenu(ListSurveyAnswers.class, p);
            } catch (MenuManagerException | MenuManagerNotSetupException e) {
                e.printStackTrace();
            }
        }
        else{
            sender.sendMessage("You must be a player to use this command");
        }
        return true;
    }
}
