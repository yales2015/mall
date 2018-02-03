package com.yy.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Category {
    private Integer id;

    private Integer parentId;

    private String name;

    private Boolean status;

    private Integer sortOrder;

    private Date createTime;

    private Date updateTime;

    @Override
    public boolean equals(Object o){
        if(this==o){
            return true;
        }
        if(o==null || getClass()!=o.getClass()){
            return false;
        }
        Category category=(Category)o;
        return (id!=null?id.equals(category.getId()):category.getId()!=null);
    }
    @Override
    public int hashCode(){
        return id!=null?id.hashCode():0;
    }
}