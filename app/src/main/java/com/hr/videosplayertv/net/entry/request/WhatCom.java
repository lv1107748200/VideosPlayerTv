package com.hr.videosplayertv.net.entry.request;

import com.hr.videosplayertv.net.base.BaseDataRequest;

/**
 * lv
 */
public class WhatCom extends BaseDataRequest {


    /**
     * Size : 20
     * Page : 1
     */

    private String Size;
    private String Page;

    public String getSize() {
        return Size;
    }

    public void setSize(String Size) {
        this.Size = Size;
    }

    public String getPage() {
        return Page;
    }

    public void setPage(String Page) {
        this.Page = Page;
    }
}
