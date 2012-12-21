package jp.mizmon21.android.mizmontouch01;

import android.os.Bundle;
import android.app.Activity;
import android.view.Window;

public class MizmonTouch01 extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// タイトルバーを表示させないおまじない
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		// GameViewを画面としてセットする
		GameView gameView = new GameView(this);
		setContentView(gameView);
	}
}