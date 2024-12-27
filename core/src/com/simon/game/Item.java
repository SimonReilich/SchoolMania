package com.simon.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class Item extends Entity {

    protected Texture texture;
    protected boolean takable;

    public Item(Texture texture, int x, int y, Map map) {
        super(x, y, map, (byte) 32, (byte) 32, false);
        this.texture = texture;
        takable = true;
    }

    public Item(Texture texture, int x, int y) {
        super(x, y, null, (byte) 32, (byte) 32, false);
        this.texture = texture;
        takable = true;
    }

    @Override
    public void interact(Player player) {
        if (takable) {
            player.addToInventory(this);
            removeFromMap();
            map = null;
        }
    }

    public abstract boolean use(Player player, Entity interaction);

    public void draw(SpriteBatch batch) {
        batch.begin();
        batch.draw(texture, rectangle.x, rectangle.y);
        batch.end();
    }

    public void draw(SpriteBatch batch, int x, int y) {
        batch.begin();
        batch.draw(texture, x, y);
        batch.end();
    }

    protected void setPos(int x, int y) {
        rectangle.x = x;
        rectangle.y = y;
    }

    public void dispose() {
        texture.dispose();
    }
}
