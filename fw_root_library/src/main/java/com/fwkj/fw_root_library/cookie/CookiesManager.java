package com.fwkj.fw_root_library.cookie;

import android.content.Context;

import java.util.List;

import androidx.annotation.NonNull;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;

public class CookiesManager implements CookieJar {

    private PersistentCookieStore cookieStore;

    public CookiesManager(Context context) {
        cookieStore = new PersistentCookieStore(context);
    }

    @Override
    public void saveFromResponse(@NonNull HttpUrl url, @NonNull List<Cookie> cookies) {
        if (cookies.size() > 0) {
            for (Cookie item : cookies) {
                cookieStore.add(url, item);
            }
        }
    }

    @Override
    public List<Cookie> loadForRequest(@NonNull HttpUrl url) {
        return cookieStore.get(url);
    }
}
