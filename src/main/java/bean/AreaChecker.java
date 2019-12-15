package bean;

import model.Point;
import oracle.sql.NUMBER;
import org.hibernate.Session;
import org.primefaces.event.CellEditEvent;
import utils.HibernateSessionFactoryUtil;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;


@ManagedBean(name = "checker")
@ApplicationScoped
public class AreaChecker implements Serializable {
    private List<Point> points;
    private double x;
    private double y;
    private double r;

    private double xCnv;
    private double yCnv;
    private double rCnv;



    private Boolean check;
    private EntityManager em = Persistence.createEntityManagerFactory("hibernate").createEntityManager();

    public AreaChecker() {
        Query query = em.createQuery("select p from Point p");
        points = query.getResultList();
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

    public void setCheck(Boolean check) {
        this.check = check;
    }


    private boolean check(double x, double y, double r) {
        check = (x >= -r && x <= 0 && y >= -r / 2 && y <= 0) ||
                (x >= 0 && y <= 0 && (x * x + y * y) <= (r / 2 * r / 2)) ||
                (x <= 0 && y >= 0 && y <= x / 2 + r);
        return check;
    }

    public void newPoint() {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Point p = new Point(getX(), getY(), getR(), check(getX(), getY(), getR()));
        setPoint(p);
        session.save(p);
        session.getTransaction().commit();
    }

    public void newPointCanvasBased() {
        if (getrCnv() != 0) {
            Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
            session.beginTransaction();
            Point p = new Point(getxCnv(), getyCnv(), getrCnv(), check(getxCnv(), getyCnv(), getrCnv()));
            setPoint(p);
            session.save(p);
            session.getTransaction().commit();
        }
    }


    private void setPoint(Point p) {
        points.add(p);
    }

    public List<Point> getPoints() {
        return points;
    }


    public void onCellEdit(CellEditEvent event) throws SQLException {
        Object oldValue = event.getOldValue();
        Object newValue = event.getNewValue();
        String colHeader = event.getColumn().getHeaderText();
        FacesContext context = FacesContext.getCurrentInstance();
        if (newValue != null && !newValue.equals(oldValue)) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Cell changed", "Old: " + oldValue + " New: " + newValue);
            context.addMessage(null, msg);
        }

        Point entity = context.getApplication().evaluateExpressionGet(context, "#{point}", Point.class);
        String query;
        switch (colHeader) {
            case "R": {
                double xEnt = entity.getX();
                double yEnt = entity.getY();
                entity.setCheck(check(xEnt, yEnt, (Double) newValue));

                break;
            }
            case "X": {
                double yEnt = entity.getY();
                double rEnt = entity.getR();
                entity.setCheck(check((Double) newValue, yEnt, rEnt));
                break;
            }
            case "Y": {
                double xEnt = entity.getX();
                double rEnt = entity.getR();
                entity.setCheck(check(xEnt, (Double) newValue, rEnt));
                break;
            }
        }
        if (getCheck()) {
            query = "UPDATE POINTS SET " + colHeader + " = " + newValue + ", RES = " +
                    new NUMBER(entity.getCheck()) + " WHERE ID = " + entity.getId();
        } else
            query = "UPDATE POINTS SET " + colHeader + " = " + newValue + ", RES = " +
                    new NUMBER(entity.getCheck()) + " WHERE " + colHeader + " = " + oldValue;
        em.getTransaction().begin();
        em.createNativeQuery(query);
        em.getTransaction().commit();
    }

    public double getxCnv() {
        return xCnv;
    }

    public void setxCnv(double xCnv) {
        this.xCnv = xCnv;
    }

    public double getyCnv() {
        return yCnv;
    }

    public void setyCnv(double yCnv) {
        this.yCnv = yCnv;
    }

    public double getrCnv() {
        return rCnv;
    }

    public void setrCnv(double rCnv) {
        this.rCnv = rCnv;
    }
}


