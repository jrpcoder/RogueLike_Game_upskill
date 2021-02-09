package pt.upskills.projeto.objects;

import pt.upskills.projeto.gui.ImageTile;

public interface Enemy {
    void setPositionAproximar(int heroX, int heroY, ImageTile enemy);
    void setPositionRandom(ImageTile enemy);
}
