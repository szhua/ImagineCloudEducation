package com.imagine.cloud.ui.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.imagine.cloud.R;
import com.imagine.cloud.adapter.SearchAdapter;
import com.imagine.cloud.base.BaseActivity;
import com.imagine.cloud.bean.SearchRecordBean;
import com.imagine.cloud.util.search.SearchRecordManager;
import com.imagine.cloud.widget.LoamoreView;
import com.orhanobut.logger.Logger;
import com.runer.liabary.recyclerviewUtil.ItemDecorations;
import com.runer.liabary.recyclerviewUtil.VerticalItemDecoration;
import com.runer.liabary.util.UiUtil;
import java.util.ArrayList;
import java.util.List;
import butterknife.ButterKnife;
import butterknife.InjectView;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class SearchActivity extends BaseActivity {
    @InjectView(R.id.search_et)
    EditText searchEt;
    @InjectView(R.id.clean_bt)
    ImageView cleanBt;
    @InjectView(R.id.recycler_view)
    RecyclerView recyclerView;

    private SearchAdapter searchAdapter;

    private List<SearchRecordBean> searchRecordBeens = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.inject(this);

        searchAdapter = new SearchAdapter(searchRecordBeens);
        searchAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

            }
        });

        searchAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM);
        searchAdapter.setLoadMoreView(new LoamoreView());
        searchAdapter.setEnableLoadMore(false);
        VerticalItemDecoration decoration = ItemDecorations.vertical(this)
                .last(R.drawable.item_decoration_shape)
                .type(0, R.drawable.item_decoration_shape).create();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(decoration);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(searchAdapter);

        searchEt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView arg0, int arg1, KeyEvent arg2) {
                if (arg1 == EditorInfo.IME_ACTION_SEARCH) {
                    editSearch();
                }
                return false;
            }
        });
        searchAdapter.setEmptyView(R.layout.empty_view_search, recyclerView);

        getRecordData();


        //清除数据库数据
        cleanBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideInputMethod(searchEt);//隐藏软件盘
                cleanHistory();//开始搜索逻辑
            }
        });


    }

    public void getRecordData() {
        //请求数据库信息；
        Flowable.just(this)
                .subscribeOn(Schedulers.io())
                .map(new Function<SearchActivity, List<SearchRecordBean>>() {
                    @Override
                    public List<SearchRecordBean> apply(SearchActivity searchActivity) throws Exception {
                        return SearchRecordManager.getInstance(searchActivity).queryData("");
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<SearchRecordBean>>() {
                    @Override
                    public void accept(List<SearchRecordBean> searchRecordBeen) throws Exception {
                        Logger.d(searchRecordBeen);
                        searchAdapter.setNewData(searchRecordBeen);
                    }
                });
    }


    private void cleanHistory() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("清空记录")
                .setMessage("确定清空搜索记录?")
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        Flowable.just(SearchActivity.this)
                                .map(new Function<Context, Boolean>() {
                                    @Override
                                    public Boolean apply(Context context) {
                                        try {
                                            SearchRecordManager.getInstance(context).deleteData();
                                            return true;
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                            return false;
                                        }
                                    }
                                })
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Consumer<Boolean>() {
                                    @Override
                                    public void accept(Boolean aBoolean) throws Exception {
                                        if (aBoolean) {
                                            UiUtil.showLongToast(getApplicationContext(), "清除成功");
                                            getRecordData();
                                        } else {
                                            UiUtil.showLongToast(getApplicationContext(), "出现错误");
                                        }
                                    }
                                });
                    }
                }).create().show();
    }


    private void editSearch() {
        //进行搜索
        String searhKey = searchEt.getText().toString();
        if (!searhKey.isEmpty()) {
            final String result = searhKey.replaceAll(" ", "");
            if (!TextUtils.isEmpty(result)) {
                Flowable.just(result)
                        .subscribeOn(Schedulers.io())
                        .map(new Function<String, Boolean>() {
                            @Override
                            public Boolean apply(String s) throws Exception {
                                Logger.d(s);
                                return SearchRecordManager.getInstance(getApplicationContext()).hasData(s);
                            }
                        })
                        .map(new Function<Boolean, String>() {
                            @Override
                            public String apply(Boolean aBoolean) throws Exception {
                                Logger.d(aBoolean);
                                //若是没有的情况下；
                                if (!aBoolean) {
                                    SearchRecordManager.getInstance(getApplicationContext()).insertData(result);
                                }
                                return "ok";
                            }
                        })
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<Object>() {
                            @Override
                            public void accept(Object o) throws Exception {
                                UiUtil.showLongToast(getApplicationContext(), "查询业务开始");
                                transUi(SearResultActivity.class, null);
                            }
                        });
            }
        }
    }
}
