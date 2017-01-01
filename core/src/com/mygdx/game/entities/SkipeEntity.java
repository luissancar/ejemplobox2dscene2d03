package com.mygdx.game.entities;

/**
 * Created by luissancar on 1/1/17.
 */
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;

import static com.mygdx.game.Constans.PIXELS_IN_METER;


public class SkipeEntity extends Actor{
    private Texture texture;
    private World world;
    private Body body;
    private Fixture fixture;

    public SkipeEntity(World word, Texture texture,float x, float y){
        this.world=word;
        this.texture=texture;


        BodyDef def=new BodyDef();
        def.position.set(x,y+0.5f);
        body=word.createBody(def);

        PolygonShape box=new PolygonShape();
        Vector2[] vertices=new Vector2[3];
        vertices[0]=new Vector2(-0.5f,-0.5f);
        vertices[1]=new Vector2(0.5f,-0.5f);
        vertices[2]=new Vector2(0,0.5f);
        box.set(vertices);

        fixture=body.createFixture(box,1);

        fixture.setUserData("spike");
        box.dispose();

        setSize(PIXELS_IN_METER,PIXELS_IN_METER);
        setPosition((x-0.5f)*PIXELS_IN_METER,y*PIXELS_IN_METER);

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        // box2d toma como refeencia el centro del cuerpo por es -0.5

        batch.draw(texture,getX(),getY(),getWidth(),getHeight());

    }

    public void detach() {
        //body.destroyFixture(fixture);
        //world.destroyBody(body);
    }
}