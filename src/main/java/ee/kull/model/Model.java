package ee.kull.model;


import lombok.Data;

@Data
public class Model {

    private String name;
    private Integer temp;
    private Double humidity;
    private String main;
}