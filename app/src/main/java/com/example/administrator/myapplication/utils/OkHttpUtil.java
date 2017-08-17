package com.example.administrator.myapplication.utils;

import android.os.Bundle;
import android.util.Log;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Created by yutianran on 16/2/24.
 */
public class OkHttpUtil {
	private static final String TAG = OkHttpUtil.class.getSimpleName();
	public static final String HMACSHA1_KEY = "0CA7E941169DFA698D4548853EE0A199";
	public static final MediaType JSON = MediaType
			.parse("application/json; charset=utf-8");
	private static final MediaType MEDIA_TYPE_PNG = MediaType
			.parse("image/png");
	private OkHttpClient okHttpClient;
	@SuppressWarnings("unused")
	private Callback callback;

	// 静态内部类的单例模式
	public static OkHttpUtil getInstance() {
		return SingletonHolder.mInstance;
	}

	private static class SingletonHolder {
		private static final OkHttpUtil mInstance = new OkHttpUtil();
	}

	/* OkHttp的基本配置 */
	private OkHttpUtil() {
		okHttpClient = new OkHttpClient();
		okHttpClient.setConnectTimeout(10, TimeUnit.SECONDS);
		okHttpClient.setReadTimeout(10, TimeUnit.SECONDS);
		okHttpClient.setWriteTimeout(10, TimeUnit.SECONDS);
		okHttpClient.setCookieHandler(new CookieManager(null,
				CookiePolicy.ACCEPT_ORIGINAL_SERVER));
		// okHttpClient.setCache(new Cache(CarConstant.HTTP_CACHE_DIR,
		// CarConstant.CACHE_SIZE));
		okHttpClient.setRetryOnConnectionFailure(true);
		// ……
	}

	/* 添加网络请求 */
	public void addRequest_String(String url, int tag,
								  Map<String, String> params, final HttpCallBack<String> callBack) {
		if (params == null || params.size() == 0) {
			Log.e(TAG, "addRequest_String params is null");
			return;
		}
		final Request request = new Request.Builder().url(url).tag(tag)
				.post(buildParams(params)).build();
		final Call call = okHttpClient.newCall(request);
		call.enqueue(new Callback() {
			@SuppressWarnings("unchecked")
			@Override
			public void onResponse(Response response) throws IOException {
				final String body = response.body().string();
				Log.i(TAG, body);
				try {
					if (body != null && body.length() > 0) {
						callBack.onSuccss(body);
					} else {
						Log.e(TAG, "onSuccss but body is empty");
						callBack.onFailure(body);
					}
				} catch (Exception e) {
					Log.e(TAG, "onSuccss Exception:" + e.getMessage());
				}
			}

			@Override
			public void onFailure(Request arg0, IOException arg1) {
				Log.e(TAG, "onFailure" + arg1.getMessage());
				String res = arg1.getMessage();
				callBack.onFailure(res);
			}
		});
	}

	/* 添加网络请求 */
	public <T> void addRequest(String url, int tag, Map<String, String> params,
							   final HttpCallBack<T> callBack) {
		if (params == null || params.size() == 0) {
			return;
		}
		final Request request = new Request.Builder().url(url).tag(tag)
				.post(buildParams(params)).build();
		final Call call = okHttpClient.newCall(request);
		call.enqueue(new Callback() {
			@SuppressWarnings("unchecked")
			@Override
			public void onResponse(Response response) throws IOException {
				final String body = response.body().string();
				Log.e("HTTP", body);
				if (body != null && body.length() > 0) {
					try {
						Type[] types = callBack.getClass()
								.getGenericInterfaces();
						ParameterizedType parameterizedType = (ParameterizedType) types[0];
						final Type actualTypeArguments = parameterizedType
								.getActualTypeArguments()[0];
						// // TODO: 2016/3/10 处理成功后的公用逻辑
						callBack.onSuccss((T) JsonUtil.getInstance()
								.stringToObj(body, actualTypeArguments));
					} catch (Exception e) {
						Log.e("返回错误1", body);
						callBack.onFailure(e.getMessage());

					}
				} else {
					callBack.onFailure("http response is null");
				}
			}

			@Override
			public void onFailure(Request arg0, IOException arg1) {
				@SuppressWarnings("unused")
				String res = arg1.getMessage();
				callBack.onFailure(res);
			}
		});
	}

