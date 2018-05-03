package cn.edu.zucc.brightqin.entity;

import javax.persistence.*;

/**
 * @author brightqin
 */
@Entity
@Table(name = "DepartmentKeyResult")
public class DepartmentKeyResult {
    private Integer keyResultId;
    private String keyResultName;
    private int selfScore;
    private int upstreamScore;
    private int totalScore;
    private DepartmentObject departmentObject;

    public DepartmentKeyResult() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "keyResultId", nullable = false, unique = true, length = 16)
    public Integer getKeyResultId() {
        return keyResultId;
    }

    public void setKeyResultId(Integer keyResultId) {
        this.keyResultId = keyResultId;
    }

    @Column(name = "keyResultName", nullable = false, length = 16)
    public String getKeyResultName() {
        return keyResultName;
    }

    public void setKeyResultName(String keyResultName) {
        this.keyResultName = keyResultName;
    }


    @Column(name = "selfScore")
    public int getSelfScore() {
        return selfScore;
    }

    public void setSelfScore(int selfScore) {
        this.selfScore = selfScore;
    }

    @Column(name = "upstreamScore")
    public int getUpstreamScore() {
        return upstreamScore;
    }

    public void setUpstreamScore(int upstreamScore) {
        this.upstreamScore = upstreamScore;
    }

    @Column(name = "totalScore")
    public int getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "objectId")
    public DepartmentObject getDepartmentObject() {
        return departmentObject;
    }

    public void setDepartmentObject(DepartmentObject departmentObject) {
        this.departmentObject = departmentObject;
    }
}
