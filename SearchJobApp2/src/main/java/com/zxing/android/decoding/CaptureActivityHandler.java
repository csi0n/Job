package com.zxing.android.decoding;/*
 * Copyright (C) 2008 ZXing authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.csi0n.searchjob.ui.widget.qr.ui.CaptureActivity;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;
import com.zxing.android.MessageIDs;
import com.zxing.android.view.ViewfinderResultPointCallback;

import java.util.Vector;

/**
 * This class handles all the messaging which comprises the state machine for
 * capture.
 *
 * @author dswitkin@google.com (Daniel Switkin)
 * 
 * 处理相机捕获信息
 * 
 */
public final class CaptureActivityHandler extends Handler {

	private static final String TAG = CaptureActivityHandler.class
			.getSimpleName();

	private final CaptureActivity activity;
	private final DecodeThread decodeThread;
	private State state;

	private enum State {
		PREVIEW, SUCCESS, DONE
	}

	public CaptureActivityHandler(CaptureActivity activity,
								  Vector<BarcodeFormat> decodeFormats, String characterSet) {
		this.activity = activity;
		decodeThread = new DecodeThread(activity, decodeFormats, characterSet,
				new ViewfinderResultPointCallback(activity.getViewfinderView()));
		//启动解析线程
		decodeThread.start();
		state = State.SUCCESS;

		//启动预览
		activity.getCameraManager().startPreview();
		restartPreviewAndDecode();
	}

	@Override
	public void handleMessage(Message message) {
		switch (message.what) {
		case MessageIDs.auto_focus:
			// Log.d(TAG, "Got auto-focus message");
			// When one auto focus pass finishes, start another. This is the
			// closest thing to
			// continuous AF. It does seem to hunt a bit, but I'm not sure what
			// else to do.
			if (state == State.PREVIEW) {
				// CameraManager.get().requestAutoFocus(this,
				// MessageIDs.auto_focus);
				activity.getCameraManager().requestAutoFocus(this,
						MessageIDs.auto_focus);
			}
			break;
		case MessageIDs.restart_preview:
			Log.d(TAG, "Got restart preview message");
			restartPreviewAndDecode();
			break;
		case MessageIDs.decode_succeeded:
			Log.d(TAG, "Got decode succeeded message");
			state = State.SUCCESS;
			Bundle bundle = message.getData();
			Bitmap barcode = bundle == null ? null : (Bitmap) bundle
					.getParcelable(DecodeThread.BARCODE_BITMAP);
			activity.handleDecode((Result) message.obj, barcode);
			break;
		case MessageIDs.decode_failed:
			// We're decoding as fast as possible, so when one decode fails,
			// start another.
			state = State.PREVIEW;
			// CameraManager.get().requestPreviewFrame(decodeThread.getHandler(),
			// MessageIDs.decode);
			activity.getCameraManager().requestPreviewFrame(
					decodeThread.getHandler(), MessageIDs.decode);
			break;
		case MessageIDs.return_scan_result:
			Log.d(TAG, "Got return scan result message");
			activity.setResult(Activity.RESULT_OK, (Intent) message.obj);
			activity.finish();
			break;
		case MessageIDs.launch_product_query:
			Log.d(TAG, "Got product query message");
			String url = (String) message.obj;
			Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
			activity.startActivity(intent);
			break;
		}
	}

	/**
	 * 退出同步
	 */
	public void quitSynchronously() {
		state = State.DONE;
		//停止相机预览
		activity.getCameraManager().stopPreview();
		Message quit = Message.obtain(decodeThread.getHandler(),
				MessageIDs.quit);
		quit.sendToTarget();
		try {
			//等待线程停止
			decodeThread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		// Be absolutely sure we don't send any queued up messages
		removeMessages(MessageIDs.decode_succeeded);
		removeMessages(MessageIDs.decode_failed);
	}
	
	/**
	 * 预览并解析
	 */
	private void restartPreviewAndDecode() {
		if (state == State.SUCCESS) {
			state = State.PREVIEW;
			activity.getCameraManager().requestPreviewFrame(
					decodeThread.getHandler(), MessageIDs.decode);
			activity.getCameraManager().requestAutoFocus(this,
					MessageIDs.auto_focus);
			activity.drawViewfinder();
		}
	}

}
