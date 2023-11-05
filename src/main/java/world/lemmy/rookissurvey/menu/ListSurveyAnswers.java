package world.lemmy.rookissurvey.menu;

import me.kodysimpson.simpapi.exceptions.MenuManagerException;
import me.kodysimpson.simpapi.exceptions.MenuManagerNotSetupException;
import me.kodysimpson.simpapi.menu.Menu;
import me.kodysimpson.simpapi.menu.PlayerMenuUtility;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import world.lemmy.rookissurvey.models.SurveyModel;
import world.lemmy.rookissurvey.service.SurveyService;

import java.util.List;

public class ListSurveyAnswers extends Menu {

    public ListSurveyAnswers(PlayerMenuUtility pmu) {
        super(pmu);
    }

    @Override
    public String getMenuName() {
        return "List of Survey Answers";
    }

    @Override
    public int getSlots() {
        return 54;
    }

    @Override
    public boolean cancelAllClicks() {
        return true;
    }

    @Override
    public void handleMenu(InventoryClickEvent e) throws MenuManagerNotSetupException, MenuManagerException {

        if (e.getCurrentItem().getType() == Material.BARRIER){
            playerMenuUtility.getOwner().closeInventory();
        }

    }

    @Override
    public void setMenuItems() {
        setFillerGlass();
        List<SurveyModel> allAnswers = SurveyService.instance.getSurveys();
        for (SurveyModel survey : allAnswers) {
            OfflinePlayer player = Bukkit.getOfflinePlayer(survey.getPlayerUUID());

            ItemStack item = makeItem(Material.PAPER, "Answer from " + player.getName(), survey.getAnswer());

            inventory.addItem(item);
        }

        int fromFriends = 0;
        int fromAnnouncements = 0;

        for (SurveyModel survey : allAnswers) {
            if (survey.getAnswer().equals("From a friend")) {
                fromFriends++;
            }
            else if (survey.getAnswer().equals("From the lemmy.world announcement")) {
                fromAnnouncements++;
            }
        }

        ItemStack friend = makeItem(Material.CAKE, "From friends:", String.valueOf(fromFriends));
        ItemStack announcement = makeItem(Material.PAPER, "From the announcement:", String.valueOf(fromAnnouncements));

        ItemStack close = makeItem(Material.BARRIER, "Close");

        inventory.setItem(49, close);
        inventory.setItem(51, friend);
        inventory.setItem(52, announcement);

    }
}
