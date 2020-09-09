package ch.noseryoung.uk.businessObjects.auction.unitTests;

import ch.noseryoung.uk.businessObjects.auction.Auction;
import ch.noseryoung.uk.businessObjects.auction.AuctionRepository;
import ch.noseryoung.uk.businessObjects.user.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@RunWith(SpringRunner.class)
@DataJpaTest
@TestPropertySource("classpath:application-test.properties")
class AuctionRepositoryTest {


    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private AuctionRepository auctionRepository;


    @BeforeEach
    void setUp() {
        Auction auction1 = new Auction(1, 300);
        Auction auction2 = new Auction(2, 450);

        List<Auction> auctionListToBeTested = Arrays.asList(auction1, auction2);

            User user1 = new User()
//                    .setAuctions(auctionListToBeTested)
                    .setUsername("alan")
                    ;

            auction1.setUser_id(user1.getId());
            auction2.setUser_id(user1.getId());


        testEntityManager.persist(user1);
        testEntityManager.persist(auction1);

     //   testEntityManager.persist(auction2);
    }

    @AfterEach
    void tearDown() {
    }


    @Test
    public void testFindAll() {

        List<Auction> all = auctionRepository.findAll();
        assertEquals(2, all.size());

    }


    /*
    @Test
    public void testFindById(){
        Optional<Auction> optAuction = auctionRepository.findAllById(1);

        assertTrue(optAuction.isPresent());
    }
    */

}