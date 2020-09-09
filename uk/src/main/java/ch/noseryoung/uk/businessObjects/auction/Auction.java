package ch.noseryoung.uk.businessObjects.auction;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity //damit eine tabelle davon erstellt wird
public class Auction {

    @Id                                                 //primarykey
    @GeneratedValue(strategy = GenerationType.IDENTITY) //autoincrement


    //regular fields
    private int id;
    private float currentPrice;
    private int user_id;


    public Auction(int id, float currentPrice) {
        this.id = id;
        this.currentPrice = currentPrice;

    }

    public Auction(int id) {
        this.id = id;
    }

    public Auction() {

    }


    //Getters and Setters
    public int getId() {
        return id;
    }


    public Auction setId(int id) {
        this.id = id;
        return this;
    }

    public float getCurrentPrice() {
        return currentPrice;
    }

    public Auction setCurrentPrice(float newPrice) {
        this.currentPrice = newPrice;
        return this;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
}
