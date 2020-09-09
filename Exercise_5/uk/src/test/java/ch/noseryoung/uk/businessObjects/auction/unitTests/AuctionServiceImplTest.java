package ch.noseryoung.uk.businessObjects.auction.unitTests;

import ch.noseryoung.uk.businessObjects.auction.Auction;
import ch.noseryoung.uk.businessObjects.auction.AuctionRepository;
import ch.noseryoung.uk.businessObjects.auction.AuctionService;
import ch.noseryoung.uk.businessObjects.auction.AuctionServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class AuctionServiceImplTest {

    private List<Auction> auctionListToBeTestedAgainst = new ArrayList<>();

    @Autowired
    private AuctionService auctionService;

    @MockBean
    private AuctionRepository auctionRepository;

    @BeforeEach
    void setUp() {
        auctionListToBeTestedAgainst = (Arrays.asList(
                new Auction(15, 450),
                new Auction(16, 440),
                new Auction(17, 650),
                new Auction(18, 700),
                new Auction(19, 750),
                new Auction(14, 50)
        ));

    }

    @AfterEach
    void tearDown() {
    }


    @Test
    public void getAuctionsByRange_requestAuctions_returnAuctionsByRange() throws Exception {
        given(auctionRepository.findAll()).willReturn(auctionListToBeTestedAgainst);

        //   List<Auction> auctionListRange = auctionService.getAuctionsByRange(440, 700);
        //  List<Auction> auctionListRange = auctionService.getAuctionsByRange(440, 701);
        List<Auction> auctionListRange = auctionService.getAuctionsByRange(440, 700);
        Assertions.assertThat(auctionListRange.get(0).getCurrentPrice()).isEqualTo(auctionListToBeTestedAgainst.get(0).getCurrentPrice());
        Assertions.assertThat(auctionListRange.get(1).getCurrentPrice()).isEqualTo(auctionListToBeTestedAgainst.get(1).getCurrentPrice());
        Assertions.assertThat(auctionListRange.get(2).getCurrentPrice()).isEqualTo(auctionListToBeTestedAgainst.get(2).getCurrentPrice());
        Assertions.assertThat(auctionListRange.get(3).getCurrentPrice()).isEqualTo(auctionListToBeTestedAgainst.get(3).getCurrentPrice());

        verify(auctionRepository, times(1)).findAll();

    }


}