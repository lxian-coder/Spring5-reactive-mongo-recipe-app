package Darcy.springframework.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * spring5-recipe-app
 * Author: Darcy Xian  2020/8/516:49
 */
@Getter
@Setter
@Document
// mongodb document
public class UnitOfMeasure {

    @Id
    private String id;
    private String description;

}

