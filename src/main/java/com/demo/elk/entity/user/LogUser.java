package com.demo.elk.entity.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.persistence.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collation = "log_user")
public class LogUser {
    @Id
    private String _id;

    @Field(name = "description")
    private String description;

    @Field(name = "date")
    private String date;

    @Field(name = "deveice_type")
    private String deviceType;
}
