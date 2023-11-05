package world.lemmy.rookissurvey;

import io.papermc.paper.event.player.AsyncChatEvent;
import me.kodysimpson.simpapi.menu.MenuManager;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import world.lemmy.rookissurvey.commands.showAnswersCommandExecutor;
import world.lemmy.rookissurvey.commands.surveyCommandExecutor;
import world.lemmy.rookissurvey.inventory.InventoryUtils;
import world.lemmy.rookissurvey.service.SurveyService;

public final class RookisSurvey extends JavaPlugin implements Listener {

    private SurveyService surveyService = new SurveyService(this);

    @Override
    public void onEnable() {
        MenuManager.setup(getServer(), this);
        getCommand("survey").setExecutor(new surveyCommandExecutor());
        getCommand("showanswers").setExecutor(new showAnswersCommandExecutor());

        getServer().getPluginManager().registerEvents(this, this);
        getServer().getPluginManager().registerEvents(new InventoryUtils(surveyService), this);

        try {
            surveyService.loadSurveys();
        } catch (Exception e) {
            getLogger().warning("Failed to load surveys!");
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
