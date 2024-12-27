package com.simon.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class MapFactory{
    public static Map create (int mapId, Camera cam, Map[] maps) {
        return switch (mapId) {
            case 0 ->
                // SMV-Zimmer
                    new Map(new TmxMapLoader().load("00.tmx"), cam,
                            new Door(15 * 32 - 16, 12 * 32 - 6, maps[0], 0, false),
                            new Entity(13 * 32, 9 * 32 - 8, maps[0], (byte) 128, (byte) 80, false) {
                                private final Texture texture = new Texture(Gdx.files.internal("Sprites/carpet.png"));

                                @Override
                                public void draw(SpriteBatch batch) {
                                    batch.begin();
                                    batch.draw(texture, getX(), getY());
                                    batch.end();
                                }

                                @Override
                                public void dispose() {
                                    texture.dispose();
                                }
                            },
                            new Entity(17 * 32 + 16, 9 * 32, maps[0], (byte) 32, (byte) 72, true) {
                                private final Texture texture = new Texture(Gdx.files.internal("Sprites/couch.png"));

                                @Override
                                public void draw(SpriteBatch batch) {
                                    batch.begin();
                                    batch.draw(texture, getX(), getY());
                                    batch.end();
                                }

                                @Override
                                public void dispose() {
                                    texture.dispose();
                                }
                            },
                            new Entity(11 * 32 + 20, 9 * 32 + 16, maps[0], (byte) 24, (byte) 32, true) {
                                private final Texture texture = new Texture(Gdx.files.internal("Sprites/table.png"));

                                @Override
                                public void draw(SpriteBatch batch) {
                                    batch.begin();
                                    batch.draw(texture, getX(), getY());
                                    batch.end();
                                }

                                @Override
                                public void dispose() {
                                    texture.dispose();
                                }
                            },
                            new Key(new Texture(Gdx.files.internal("Sprites/keyOrange.png")), 11 * 32 + 16, 10 * 32 - 8, maps[0], 0)) {
                        @Override
                        public byte keepInBounds(Player player) {
                            if (player.getX() < 11 * 32) {
                                player.setX(11 * 32);
                            } else if (player.getX() + player.getRectangle().width > 19 * 32) {
                                player.setX((int) (19 * 32 - player.getRectangle().width));
                            }

                            if (player.getY() < 8 * 32) {
                                player.setY(8 * 32);
                            } else if (player.getY() + player.getRectangle().height > 12 * 32 + 16) {
                                player.setY((int) (12 * 32 + 16 - player.getRectangle().height));
                            }

                            for (Entity entity : entities) {
                                if (entity.collide) {
                                    entity.collision(player);
                                }
                            }

                            if (player.getRectangle().contains(new Vector2(15 * 32, 12 * 32))) {
                                player.setX(28 * 32);
                                player.setY(9 * 32);
                                return 1;
                            }

                            return 0;
                        }

                        @Override
                        public void enter() {

                        }
                    };
            case 1 ->
                // Aula
                    new Map(new TmxMapLoader().load("01.tmx"), cam, new Entity(7 * 32, 13 * 32, maps[1], (short) 320, (short) 128, false) {

                        private final Texture texture = new Texture(Gdx.files.internal("Sprites/stage.png"));

                        @Override
                        public void draw(SpriteBatch batch) {
                            batch.begin();
                            batch.draw(texture, getX(), getY());
                            batch.end();
                        }

                        @Override
                        public void dispose() {
                            texture.dispose();
                        }
                    }, new Entity(8 * 32, 14 * 32 + 16, maps[1], (short) 256, (short) 96, true) {
                        @Override
                        public void draw(SpriteBatch batch) {

                        }

                        @Override
                        public void dispose() {

                        }
                    }, new Door(19 * 32 + 16, 16 * 32 - 6, maps[1], 2, false)) {
                        @Override
                        public byte keepInBounds(Player player) {
                            if (player.getX() < 1 * 32) {
                                player.setX(1 * 32);
                            } else if (player.getX() + player.getRectangle().width > 29 * 32) {
                                player.setX((int) (29 * 32 - player.getRectangle().width));
                            }

                            if (player.getY() < 4 * 32) {
                                player.setY(4 * 32);
                            } else if (player.getY() + player.getRectangle().height > 16 * 32 + 16) {
                                player.setY((int) (16 * 32 + 16 - player.getRectangle().height));
                            }

                            for (Entity entity : entities) {
                                if (entity.collide) {
                                    entity.collision(player);
                                }
                            }

                            if (player.getRectangle().contains(new Vector2(29 * 32, 10 * 32))) {
                                player.setX(15 * 32 - 8);
                                player.setY(11 * 32 - 8);
                                return 0;
                            }

                            if (player.getRectangle().overlaps(new Rectangle(1 * 32 + 1, 8 * 32 + 16, 0, 2 * 32))) {
                                player.setX(19 * 32);
                                player.setY(9 * 32);
                                return 2;
                            }

                            if (player.getRectangle().contains(new Vector2(20 * 32, 16 * 32))) {
                                player.setX(18 * 32 + 8);
                                player.setY(15 * 32 - 8);
                                return 5;
                            }

                            if (player.getRectangle().overlaps(new Rectangle(2 * 32 + 16, 16 * 32 + 15, 2 * 32, 0))) {
                                player.setX(3 * 32 + 8);
                                player.setY(3 * 32);
                                return 6;
                            }

                            if (player.getRectangle().overlaps(new Rectangle(25 * 32 + 16, 16 * 32 + 15, 2 * 32, 0))) {
                                player.setX(26 * 32 + 8);
                                player.setY(3 * 32);
                                return 6;
                            }

                            return 1;
                        }

                        @Override
                        public void enter() {

                        }
                    };
            case 2 ->
                // NuT-Gang
                    new Map(new TmxMapLoader().load("02.tmx"), cam,
                            new Entity(10 * 32, 7 * 32 + 16, maps[2], (short) (6 * 32), (short) (5 * 32 + 16), true) {
                                @Override
                                public void draw(SpriteBatch batch) {
                                    return;
                                }

                                @Override
                                public void dispose() {
                                    return;
                                }
                            },
                            new Door(12 * 32 + 16, 7 * 32 - 6, maps[2], 1, true),
                            new Door(12 * 32 + 16, 17 * 32 - 6, maps[2], 1, false)) {
                        @Override
                        public byte keepInBounds(Player player) {
                            if (player.getX() < 10 * 32) {
                                player.setX(10 * 32);
                            } else if (player.getX() + player.getRectangle().width > 20 * 32) {
                                player.setX((int) (20 * 32 - player.getRectangle().width));
                            }

                            if (player.getY() < 3 * 32) {
                                player.setY(3 * 32);
                            } else if (player.getY() + player.getRectangle().height > 17 * 32 + 16) {
                                player.setY((int) (17 * 32 + 16 - player.getRectangle().height));
                            }

                            if (player.getRectangle().overlaps(new Rectangle(20 * 32, 8 * 32 + 16, 0, 2 * 32))) {
                                player.setX(2 * 32);
                                player.setY(9 * 32);
                                return 1;
                            }

                            if (player.getRectangle().contains(new Vector2(13 * 32, 7 * 32))) {
                                player.setX(10 * 32 + 8);
                                player.setY(14 * 32 - 16);
                                return 3;
                            }

                            if (player.getRectangle().contains(new Vector2(13 * 32, 17 * 32))) {
                                player.setX(10 * 32 + 8);
                                player.setY(14 * 32 - 16);
                                return 4;
                            }

                            for (Entity entity : entities) {
                                if (entity.collide) {
                                    entity.collision(player);
                                }
                            }

                            return 2;
                        }

                        @Override
                        public void enter() {

                        }
                    };
            case 3 ->
                // NuT-Raum 1
                    new Map(new TmxMapLoader().load("03.tmx"), cam,
                            new Door(10 * 32, 15 * 32 - 6, maps[3], 1, true),
                            new Key(new Texture(Gdx.files.internal("Sprites/keyYellow.png")), 10 * 32, 10 * 32, 1)) {
                        @Override
                        public byte keepInBounds(Player player) {
                            if (player.getX() < 8 * 32) {
                                player.setX(8 * 32);
                            } else if (player.getX() + player.getRectangle().width > 22 * 32) {
                                player.setX((int) (22 * 32 - player.getRectangle().width));
                            }

                            if (player.getY() < 5 * 32) {
                                player.setY(5 * 32);
                            } else if (player.getY() + player.getRectangle().height > 15 * 32 + 16) {
                                player.setY((int) (15 * 32 + 16 - player.getRectangle().height));
                            }

                            if (player.getRectangle().contains(new Vector2(10 * 32 + 16, 15 * 32))) {
                                player.setX(13 * 32 - 8);
                                player.setY(6 * 32 - 16);
                                return 2;
                            }

                            for (Entity entity : entities) {
                                if (entity.collide) {
                                    entity.collision(player);
                                }
                            }

                            return 3;
                        }

                        @Override
                        public void enter() {

                        }
                    };
            case 4 ->
                // NuT-Raum 2
                    new Map(new TmxMapLoader().load("04.tmx"), cam,
                            new Door(10 * 32, 15 * 32 - 6, maps[3], 1, true),
                            new ExplosiveStuff(10 * 32, 10 * 32, maps[4]))
                    {
                        @Override
                        public byte keepInBounds(Player player) {
                            if (player.getX() < 8 * 32) {
                                player.setX(8 * 32);
                            } else if (player.getX() + player.getRectangle().width > 22 * 32) {
                                player.setX((int) (22 * 32 - player.getRectangle().width));
                            }

                            if (player.getY() < 5 * 32) {
                                player.setY(5 * 32);
                            } else if (player.getY() + player.getRectangle().height > 15 * 32 + 16) {
                                player.setY((int) (15 * 32 + 16 - player.getRectangle().height));
                            }

                            if (player.getRectangle().contains(new Vector2(10 * 32 + 16, 15 * 32))) {
                                player.setX(13 * 32 - 8);
                                player.setY(16 * 32 - 16);
                                return 2;
                            }

                            for (Entity entity : entities) {
                                if (entity.collide) {
                                    entity.collision(player);
                                }
                            }

                            return 4;
                        }

                        @Override
                        public void enter() {
                            if (entities.size < 2) {
                                entities.add(new ExplosiveStuff(10 * 32, 10 * 32, maps[4]));
                            }
                        }
                    };
            case 5 ->
                // Lehrerzimmer
                    new Map(new TmxMapLoader().load("05.tmx"), cam,
                            new Door(18 * 32, 16 * 32 - 6, maps[5], 2, true),
                            new Key(new Texture(Gdx.files.internal("Sprites/keyBlue.png")), 15 * 32, 10 * 32, maps[5], 3)){
                        @Override
                        public byte keepInBounds(Player player) {
                            if (player.getX() < 10 * 32) {
                                player.setX(10 * 32);
                            } else if (player.getX() + player.getRectangle().width > 20 * 32) {
                                player.setX((int) (20 * 32 - player.getRectangle().width));
                            }

                            if (player.getY() < 4 * 32) {
                                player.setY(4 * 32);
                            } else if (player.getY() + player.getRectangle().height > 16 * 32 + 16) {
                                player.setY((int) (16 * 32 + 16 - player.getRectangle().height));
                            }

                            if (player.getRectangle().contains(new Vector2(18 * 32 + 16, 16 * 32))) {
                                player.setX(20 * 32 - 8);
                                player.setY(15 * 32 - 8);
                                return 1;
                            }

                            for (Entity entity : entities) {
                                if (entity.collide) {
                                    entity.collision(player);
                                }
                            }

                            return 5;
                        }

                        @Override
                        public void enter() {

                        }
                    };
            case 6 ->
                // 1. Stock
                new Map(new TmxMapLoader().load("06.tmx"), cam,
                        new Entity(9 * 32, 7 * 32 + 16, maps[6], (short) (12 * 32), (short) (5 * 32 + 16), true) {
                            @Override
                            public void draw(SpriteBatch batch) {

                            }

                            @Override
                            public void dispose() {

                            }
                        },
                        new Door(11 * 32, 7 * 32 - 6, maps[6], 3, false),
                        new Door(10 * 32, 17 * 32 - 6, maps[6], 4, false),
                        new Door(19 * 32, 17 * 32 - 6, maps[6], 4, false)) {
                    @Override
                    public byte keepInBounds(Player player) {
                        if (player.getX() < 1 * 32) {
                            player.setX(1 * 32);
                        } else if (player.getX() + player.getRectangle().width > 29 * 32) {
                            player.setX((int) (29 * 32 - player.getRectangle().width));
                        }

                        if (player.getY() < 3 * 32) {
                            player.setY(3 * 32);
                        } else if (player.getY() + player.getRectangle().height > 17 * 32 + 16) {
                            player.setY((int) (17 * 32 + 16 - player.getRectangle().height));
                        }

                        if (player.getRectangle().overlaps(new Rectangle(2 * 32 + 16, 2 * 32 + 17, 2 * 32, 0))) {
                            player.setX(3 * 32 + 8);
                            player.setY(15 * 32);
                            return 1;
                        }

                        if (player.getRectangle().overlaps(new Rectangle(25 * 32 + 16, 2 * 32 + 17, 2 * 32, 0))) {
                            player.setX(26 * 32 + 8);
                            player.setY(15 * 32);
                            return 1;
                        }

                        if (player.getRectangle().contains(new Vector2(11 * 32 + 16, 7 * 32))) {
                            player.setX(18 * 32 + 8);
                            player.setY(13 * 32 - 16);
                            return 7;
                        }

                        if (player.getRectangle().contains(new Vector2(10 * 32 + 16, 17 * 32))) {
                            player.setX(18 * 32 + 8);
                            player.setY(13 * 32 - 16);
                            return 8;
                        }

                        if (player.getRectangle().contains(new Vector2(19 * 32 + 16, 17 * 32))) {
                            player.setX(18 * 32 + 8);
                            player.setY(13 * 32 - 16);
                            return 9;
                        }

                        for (Entity entity : entities) {
                            if (entity.collide) {
                                entity.collision(player);
                            }
                        }

                        return 6;
                    }

                    @Override
                    public void enter() {

                    }
                };
            case 7 ->
                // Mehrzweckraum
                new Map(new TmxMapLoader().load("07.tmx"), cam,
                        new Door(18 * 32, 14 * 32 - 6, maps[7], 3, true),
                        new Vent(10 * 32, 14 * 32 - 4, maps[7]),
                        new Item(new Texture(Gdx.files.internal("Sprites/screwdriver.png")), 15 * 32, 10 * 32, maps[4]) {
                            @Override
                            public boolean use(Player player, Entity interaction) {
                                if (interaction instanceof Vent) {
                                    ((Vent) interaction).open();
                                    return true;
                                }
                                return false;
                            }
                        }) {
                    @Override
                    public byte keepInBounds(Player player) {
                        if (player.getX() < 9 * 32) {
                            player.setX(9 * 32);
                        } else if (player.getX() + player.getRectangle().width > 21 * 32) {
                            player.setX((int) (21 * 32 - player.getRectangle().width));
                        }

                        if (player.getY() < 6 * 32) {
                            player.setY(6 * 32);
                        } else if (player.getY() + player.getRectangle().height > 14 * 32 + 16) {
                            player.setY((int) (14 * 32 + 16 - player.getRectangle().height));
                        }

                        if (player.getRectangle().contains(new Vector2(18 * 32 + 16, 14 * 32))) {
                            player.setX(11 * 32 + 8);
                            player.setY(6 * 32 - 16);
                            return 6;
                        }

                        if (player.getRectangle().contains(new Vector2(10 * 32 + 16, 14 * 32))) {
                            return 8;
                        }

                        for (Entity entity : entities) {
                            if (entity.collide) {
                                entity.collision(player);
                            }
                        }

                        return 7;
                    }

                    @Override
                    public void enter() {

                    }
                };
            case 8 ->
                // Klassenzimmer 1
                    new Map(new TmxMapLoader().load("07.tmx"), cam,
                            new Door(18 * 32, 14 * 32 - 6, maps[7], 4, false),
                            new Vent(10 * 32, 14 * 32 + 6, maps[7]).open(),
                            new Key(new Texture(Gdx.files.internal("Sprites/keyGreen.png")), 15 * 32, 10 * 32, 4)) {
                        @Override
                        public byte keepInBounds(Player player) {
                            if (player.getX() < 9 * 32) {
                                player.setX(9 * 32);
                            } else if (player.getX() + player.getRectangle().width > 21 * 32) {
                                player.setX((int) (21 * 32 - player.getRectangle().width));
                            }

                            if (player.getY() < 6 * 32) {
                                player.setY(6 * 32);
                            } else if (player.getY() + player.getRectangle().height > 14 * 32 + 16) {
                                player.setY((int) (14 * 32 + 16 - player.getRectangle().height));
                            }

                            if (player.getRectangle().contains(new Vector2(18 * 32 + 16, 14 * 32))) {
                                player.setX(10 * 32 + 8);
                                player.setY(16 * 32 - 16);
                                return 6;
                            }

                            for (Entity entity : entities) {
                                if (entity.collide) {
                                    entity.collision(player);
                                }
                            }

                            return 8;
                        }

                        @Override
                        public void enter() {

                        }
                    };
            case 9 ->
                // Ausgang
                new Map(new TmxMapLoader().load("09.tmx"), cam,
                        new Door(18 * 32, 14 * 32 - 6, maps[7], 4, true),
                        new Entity(0, 0, maps[9], (short) 96, (short) 42, false) {
                            private final Texture texture = new Texture(Gdx.files.internal("Sprites/light.png"));

                            @Override
                            public void draw(SpriteBatch batch) {
                                batch.begin();
                                batch.draw(texture, 13 * 32 + 16, 6 * 32);
                                batch.draw(texture, 13 * 32 + 16, 6 * 32);
                                batch.end();
                            }

                            @Override
                            public void dispose() {

                            }
                        }) {
                    @Override
                    public byte keepInBounds(Player player) {
                        if (player.getX() < 9 * 32) {
                            player.setX(9 * 32);
                        } else if (player.getX() + player.getRectangle().width > 21 * 32) {
                            player.setX((int) (21 * 32 - player.getRectangle().width));
                        }

                        if (player.getY() < 6 * 32) {
                            player.setY(6 * 32);
                        } else if (player.getY() + player.getRectangle().height > 14 * 32 + 16) {
                            player.setY((int) (14 * 32 + 16 - player.getRectangle().height));
                        }

                        if (player.getRectangle().overlaps(new Rectangle(0, 0, 30 * 32, 10 * 32))) {
                            return 10;
                        }

                        return 9;
                    }

                    @Override
                    public void enter() {

                    }
                };
            default -> null;
        };
    }
}
