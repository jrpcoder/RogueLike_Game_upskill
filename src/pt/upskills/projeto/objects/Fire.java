package pt.upskills.projeto.objects;

import pt.upskills.projeto.game.Engine;
import pt.upskills.projeto.gui.FireTile;
import pt.upskills.projeto.gui.ImageTile;
import pt.upskills.projeto.rogue.utils.Position;

import java.util.Iterator;

public class Fire implements ImageTile, FireTile {
    private Position position;
    private int damage = 50;
    private String fireStatus = "Fire";

    public Fire(Position position) {
        this.position = position;
    }

    public void giveDamage() {
        Iterator i = Engine.getTiles().iterator();
        boolean toDie = false;
        while(i.hasNext()){
            ImageTile figureImgTile = (ImageTile) i.next();
            if((figureImgTile.getName().equals("Skeleton")
                    || figureImgTile.getName().equals("Bat")
                    || figureImgTile.getName().equals("Thief"))
                    && (figureImgTile.getPosition().getX() == position.getX() && figureImgTile.getPosition().getY() == position.getY())){
                Character figure = (Character) figureImgTile;
                figure.removeLife(damage);
                if(figure.getLife() <= 0) {
                    toDie = true;
                }
            }
            if(toDie) {
                i.remove();
                toDie = false;
            }
        }
    }

    @Override
    public String getName() {
        return fireStatus;
    }

    @Override
    public Position getPosition() {
        return position;
    }
    //

    @Override
    public boolean validateImpact() {
        boolean NoImpact = true;
        Iterator i = Engine.getTiles().iterator();
        while(i.hasNext()){
            ImageTile figureImgTile = (ImageTile) i.next();

            if((figureImgTile.getName().equals("Wall")
                    || figureImgTile.getName().equals("Skeleton")
                    || figureImgTile.getName().equals("Bat")
                    || figureImgTile.getName().equals("Thief")
                    || figureImgTile.getName().equals("DoorClosed")
                    || figureImgTile.getName().equals("DoorOpen"))
                    && (figureImgTile.getPosition().getX() == position.getX() && figureImgTile.getPosition().getY() == position.getY())){
                NoImpact = false;
                fireStatus = "Fire_old";
            }
        }
        return NoImpact;
    }

    @Override
    public void setPosition(Position position) {
        this.position = position;
    }
}
