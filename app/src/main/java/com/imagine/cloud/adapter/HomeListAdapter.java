package com.imagine.cloud.adapter;
import android.support.annotation.IntRange;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.imagine.cloud.R;
import com.squareup.picasso.Picasso;
import java.util.List;

/**
 * Created by szhua on 2017/7/4/004.
 * github:https://github.com/szhua
 * ImagineCloudEducation
 * HomeListAdapter
 */
public class HomeListAdapter extends BaseQuickAdapter<String ,BaseViewHolder> {

    public HomeListAdapter(List<String>data){
        super(R.layout.item_home_layout,data);
    }
    @Override
    protected void convert(BaseViewHolder helper, String item) {
        //  helper.setText(R.id.title,item);
        // ImageView imageView = helper.getView(R.id.image);
    }
}
