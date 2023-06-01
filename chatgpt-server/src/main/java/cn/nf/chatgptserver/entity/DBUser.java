package cn.nf.chatgptserver.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("sys_user")
public class DBUser implements Serializable {
    @TableId
    private String email;
    private String phone;
    private String password;
    private Integer status;
    private Date createTime;
    private String remark;
}
