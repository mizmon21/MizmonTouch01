package jp.mizmon21.android.mizmontouch01;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;

class GameViewThread extends Thread {
	// システム全体の変数
	private SurfaceHolder		mHolder;							// サーフェイスホルダー

	// スレッド関係
	private static final int	CYCLE_TIME	= 50;					// サイクルタイム=50ms

	// 各種フラグ
	private boolean				bRunning		= false;			// メインループ動作フラグ（外部アクセス）

/*********************** 外部から呼ばれるメソッド ***********************/
	// コンストラクタ
	public GameViewThread(SurfaceHolder surfaceHolder) {
		this.mHolder = surfaceHolder;
	}

	// メインループの動作許可設定
	public void enableRunning(boolean flag) {
		this.bRunning = flag;		// メインループ動作許可
	}

/*********************** メインループ ***********************/
	// スレッドを起動すると呼ばれる
	@Override
	public void run() {
		long lastTime = System.currentTimeMillis();
		long now = 0;
		long start_time = 0;
		long loop_time = 0;
		long sleep_time = 0;
		while(bRunning) {
			// ループ開始時刻を記録
			start_time = System.currentTimeMillis();

			Canvas canvas = null;
			try {
				// 描画を開始を宣言
				canvas = mHolder.lockCanvas(null);
				{	// for Debug
					Paint paint=new Paint();
					paint.setAntiAlias(true);
					canvas.drawColor(Color.argb(255, 0, 0, 32));

					paint.setTextSize(36);
					paint.setColor(Color.YELLOW);
					int j=2;
					canvas.drawText("loop_time="+loop_time, 0, 40*j, paint); j++;
					canvas.drawText("sleep_time="+sleep_time, 0, 40*j, paint); j++;
				}
			} catch(Exception e) {
			} finally {
				if (null != canvas) {
					//描画を終了
					mHolder.unlockCanvasAndPost(canvas);
				}
			}

			// ループが一定時間の間隔で回るための処理
			now = System.currentTimeMillis();
			loop_time = now - lastTime;		// ループ１周分の時間 for Debug
			sleep_time = CYCLE_TIME - (now - start_time);
			lastTime = now;

			//スリープ
			try {
				Thread.sleep(sleep_time);
			} catch (Exception e) {
			}
		}
	}
}
