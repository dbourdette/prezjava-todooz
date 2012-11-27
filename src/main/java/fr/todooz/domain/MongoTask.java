package fr.todooz.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang.StringUtils;
import org.bson.types.ObjectId;
import org.hibernate.validator.constraints.NotBlank;

@com.google.code.morphia.annotations.Entity(value = "tasks", noClassnameStored = true)
public class MongoTask {
    @com.google.code.morphia.annotations.Id
    private ObjectId id;

    @Column
    private Date createdAt = new Date();

    @Column
    @NotBlank
    @Size(min = 1, max = 255)
    private String title;

    @Column(length = 4000, nullable = true)
    @Size(max = 4000)
    private String text;

    @Column
    @NotNull
    private Date date = new Date();

    @Column(nullable = true)
    private String tags;

    public String[] getTagArray() {
        return StringUtils.split(tags, ",");
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
