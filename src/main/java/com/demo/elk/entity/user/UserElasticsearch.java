package com.demo.elk.entity.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.elasticsearch.index.VersionType;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.persistence.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(indexName = "user_index", versionType = VersionType.EXTERNAL)
public class UserElasticsearch {

    @Id
    private String id;

    @Field(type = FieldType.Keyword, name = "username")
    private String email;

    @Field(type = FieldType.Keyword, name = "username")
    private String username;

    @Field(type = FieldType.Keyword, name = "full_name")
    private String fullName;

    @Field(type = FieldType.Keyword, name = "phone_number")
    private String phoneNumber;
}
