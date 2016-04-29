package com.csi0n.searchjobapp.app;

import com.csi0n.searchjobapp.business.api.SearchJobApi;
import com.csi0n.searchjobapp.business.api.retrofit.SearchJobApiRetrofitImpl;
import com.csi0n.searchjobapp.business.domain.SearchJobDomain;
import com.csi0n.searchjobapp.business.domain.impl.SearchJobDomainImpl;
import com.google.inject.AbstractModule;

/**
 * Created by chqss on 2016/4/29 0029.
 */
public class BindImplModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(SearchJobDomain.class).to(SearchJobDomainImpl.class);
        bind(SearchJobApi.class).to(SearchJobApiRetrofitImpl.class);
    }
}
