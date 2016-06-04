package com.example.vincebarry.biaoqinglib;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.LruCache;
import rx.Observer;

public class MainActivity extends AppCompatActivity {
    private GridView mGvBiaoQing;
    private ImageView mIv;
    private LruCache lruCache;
    private BiaoQingAdapter biaoQingAdapter;
    private static int position;
    private int pageNum;
    private Button mBtLast;
    private Button mBtNext;
    private TextView mTvPage;
    private String URL="http://qq.yh31.com/zjbq/0551964.html";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lruCache = new LruCache(MainActivity.this);
        position = 0;
        pageNum = 1;
        mGvBiaoQing = (GridView) findViewById(R.id.gv);
        mBtNext = (Button) findViewById(R.id.bt_next);
        biaoQingAdapter = new BiaoQingAdapter(lruCache,MainActivity.this);
        mGvBiaoQing.setAdapter(biaoQingAdapter);
        mGvBiaoQing.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                new SaveBitmap(MainActivity.this,lruCache).sendTo(position);
            }
        });
        mGvBiaoQing.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                new SaveBitmap(MainActivity.this,lruCache).saveToSD(position);
                return true;
            }
        });
        final Observer<Bitmap> observer = new Observer<Bitmap>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Bitmap bitmap) {
                lruCache.set(position+"",bitmap);
                L.i("size",lruCache.size()+"");
                L.i("size","max:"+lruCache.size()+"");
                L.i("size",position+"");
                position++;
                biaoQingAdapter.notifyDataSetChanged();
                L.i(bitmap.toString());
            }
        };
        RxBiaoQing.getBiaoQing(MainActivity.this,URL).subscribe(observer);
        mBtNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pageNum++;
                if(pageNum!=1){
                    URL= "http://qq.yh31.com/zjbq/0551964"+"_"+pageNum+".html";
                    RxBiaoQing.getBiaoQing(MainActivity.this,URL).subscribe(observer);
                }else{
                    RxBiaoQing.getBiaoQing(MainActivity.this,URL).subscribe(observer);
                }
            }
        });
    }


}
