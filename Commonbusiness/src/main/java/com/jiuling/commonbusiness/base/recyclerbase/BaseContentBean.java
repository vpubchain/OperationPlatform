package com.jiuling.commonbusiness.base.recyclerbase;

/**
 * desc:
 * 通用BaseRecyclerView String类型的Bean
 */

public class BaseContentBean extends BaseRecyclerBean {
    private String content;

    public BaseContentBean() {
    }

    public BaseContentBean(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "BaseContentBean{" +
                "content='" + content + '\'' +
                '}';
    }
}
