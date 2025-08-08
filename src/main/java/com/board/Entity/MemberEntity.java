package com.board.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name="pracuser_table")
public class MemberEntity {
    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private String nickname;
    private String age;
    private String password;
}
