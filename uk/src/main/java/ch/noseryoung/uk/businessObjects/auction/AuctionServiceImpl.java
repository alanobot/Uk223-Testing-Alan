package ch.noseryoung.uk.businessObjects.auction;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class AuctionServiceImpl implements AuctionService {

    private AuctionRepository auctionRepository;


    @Autowired
    public AuctionServiceImpl(AuctionRepository auctionRepository) {
        this.auctionRepository = auctionRepository;
    }


    // The logic for creating a new auction
    @Override
    public Auction create(Auction auction) {
        return auction;
    }


    // The logic for retrieving all auctions
    @Override
    public List<Auction> findAll() {
        return auctionRepository.findAll();
    }


    // The logic for retrieving a single auction with a given id
    @Override
    public Auction findById(int id) {
        Auction chosenAuction = new Auction();

        if (auctionRepository.findById(id).isPresent()) //schaut ob es das gibt
            chosenAuction = auctionRepository.findById(id).get();

        return chosenAuction;
    }


    // The logic for updating an existing auction with a given id and data
    @Override
    public Auction updateById(int id, Auction auction) {
        if (auctionRepository.existsById(id)) {
            auction.setId(id);
            auctionRepository.save(auction); //updatet den eintrag in der db
            return auction;
        } else throw new NoSuchElementException();


    }


    // The logic for deleting a auction with a given id
    @Override
    public void deleteById(int id) {
        auctionRepository.deleteById(id);
    }

    @Override
    public List<Auction> getAuctionsByRange(int startPrice, int endPrice) {

        List<Auction> auctionListAll =  auctionRepository.findAll();
        List<Auction> auctionList = new ArrayList<>();

        for(Auction auction : auctionListAll){
           if (auction.getCurrentPrice()>= startPrice && auction.getCurrentPrice() <= endPrice){
               auctionList.add(auction);
           }
       }
       return auctionList;
    }


}
