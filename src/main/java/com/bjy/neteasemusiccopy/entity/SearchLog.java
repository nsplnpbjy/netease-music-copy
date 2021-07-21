package com.bjy.neteasemusiccopy.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


/**
 * <p>
 * 
 * </p>
 *
 * @author jobob
 * @since 2021-07-20
 */
@Data
@EqualsAndHashCode()
@AllArgsConstructor
@NoArgsConstructor
public class SearchLog  {

    private static final long serialVersionUID = 1L;

    private String id;

    private String text;

    private LocalDateTime time;


}
