package world.lemmy.rookissurvey.inventory;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import world.lemmy.rookissurvey.RookisSurvey;
import world.lemmy.rookissurvey.service.SurveyService;

import java.util.List;

public class InventoryUtils implements Listener {

    private SurveyService surveyService;
    public InventoryUtils(SurveyService service){
        this.surveyService = service;
    }

    public static void openSurveyInventory(Player p) {
        Inventory inv = Bukkit.createInventory(p, 27, "Survey");

        ItemStack decoy = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);

        ItemMeta meta = decoy.getItemMeta();
        meta.setDisplayName("Never gona give you up");
        decoy.setItemMeta(meta);

        for (int i = 0; i < inv.getSize(); i++) {
            inv.setItem(i, decoy);
        }
        ItemStack question = new ItemStack(Material.BOOK);

        ItemMeta questionMeta = question.getItemMeta();
        questionMeta.setDisplayName("A question:");
        questionMeta.setLore(List.of("From where do you know this server?"));
        questionMeta.addEnchant(Enchantment.DURABILITY, 1, true);
        questionMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

        question.setItemMeta(questionMeta);

        inv.setItem(4, question);


        ItemStack friend = new ItemStack(Material.CAKE);
        ItemStack announcement = new ItemStack(Material.PAPER);

        ItemMeta friendMeta = friend.getItemMeta();
        friendMeta.setDisplayName("From a friend");
        friendMeta.addEnchant(Enchantment.DURABILITY, 1, true);
        friendMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

        friend.setItemMeta(friendMeta);

        inv.setItem(12, friend);


        ItemMeta announcementMeta = announcement.getItemMeta();
        announcementMeta.setDisplayName("From the lemmy.world announcement");
        announcementMeta.addEnchant(Enchantment.DURABILITY, 1, true);
        announcementMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

        announcement.setItemMeta(announcementMeta);

        inv.setItem(14, announcement);


        p.openInventory(inv);
    }

    @EventHandler
    public void handleChoose(InventoryClickEvent e) {
        if(e.getView().getTitle() == "Survey"){
            e.setCancelled(true);
            if(surveyService.getSurveyByPlayerUUID((Player) e.getWhoClicked()) != null){
                e.getWhoClicked().closeInventory();
                e.getWhoClicked().sendMessage("You already answered the survey!");
                return;
            }
            if(e.getCurrentItem() == null || e.getCurrentItem().getType() == Material.GRAY_STAINED_GLASS_PANE){
                return;
            }
            if(e.getCurrentItem().getType() == Material.CAKE){
                surveyService.addSurvey((Player) e.getWhoClicked(), "From a friend");
            }else if(e.getCurrentItem().getType() == Material.PAPER){
                surveyService.addSurvey((Player) e.getWhoClicked(), "From the lemmy.world announcement");
            }
            e.getWhoClicked().sendMessage("Thanks for participating!");
            e.getWhoClicked().closeInventory();
        }
    }
}
