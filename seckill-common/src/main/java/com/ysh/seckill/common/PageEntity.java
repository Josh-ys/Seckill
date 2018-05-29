package com.ysh.seckill.common;

import lombok.Data;

import java.util.List;

/**
 * @author joey
 * @date 2018/05/30 01:53
 */
@Data
public class PageEntity<T> {
    private Long totalCount;
    private List<T> dataList;

    public static Builder createBuilder() {
        return new Builder();
    }

    public static class Builder<T> {
        private Long totalCount;
        private List<T> dataList;

        public Builder<T> withTotalCount(Long totalCount) {
            this.totalCount = totalCount;
            return this;
        }

        public Builder<T> withDataList(List<T> dataList) {
            this.dataList = dataList;
            return this;
        }

        public PageEntity<T> builder() {
            PageEntity<T> tPageEntity = new PageEntity<>();
            tPageEntity.setDataList(this.dataList);
            tPageEntity.setTotalCount(this.totalCount);
            return tPageEntity;
        }
    }
}
