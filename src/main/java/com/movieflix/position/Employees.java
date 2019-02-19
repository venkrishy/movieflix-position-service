package com.movieflix.position;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Slf4j
@Data
@NoArgsConstructor
@Entity
@Table(name="employees")
public class Employees {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int empNo;

    private String firstName;
    private String lastName;
    private String gender;


}
