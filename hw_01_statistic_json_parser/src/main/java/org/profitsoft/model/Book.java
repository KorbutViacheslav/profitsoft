package org.profitsoft.model;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Author: Viacheslav Korbut
 * Date: 04.04.2024
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Book {

    private String title;

    private Author author;

    @SerializedName("year_published")
    private Integer yearPublished;

    private List<Genre> genre;

}
