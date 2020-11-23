package com.example.demo.entity;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;
import org.apache.commons.lang.StringUtils;

import java.io.Serializable;

@Data
public class Query implements Serializable {

    private Integer id;

    private String page = "1";

    private String limit = "10";

    public int getPage() {
        if (StringUtils.isEmpty(page)) {
            this.page = "1";
        }
        try {
            return Integer.parseInt(this.page);
        } catch (Exception e) {
            return 1;
        }
    }

    public int getLimit() {
        if (StringUtils.isEmpty(page)) {
            this.limit = "10";
        }
        try {
            return Integer.parseInt(this.limit);
        } catch (Exception e) {
            return 10;
        }
    }

    public Page conversionToPage() {
        Page page1 = new Page();
        page1.setCurrent(getPage());
        page1.setSize(getLimit());
        return page1;
    }

}
