public class Tower extends  Entity{

    Tower(Card card, double x, double y, Spieler affiliation) {
        super(card, x, y, affiliation);
    }

    void KickTheBucket(){
        super.KickTheBucket();
        System.out.println("Tower has fallen at: " + cords[0] + " " + cords[1]);
    }
}
