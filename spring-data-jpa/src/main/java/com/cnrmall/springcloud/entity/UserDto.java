package com.cnrmall.springcloud.entity;

import lombok.*;

import javax.persistence.*;

/**
 * @author David
 * @date 2022/12/26 16:01
 */

@Entity
@Table(name = "User")
@Data
@NoArgsConstructor(force = true)   //当类中有final字段没有被初始化时，编译器会报错，此时可用@NoArgsConstructor(force = true)
@AllArgsConstructor
@RequiredArgsConstructor //部分构造参数，这参数只能是以final修饰的未经初始化的字段，或者是以@NonNull注解的未经初始化的字段
public class UserDto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)   //自增
    private Integer id;

    @NonNull
    @Column(length = 32)
    private final  String name ;   // user.setName("任我行");  不能使用

    @Column(length = 32)
    private final String account;

    @Column(length = 64)
    private final String pwd;

}
