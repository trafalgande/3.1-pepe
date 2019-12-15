package model;

import javax.persistence.*;

@Entity
@Table(name = "Points")
public class Point {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", nullable = false)
    private Integer id;
    @Column(name = "X", nullable = false)
    private double x;
    @Column(name = "Y", nullable = false)
    private double y;
    @Column(name = "R", nullable = false)
    private double r;
    @Column(name = "RES", nullable = false)
    private Boolean check;

    public Point() {
    }

    public Point(double x, double y, double r, Boolean check) {
        this.x = x;
        this.y = y;
        this.r = r;
        this.check = check;
    }


    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getR() {
        return r;
    }

    public void setR(double r) {
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
