package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.Game;

public class MyGdxGame extends Game {




	public BaseScreen gamescreen, gameoverscreen, menuScreen, loadingScreen;
	private AssetManager manager;  //para centralizar los recursos


	public AssetManager getManager() {
		return manager;
	}




	@Override
	public void create() {
		manager=new AssetManager();
		manager.load("floor.png",Texture.class);
		manager.load("overfloor1.png",Texture.class);
		manager.load("spike.png",Texture.class);
		manager.load("logo.png", Texture.class);
		manager.load("player.png",Texture.class);
		manager.load("audio/die.ogg",Sound.class);
		manager.load("audio/jump.ogg",Sound.class);
		manager.load("audio/song.ogg",Music.class);
		manager.load("gameover.png",Texture.class);
		manager.finishLoading();
		loadingScreen=new LoadingScreen(this);

		setScreen(loadingScreen);
	}

	/**
	 * This method is invoked by LoadingScreen when all the assets are loaded. Use this method
	 * as a second-step loader. You can load the rest of the screens here and jump to the main
	 * screen now that everything is loaded.
	 */
	public void finishLoading() {
		menuScreen = new MenuScreen(this);
		gamescreen = new GameScreen(this);
		gameoverscreen = new GameOverScreen(this);
		//creditsScreen = new CreditsScreen(this);
		setScreen(gamescreen);
	}
}