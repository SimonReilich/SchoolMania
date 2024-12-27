package com.simon.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

public class Game extends ApplicationAdapter {

	private SpriteBatch spriteBatch;
	private ExtendViewport viewport;
	private OrthographicCamera cam;
	private float viewportWidth,viewportHeight;
	Player player;
	private Map[] maps;
	private byte mapId;
	private int firstFrame;
	private Texture logo;
	
	@Override
	public void create () {
		viewportWidth = 32 * 30;
		viewportHeight = 32 * 20;

		spriteBatch = new SpriteBatch();
		cam = new OrthographicCamera();
		viewport = new ExtendViewport(viewportWidth, viewportHeight, cam);

		// Managing maps
		mapId = 0;
		maps = new Map[10];
		maps[mapId] = MapFactory.create(mapId, cam, maps);
		maps[mapId].enter();

		// Setting up everything regarding the player-character
		player = new Player(maps);
		player.updateMap(mapId);

		// Startup Animation
		firstFrame = -1;
		logo = new Texture(Gdx.files.internal("Sprites/title.png"));

		// Input Processor for managing the keyboard inputs
		Gdx.input.setInputProcessor(new InputAdapter() {
			@Override
			public boolean keyDown(int keycode) {
				switch (keycode) {
					case Input.Keys.ESCAPE:
						if (player.isInInventory()) {
							player.closeInventory();
						} else {
							Gdx.app.exit();
						}
						return super.keyDown(keycode);
					case Input.Keys.W:
						player.posYDelta++;
						return super.keyDown(keycode);
					case Input.Keys.S:
						player.posYDelta--;
						return super.keyDown(keycode);
					case Input.Keys.D:
						player.posXDelta++;
						return super.keyDown(keycode);
					case Input.Keys.A:
						player.posXDelta--;
						return super.keyDown(keycode);
					case Input.Keys.CONTROL_LEFT:
						player.startSprint();
						return super.keyDown(keycode);
					case Input.Keys.E:
						if (!player.isInInventory()) {
							player.openInventory();
							return super.keyDown(keycode);
						}
						if (player.isInInventory()) {
							player.closeInventory();
							return super.keyDown(keycode);
						}
					case Input.Keys.LEFT:
						if (player.isInInventory()) {
							player.selectL();
						}
						return super.keyDown(keycode);
					case Input.Keys.RIGHT:
						if (player.isInInventory()) {
							player.selectR();
						}
						return super.keyDown(keycode);
					case Input.Keys.F:
						maps[mapId].pickUp(player);
						return super.keyDown(keycode);
					case Input.Keys.SPACE:
						if (player.isInInventory()) {
							player.useItem(maps[mapId]);
						}
						return super.keyDown(keycode);
					case Input.Keys.ENTER:
						if (firstFrame == -1) {
							firstFrame = 0;
							logo.dispose();
						} else if (firstFrame == 1) {
							Gdx.app.exit();
						}
						return super.keyDown(keycode);
				}
				return super.keyDown(keycode);
			}

			@Override
			public boolean keyUp(int keycode) {
				switch (keycode) {
					case Input.Keys.W -> {
						player.posYDelta--;
						return super.keyUp(keycode);
					}
					case Input.Keys.S -> {
						player.posYDelta++;
						return super.keyUp(keycode);
					}
					case Input.Keys.D -> {
						player.posXDelta--;
						return super.keyUp(keycode);
					}
					case Input.Keys.A -> {
						player.posXDelta++;
						return super.keyUp(keycode);
					}
					case Input.Keys.CONTROL_LEFT -> {
						player.endSprint();
						return super.keyUp(keycode);
					}
				}
				return super.keyUp(keycode);
			}
		});
	}

	@Override
	public void render () {
		ScreenUtils.clear(Color.BLACK);
		viewport.apply();
		spriteBatch.setProjectionMatrix(viewport.getCamera().combined);
		if (firstFrame == -1) {
			spriteBatch.begin();
			spriteBatch.draw(logo, 0, 0);
			spriteBatch.end();
		} else if (firstFrame == 0) {
			maps[mapId].draw(spriteBatch);
			player.draw(spriteBatch);
			act();
		} else {
			spriteBatch.begin();
			spriteBatch.draw(logo, 0, 0);
			spriteBatch.end();
		}
	}

	private void act() {
		player.act();

		// updating the map
		if (mapId != (mapId = maps[mapId].keepInBounds(player))) {
			player.updateMap(mapId);
			if (mapId == 10) {
				firstFrame = 1;
				logo = new Texture(Gdx.files.internal("Sprites/end.png"));
			} else {
				if (maps[mapId] == null) {
					maps[mapId] = MapFactory.create(mapId, cam, maps);
				}
				maps[mapId].enter();
			}
		}
	}

	@Override
	public void resize(int width, int height) {
		viewport.update(width, height, false);
		viewport.getCamera().position.set(viewportWidth/2f, viewportHeight/2f, 0);
		viewport.getCamera().update();
	}
	
	@Override
	public void dispose () {
		player.dispose();
		spriteBatch.dispose();
	}
}
