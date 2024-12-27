package com.simon.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class ExplosiveStuff extends Item{

    private Texture explosion;
    private float duration;
    public ExplosiveStuff(int x, int y, Map map) {
        super(new Texture(Gdx.files.internal("Sprites/explosiveStuff.png")), x, y, map);
        duration = Float.MIN_VALUE;
    }

    @Override
    public boolean use(Player player, Entity interaction) {
        player.removeFromInv(this);
        takable = false;
        this.map = player.getMap();
        map.addEntity(this);
        setPos(player.getX(), player.getY());
        duration = 6.0f;
        texture = new Texture(Gdx.files.internal("Sprites/explosiveStuffLit.png"));
        explosion = new Texture(Gdx.files.internal("Sprites/explosion.png"));
        return true;
    }

    @Override
    public void draw(SpriteBatch batch) {
        if (duration == Float.MIN_VALUE) {
            batch.begin();
            batch.draw(texture, getX(), getY());
            batch.end();
        } else {
            duration -= Gdx.graphics.getDeltaTime();
            if (duration > 1) {
                batch.begin();
                batch.draw(texture, getX(), getY());
                batch.end();
            } else if (duration > 0) {
                batch.begin();
                batch.draw(explosion, getX() - 32, getY() - 32);
                batch.end();
            } else {
                map.removeEntity(this);
                this.dispose();
            }
        }
    }

    @Override
    public void dispose() {
        texture.dispose();
        if (explosion != null) {
            Entity entity = map.getNearestEntity(getX() + 16, getY() + 16);
            if (entity instanceof Door) {
                ((Door) entity).open(2);
            }
            explosion.dispose();
        }
    }
}
