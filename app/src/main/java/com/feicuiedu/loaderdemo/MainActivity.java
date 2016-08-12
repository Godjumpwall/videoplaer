package com.feicuiedu.loaderdemo;

import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.widget.GridView;

// 使用Loader
// 获取显示手机所有音频文件
public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private GridView gridView;
    private AudioAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gridView = (GridView) findViewById(R.id.gridView);
        adapter = new AudioAdapter(this);
        gridView.setAdapter(adapter);
        // 初始Loader(1. id为0的loader已存在，拿出来直接重用， id为0的loader不存在,触发onCreateLoader方法 )
        getSupportLoaderManager().initLoader(0, null, this);
    }

    @Override public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection = {
                MediaStore.Audio.Media._ID,          // id
                MediaStore.Audio.Media.DISPLAY_NAME, // 音乐名称
                MediaStore.Audio.Media.DATA,  // data
                MediaStore.Audio.Media.ALBUM, // 音乐专辑
                MediaStore.Audio.Media.ARTIST, // 谁唱的
                MediaStore.Audio.Media.DURATION, // 时长
                MediaStore.Audio.Media.SIZE // 大小
        };
        return new CursorLoader(
                this,
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, // 视频数据的URI
                projection, null, null, null);
    }

    @Override public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        // 将Loader异步加载完成的Cursor，给adapter
        adapter.swapCursor(cursor);
    }

    @Override public void onLoaderReset(Loader<Cursor> loader) {
        adapter.swapCursor(null);
    }
}