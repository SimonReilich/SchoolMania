package com.simon.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Door extends Entity{

    private final int keyCode;
    private Texture texture;

    public Door(int x, int y, Map map, int key, boolean open) {
        super(x, y, map, (byte) 32, (byte) 32, true);
        keyCode = key;
        switch (key) {
            case 0 -> {
                if (!open) {
                    texture = new Texture(Gdx.files.internal("Sprites/doorClosedOrange.png"));
                } else {
                    texture = new Texture(Gdx.files.internal("Sprites/doorOpenOrange.png"));
                }
            }
            case 1 -> {
                if (!open) {
                    texture = new Texture(Gdx.files.internal("Sprites/doorClosedYellow.png"));
                } else {
                    texture = new Texture(Gdx.files.internal("Sprites/doorOpenYellow.png"));
                }
            }
            case 2 -> {
                if (!open) {
                    texture = new Texture(Gdx.files.internal("Sprites/doorClosedOld.png"));
                } else {
                    texture = new Texture(Gdx.files.internal("Sprites/doorOpenOld.png"));
                }
            }
            case 3 -> {
                if (!open) {
                    texture = new Texture(Gdx.files.internal("Sprites/doorClosedBlue.png"));
                } else {
                    texture = new Texture(Gdx.files.internal("Sprites/doorOpenBlue.png"));
                }
            }
            case 4 -> {
                if (!open) {
                    texture = new Texture(Gdx.files.internal("Sprites/doorClosedGreen.png"));
                } else {
                    texture = new Texture(Gdx.files.internal("Sprites/doorOpenGreen.png"));
                }
            }
        }
        collide = !open;
    }

    @Override
    public void draw(SpriteBatch batch) {
        batch.begin();
        batch.draw(texture, rectangle.x, rectangle.y);
        batch.end();
    }

    @Override
    public void interact(Player player) {

    }

    @Override
    public void dispose() {
        texture.dispose();
    }

    public void open(int code) {
        if (code == keyCode) {
            collide = false;
            texture.dispose();
            switch (keyCode) {
                case 0 -> texture = new Texture(Gdx.files.internal("Sprites/doorOpenOrange.png"));
                case 1 -> texture = new Texture(Gdx.files.internal("Sprites/doorOpenYellow.png"));
                case 2 -> texture = new Texture(Gdx.files.internal("Sprites/doorOpenOld.png"));
                case 3 -> texture = new Texture(Gdx.files.internal("Sprites/doorOpenBlue.png"));
                case 4 -> texture = new Texture(Gdx.files.internal("Sprites/doorOpenGreen.png"));
            }
        }
    }
}