	/* 添加网络请求 */
	public <T> void addRequest(String url, int tag, Bundle params,
							   final HttpCallBack<T> callBack) {
		if (params == null || params.size() == 0) {
			return;
		}

		final Request request = new Request.Builder().url(url).tag(tag)
				.post(buildParamsBundle(params)).build();
		final Call call = okHttpClient.newCall(request);
		call.enqueue(new Callback() {
			@SuppressWarnings("unchecked")
			@Override
			public void onResponse(Response response) throws IOException {
				final String body = response.body().string();
				Log.e("网络请求正确2", body);
				try {
					Type[] types = callBack.getClass().getGenericInterfaces();
					ParameterizedType parameterizedType = (ParameterizedType) types[0];
					final Type actualTypeArguments = parameterizedType
							.getActualTypeArguments()[0];
					// // TODO: 2016/3/10 处理成功后的公用逻辑
					callBack.onSuccss((T) JsonUtil.getInstance().stringToObj(
							body, actualTypeArguments));
				} catch (Exception e) {
					Log.e("返回错误2", body);
				}
			}

			@Override
			public void onFailure(Request arg0, IOException arg1) {
				final String res = arg1.getMessage();
				Log.e("返回错误3", res);

				callBack.onFailure(res);
			}
		});

	}

//	/* 添加网络请求 */
//	public <T> void addRequest(String url, int tag,
//			final HttpCallBack<T> callBack) {
//		final Request request = new Request.Builder().url(url).tag(tag).build();
//		Call call = okHttpClient.newCall(request);
//		call.enqueue(new Callback() {
//			@Override
//			public void onResponse(Response response) throws IOException {
//				final String body = response.body().string();
//				Log.e("网络请求正确3", body);
//				try {
//					Type[] types = callBack.getClass().getGenericInterfaces();
//					ParameterizedType parameterizedType = (ParameterizedType) types[0];
//					final Type actualTypeArguments = parameterizedType
//							.getActualTypeArguments()[0];
//					// // TODO: 2016/3/10 处理成功后的公用逻辑
//					callBack.onSuccss((T) JsonUtil.getInstance().stringToObj(
//							body, actualTypeArguments));
//
//				} catch (Exception e) {
//					Log.e("网络请求错误4", e.getMessage());
//
//				}
//			}
//
//			@Override
//			public void onFailure(Request arg0, IOException arg1) {
//				final String res = arg1.getMessage();
//				Log.e("网络请求错误5", res);
//			}
//		});
//	}
//
//	/**
//	 * 上传文件
//	 */
//	public <T> void uploadRequest(String url, String path,
//			final HttpCallBack<T> callBack) {
//		// final MediaType
//		// MEDIA_TYPE_MARKDOWN=MediaType.parse("text/x-markdown; charset=utf-8");
//		MultipartBuilder builder = new MultipartBuilder()
//				.type(MultipartBuilder.FORM);
//		// builder.addFormDataPart(null, null,
//		// RequestBody.create(MEDIA_TYPE_PNG, new File(path)));
//		builder.addPart(RequestBody.create(MEDIA_TYPE_PNG, new File(path)));
//		RequestBody requestBody = builder.build();
//
//		final Request request = new Request.Builder().url(url)
//				.post(requestBody).build();
//		Call call = okHttpClient.newCall(request);
//		call.enqueue(new Callback() {
//
//			@SuppressWarnings("unchecked")
//			@Override
//			public void onResponse(Response response) throws IOException {
//				final String body = response.body().string();
//				Log.e("网络请求正确4", body);
//				try {
//					Type[] types = callBack.getClass().getGenericInterfaces();
//					ParameterizedType parameterizedType = (ParameterizedType) types[0];
//					final Type actualTypeArguments = parameterizedType
//							.getActualTypeArguments()[0];
//					// // TODO: 2016/3/10 处理成功后的公用逻辑
//					callBack.onSuccss((T) JsonUtil.getInstance().stringToObj(
//							body, actualTypeArguments));
//
//				} catch (Exception e) {
//					Log.e("网络请求错误6", e.getMessage());
//
//				}
//			}
//
//			@Override
//			public void onFailure(Request arg0, IOException arg1) {
//				final String res = arg1.getMessage();
//				Log.e("网络请求错误7", res);
//			}
//		});
//	}

	/* 移除网络请求 */
	public void removeRequest(int tag) {
		okHttpClient.cancel(tag);
	}

	/* 包装网络请求参数 */
	private RequestBody buildParams(Map<String, String> params) {
//	private RequestBody buildParams(Map<String, Object> params) {
//		JSONObject jsonObject = new JSONObject(params);
//		String transMapToString = jsonObject.toString();
//
//		Log.i("传参", transMapToString);
//		RequestBody body = RequestBody.create(JSON, transMapToString);
		 FormEncodingBuilder builder = new FormEncodingBuilder();
		 Set<Map.Entry<String, String>> entries = params.entrySet();
		 for (Map.Entry<String, String> entry : entries) {
		 builder.add(entry.getKey(), entry.getValue());
		 }

		 RequestBody requestBody = builder.build();
		return requestBody;
	}

	/* 包装网络请求参数 */
	private RequestBody buildParamsBundle(Bundle bundle) {
		FormEncodingBuilder builder = new FormEncodingBuilder();
		Set<String> entries = bundle.keySet();
		for (String entrie : entries) {
			builder.add(entrie, bundle.getString(entrie));
		}

		RequestBody requestBody = builder.build();
		return requestBody;
	}

	/* 网络回调 */
	public interface HttpCallBack<T> {
		void onSuccss(T t);

		void onFailure(String error);
	}

}
