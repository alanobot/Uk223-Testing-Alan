package ch.noseryoung.uk.businessObjects.auction;

import java.util.List;

public interface AuctionService {

    //Method implemetation with CRUD logic
    Auction create(Auction auction);

    List<Auction> findAll();

    Auction findById(int id);

    Auction updateById(int id, Auction auction);

    void deleteById(int id);

    List<Auction> getAuctionsByRange(int startPrice, int endPrice);


}
