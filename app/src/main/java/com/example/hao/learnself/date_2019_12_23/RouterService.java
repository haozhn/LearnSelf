package com.example.hao.learnself.date_2019_12_23;

import com.example.annotation.Page;
import com.example.annotation.Param;

public interface RouterService {
    @Page(ProxyTestActivity.class)
    void gotoMain(@Param("proxy_key") String value);
}
