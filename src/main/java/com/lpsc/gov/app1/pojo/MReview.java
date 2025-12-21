
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
@Table(name = "MReview")
public class MReview {

    @Id
    @Column(name = "id")
    @GenericGenerator(name = "customidgenerator", strategy = "com.lpsc.gov.app1.generators.CustomIdGenerator")
    @GeneratedValue(generator = "customidgenerator")
    private long id;

    @Column(name = "category")
    private String category;

    @Column(name = "update_username")
    private String updateUserName;

    @OneToMany(orphanRemoval = true, mappedBy = "mReview", cascade = { CascadeType.ALL }, fetch = FetchType.LAZY)
    private List<MCheckListEntry> checkListEntries;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getUpdateUserName() {
        return updateUserName;
    }

    public void setUpdateUserName(String updateUserName) {
        this.updateUserName = updateUserName;
    }

    public List<MCheckListEntry> getCheckListEntries() {
        return checkListEntries;
    }

    public void setCheckListEntries(List<MCheckListEntry> checkListEntries) {
        this.checkListEntries = checkListEntries;
    }

}
