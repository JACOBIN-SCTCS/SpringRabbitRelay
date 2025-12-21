
package com.lpsc.gov.app1.pojo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "MCheckListEntry")
public class MCheckListEntry {
    @Id
    @Column(name = "id")
    @GenericGenerator(name = "customidgenerator", strategy = "com.lpsc.gov.app1.generators.CustomIdGenerator")
    @GeneratedValue(generator = "customidgenerator")
    private long id;

    @ManyToOne
    @JoinColumn(name = "checklistitem")
    private MCheckListItem checkListItem;

    @ManyToOne
    @JoinColumn(name = "observation")
    @JsonIgnore
    private MReview mReview;

    @Column(name = "value")
    private String value;

    @Column(name = "active")
    private boolean active;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public MCheckListItem getCheckListItem() {
        return checkListItem;
    }

    public void setCheckListItem(MCheckListItem checkListItem) {
        this.checkListItem = checkListItem;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public MReview getmReview() {
        return mReview;
    }

    public void setmReview(MReview mReview) {
        this.mReview = mReview;
    }
}
