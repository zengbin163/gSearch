package com.gaojie.gsearch.vo.entity;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.SqlCondition;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import lombok.Data;

/**
 * @author zengbin
 * @Date 2019/6/8 13:30
 */
@Data
@TableName(value = "t_app_user", keepGlobalPrefix = false)

public class TUser extends Model<TUser> {

    private static final long serialVersionUID = -4972030881763418909L;
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @TableField(condition = SqlCondition.LIKE, strategy = FieldStrategy.NOT_EMPTY)
    private String name;
    @TableField(condition = "%s&gt;=#{%s}")
    private String phone;
    private String realName;

    public TUser() {
    }

    public TUser(String name, Integer id, String phone, String realName) {
        this.name = name;
        this.id = id;
        this.phone = phone;
        this.realName = realName;
    }

    @Override
    public String toString() {
        return "User{" + "name='" + name + '\'' + ", id=" + id + ", phone=" + phone + ", realName='" + realName + '\''
                + '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

}
