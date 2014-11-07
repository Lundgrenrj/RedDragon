package red.dragon.pojos;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;

/**
 * The persistent class for the Accounting database table.
 * 
 */
@Entity
@NamedQuery(name = "Accounting.findAll", query = "SELECT a FROM Accounting a")
public class Accounting implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    private double dailySum;

    public Accounting() {
    }

    public Date getDate() {
	return this.date;
    }

    public void setDate(Date date) {
	this.date = date;
    }

    public double getDailySum() {
	return this.dailySum;
    }

    public void setDailySum(double dailySum) {
	this.dailySum = dailySum;
    }

}