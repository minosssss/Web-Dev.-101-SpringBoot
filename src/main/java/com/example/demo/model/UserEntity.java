package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = "email")})
public class UserEntity {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid",strategy = "uuid")
    private String id;      // 사용자에게 부여되는 id (key)
    @Column(nullable = false)
    private String username;// 사용자 이름

    @Column(nullable = false)
    private String email;   // 사용자 이메일 (id 와 같은 기능)
    @Column(nullable = false)
    private String password; // 패스워드

}
