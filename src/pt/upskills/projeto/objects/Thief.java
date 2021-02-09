package pt.upskills.projeto.objects;

import pt.upskills.projeto.game.Engine;
import pt.upskills.projeto.gui.ImageTile;
import pt.upskills.projeto.rogue.utils.Direction;
import pt.upskills.projeto.rogue.utils.Position;
import pt.upskills.projeto.rogue.utils.Vector2D;

public class Thief extends Character implements ImageTile, Enemy {
    private Position position;

    public Thief(Position position) {
        this.position = position;
    }

    public void setPosition(Position position) {this.position = position;}

    @Override
    public String getName() {
        return "Thief";
    }

    @Override
    public Position getPosition() {
        return position;
    }

    @Override
    public void setPositionAproximar(int heroX, int heroY, ImageTile enemy) {
        double distanceToHeroDiagonal = Math.sqrt(Math.pow(Math.abs(heroX - position.getX()), 2) + Math.pow(Math.abs(heroY - position.getY()), 2));
        int enemyX = enemy.getPosition().getX();
        int enemyY = enemy.getPosition().getY();
        Position newPosition;
        if(distanceToHeroDiagonal < 5) {
            if(heroX > enemyX && heroY > enemyY) {
                newPosition = enemy.getPosition().plus(new Vector2D(1, 1));
                if(canMoveOver(position.plus(new Vector2D(1, 1)))) {
                    if (newPosition.getX() < 9 && newPosition.getY() < 9) {
                        setPosition(newPosition);
                    }
                } else {
                    toAttackPhysical(newPosition.getX(), newPosition.getY(), "Hero");
                }
            } else if(heroX < enemyX && heroY > enemyY) {
                newPosition = enemy.getPosition().plus(new Vector2D(-1, 1));;
                if(canMoveOver(position.plus(new Vector2D(-1, 1)))) {
                    if (newPosition.getX() >= 1 && newPosition.getY() < 9) {
                        setPosition(newPosition);
                    }
                } else {
                    toAttackPhysical(newPosition.getX(), newPosition.getY(), "Hero");
                }

            } else if(heroX < enemyX && heroY < enemyY) {
                newPosition = enemy.getPosition().plus(new Vector2D(-1, -1));
                if(canMoveOver(position.plus(new Vector2D(-1, -1)))) {
                    if (newPosition.getX() >= 1 && newPosition.getY() >= 1) {
                        setPosition(newPosition);
                    }
                } else {
                    toAttackPhysical(newPosition.getX(), newPosition.getY(), "Hero");
                }
            } else if(heroX > enemyX && heroY < enemyY) {
                newPosition = enemy.getPosition().plus(new Vector2D(1, -1));
                if(canMoveOver(position.plus(new Vector2D(1, -1)))){
                    if (newPosition.getX() < 9 && newPosition.getY() >= 1) {
                        setPosition(newPosition);
                    }
                } else {
                    toAttackPhysical(newPosition.getX(), newPosition.getY(), "Hero");
                }
            } else {
                setPositionRandom(enemy);
            }
        } else {
            setPositionRandom(enemy);
        }
        Engine.setTilesAux(true);
    }

    @Override
    public void setPositionRandom(ImageTile enemy) {
        int newX;
        int newY;
        Position newPosition;
        int directionChooser = (int) (Math.random() * 4);
        switch (directionChooser) {
            case 0:
                newPosition = enemy.getPosition().plus(new Vector2D(1, 1));
                newX = newPosition.getX();
                newY = newPosition.getY();
                if(newX < 9 && newY < 9 && canMoveOver(newPosition)) {
                    setPosition(newPosition);
                    break;
                }
            case 1:
                newPosition = enemy.getPosition().plus(new Vector2D(-1, 1));
                newX = newPosition.getX();
                newY = newPosition.getY();
                if(newX >= 1 && newY < 9 && canMoveOver(newPosition)) {
                    setPosition(newPosition);
                    break;
                }
            case 2:
                newPosition = enemy.getPosition().plus(new Vector2D(1, -1));
                newX = newPosition.getX();
                newY = newPosition.getY();
                if(newX < 9 && newY >= 1 && canMoveOver(newPosition)) {
                    setPosition(newPosition);
                    break;
                }
            case 3:
                newPosition = enemy.getPosition().plus(new Vector2D(-1, -1));
                newX = newPosition.getX();
                newY = newPosition.getY();
                if(newX >= 1 && newY >= 1 && canMoveOver(newPosition)) {
                    setPosition(newPosition);
                    break;
                }
        }
    }
}
