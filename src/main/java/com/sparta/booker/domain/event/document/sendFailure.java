package com.sparta.booker.domain.event.document;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.*;

@Getter
@Document(indexName = "sendfailure", createIndex = true)
@NoArgsConstructor
@Mapping(mappingPath = "static/mappings/es-logMappings.json")
public class sendFailure {
    @Id
    @Field(type = FieldType.Keyword)
    private Long id;

    @Field(type = FieldType.Keyword)
    private Long eventId;
    @Field(type = FieldType.Text)
    private String userId;
    @Field(type = FieldType.Text)
    private String eventDate;
    @Field(type = FieldType.Text)
    private String eventTime;

    public sendFailure(Long eventId, String userId, String eventDate, String eventTime) {
        this.id = id;
        this.eventId = eventId;
        this.userId = userId;
        this.eventDate = eventDate;
        this.eventTime = eventTime;
    }
}