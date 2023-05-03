package com.team13.piazzapanic;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class HUD implements Disposable {
    public Stage stage;
    public String timeStr;
    public Table table;
    public BitmapFont font;
    Integer repPoints = 3;
    Boolean boost = false;
    Label timeLabelT;
    Label timeLabel;
    Label scoreLabel;
    Label scoreLabelT;
    Label orderNumL;
    Label orderNumLT;
    Label reputation;
    Label reputationT;
    Label powerUpTextLabel;
    private Boolean scenarioComplete;
    private Integer worldTimerM;
    private Integer worldTimerS;
    private Integer score;

    public HUD(SpriteBatch sb) {
        this.scenarioComplete = false;
        worldTimerM = 0;
        worldTimerS = 0;
        score = 0;  // score = money
        timeStr = String.format("%d", worldTimerM) + " : " + String.format("%d", worldTimerS);
        float fontX = 0.5F;
        float fontY = 0.3F;
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        //textButtonStyle.font = Font.ITALIC;
        textButtonStyle.fontColor = Color.WHITE;

        font = new BitmapFont();
        font.getData().setScale(fontX, fontY);
        Viewport viewport = new FitViewport(MainGame.V_WIDTH, MainGame.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, sb);

        table = new Table();
        table.left().top();
        table.setFillParent(true);

        timeLabel = new Label(String.format("%d", worldTimerM, ":", "%i", worldTimerS), new Label.LabelStyle(font, Color.WHITE));
        timeLabelT = new Label("TIME", new Label.LabelStyle(font, Color.BLACK));
        orderNumLT = new Label("ORDER", new Label.LabelStyle(font, Color.BLACK));
        orderNumL = new Label(String.format("%d", 0), new Label.LabelStyle(font, Color.WHITE));

        scoreLabel = new Label(String.format("%d", score), new Label.LabelStyle(font, Color.WHITE));
        scoreLabelT = new Label("MONEY", new Label.LabelStyle(font, Color.BLACK));

        reputationT = new Label("REP", new Label.LabelStyle(font, Color.BLACK));
        reputation = new Label(String.format("%d", repPoints), new Label.LabelStyle(font, Color.WHITE));

        Label powerUpLabel = new Label("PowerUp:", new Label.LabelStyle(font, Color.BLACK));
        powerUpTextLabel = new Label("None", new Label.LabelStyle(font, Color.GREEN));

        table.add(timeLabelT).padTop(2).padLeft(2);
        table.add(scoreLabelT).padTop(2).padLeft(2);
        table.add(orderNumLT).padTop(2).padLeft(2);
        table.add(reputationT).padTop(2).padLeft(2);
        table.row();
        table.add(timeLabel).padTop(2).padLeft(2);
        table.add(scoreLabel).padTop(2).padLeft(2);
        table.add(orderNumL).padTop(2).padLeft(2);
        table.add(reputation).padTop(2).padLeft(2);
        table.row();
        table.add(powerUpLabel).colspan(2).padTop(2).padLeft(2); // add the new label here
        table.add(powerUpTextLabel).padTop(2).padLeft(2);

        table.left().top();
        stage.addActor(table);
    }

    /**
     * Updates the time label.
     *
     * @param scenarioComplete Whether the game scenario has been completed.
     */
    public void updateTime(Boolean scenarioComplete) {
        if (scenarioComplete) {
            timeLabel.setColor(Color.GREEN);
            timeStr = String.format("%d", worldTimerM) + ":" + String.format("%d", worldTimerS);
            timeLabel.setText(String.format("TIME: " + timeStr + " MONEY: %d", score));
            timeLabelT.setText("SCENARIO COMPLETE");
            table.center().top();
            stage.addActor(table);
            return;
        } else {
            if (worldTimerS == 59) {
                worldTimerM += 1;
                worldTimerS = 0;
            } else {
                worldTimerS += 1;
            }
        }
        table.left().top();
        if (worldTimerS < 10) {
            timeStr = String.format("%d", worldTimerM) + ":0" + String.format("%d", worldTimerS);
        } else {
            timeStr = String.format("%d", worldTimerM) + ":" + String.format("%d", worldTimerS);
        }
        timeLabel.setText(timeStr);
        stage.addActor(table);
    }

    /**
     * Calculates the user's score per order and updates the label.
     *
     * @param scenarioComplete Whether the game scenario has been completed.
     * @param expectedTime     The expected time an order should be completed in.
     */
    public void updateScore(Boolean scenarioComplete, Integer expectedTime, float orderTime) {
        int addScore;
        int currentTime;

        if (!this.scenarioComplete) {
            currentTime = (worldTimerM * 60) + worldTimerS;
            if (orderTime > 0) {
                if (boost) {
                    addScore = 100;
                    boost = false;
                } else {
                    addScore = 50;
                }
            } else {
                if (boost) {
                    addScore = 40;
                    boost = false;
                } else {
                    addScore = 20;
                }
                if (currentTime < 0) {
                    addScore = 20;
                }
            }
            score += addScore;
        }

        if (scenarioComplete) {
            updateScore();
            return;
        }

        table.left().top();
        scoreLabel.setText(String.format("%d", score));
        stage.addActor(table);

    }

    private void updateScore() {
        scoreLabel.setColor(Color.GREEN);
        scoreLabel.setText("");
        scoreLabelT.setText("");
        scoreLabelT.remove();
        scoreLabel.remove();
        table.center().top();
        stage.addActor(table);
        this.scenarioComplete = true;
    }

    /**
     * Updates the order label.
     *
     * @param scenarioComplete Whether the game scenario has been completed.
     * @param orderNum         The index number of the order.
     */
    public void updateOrder(Boolean scenarioComplete, Integer orderNum) {
        if (scenarioComplete) {
            orderNumL.remove();
            orderNumLT.remove();
            table.center().top();
            stage.addActor(table);
            return;
        }

        table.left().top();
        orderNumL.setText(String.format("%d", orderNum));
        orderNumLT.setText("ORDER");
        stage.addActor(table);

    }

    public void decrementReps() {
        System.out.println("I ran");
        repPoints--;
        reputation.setText(String.format("%d", repPoints));
        stage.addActor(table);
    }

    public Integer getRepPoints() {
        return repPoints;
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer expectedTime) {
        int addScore;
        int currentTime;

        currentTime = (worldTimerM * 60) + worldTimerS;
        if (currentTime <= expectedTime) {
            addScore = 100;
        } else {
            addScore = 100 - (5 * (currentTime - expectedTime));
            if (addScore < 0) {
                addScore = 0;
            }
        }
        score = expectedTime;

        if (scenarioComplete) {
            updateScore();
        }

        table.left().top();
        scoreLabel.setText(String.format("%d", score));
        stage.addActor(table);

    }

    public Integer getWorldTimerM() {
        return worldTimerM;
    }

    public void setWorldTimerM(Integer worldTimerM) {
        this.worldTimerM = worldTimerM;
    }

    public Integer getWorldTimerS() {
        return worldTimerS;
    }

    public void setWorldTimerS(Integer worldTimerS) {
        this.worldTimerS = worldTimerS;
    }

    public void purchase(Integer price) {
        score -= price;
        if (scenarioComplete) {
            updateScore();
        }
        table.left().top();
        scoreLabel.setText(String.format("%d", score));
        stage.addActor(table);
    }

    public void setRepPoints(Integer repPoint) {
        //System.out.println("I ran");
        repPoints = repPoint;
        reputation.setText(String.format("%d", repPoints));
        stage.addActor(table);
    }

    public void addRep() {
        repPoints += 1;
        System.out.println("Added reputation you now you have a reputation of " + repPoints);
        setRepPoints(repPoints);
    }

    public void doublePoints() {
        boost = true;
        System.out.println("Double Points for next order");
    }

    public void updatePowerUp(int powerUpType) {
        String powerUpText;
        // Determine the power up text based on the powerUpType integer input
        switch (powerUpType) {
            case 0:
                powerUpText = "x2 Points!";
                break;
            case 1:
                powerUpText = "Speed!";
                break;
            case 2:
                powerUpText = "x2 Cooking!";
                break;
            case 3:
                powerUpText = "Extra Time!";
                break;
            case 4:
                powerUpText = "+1 Rep!";
                break;
            default:
                powerUpText = "None";
                break;
        }
        powerUpTextLabel.setText(powerUpText);
        System.out.println(powerUpTextLabel);
    }
}
