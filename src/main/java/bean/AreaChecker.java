package bean;

import com.google.gson.Gson;
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
    private Double x;
    private Double y;
    private Double r;

    private Double xCnv;
    private Double yCnv;
    private Double rCnv;



    private Boolean check;
    private EntityManager em = Persistence.createEntityManagerFactory("hibernate").createEntityManager();

    public AreaChecker() {
        r = 1.0;
        x = (double) -5;
        Query query = em.createQuery("select p from Point p");
        points = query.getResultList();
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

    public void setCheck(Boolean check) {
        this.check = check;
    }


    private boolean check(Double x, Double y, Double r) {
        check = (x >= -r && x <= 0 && y >= -r / 2 && y <= 0) ||
                (x >= 0 && y <= 0 && (x * x + y * y) <= (r / 2 * r / 2)) ||
                (x <= 0 && y >= 0 && y <= x / 2 + r / 2);
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
                Double xEnt = entity.getX();
                Double yEnt = entity.getY();
                entity.setCheck(check(xEnt, yEnt, (Double) newValue));

                break;
            }
            case "X": {
                Double yEnt = entity.getY();
                Double rEnt = entity.getR();
                entity.setCheck(check((Double) newValue, yEnt, rEnt));
                break;
            }
            case "Y": {
                Double xEnt = entity.getX();
                Double rEnt = entity.getR();
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

    public Double getxCnv() {
        return xCnv;
    }

    public void setxCnv(Double xCnv) {
        this.xCnv = xCnv;
    }

    public Double getyCnv() {
        return yCnv;
    }

    public void setyCnv(Double yCnv) {
        this.yCnv = yCnv;
    }

    public Double getrCnv() {
        return rCnv;
    }

    public void setrCnv(Double rCnv) {
        this.rCnv = rCnv;
    }

    public String getPointsAsJson() {
        return new Gson().toJson(points);
    }



    public boolean commandButtonBehavior(){
        return y==null;
    }
}


