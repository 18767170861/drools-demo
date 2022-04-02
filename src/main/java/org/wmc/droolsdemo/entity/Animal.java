package org.wmc.droolsdemo.entity;

import lombok.Data;
import java.util.List;

@Data
public class Animal {

    private Integer level;

    private List<People> peoples;

}