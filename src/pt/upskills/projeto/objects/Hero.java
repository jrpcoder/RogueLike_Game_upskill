package pt.upskills.projeto.objects;

import pt.upskills.projeto.game.Engine;
import pt.upskills.projeto.game.FireBallThread;
import pt.upskills.projeto.gui.FireTile;
import pt.upskills.projeto.gui.ImageMatrixGUI;
import pt.upskills.projeto.gui.ImageTile;
import pt.upskills.projeto.rogue.utils.Direction;
import pt.upskills.projeto.rogue.utils.Position;

import java.awt.event.KeyEvent;
import java.util.*;

public class Hero extends Character implements ImageTile, Observer {

    private Position position;
    private String directionFire = "";
    private boolean hasKey;
    private boolean hasTrophy = false;

    public Hero(Position position) {
        this.position = position;
    }

    public boolean getHasTrophy() {
        return hasTrophy;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public void setHasTrophy(boolean hasTrophy) {
        this.hasTrophy = hasTrophy;
    }

    //method to check if there are any spaces left on the items list
    public boolean hasStatusTilesSpace(){
        boolean hasSpace = false;
        for(ImageTile item : Engine.getStatusTilesItems()) {
            if(item.getName().equals("Black")) {
                hasSpace = true;
                break;
            }
        }
        return hasSpace;
    }

    public void addItemtoStatusMenu(ImageTile object) {
        ListIterator i = Engine.getStatusTilesItems().listIterator();
        while(i.hasNext()) {
            ImageTile item = (ImageTile) i.next();
            if(item.getName().equals("Black")) {
                switch (object.getName()) {
                    case "Sword":
                        Sword sword = new Sword(new Position(item.getPosition().getX(), item.getPosition().getY()));
                        i.set(sword);
                        break;
                    case "Hammer":
                        Hammer hammer = new Hammer(new Position(item.getPosition().getX(), item.getPosition().getY()));
                        i.set(hammer);
                        break;
                    case "Key":
                        Key key = new Key(new Position(item.getPosition().getX(), item.getPosition().getY()));
                        hasKey = true;
                        i.set(key);
                }
                Engine.setStatusAux(true);
                break;
            }
        }
    }

    public void dropItem(int indexPosition, int itemX, int itemY) {
        int startingX = 7;
        switch (Engine.getStatusTilesItems().get(indexPosition).getName()) {
            case "Sword":
                Sword sword = new Sword(new Position(itemX, itemY));
                sword.setWasAlreadyCaught(true);
                Engine.getTiles().add(sword);
                removeAttack(sword.getDamage());
                break;
            case "Hammer":
                Hammer hammer = new Hammer(new Position(itemX, itemY));
                hammer.setWasAlreadyCaught(true);
                Engine.getTiles().add(hammer);
                removeAttack(hammer.getDamage());
                break;
        }
        if(!Engine.getStatusTilesItems().get(indexPosition).getName().equals("Key")) {
            Black black = new Black(new Position(indexPosition + startingX, 0));
            Engine.getStatusTilesItems().set(indexPosition, black);
            Engine.setStatusAux(true);
            Engine.setTilesAux(true);
        }
    }

    public void catchItem(int updatedX, int updatedY) {
        Iterator i = Engine.getTiles().iterator();
        while(i.hasNext()){
            ImageTile item = (ImageTile) i.next();
            if(item.getPosition().getX() == updatedX && item.getPosition().getY() == updatedY) {
                if(item.getName().equals("Sword")
                        || item.getName().equals("Hammer")
                        || item.getName().equals("Trophy")
                        || item.getName().equals("Key")){
                    if(item.getName().equals("Key")) {
                        if(hasStatusTilesSpace()) {
                            i.remove();
                            addItemtoStatusMenu(item);
                            addItemPoints(item.getName());
                            break;
                        }
                    } else if(item.getName().equals("Trophy")) {
                        addItemPoints(item.getName());
                        System.out.println("--------------------------------------------");
                        System.out.println("CONGRATULATIONS!!! YOU REACHED THE END!!!");
                        System.out.println("Your final score is " + points + ".");
                        setHasTrophy(true);
                    } else {
                        Item itemAsItem = (Item) item;
                        i.remove();
                        addAttack(itemAsItem.getDamage());
                        addItemtoStatusMenu(item);
                        if(!itemAsItem.getWasAlreadyCaught()) {
                            addItemPoints(item.getName());
                        }
                        break;
                    }
                }
            }
        }
        Engine.setTilesAux(true);
    }

    public void getNewLifeStatusList(String ...colors) {
        Engine.getStatusTilesLife().clear();
        int startingX = 3;
        for(int i = 0; i < colors.length; i++) {
            switch (colors[i]) {
                case "Green":
                    Engine.getStatusTilesLife().add(new LifeGreen(new Position(i + startingX, 0)));
                    break;
                case "Red":
                    Engine.getStatusTilesLife().add(new LifeRed(new Position(i + startingX, 0)));
                    break;
                case "RedGreen":
                    Engine.getStatusTilesLife().add(new LifeRedGreen(new Position(i + startingX, 0)));
                    break;
            }
        }
    }

    public void updateLifeStatus() {
        switch (getLife()) {
            case 120:
                getNewLifeStatusList("Green", "Green", "Green", "Green");
                break;
            case 105:
                getNewLifeStatusList("RedGreen", "Green", "Green", "Green");
                break;
            case 90:
                getNewLifeStatusList("Red", "Green", "Green", "Green");
                break;
            case 75:
                getNewLifeStatusList("Red", "RedGreen", "Green", "Green");
                break;
            case 60:
                getNewLifeStatusList("Red", "Red", "Green", "Green");
                break;
            case 45:
                getNewLifeStatusList("Red", "Red", "RedGreen", "Green");
                break;
            case 30:
                getNewLifeStatusList("Red", "Red", "Red", "Green");
                break;
            case 15:
                getNewLifeStatusList("Red", "Red", "Red", "RedGreen");
                break;
            default:
                getNewLifeStatusList("Red", "Red", "Red", "Red");
        }
        Engine.setStatusAux(true);
    }

    public void updateFireBallStatus() {
        Engine.getStatusTilesFire().remove(0);
        Black black = new Black();
        Engine.getStatusTilesFire().add(black);
        for(int i = 0; i < Engine.getStatusTilesFire().size(); i++) {
            if(Engine.getStatusTilesFire().get(i).getName().equals("Fire")) {
                Engine.getStatusTilesFire().set(i, new Fire(new Position(i, 0)));
            } else {
                Engine.getStatusTilesFire().set(i, new Black(new Position(i, 0)));
            }
        }
        Engine.setStatusAux(true);
    }

    public void removeWalkingPoint() {
        if(points - 1 >= 0) {
            points = points - 1;
        }
    }

    public void addItemPoints(String item) {
        switch (item) {
            case "Sword":
                points = points + 50;
                break;
            case "Hammer":
                points = points + 100;
                break;
            case "Key":
                points = points + 150;
                break;
            case "Trophy":
                points = points + 1000;
                break;
        }
        System.out.println("-------------");
        System.out.println("New Score:");
        System.out.println(points);
    }

    public void addAttack(int value) {
        super.attack = getAttack() + value;
    }

    public void removeAttack(int value) {
        super.attack = getAttack() - value;
    }

    public void enterDoor() {
        for(ImageTile tile : Engine.getTiles()) {
            if((tile.getName().equals("DoorClosed"))) {
                if(tile.getPosition().getX() == position.getX() && tile.getPosition().getY() == position.getY()) {
                    Door door = (Door) tile;
                    if(door.getKey()) {
                        if(hasKey) {
                            Engine.getEnteredDoorInfo().clear();
                            Engine.getEnteredDoorInfo().add(door.getNumber());
                            Engine.getEnteredDoorInfo().add(door.getNextRoom());
                            Engine.getEnteredDoorInfo().add(door.getNextRoomHeroDoor());
                            int counter = 0;
                            for(ImageTile item : Engine.getStatusTilesItems()) {
                                if(item.getName().equals("Key")){
                                    Engine.getStatusTilesItems().set(counter, new Black(new Position(item.getPosition().getX(), item.getPosition().getY())));
                                    break;
                                }
                                counter++;
                            }
                            counter = 0;
                            for(ImageTile itemFire : Engine.getStatusTilesFire()) {
                                if(itemFire.getName().equals("Black")){
                                    Engine.getStatusTilesFire().set(counter, new Fire(new Position(itemFire.getPosition().getX(), itemFire.getPosition().getY())));
                                }
                                counter++;
                            }
                            Engine.setPassedDoor(true);
                            hasKey = false;
                        }
                    } else {
                        Engine.getEnteredDoorInfo().clear();
                        Engine.getEnteredDoorInfo().add(door.getNumber());
                        Engine.getEnteredDoorInfo().add(door.getNextRoom());
                        Engine.getEnteredDoorInfo().add(door.getNextRoomHeroDoor());
                        Engine.setPassedDoor(true);
                    }
                    break;
                }
            }
        }
    }

    public void setHasKey(boolean hasKey) {
        this.hasKey = hasKey;
    }

    @Override
    public String getName() {
        return "Hero";
    }

    @Override
    public Position getPosition() {
        return position;
    }

    /**
     * This method is called whenever the observed object is changed. This function is called when an
     * interaction with the graphic component occurs {{@link ImageMatrixGUI}}
     * @param o
     * @param arg
     */
    @Override
    public void update(Observable o, Object arg) {
        Integer keyCode = (Integer) arg;

        if(!hasTrophy && getLife() > 0) {
            if (keyCode == KeyEvent.VK_DOWN){
                directionFire = "DOWN";
                // do something
                int newY = position.plus(Direction.DOWN.asVector()).getY();
                if(newY < 10) {
                    toAttackPhysical(position.getX(), newY, "Skeleton", "Bat", "Thief");
                    if(canMoveOverY(newY, position)) {
                        position = new Position(position.getX(), newY);
                        removeWalkingPoint();
                        catchItem(position.getX(), newY);
                    }
                }
                for(ImageTile tile : Engine.getTiles()) {
                    if(tile.getName().equals("Skeleton")) {
                        Skeleton figure = (Skeleton) tile;
                        if(Math.abs(position.getX() - tile.getPosition().getX()) <= 3 && Math.abs(position.getY() - tile.getPosition().getY()) <=3) {
                            figure.setPositionAproximar(position.getX(), position.getY(), tile);
                        } else {
                            figure.setPositionRandom(tile);
                        }
                    } else if(tile.getName().equals("Bat")) {
                        Bat figure = (Bat) tile;
                        if(Math.abs(position.getX() - tile.getPosition().getX()) <= 3 && Math.abs(position.getY() - tile.getPosition().getY()) <=3) {
                            figure.setPositionAproximar(position.getX(), position.getY(), tile);
                        } else {
                            figure.setPositionRandom(tile);
                        }
                    } else if(tile.getName().equals("Thief")) {
                        Thief figure = (Thief) tile;
                        figure.setPositionAproximar(position.getX(), position.getY(), tile);
                    }
                    if(getLife() <= 0) {
                        System.out.println("----------------------------------");
                        System.out.println("GAME OVER");
                        System.out.println("Your final score is " + points + ".");
                        System.out.println("TRY AGAIN!");
                        System.out.println("----------------------------------");
                        setHasKey(false);
                        break;
                    }
                }
                updateLifeStatus();
                if(getLife() > 0) {
                    enterDoor();
                } else {
                    Engine.setIsHeroAlive(false);
                }
            }

            if (keyCode == KeyEvent.VK_UP){
                directionFire = "UP";
                // do something
                int newY = position.plus(Direction.UP.asVector()).getY();
                if(newY >= 0) {
                    toAttackPhysical(position.getX(), newY, "Skeleton", "Bat", "Thief");
                    if(canMoveOverY(newY, position)) {
                        position = new Position(position.getX(), newY);
                        removeWalkingPoint();
                        catchItem(position.getX(), newY);
                    }
                }
                for(ImageTile tile : Engine.getTiles()) {
                    if(tile.getName().equals("Skeleton")) {
                        Skeleton figure = (Skeleton) tile;
                        if(Math.abs(position.getX() - tile.getPosition().getX()) <= 3 && Math.abs(position.getY() - tile.getPosition().getY()) <=3) {
                            figure.setPositionAproximar(position.getX(), position.getY(), tile);
                        } else {
                            figure.setPositionRandom(tile);
                        }
                    } else if(tile.getName().equals("Bat")) {
                        Bat figure = (Bat) tile;
                        if(Math.abs(position.getX() - tile.getPosition().getX()) <= 3 && Math.abs(position.getY() - tile.getPosition().getY()) <=3) {
                            figure.setPositionAproximar(position.getX(), position.getY(), tile);
                        } else {
                            figure.setPositionRandom(tile);
                        }
                    } else if(tile.getName().equals("Thief")) {
                        Thief figure = (Thief) tile;
                        figure.setPositionAproximar(position.getX(), position.getY(), tile);
                    }
                    if(getLife() <= 0) {
                        System.out.println("----------------------------------");
                        System.out.println("GAME OVER");
                        System.out.println("Your final score is " + points + ".");
                        System.out.println("TRY AGAIN!");
                        System.out.println("----------------------------------");
                        setHasKey(false);
                        break;
                    }
                }
                updateLifeStatus();
                if(getLife() > 0) {
                    enterDoor();
                } else {
                    Engine.setIsHeroAlive(false);
                }
            }

            if (keyCode == KeyEvent.VK_LEFT){
                directionFire = "LEFT";
                // do something
                int newX = position.plus(Direction.LEFT.asVector()).getX();
                if(newX >= 0) {
                    toAttackPhysical(newX, position.getY(), "Skeleton", "Bat", "Thief");
                    if(canMoveOverX(newX, position)) {
                        position = new Position(newX, position.getY());
                        removeWalkingPoint();
                        catchItem(newX, position.getY());
                    }
                }
                for(ImageTile tile : Engine.getTiles()) {
                    if(tile.getName().equals("Skeleton")) {
                        Skeleton figure = (Skeleton) tile;
                        if(Math.abs(position.getX() - tile.getPosition().getX()) <= 3 && Math.abs(position.getY() - tile.getPosition().getY()) <=3) {
                            figure.setPositionAproximar(position.getX(), position.getY(), tile);
                        } else {
                            figure.setPositionRandom(tile);
                        }
                    } else if(tile.getName().equals("Bat")) {
                        Bat figure = (Bat) tile;
                        if(Math.abs(position.getX() - tile.getPosition().getX()) <= 3 && Math.abs(position.getY() - tile.getPosition().getY()) <=3) {
                            figure.setPositionAproximar(position.getX(), position.getY(), tile);
                        } else {
                            figure.setPositionRandom(tile);
                        }
                    } else if(tile.getName().equals("Thief")) {
                        Thief figure = (Thief) tile;
                        figure.setPositionAproximar(position.getX(), position.getY(), tile);
                    }
                    if(getLife() <= 0) {
                        System.out.println("----------------------------------");
                        System.out.println("GAME OVER");
                        System.out.println("Your final score is " + points + ".");
                        System.out.println("TRY AGAIN!");
                        System.out.println("----------------------------------");
                        setHasKey(false);
                        break;
                    }
                }
                updateLifeStatus();
                if(getLife() > 0) {
                    enterDoor();
                } else {
                    Engine.setIsHeroAlive(false);
                }
            }

            if (keyCode == KeyEvent.VK_RIGHT){
                directionFire = "RIGHT";
                // do something
                int newX = position.plus(Direction.RIGHT.asVector()).getX();
                if(newX < 10) {
                    toAttackPhysical(newX, position.getY(), "Skeleton", "Bat", "Thief");
                    if(canMoveOverX(newX, position)) {
                        position = new Position(newX, position.getY());
                        removeWalkingPoint();
                        catchItem(newX, position.getY());
                    }
                }
                for(ImageTile tile : Engine.getTiles()) {
                    if(tile.getName().equals("Skeleton")) {
                        Skeleton figure = (Skeleton) tile;
                        if(Math.abs(position.getX() - tile.getPosition().getX()) <= 3 && Math.abs(position.getY() - tile.getPosition().getY()) <=3) {
                            figure.setPositionAproximar(position.getX(), position.getY(), tile);
                        } else {
                            figure.setPositionRandom(tile);
                        }
                    } else if(tile.getName().equals("Bat")) {
                        Bat figure = (Bat) tile;
                        if(Math.abs(position.getX() - tile.getPosition().getX()) <= 3 && Math.abs(position.getY() - tile.getPosition().getY()) <=3) {
                            figure.setPositionAproximar(position.getX(), position.getY(), tile);
                        } else {
                            figure.setPositionRandom(tile);
                        }
                    } else if(tile.getName().equals("Thief")) {
                        Thief figure = (Thief) tile;
                        figure.setPositionAproximar(position.getX(), position.getY(), tile);
                    }
                    if(getLife() <= 0) {
                        System.out.println("----------------------------------");
                        System.out.println("GAME OVER");
                        System.out.println("Your final score is " + points + ".");
                        System.out.println("TRY AGAIN!");
                        System.out.println("----------------------------------");
                        setHasKey(false);
                        break;
                    }
                }
                updateLifeStatus();
                if(getLife() > 0) {
                    enterDoor();
                } else {
                    Engine.setIsHeroAlive(false);
                }
            }

            if(keyCode == KeyEvent.VK_1 || keyCode == KeyEvent.VK_NUMPAD1) {
                dropItem(0, position.getX(), position.getY());
            }

            if(keyCode == KeyEvent.VK_2 || keyCode == KeyEvent.VK_NUMPAD2) {
                dropItem(1, position.getX(), position.getY());
            }

            if(keyCode == KeyEvent.VK_3 || keyCode == KeyEvent.VK_NUMPAD3) {
                dropItem(2, position.getX(), position.getY());
            }

            if(keyCode == KeyEvent.VK_SPACE && Engine.getStatusTilesFire().get(0).getName() == "Fire") {
                FireTile fireball = new Fire(position);
                updateFireBallStatus();
                Engine.getTiles().add(fireball);
                Engine.setTilesAux(true);
                switch (directionFire) {
                    case "DOWN":
                        FireBallThread fireBallThread1 = new FireBallThread(Direction.DOWN, fireball);
                        fireBallThread1.run();
                        break;
                    case "UP":
                        FireBallThread fireBallThread2 = new FireBallThread(Direction.UP, fireball);
                        fireBallThread2.run();
                        break;
                    case "RIGHT":
                        FireBallThread fireBallThread3 = new FireBallThread(Direction.RIGHT, fireball);
                        fireBallThread3.run();
                        break;
                    case "LEFT":
                        FireBallThread fireBallThread4 = new FireBallThread(Direction.LEFT, fireball);
                        fireBallThread4.run();
                        break;
                }
                Fire fireballTypeFire = (Fire) fireball;
                fireballTypeFire.giveDamage();
                points = points + 75;
                System.out.println("-------------");
                System.out.println("New Score:");
                System.out.println(points);
                Iterator i = Engine.getTiles().iterator();
                while(i.hasNext()){
                    ImageTile figureImgTile = (ImageTile) i.next();
                    if(figureImgTile.getName().equals("Fire_old")){
                        i.remove();
                    }
                }
                Engine.setTilesAux(true);
            }
            if(keyCode == KeyEvent.VK_S) {
                Engine.setWasSaved(true);
            }
        }
    }
}
