package com.lpsc.gov.app1.pojo;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "MCheckListItem")
public class MCheckListItem {

    @Id
    @Column(name = "id")
    @GenericGenerator(name = "customidgenerator", strategy = "com.lpsc.gov.app1.generators.CustomIdGenerator")
    @GeneratedValue(generator = "customidgenerator")
    private long id;

    @Column(name = "item")
    private String checkListItem;

    @OneToMany(mappedBy = "mCheckListItem", orphanRemoval = true, cascade = {
            CascadeType.ALL }, fetch = FetchType.EAGER)
    private List<MCheckListItemValue> checkListItemValues;

    @Column(name = "is_active")
    private boolean isActive;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCheckListItem() {
        return checkListItem;
    }

    public void setCheckListItem(String checkListItem) {
        this.checkListItem = checkListItem;
    }

    public List<MCheckListItemValue> getCheckListItemValues() {
        return checkListItemValues;
    }

    public void setCheckListItemValues(List<MCheckListItemValue> checkListItemValues) {
        this.checkListItemValues = checkListItemValues;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }
}
