package ch.noseryoung.uk.businessObjects.auction.dto;

public class AuctionDTO {



    private float currentPrice;

    private int id;



    public AuctionDTO(int id, float currentPrice){
        this.id = id;
        this.currentPrice = currentPrice;

    }



    public AuctionDTO(){

    }



    //getters
    public float getCurrentPrice() {
        return currentPrice;
    }


    public int getId() {
        return id;
    }


    //setters
    public AuctionDTO setId(int id) {
        this.id = id;
        return this;
    }

    public AuctionDTO setCurrentPrice(float currentPrice) {
        this.currentPrice = currentPrice;
        return this;
    }
}
