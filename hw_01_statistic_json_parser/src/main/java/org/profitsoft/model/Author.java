package org.profitsoft.model;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Author: Viacheslav Korbut
 * Date: 04.04.2024
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Author {

    @SerializedName("first_name")
    private String firstName;

    @SerializedName("last_name")
    private String lastName;

}
