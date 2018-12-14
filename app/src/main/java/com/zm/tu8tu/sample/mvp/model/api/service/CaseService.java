package com.zm.tu8tu.sample.mvp.model.api.service;

import com.zm.tu8tu.sample.mvp.model.api.bean.ListBeanDto;
import com.zm.tu8tu.sample.mvp.model.api.bean.NewCaseDto;
import com.zm.tu8tu.sample.mvp.model.api.bean.ResultDto;

import io.reactivex.Observable;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * @author : zengmei
 * @version : v1.7.0
 * @date : 2018/5/4
 * @description : 描述...
 */
public interface CaseService {
    /**
     * 获取客厅列表数
     */
    @POST("/mobileapp/xnjz/index.php")
    Observable<ResultDto<ListBeanDto>> getList(@Query("controller") String controller,
                                               @Query("action") String action,
                                               @Query("ispass") int ispass,
                                               @Query("from") int from,
                                               @Query("page_no") int page_no,
                                               @Query("per_page") int per_page,
                                               @Query("style_id") int style_id,
                                               @Query("type_id") int type_id,
                                               @Query("zone_id") int zone_id);

    /**
     * 获取客厅单个详情
     */
    @POST("mobileapp/xnjz/index.php?")
    Observable<ResultDto<NewCaseDto>> getDetail(@Query("controller") String controller,
                                                @Query("action") String action,
                                                @Query("vrid") int vrid);
}
