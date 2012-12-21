package jp.mizmon21.android.mizmontouch01;

import android.content.Context;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

class GameView extends SurfaceView implements SurfaceHolder.Callback {
	private GameViewThread		mThread;	// スレッドのインスタンス

	public GameView(Context context) {
		super(context);

		//サーフェイスホルダーを取得(SurfaceView)
		SurfaceHolder holder = getHolder();

		// コールバックを設定(SurfaceHolder)
		holder.addCallback(this);
	}

// ************** SurfaceHolder.Callbackの３兄弟 **********************
	// surface生成時にコールバックされる
	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// スレッド生成しインスタンス化
		mThread = new GameViewThread(holder);

		// スレッド内のメインループ動作を許可する
		// 先に動作許可しておかないとスレッド起動しても直ぐ終了してしまう
		mThread.enableRunning(true);

		// スレッドを起動する
		try {
			mThread.start();
		} catch (IllegalThreadStateException e) {
			// スレッド起動失敗
		}
	}

	// surface変更時にコールバックされる
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
	}

	// surface破棄時にコールバックされる
	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// スレッド内のメインループ動作を停止する
		mThread.enableRunning(false);

		// スレッドを確実に停止させるためwhileでループさせる
		while (true) {
			try {
				// スレッドを停止させる
				mThread.join();
				break;
			} catch (InterruptedException e) {
			}
		}
	}
}
