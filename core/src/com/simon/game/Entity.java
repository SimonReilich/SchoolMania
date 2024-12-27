package com.simon.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public abstract class Entity {

    protected Rectangle rectangle;
    protected Map map;
    protected boolean collide;

    public Entity(int x, int y, Map map, short width, short height, boolean collide) {
        rectangle = new Rectangle(x, y, width, height);
        this.map = map;
        this.collide = collide;
    }

    public abstract void draw(SpriteBatch batch);

    public void interact(Player player) {
        return;
    }

    public void removeFromMap() {
        map.removeEntity(this);
    }

    public void collision(Player player) {
        if (collide) {
            while (player.getRectangle().overlaps(rectangle)) {
                player.revertMovement();
            }
        }
    }

    public int getX() {
        return (int) rectangle.x;
    }

    public int getY() {
        return (int) rectangle.y;
    }

    public abstract void dispose();
}