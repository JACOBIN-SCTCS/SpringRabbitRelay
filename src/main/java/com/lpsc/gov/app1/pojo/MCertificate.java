
package com.lpsc.gov.app1.pojo;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "MCertificate")
public class MCertificate {

    @Id
    @Column(name = "id")
    @GenericGenerator(name = "customidgenerator", strategy = "com.lpsc.gov.app1.generators.CustomIdGenerator")
    @GeneratedValue(generator = "customidgenerator")
    private long id;

    @Column(name = "certificate")
    private String certString;

    @Column(name = "active")
    private int active;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "review")
    private MReview mReview;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public String getCertString() {
        return certString;
    }

    public void setCertString(String certString) {
        this.certString = certString;
    }
}
