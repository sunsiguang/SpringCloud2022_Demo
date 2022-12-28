package com.cnrmall.springcloud.entity;

import lombok.Data;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.*;

/**
 * @author David
 * @date 2022/12/27 16:53
 */
@Entity
@Data
@Table(name = "User")
@NamedQuery(name = "User.findByTheUsersName", query = "from User u where u.username = ?1")
public class User {
    private static final long serialVersionUID = -2952735933715107252L;

    @Id  //主键
    @GeneratedValue(strategy = GenerationType.IDENTITY)   //自增策略
    private Long id;
    @Column(unique = true) private String username;

    private String firstname;
    private String lastname;


    public User() {
        this(null);
    }

    /**
     * Creates a new user instance.
     */
    public User(Long id) {
        this.setId(id);
    }

    public User(String firstname, String lastname) {
        this.firstname = firstname;
        this.lastname = lastname;
    }
}
