
/*  StateHandler.java
	Name: Kalyn Muhlenberg
	Email: Kalyn.muhlenberg@tufts.edu
	Description: The StateHandler class keeps track of the state of the 
    simulation, or the current stats. This includes the number of fish caught, 
    the amount of money the player has made, and the number of lives the player 
    has left. The class also updates the stats display as the statistics are 
    updates.
*/

public class StateHandler {
    private StatsDisplay stats_display;
    private SimulationModel model;
    // Simulation statistics
    private int fish_caught;
    private int money_made;
    private int lives_left;

    public StateHandler(SimulationModel m) {
        model = m;
        fish_caught = 0;
        money_made = 0;
        lives_left = 5;
    }

    public void setStatDisplay(StatsDisplay display){
        stats_display = display;
    }

    public void resetStats(){
        fish_caught = 0;
        money_made = 0;
        lives_left = 5;
        stats_display.updateStats(fish_caught, money_made, lives_left);
    }

    public void updateMoneyMade(int change){
        money_made += change;
        stats_display.updateStats(fish_caught, money_made, lives_left);
    }

    public void updateFishCaught(int change){
        fish_caught += change;
        stats_display.updateStats(fish_caught, money_made, lives_left);
    }

    public void updateLivesLeft(int change){
        lives_left += change;
        stats_display.updateStats(fish_caught, money_made, lives_left);
        if(lives_left <= 0){
            model.gameOver(fish_caught, money_made);
        }
    }

    public int getMoneyMade(){
        return money_made;
    }

    // public void gameOver(){
    //     GameOverPopUp game_over = new GameOverPopUp(model, window, fish_caught, money_made);
    //     game_over.setVisible(true);
        
    // }


}
