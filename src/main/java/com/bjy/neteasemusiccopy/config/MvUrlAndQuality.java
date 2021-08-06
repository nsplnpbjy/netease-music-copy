package com.bjy.neteasemusiccopy.config;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MvUrlAndQuality {
    private String mvPlayUrl;
    private int quality;
}
