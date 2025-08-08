package com.board;

import jakarta.persistence.*;

@Entity
@Table(name="Trial_table")
public class BoardMemo {
    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String title;
}

