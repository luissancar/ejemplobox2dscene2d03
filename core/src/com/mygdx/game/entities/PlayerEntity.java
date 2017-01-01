package com.mygdx.game.entities;

/**
 * Created by luissancar on 1/1/17.
 */

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.Constans;

import static com.mygdx.game.Constans.PIXELS_IN_METER;
import static com.mygdx.game.Constans.SPEED_PLAYER;

public class PlayerEntity extends Actor{
    private Texture texture;
    private World world;
    private Body body;
    private Fixture fixture;
    private boolean alive=true, jumping=false, mustJump=false;

    public PlayerEntity(World word, Texture texture, Vector2 position){
        this.world=word;
        this.texture=texture;

        BodyDef def=new BodyDef();
        def.position.set(position);
        def.type= BodyDef.BodyType.DynamicBody;
        body=word.createBody(def);

        PolygonShape box=new PolygonShape();
        box.setAsBox(0.5f,0.5f);
        fixture=body.createFixture(box,3);
        fixture.setUserData("player");
        box.dispose();

        setSize(PIXELS_IN_METER,PIXELS_IN_METER);

    }

    @Override
    public void act(float delta) {
        // saltar si se toca pantalla
        if ( mustJump) {
            setMustJump(false);
            jump();
        }
        if (isAlive()) {
            float speedY=body.getLinearVelocity().y;
            body.setLinearVelocity(SPEED_PLAYER,speedY);
        }
        if (jumping) {
            body.applyForceToCenter(0,Constans.IMPULSE_JUMP*-1.1f,true); // para que caiga más rápido
        }

        // Si está vivo avanzar
    }

    public  void jump(){
        if (!jumping && alive) {
            jumping=true;
            Vector2 position = body.getPosition();
            body.applyLinearImpulse(0, Constans.IMPULSE_JUMP, position.x, position.y, true);
        }

    }

    public void setMustJump(boolean mustJump) {
        this.mustJump = mustJump;
    }

    public boolean isMustJump() {
        return mustJump;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        // box2d toma como refeencia el centro del cuerpo por es -0.5
        setPosition((body.getPosition().x-0.5f)*PIXELS_IN_METER,
                (body.getPosition().y-0.5f)*PIXELS_IN_METER);
        batch.draw(texture,getX(),getY(),getWidth(),getHeight());

    }

    public void detach() {
        body.destroyFixture(fixture);
        world.destroyBody(body);
    }

    public void setJumping(boolean jumping) {
        this.jumping = jumping;
    }

    public boolean isJumping() {
        return jumping;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public boolean isAlive() {
        return alive;
    }

    public static class FloorEntiy extends Actor{
        private Texture floor, overfloor;
        private World world;
        private Body body;
        private Fixture fixture;

        public FloorEntiy(World word, Texture floor,Texture overfloor, float x, float width, float y){
            this.world=word;
            this.floor=floor;
            this.overfloor=overfloor;

            BodyDef def=new BodyDef();
            def.position.set(x+width/2,y-0.5f);
            body=word.createBody(def);

            PolygonShape box=new PolygonShape();
            box.setAsBox(width/2,0.5f);
            fixture=body.createFixture(box,1);
            box.dispose();

            setSize(PIXELS_IN_METER,PIXELS_IN_METER);
            setPosition((x-width/2)*PIXELS_IN_METER,(y-1)*PIXELS_IN_METER);

        }

        @Override
        public void draw(Batch batch, float parentAlpha) {
            // box2d toma como refeencia el centro del cuerpo por es -0.5
            setPosition((body.getPosition().x-0.5f)*PIXELS_IN_METER,
                    (body.getPosition().y-0.5f)*PIXELS_IN_METER);
            batch.draw(floor,getX(),getY(),getWidth(),getHeight());

        }

        public void detach() {
            body.destroyFixture(fixture);
            world.destroyBody(body);
        }
    }
}