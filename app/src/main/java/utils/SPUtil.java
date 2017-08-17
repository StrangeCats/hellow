package utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.text.DecimalFormat;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class SPUtil {

	/**
	 * ��ȡ��ѡ��
	 * 
	 * @param context
	 * @return
	 */
	public static SharedPreferences getSharedPreference(Context context) {
		return context.getSharedPreferences("config", Context.MODE_PRIVATE);
	}

	/**
	 * ��ȡһ��string���͵���ݣ�Ĭ��ֵΪnull
	 * 
	 * @param context
	 * @param key
	 * @return
	 */
	public static String getString(Context context, String key) {
		SharedPreferences sp = getSharedPreference(context);
		String result = sp.getString(key, null);
		return result;
	}

	/**
	 * ��ȡһ��int���͵���ݣ�Ĭ��ֵΪ0
	 * 
	 * @param context
	 * @param key
	 * @return
	 */
	public static int getInt(Context context, String key) {
		SharedPreferences sp = getSharedPreference(context);
		int result = sp.getInt(key, 0);
		return result;
	}

	/**
	 * ��ȡһ��boolean���͵���ݣ�Ĭ��ֵΪfalse
	 * 
	 * @param context
	 * @param key
	 * @return
	 */
	public static boolean getBoolean(Context context, String key) {
		SharedPreferences sp = getSharedPreference(context);
		boolean result = sp.getBoolean(key, false);
		return result;
	}

	/**
	 * ����ѡ���б���String��int��boolean���͵����
	 * 
	 * @param context
	 * @param key
	 * @param value
	 */
	public static void put(Context context, String key, Object value) {
		SharedPreferences sp = getSharedPreference(context);
		Editor editor = sp.edit();
		if (value instanceof String) {
			editor.putString(key, (String) value);
		} else if (value instanceof Integer) {
			editor.putInt(key, (Integer) value);
		} else if (value instanceof Boolean) {
			editor.putBoolean(key, (Boolean) value);
		}
		// �ύ
		editor.commit();
	}

	public static int getInt(Context context, String key, int defValue) {
		SharedPreferences sp = getSharedPreference(context);
		int result = sp.getInt(key, defValue);
		return result;
	}

	public static void startActivity(Context context,
			Class<? extends Activity> clazz) {
		Intent intent = new Intent(context, clazz);
		context.startActivity(intent);
	}

	public static void showToast(Context context, String text) {
		Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
	}

	// // 获取路径
	// public static String getImgurl(Context context, String imgid) {
	// String url = "";
	// if(SPUtil.TokenValid(context))
	// {
	// url = context.getString(R.string.IP)
	// + context.getString(R.string.imageurl) + imgid
	// + "?access_token="
	// + SPUtil.getString(context, Constant.ACCESS_TOKEN);
	// }
	// else
	// {
	// String ip = context.getString(R.string.IP)
	// + context.getString(R.string.imageurl) + imgid;
	// url = OkHttpUtil.getInstance().getRequestUrl(ip);
	// }
	// return url;
	// }

	// 防止重复点击
	private static long lastClickTime;

	public synchronized static boolean isFastClick() {
		long time = System.currentTimeMillis();
		if (time - lastClickTime < 500) {
			return true;
		}
		lastClickTime = time;
		return false;
	}

	public static String FormatString(String instr, int dignum) {
		if (instr == null || instr.equals("")) {
			instr = "0";
		}
		double d = 0;
		String outstr = "";
		try {
			d = Double.valueOf(instr);
			DecimalFormat df = new DecimalFormat();
			df.setMaximumFractionDigits(dignum);
			outstr = df.format(d);
		} catch (Exception e) {
			Log.i("FormatString", e.getMessage());
		}
		return outstr;
	}

	public static String FormatString(Double indouble, int dignum) {
		double d = indouble;
		String outstr = "";
		try {
			DecimalFormat df = new DecimalFormat();
			df.setMaximumFractionDigits(dignum);
			outstr = df.format(d);
		} catch (Exception e) {
			Log.i("FormatString", e.getMessage());
		}
		return outstr;
	}

	public static Bitmap getBitmapFromView(View view) {
		view.destroyDrawingCache();
		view.measure(View.MeasureSpec.makeMeasureSpec(0,
				View.MeasureSpec.UNSPECIFIED), View.MeasureSpec
				.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
		view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
		view.setDrawingCacheEnabled(true);
		Bitmap bitmap = view.getDrawingCache(true);
		return bitmap;
	}

	public static Bitmap getBitmapFromPath(String path) {

		if (!new File(path).exists()) {
			System.err.println("getBitmapFromPath: file not exists");
			return null;
		}
		// Bitmap bitmap = Bitmap.createBitmap(1366, 768, Config.ARGB_8888);
		// Canvas canvas = new Canvas(bitmap);
		// Movie movie = Movie.decodeFile(path);
		// movie.draw(canvas, 0, 0);
		//
		// return bitmap;

		byte[] buf = new byte[1024 * 1024];// 1M
		Bitmap bitmap = null;

		try {

			FileInputStream fis = new FileInputStream(path);
			int len = fis.read(buf, 0, buf.length);
			bitmap = BitmapFactory.decodeByteArray(buf, 0, len);
			if (bitmap == null) {
				System.out.println("len= " + len);
				System.err
						.println("path: " + path + "  could not be decode!!!");
			}
		} catch (Exception e) {
			e.printStackTrace();

		}

		return bitmap;
	}

	public static boolean isNetworkAvailable(Context context) {   
        ConnectivityManager cm = (ConnectivityManager) context   
                .getSystemService(Context.CONNECTIVITY_SERVICE);   
        if (cm == null) {   
        } else {
            NetworkInfo[] info = cm.getAllNetworkInfo();   
            if (info != null) {   
                for (int i = 0; i < info.length; i++) {   
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {   
                        return true;   
                    }   
                }   
            }   
        }   
        return false;   
       }

	public static Typeface getTypeface(Context context){
		Typeface typeface = Typeface.createFromAsset(context.getAssets(),
				"PingFang Regular.ttf");
		return typeface;
	}
}
