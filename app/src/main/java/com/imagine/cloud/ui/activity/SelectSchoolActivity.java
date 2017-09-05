package com.imagine.cloud.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.imagine.cloud.R;
import com.imagine.cloud.adapter.SelectSchoolAdapter;
import com.imagine.cloud.base.BaseActivity;
import com.imagine.cloud.bean.SchoolBean;
import com.imagine.cloud.dao.GetSchoolDao;
import com.imagine.cloud.widget.LoamoreView;
import com.orhanobut.logger.Logger;
import com.runer.liabary.recyclerviewUtil.ItemDecorations;
import com.runer.liabary.recyclerviewUtil.VerticalItemDecoration;
import com.runer.liabary.util.RunerLinearManager;
import com.runer.liabary.util.UiUtil;
import com.runer.net.RequestCode;

import java.util.ArrayList;
import java.util.List;
import butterknife.ButterKnife;
import butterknife.InjectView;

public class SelectSchoolActivity extends BaseActivity {

    @InjectView(R.id.recycler_view)
    RecyclerView recyclerView;
    @InjectView(R.id.swiperefresh)
    SwipeRefreshLayout swiperefresh;
    @InjectView(R.id.search_edit)
    EditText searchEdit;


    private GetSchoolDao getSchoolDao ;
    private SelectSchoolAdapter selectSchoolAdapter ;
    private List<SchoolBean> datas ;

    private String currentKey ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_school);
        ButterKnife.inject(this);

        getSchoolDao =new GetSchoolDao(this,this);
        //getSchoolDao.refresh("济南");
        searchEdit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView arg0, int arg1, KeyEvent arg2) {
                if (arg1 == EditorInfo.IME_ACTION_SEARCH) {
                    if(!TextUtils.isEmpty(searchEdit.getText())){
                        currentKey =searchEdit.getText().toString() ;
                        getSchoolDao.refresh(currentKey);
                        hideInputMethod(searchEdit);
                    }
                }
                return false;
            }
        });

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                hideInputMethod(searchEdit);
            }
        });
        searchEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
              if(!TextUtils.isEmpty(searchEdit.getText())){
                  currentKey =searchEdit.getText().toString() ;
                  getSchoolDao.refresh(currentKey);
              }
            }
        });

        swiperefresh.setColorSchemeColors(getRefreshColor(this));
        selectSchoolAdapter =new SelectSchoolAdapter(datas);
        selectSchoolAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM);
        selectSchoolAdapter.setLoadMoreView(new LoamoreView());
        selectSchoolAdapter.setOnLoadMoreListener(this,recyclerView);
        selectSchoolAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                SelectSchoolAdapter myAdapter = ((SelectSchoolAdapter) adapter);
                hideInputMethod(searchEdit);
                String school =myAdapter.getItem(position).getName();
                Intent intent =new Intent() ;
                intent.putExtra("school",school) ;
                setResult(RESULT_OK,intent);
                finish();
            }
        });
        swiperefresh.setOnRefreshListener(this);
        recyclerView.setLayoutManager(new RunerLinearManager(this));
        VerticalItemDecoration decoration = ItemDecorations.vertical(this).type(0, R.drawable.item_decoration_shape).create();
        recyclerView.addItemDecoration(decoration);
        recyclerView.setAdapter(selectSchoolAdapter);

    }

    @Override
    public void onRequestSuccess(int requestCode) {
        super.onRequestSuccess(requestCode);
        if(requestCode== RequestCode.CODE_1){
            datas =getSchoolDao.getDatas() ;
            selectSchoolAdapter.setNewData(datas);
            if(datas==null||datas.isEmpty()){
                selectSchoolAdapter.setEmptyView(getEmptyView("无搜索结果"));
            }
        }
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        getSchoolDao.refresh(currentKey);
    }

    @Override
    public void onCompeleteRefresh() {
        super.onCompeleteRefresh();
        if(swiperefresh!=null){
            swiperefresh.setRefreshing(false);
        }
        if(selectSchoolAdapter!=null&&recyclerView!=null){
            selectSchoolAdapter.loadMoreComplete();
        }
    }

    @Override
    public void onLoadMoreRequested() {
        super.onLoadMoreRequested();
        if(getSchoolDao.hasMore()){
            getSchoolDao.nextPage(currentKey);
        }else{
            selectSchoolAdapter.loadMoreEnd();
        }
    }


}
