package pt.upskills.projeto.objects;

import pt.upskills.projeto.gui.ImageTile;
import pt.upskills.projeto.rogue.utils.Position;

public class Hammer extends Item implements ImageTile {
    private Position position;
    private final int damage = 30;

    public Hammer(Position position) {
        this.position = position;
    }

    @Override
    public String getName() {
        return "Hammer";
    }

    @Override
    public Position getPosition() {
        return position;
    }

    @Override
    public int getDamage(){return damage;}

    @Override
    public void setWasAlreadyCaught(boolean val) {
        super.wasAlreadyCaught = val;
    }
}
