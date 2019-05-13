package com.fwkj.fw_root_library.net;

import android.content.Context;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.fwkj.fw_root_library.BuildConfig;
import com.fwkj.fw_root_library.cookie.CookiesManager;
import com.fwkj.fw_root_library.logging.Level;
import com.fwkj.fw_root_library.logging.LoggingInterceptor;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;
import okhttp3.internal.platform.Platform;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetWorkManager {
    private static NetWorkManager mInstance;
    private static Retrofit retrofit;
    private static Context mContext;

    public static NetWorkManager getInstance(Context pAppContext) {
        if (mInstance == null) {
            synchronized (NetWorkManager.class) {
                if (mInstance == null) {
                    mInstance = new NetWorkManager();
                }
            }
        }
        mContext = pAppContext;
        return mInstance;
    }

    /**
     * 初始化必要对象和参数
     */
    public void init(boolean isLog, String baseUrl) {
        if (ObjectUtils.isEmpty(baseUrl)){
            return;
        }
        LoggingInterceptor.Builder builder = new LoggingInterceptor.Builder()
                .loggable(isLog)
                .setLevel(Level.BASIC)
                .log(Platform.INFO)
                .loggable(BuildConfig.DEBUG)
                .request("Request")
                .response("Response");
//                .addHeader("versionMsg", BuildConfig.VERSION_NAME);
//                .addHeader("version", BuildConfig.VERSION_NAME)
//                .addQueryParam("query", "0");
//              .logger(new Logger() {
//                  @Override
//                  public void log(int level, String tag, String msg) {
//                      Log.w(tag, msg);
//                  }
//              }
        // 初始化okhttp
        OkHttpClient client = getUnsafeOkHttpClient().newBuilder()
                .addInterceptor(builder.build())
                .cookieJar(new CookiesManager(mContext))
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .build();
//            OkHttpClient client = new OkHttpClient.Builder()
//                    .addInterceptor(builder.build())
//                    .cookieJar(new CookiesManager(mContext))
//                    .readTimeout(10, TimeUnit.SECONDS)
//                    .writeTimeout(10, TimeUnit.SECONDS)
//                    .build();
            // 初始化Retrofit
            retrofit = new Retrofit.Builder()
                    .client(client)
                    .baseUrl(baseUrl)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
    }

    private static OkHttpClient getUnsafeOkHttpClient() {
        try {
            // Create a trust manager that does not validate certificate chains
            final TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return new java.security.cert.X509Certificate[]{};
                        }
                    }
            };

            // Install the all-trusting trust manager
            final SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            // Create an ssl socket factory with our all-trusting manager
            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.sslSocketFactory(sslSocketFactory);
            builder.hostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });

            OkHttpClient okHttpClient = builder.build();
            return okHttpClient;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private SSLSocketFactory getSsl() {
        String fileName = "hdl.bks";
        SSLContext sslContext = null;
        try {
            InputStream stream = mContext.getResources().getAssets().open(fileName);
            //keystore添加证书内容和密码
            String keyStoreType = KeyStore.getDefaultType();
            KeyStore keyStore = KeyStore.getInstance(keyStoreType);
            keyStore.load(stream, "123456".toCharArray());
            //证书工厂类，生成证书
            CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509", "BC");
            Certificate certificate = certificateFactory.generateCertificate(stream);
            keyStore.setCertificateEntry("ca", certificate);
//            LogUtils.d(certificate.getPublicKey().toString());
            //信任管理器工厂
            String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
            TrustManagerFactory tmf = TrustManagerFactory.getInstance(tmfAlgorithm);
            tmf.init(keyStore);
            //构建一个ssl上下文，加入ca证书格式，与后台保持一致
            sslContext = SSLContext.getInstance("TLS");
            //参数，添加受信任证书和生成随机数
            sslContext.init(null, tmf.getTrustManagers(), null);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        }
        return sslContext != null ? sslContext.getSocketFactory() : null;
    }

    public static Retrofit getRetrofit() {
        return retrofit;
    }
}
