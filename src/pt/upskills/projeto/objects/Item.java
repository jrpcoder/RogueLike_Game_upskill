package pt.upskills.projeto.objects;

public abstract class Item {
    protected int damage;
    protected boolean wasAlreadyCaught = false;

    public abstract int getDamage();
    public boolean getWasAlreadyCaught() {return wasAlreadyCaught;}
    public abstract void setWasAlreadyCaught(boolean val);
}
