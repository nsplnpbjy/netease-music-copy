package com.bjy.neteasemusiccopy.config;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * @author nsplnpbjy
 */
@Data
@AllArgsConstructor
public class SearchBody implements Serializable {
    private String s;
    private Integer offset;
    private Integer limit;
    private Integer type;
}
