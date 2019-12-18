package model;

import javax.annotation.PostConstruct;
import javax.persistence.*;

@Entity
@Table(name = "Points")
public class Point {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", nullable = false)
    private Integer id;
    @Column(name = "X", nullable = false)
    private Double x;
    @Column(name = "Y", nullable = false)
    private Double y;
    @Column(name = "R", nullable = false)
    private Double r;
    @Column(name = "RES", nullable = false)
    private Boolean check;

    public Point() {
    }

    public Point(Double x, Double y, Double r, Boolean check) {
        this.x = x;
        this.y = y;
        this.r = r;
        this.check = check;
    }


    public Double getX() {
        return x;
    }

    public void setX(Double x) {
        this.x = x;
    }

    public Double getY() {
        return y;
    }

    public void setY(Double y) {
        this.y = y;
    }

    public Double getR() {
        return r;
    }

    public void setR(Double r) {
        this.r = r;
    }

    public Boolean getCheck() {
        return check;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setCheck(Boolean check) {
        this.check = check;
    }

}
