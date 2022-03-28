package uz.pdp.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import static uz.pdp.utils.BaseUtils.generateUniqueID;

/**
 * @author Narzullayev Husan, чт 9:43. 02.12.2021
 */

@Getter
@Setter
@ToString
public abstract class BaseModel {
    private String id;

    public  BaseModel() {
        this.id = generateUniqueID();
    }
}
