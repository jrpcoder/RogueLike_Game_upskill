package pt.upskills.projeto.objects;

import pt.upskills.projeto.game.Engine;
import pt.upskills.projeto.gui.ImageTile;
import pt.upskills.projeto.rogue.utils.Direction;
import pt.upskills.projeto.rogue.utils.Position;

import java.awt.event.KeyEvent;
import java.util.Observable;
import java.util.Observer;

public class Skeleton extends Character implements ImageTile, Enemy {
    private Position position;

    public Skeleton(Position position) {
        this.position = position;
    }

    public void setPosition(Position position) {this.position = position;}

    @Override
    public String getName() {
        return "Skeleton";
    }

    @Override
    public Position getPosition() {
        return position;
    }

    @Override
    public void setPositionAproximar(int heroX, int heroY, ImageTile enemy) {
        if(Math.abs(heroX - position.getX()) <= (Math.abs(position.getY() - heroY))) {
            if(position.getX() < heroX) {
                int newX = position.plus(Direction.RIGHT.asVector()).getX();
                if(canMoveOverX(newX, position)) {
                    if(newX < 9) {
                        position = new Position(newX, position.getY());
                    }
                } else {
                    setPositionRandom(enemy);
                }
            } else if (position.getX() == heroX) {
                if(position.getY() <= heroY) {
                    int newY = position.plus(Direction.DOWN.asVector()).getY();
                    if(canMoveOverY(newY, position)) {
                        if(newY < 9) {
                            position = new Position(position.getX(), newY);
                        }
                    } else {
                        toAttackPhysical(position.getX(), newY, "Hero");
                    }
                } else {
                    int newY = position.plus(Direction.UP.asVector()).getY();
                    if(canMoveOverY(newY, position)){
                        if(newY >= 1) {
                            position = new Position(position.getX(), newY);
                        }
                    } else {
                        toAttackPhysical(position.getX(), newY, "Hero");
                    }
                }
            } else {
                int newX = position.plus(Direction.LEFT.asVector()).getX();
                if(canMoveOverX(newX, position)) {
                    if(newX >= 1) {
                        position = new Position(newX, position.getY());
                    }
                } else {
                    setPositionRandom(enemy);
                }
            }
        } else {
            if(position.getY() < heroY) {
                int newY = position.plus(Direction.DOWN.asVector()).getY();
                if(canMoveOverY(newY, position)) {
                    if(newY < 9) {
                        position = new Position(position.getX(), newY);
                    }
                } else {
                    setPositionRandom(enemy);
                }
            } else if (position.getY() == heroY) {
                if(position.getX() <= heroX) {
                    int newX = position.plus(Direction.RIGHT.asVector()).getX();
                    if(canMoveOverX(newX, position)) {
                        if(newX < 9) {
                            position = new Position(newX, position.getY());
                        }
                    } else {
                        toAttackPhysical(newX, position.getY(), "Hero");
                    }
                } else {
                    int newX = position.plus(Direction.LEFT.asVector()).getX();
                    if(canMoveOverX(newX, position)) {
                        if(newX >= 1) {
                            position = new Position(newX, position.getY());
                        }
                    } else {
                        toAttackPhysical(newX, position.getY(), "Hero");
                    }
                }
            } else {
                int newY = position.plus(Direction.UP.asVector()).getY();
                if(canMoveOverY(newY, position)){
                    if(newY >= 1) {
                        position = new Position(position.getX(), newY);
                    }
                } else {
                    setPositionRandom(enemy);
                }
            }
        }
        Engine.setTilesAux(true);
    }

    @Override
    public void setPositionRandom(ImageTile enemy) {
        int directionChooser = (int) (Math.random() * 4);
        switch (directionChooser) {
            case 0:
                int newXPlus = enemy.getPosition().plus(Direction.RIGHT.asVector()).getX();
                if(newXPlus < 9 && canMoveOverX(newXPlus, enemy.getPosition())) {
                    setPosition(new Position(newXPlus, enemy.getPosition().getY()));
                    break;
                }
            case 1:
                int newXMinus = enemy.getPosition().plus(Direction.LEFT.asVector()).getX();
                if(newXMinus >= 1 && canMoveOverX(newXMinus, enemy.getPosition())) {
                    setPosition(new Position(newXMinus, enemy.getPosition().getY()));
                    break;
                }
            case 2:
                int newYPlus = enemy.getPosition().plus(Direction.DOWN.asVector()).getY();
                if(newYPlus < 9 && canMoveOverY(newYPlus, enemy.getPosition())) {
                    setPosition(new Position(enemy.getPosition().getX(), newYPlus));
                    break;
                }
            case 3:
                int newYMinus = enemy.getPosition().plus(Direction.UP.asVector()).getY();
                if(newYMinus >= 1 && canMoveOverY(newYMinus, enemy.getPosition())) {
                    setPosition(new Position(enemy.getPosition().getX(), newYMinus));
                    break;
                }
        }
        Engine.setTilesAux(true);
    }
}
