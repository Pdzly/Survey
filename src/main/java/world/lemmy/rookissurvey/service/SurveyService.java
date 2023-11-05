package world.lemmy.rookissurvey.service;

import com.google.gson.Gson;
import org.bukkit.entity.Player;
import world.lemmy.rookissurvey.RookisSurvey;
import world.lemmy.rookissurvey.models.SurveyModel;

import javax.annotation.Nullable;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class SurveyService {
    private RookisSurvey plugin;

    public static SurveyService instance;
    public SurveyService(RookisSurvey plugin) {
        instance = this;
        this.plugin = plugin;
    }

    private ArrayList<SurveyModel> surveys = new ArrayList<SurveyModel>();

    public SurveyModel addSurvey(Player p, String answer) {

        SurveyModel survey = new SurveyModel("From where do you know this server?", answer, p.getUniqueId(), new Date());
        addSurvey(survey);

        return survey;
    }

    public void addSurvey(SurveyModel survey) {
        surveys.add(survey);
        try {
            saveSurveys();
        } catch (IOException e) {
            plugin.getLogger().warning("Failed to save surveys!");
        }
    }

    public ArrayList<SurveyModel> getSurveys() {
        return surveys;
    }

    public @Nullable SurveyModel getSurveyByPlayerUUID(Player p) {
        for (SurveyModel survey : surveys) {
            if (survey.getPlayerUUID().equals(p.getUniqueId())) {
                return survey;
            }
        }
        return null;
    }

    public void saveSurveys() throws IOException {
        Gson gson = new Gson();
        File file = new File(plugin.getDataFolder().getAbsolutePath() + "/surveys.json");
        file.getParentFile().mkdir();
        file.createNewFile();

        Writer writer = new FileWriter(file, false);

        gson.toJson(surveys, writer);

        writer.flush();
        writer.close();

        plugin.getLogger().info("Saved surveys to " + file.getAbsolutePath());
    }

    public void loadSurveys() throws FileNotFoundException {
        Gson gson = new Gson();
        File file = new File(plugin.getDataFolder().getAbsolutePath() + "/surveys.json");
        if (!file.exists()) {
            plugin.getLogger().info("No surveys found");
            return;
        }

        surveys.clear();

        Reader reader = new FileReader(file);
        SurveyModel[] loaded_surveys = gson.fromJson(reader, SurveyModel[].class);
        surveys = new ArrayList<SurveyModel>(Arrays.asList(loaded_surveys));

        plugin.getLogger().info("Loaded surveys from " + file.getAbsolutePath());
    }

    public void deleteNoteByPlayerUUID(Player p) {
        surveys.removeIf(survey -> survey.getPlayerUUID().equals(p.getUniqueId()));
    }


}
