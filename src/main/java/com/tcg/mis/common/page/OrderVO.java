package com.tcg.mis.common.page;

import com.google.common.collect.ImmutableList;
import com.tcg.mis.common.checker.Preconditions;

import java.util.List;
import java.util.Objects;

public class OrderVO {
    
    private static final List<String> DIRECTIONS = ImmutableList.of("ASC", "DESC");
    
    private String key;
    private String direction = "asc";
    private Integer nullLastOrFirst = null; // 1: nullFirst, 2 nullLast

    public OrderVO(String key, String direction) {
        
        Preconditions.checkNotNull(direction, "direction must not be null");
        Preconditions.checkParameter(DIRECTIONS.contains(direction.toUpperCase()), "direction must 'ASC' or 'DESC'");
        
        this.key = key;
        this.direction = direction;
    }

    public OrderVO(String key, String direction, Integer nullLastOrFirst) {
        this(key, direction);
        
        this.nullLastOrFirst = nullLastOrFirst;
    }

    
    public Integer getNullLastOrFirst() {
        return nullLastOrFirst;
    }

    public void setNullLastOrFirst(Integer nullLastOrFirst) {
        this.nullLastOrFirst = nullLastOrFirst;
    }



    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    @Override
    public String toString() {
        return key + " " + direction;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        OrderVO orderVO = (OrderVO) o;
        return Objects.equals(key, orderVO.key) &&
               Objects.equals(direction, orderVO.direction);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key, direction);
    }
}
