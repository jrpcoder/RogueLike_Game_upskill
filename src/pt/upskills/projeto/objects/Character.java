package pt.upskills.projeto.objects;

import pt.upskills.projeto.game.Engine;
import pt.upskills.projeto.gui.ImageTile;
import pt.upskills.projeto.rogue.utils.Position;

import java.util.Iterator;

public abstract class Character{
    private int life;
    protected int attack;
    protected int points;

    public int getLife() {
        return life;
    }

    public int getAttack() {
        return attack;
    }

    public int getPoints() {
        return points;
    }

    public void setLife(int life) {
        this.life = life;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }
    public void setPoints(int points) {
        this.points = points;
    }

    public void removeLife(int damage) {this.life = life - damage;}

    public void toAttackPhysical(int x, int y, String ...CharacterNames) {
        Iterator i = Engine.getTiles().iterator();
        boolean toDie = false;
        String figure = "";
        while(i.hasNext()){
            ImageTile figureImgTile = (ImageTile) i.next();
            for(String character : CharacterNames) {
                if(figureImgTile.getName().equals(character) && figureImgTile.getPosition().getX() == x && figureImgTile.getPosition().getY() == y){
                    Character figureCharacter = (Character) figureImgTile;
                    figureCharacter.removeLife(getAttack());
                    if(figureCharacter.getLife() <= 0) {
                        toDie = true;
                        figure = character;
                    }
                    break;
                }
            }
            if(toDie) {
                if(figure != "Hero") {
                    if(figureImgTile.getName().equals("Bat")) {
                        points = points + 50;
                    } else if(figureImgTile.getName().equals("Skeleton")) {
                        points = points + 100;
                    } else if(figureImgTile.getName().equals("Thief")) {
                        points = points + 200;
                    }
                    System.out.println("-------------");
                    System.out.println("New Score:");
                    System.out.println(points);
                    i.remove();
                    Engine.setTilesAux(true);
                } else {
                    System.out.println("Died");
                }
                toDie = false;
                break;
            }
        }



    }

    public boolean canMoveOverX(int newX, Position position) {
        boolean isOccupied = false;
        for(ImageTile spot : Engine.getTiles()){
            if(!spot.getName().equals("Floor")
                    && !spot.getName().equals("Sword")
                    && !spot.getName().equals("Hammer")
                    && !spot.getName().equals("DoorClosed")
                    && !spot.getName().equals("Key")
                    && !spot.getName().equals("Grass")
                    && !spot.getName().equals("Trophy")
                    && spot.getPosition().getX() == newX && spot.getPosition().getY() == position.getY()){
                isOccupied = true;
                break;
            }
        }
        return !isOccupied;
    }

    public boolean canMoveOverY(int newY, Position position) {
        boolean isOccupied = false;
        for(ImageTile spot : Engine.getTiles()){
            if(!spot.getName().equals("Floor")
                    && !spot.getName().equals("Sword")
                    && !spot.getName().equals("Hammer")
                    && !spot.getName().equals("DoorClosed")
                    && !spot.getName().equals("Key")
                    && !spot.getName().equals("Grass")
                    && !spot.getName().equals("Trophy")
                    && spot.getPosition().getX() == position.getX() && spot.getPosition().getY() == newY){
                isOccupied = true;
                break;
            }
        }
        return !isOccupied;
    }

    public boolean canMoveOver(Position position) {
        boolean isOccupied = false;
        for(ImageTile spot : Engine.getTiles()){
            if(!spot.getName().equals("Floor")
                    && !spot.getName().equals("Sword")
                    && !spot.getName().equals("Hammer")
                    && !spot.getName().equals("DoorClosed")
                    && !spot.getName().equals("Key")
                    && !spot.getName().equals("Grass")
                    && spot.getPosition().getX() == position.getX() && spot.getPosition().getY() == position.getY()){
                isOccupied = true;
                break;
            }
        }
        return !isOccupied;
    }
}
