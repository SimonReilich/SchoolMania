package com.simon.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Vent extends Entity{
    private Texture texture;

    public Vent(int x, int y, Map map) {
        super(x, y, map, (short) 32, (short) 24, true);
        texture = new Texture(Gdx.files.internal("Sprites/ventClosed.png"));
    }

    @Override
    public void draw(SpriteBatch batch) {
        batch.begin();
        batch.draw(texture, getX(), getY());
        batch.end();
    }

    public Vent open() {
        collide = false;
        texture.dispose();
        texture = new Texture(Gdx.files.internal("Sprites/ventOpen.png"));
        return this;
    }

    @Override
    public void dispose() {
        texture.dispose();
    }
}
