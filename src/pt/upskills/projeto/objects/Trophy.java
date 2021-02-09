package pt.upskills.projeto.objects;

import pt.upskills.projeto.gui.ImageTile;
import pt.upskills.projeto.rogue.utils.Position;

public class Trophy implements ImageTile {
    private Position position;

    public Trophy(Position position) {
        this.position = position;
    }

    @Override
    public String getName() {
        return "Trophy";
    }

    @Override
    public Position getPosition() {
        return position;
    }
}
