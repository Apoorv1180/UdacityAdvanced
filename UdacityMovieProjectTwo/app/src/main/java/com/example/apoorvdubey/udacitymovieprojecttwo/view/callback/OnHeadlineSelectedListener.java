package com.example.apoorvdubey.udacitymovieprojecttwo.view.callback;

import com.example.apoorvdubey.udacitymovieprojecttwo.service.model.DetailResult;


import java.util.List;

public interface OnHeadlineSelectedListener {
        void onArticleSelected(int position, List<DetailResult> response);
    }
