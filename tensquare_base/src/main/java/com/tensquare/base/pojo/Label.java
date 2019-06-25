package com.tensquare.base.pojo;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/*    标签实体类
 *  Serializable:分布式开发必须使用序列化，才能在不同平台使用IO流传输，
 *  不加io流不能在各种平台进行传输
 * */
@Entity
@Table(name = "tb_label")
public class Label implements Serializable {

    @Id
    private String id;//数据库ID
    private String labelname;//标签名称
    private String state;//状态
    private long count;//使用数量
    private long fans;//关注数
    private String recommend;//是否推荐

    //set get方法

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLabelname() {
        return labelname;
    }

    public void setLabelname(String labelname) {
        this.labelname = labelname;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public long getFans() {
        return fans;
    }

    public void setFans(long fans) {
        this.fans = fans;
    }

    public String getRecommend() {
        return recommend;
    }

    public void setRecommend(String recommend) {
        this.recommend = recommend;
    }


    }

