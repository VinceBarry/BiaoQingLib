package com.example.vincebarry.biaoqinglib;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;

import com.squareup.picasso.Picasso;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by VinceBarry on 2016/6/3.
 */
public class RxBiaoQing {
    private final static String URLPrefix = "http://qq.yh31.com";

    public static Observable<Bitmap> getBiaoQing(final Context context, final String URL) {
        return Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                            try {
                                Document doc = Jsoup.connect(URL)
                                        .timeout(3000)
                                        .get();
                                Elements elements = doc.getElementsByTag("img");
                                for (Element e : elements) {
                                    if (!e.attr("class").toString().equals("pic2")) {
                                        subscriber.onNext(URLPrefix + e.attr("src").toString());
//                            L.i(URLPrefix+e.attr("src").toString());
                                    }
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

            }
        }).flatMap(new Func1<String, Observable<Bitmap>>() {
            @Override
            public Observable<Bitmap> call(String s) {
                Bitmap bitmap = null;
                try {
                    bitmap = Picasso.with(context).load(s).get();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return Observable.just(bitmap);
            }
        }).subscribeOn(Schedulers.io())
          .observeOn(AndroidSchedulers.mainThread());
    }

}
