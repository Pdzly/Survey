package world.lemmy.rookissurvey.models;

import javax.annotation.Nullable;
import java.util.Date;
import java.util.UUID;

public class SurveyModel {
    private String question;
    private String answer;
    private UUID playerUUID;

    private Date dateCreated;

    public SurveyModel(String question, String answer, UUID playerUUID, Date dateCreated) {
        this.question = question;
        this.answer = answer;
        this.playerUUID = playerUUID;
        this.dateCreated = dateCreated;

    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    @Nullable
    public String getAnswer() {
        return answer;
    }

    public void setAnswer(@Nullable String answer) {
        this.answer = answer;
    }

    public UUID getPlayerUUID() {
        return playerUUID;
    }

    public void setPlayerUUID(UUID playerUUID) {
        this.playerUUID = playerUUID;
    }
}
