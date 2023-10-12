package qa.guru.models;

import lombok.Data;
import com.fasterxml.jackson.annotation.JsonProperty;
@Data
public class SingleResourceResponseModel {

    String name;
    String color;
    @JsonProperty("pantone_value")
    String pantoneValue;
    int year, id;
    SingleResourceResponseModel data;
    SingleResourceSupportResponseModel support;
}
