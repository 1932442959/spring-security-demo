package com.jasfair.springsecuritydemo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "an_user_info")
public class UserInfo {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String name;

    private Long age;
}
