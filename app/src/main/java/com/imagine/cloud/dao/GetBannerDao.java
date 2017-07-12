package com.imagine.cloud.dao;

import android.content.Context;
import com.fasterxml.jackson.databind.JsonNode;
import com.imagine.cloud.net.BaseRequest;
import com.imagine.cloud.net.IRequestParam;
import com.imagine.cloud.net.NetInter;
import com.imagine.cloud.net.Requst;
import com.imagine.cloud.widget.adviewpager.BannerBean;
import com.orhanobut.logger.Logger;
import com.runer.net.JsonUtil;
import com.runer.net.RequestCode;
import com.runer.net.interf.INetResult;
import java.io.IOException;
import java.util.List;

/**
 * Created by szhua on 2017/7/11/011.
 * github:https://github.com/szhua
 * ImagineCloudEducation
 * GetBannerDao
 */

public class GetBannerDao extends BaseRequest {

    private List<BannerBean> bannerBeens ;
    public List<BannerBean> getBannerBeens() {
        return bannerBeens;
    }
    public GetBannerDao(Context context, INetResult iNetResult) {
        super(context, iNetResult);
    }
    @Override
    public void onRequestSuccess(JsonNode result, int requestCode) throws IOException {
        if(requestCode==RequestCode.GET_BANNER){
            bannerBeens = JsonUtil.node2pojoList(result.findValue("list"),BannerBean.class) ;
            Logger.d(bannerBeens);
        }
    }
    public void getBannerList(){
        IRequestParam param =new IRequestParam() ;
        basePostRequst(Requst.BASE_URL+getReqestUrl(NetInter.BannerList),param, RequestCode.GET_BANNER);
    }

}
