public class Spiel {
    String gamemode;
    Spielfeld feld;
    TroopSelection gamesTroops;
    Spieler[] player = new Spieler[]{new Spieler("Lukas"), new Spieler("Bot")};

    Spiel(String gamemode, Card[] troopsForGame){
        this.gamemode = gamemode;
        feld = new Spielfeld(1000/60, player);
        gamesTroops = new TroopSelection(troopsForGame);
    }
}
