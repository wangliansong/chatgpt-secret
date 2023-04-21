package com.secret.chatgpt.front.entity;


import lombok.Data;
import javax.persistence.*;

@Data
@Entity
public class UserInfo2 {
    @Id
    @GeneratedValue
    private Long id; // 主键.
    @Column(unique = true)
    private String username; // 登录账户,唯一.
    //private String name; // 名称(匿名或真实姓名),用于UI显示
    private String password; // 密码.
    //private String salt; // 加密密码的盐
    //@JsonIgnoreProperties(value = {"userInfos"})
    //@ManyToMany(fetch = FetchType.EAGER) // 立即从数据库中进行加载数据
    //@JoinTable(name = "SysUserRole", joinColumns = @JoinColumn(name = "uid"), inverseJoinColumns = @JoinColumn(name = "roleId"))
    //private List<SysRole> roles;
}
