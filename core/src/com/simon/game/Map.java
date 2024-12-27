package com.simon.game;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.Array;

import java.awt.*;
import java.util.Arrays;
import java.util.Optional;

public abstract class Map {
    private final TiledMap map;
    protected final Array<Entity> entities;
    private final TiledMapRenderer renderer;
    private final Camera cam;

    public Map(TiledMap map, Camera cam, Entity... entities) {
        this.map = map;
        this.entities = new Array<>(entities);
        this.renderer = new OrthogonalTiledMapRenderer(map);
        this.cam = cam;
    }

    public TiledMap getMap() {
        return map;
    }

    public abstract byte keepInBounds(Player player);
    public abstract void enter();

    public void draw(SpriteBatch batch) {
        renderer.setView((OrthographicCamera) cam);
        renderer.render();

        for(int i = 0; i < entities.size; i++) {
            if (entities.get(i) != null) {
                entities.get(i).draw(batch);
            }
        }
    }

    public void removeEntity(Entity entity) {
        entities.removeValue(entity, true);
    }

    public Entity getNearestEntity(int x, int y) {
        Optional<Entity> res = Arrays.stream(entities.toArray()).filter((a) -> (new Point(a.getX(), a.getY()).distance(x + 8, y + 16) <= 64)).min((a, b) ->((b.getX() - x)^2 + (b.getY() - y)^2) - ((a.getX() - x)^2 + (a.getY() - y)^2));
        return res.orElse(null);
    }

    public void pickUp(Player player) {
        Optional<Entity> itemOpt = Arrays.stream(entities.toArray()).filter((a) -> a instanceof Item).filter((a) -> (new Point(a.getX(), a.getY()).distance(player.getX() + 8, player.getY() + 16) <= 64)).min((a, b) -> (int) (new Point(a.getX(), a.getY()).distance(player.getX() + 8, player.getY() + 16) - new Point(b.getX(), b.getY()).distance(player.getX() + 8, player.getY() + 16)));
        if (itemOpt.isPresent()) {
            Item item = (Item) itemOpt.get();
            removeEntity(item);
            player.addToInventory(item);
        }
    }

    public void addEntity(Entity entity) {
        entities.add(entity);
    }
}
