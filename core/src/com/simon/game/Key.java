package com.simon.game;

import com.badlogic.gdx.graphics.Texture;

public class Key extends Item{

    private final int keyCode;

    public Key(Texture texture, int x, int y, Map map, int code) {
        super(texture, x, y, map);
        keyCode = code;
    }

    public Key(Texture texture, int x, int y, int code) {
        super(texture, x, y);
        keyCode = code;
    }

    @Override
    public boolean use(Player player, Entity interaction) {
        if (interaction instanceof Door) {
            ((Door) interaction).open(keyCode);
            return true;
        }
        return false;
    }
}