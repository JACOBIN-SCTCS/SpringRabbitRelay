package com.lpsc.gov.app1.pojo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "MCheckListItemValue")
public class MCheckListItemValue {

    @Id
    @Column(name = "id")
    @GenericGenerator(name = "customidgenerator", strategy = "com.lpsc.gov.app1.generators.CustomIdGenerator")
    @GeneratedValue(generator = "customidgenerator")
    private long id;

    @ManyToOne
    @JoinColumn(name = "checklistitem_id")
    private MCheckListItem mCheckListItem;

    @Column(name = "value", length = 100)
    private String value;

    @Column(name = "rank_")
    private int rank_;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public MCheckListItem getmCheckListItem() {
        return mCheckListItem;
    }

    public void setmCheckListItem(MCheckListItem mCheckListItem) {
        this.mCheckListItem = mCheckListItem;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getRank_() {
        return rank_;
    }

    public void setRank_(int rank_) {
        this.rank_ = rank_;
    }

}
