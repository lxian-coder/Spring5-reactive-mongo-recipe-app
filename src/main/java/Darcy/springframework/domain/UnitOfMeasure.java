package Darcy.springframework.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * spring5-recipe-app
 * Author: Darcy Xian  2020/8/516:49
 */
@Getter
@Setter

public class UnitOfMeasure {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    private String description;

}

