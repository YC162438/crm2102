package cn.itcast.mp.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@TableName("tb_user")
public class User{
    @TableId(value = "ID",type = IdType.ID_WORKER)//指定主键的字段名
    private Long id;
    @TableField("USER_NAME")
    private String userName;//驼峰命名,则无需注解
    @TableField("PASSWORD")
    private String password;
    @TableField("NAME")
    private String name;
    @TableField("AGE")
    private Integer age;
    @TableField("EMAIL")
    private String email;
    @TableField("birthday")
    private LocalDateTime birthday;


}
