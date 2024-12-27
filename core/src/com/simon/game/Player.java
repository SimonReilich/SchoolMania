package com.simon.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

public class Player {
    private final Rectangle rectangle;
    public byte posXDelta, posYDelta;
    private byte sprintFac;
    private final Texture[] playerTexture;
    private byte renderState;
    private final Array<Item> inventory;
    private final Texture dark;
    private byte selected;
    private final Texture selector;
    private float lastDeltaX;
    private float lastDeltaY;
    private int map;
    private Map[] maps;

    public Player(Map[] maps) {
        rectangle = new Rectangle(472, 304, 16, 32);
        posXDelta = 0;
        posYDelta = 0;

        lastDeltaX = 0;
        lastDeltaY = 0;

        map = 0;
        this.maps = maps;

        sprintFac = 35;
        renderState = 0;

        playerTexture = new Texture[9];
        playerTexture[0] = new Texture(Gdx.files.internal("Sprites/playerIdle.png"));
        playerTexture[1] = new Texture(Gdx.files.internal("Sprites/playerFront1.png"));
        playerTexture[2] = new Texture(Gdx.files.internal("Sprites/playerFront2.png"));
        playerTexture[3] = new Texture(Gdx.files.internal("Sprites/playerBack1.png"));
        playerTexture[4] = new Texture(Gdx.files.internal("Sprites/playerBack2.png"));
        playerTexture[5] = new Texture(Gdx.files.internal("Sprites/playerLeft1.png"));
        playerTexture[6] = new Texture(Gdx.files.internal("Sprites/playerLeft2.png"));
        playerTexture[7] = new Texture(Gdx.files.internal("Sprites/playerRight1.png"));
        playerTexture[8] = new Texture(Gdx.files.internal("Sprites/playerRight2.png"));

        Pixmap darken = new Pixmap(Gdx.files.internal("Sprites/dark.png"));
        Pixmap resized = new Pixmap(960, 640, darken.getFormat());
        resized.drawPixmap(darken, 0, 0, 1, 1, 0, 0, resized.getWidth(), resized.getHeight());
        dark = new Texture(resized);

        darken.dispose();
        resized.dispose();

        selector = new Texture("Sprites/selector.png");

        inventory = new Array<>();
        selected = 0;
    }

    public void startSprint() {
        sprintFac = 60;
    }

    public void endSprint() {
        sprintFac = 35;
    }

    public byte getSprintFac() {
        return sprintFac;
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    public void draw(SpriteBatch batch) {
        if (renderState != -1) {
            if (posYDelta == -1) {
                batch.begin();
                batch.draw(playerTexture[1 + (renderState / 15) % 2], rectangle.x, rectangle.y);
                batch.end();
            } else if (posYDelta == 1) {
                batch.begin();
                batch.draw(playerTexture[3 + (renderState / 15) % 2], rectangle.x, rectangle.y);
                batch.end();
            } else if (posXDelta == -1) {
                batch.begin();
                batch.draw(playerTexture[5 + (renderState / 15) % 2], rectangle.x, rectangle.y);
                batch.end();
            } else if (posXDelta == 1) {
                batch.begin();
                batch.draw(playerTexture[7 + (renderState / 15) % 2], rectangle.x, rectangle.y);
                batch.end();
            } else {
                batch.begin();
                batch.draw(playerTexture[0], rectangle.x, rectangle.y);
                batch.end();
            }
        } else {
            batch.begin();
            batch.draw(playerTexture[0], rectangle.x, rectangle.y);
            batch.end();

            batch.begin();
            batch.draw(dark, 0, 0);
            batch.end();

            if (inventory.size > 1) {
                batch.begin();
                batch.draw(selector, selected * 48 + 464 - ((inventory.size - 1) * 32 + (inventory.size - 2) * 16) / 2, 272);
                batch.end();
            }

            for (int i = 0; i < inventory.size; i++) {
                inventory.get(i).draw(batch, 48 * i + 464 - ((inventory.size - 1) * 32 + (inventory.size - 2) * 16) / 2 - 8, 304);
            }
        }
    }

    public void act() {
        if (renderState != -1) {
            if (posXDelta != 0 || posYDelta != 0) {
                renderState++;
                renderState %= 100;
                float frameTime = Gdx.graphics.getDeltaTime();
                lastDeltaX = posXDelta * getSprintFac() * frameTime;
                rectangle.x += lastDeltaX;
                lastDeltaY = posYDelta * getSprintFac() * frameTime;
                rectangle.y += lastDeltaY;
            } else {
                renderState = 0;
            }
        }
    }

    public void addToInventory(Item item) {
        inventory.add(item);
    }

    public void dispose() {
        for (Texture texture : playerTexture) {
            texture.dispose();
        }
        for (Item item : inventory) {
            item.dispose();
        }
        dark.dispose();
    }

    public void openInventory() {
        renderState = -1;
    }

    public void closeInventory() {
        renderState = 0;
    }

    public boolean isInInventory() {
        return renderState == -1;
    }

    public void selectL() {
        if (inventory.size > 1) {
            selected--;
            if (selected < 0) {
                selected = (byte) (inventory.size - 1);
            }
        } else {
            selected = 0;
        }
    }

    public void selectR() {
        if (inventory.size > 1) {
            selected++;
            selected %= inventory.size;
        } else {
            selected = 0;
        }
    }

    public void useItem(Map map) {
        inventory.get(selected).use(this, map.getNearestEntity((int) (rectangle.x + 8), (int) (rectangle.y + 16)));
    }

    public int getX() {
        return (int) rectangle.x;
    }

    public int getY() {
        return (int) rectangle.y;
    }

    public void setX(int x) {
        rectangle.x = x;
    }

    public void setY(int y) {
        rectangle.y = y;
    }

    public void revertMovement() {
        rectangle.x -= lastDeltaX;
        rectangle.y -= lastDeltaY;
    }

    public void removeFromInv(Item item) {
        inventory.removeValue(item, true);
    }

    public Map getMap() {
        return maps[map];
    }

    public void updateMap(int map) {
        this.map = map;
    }
}
