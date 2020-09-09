package ch.noseryoung.uk.businessObjects.auction.integrationTests;


import ch.noseryoung.uk.businessObjects.auction.Auction;
import ch.noseryoung.uk.businessObjects.auction.AuctionRepository;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


import static org.hamcrest.Matchers.hasSize;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD) //nach jeder Methode wird die DB gereinigt
public class AuctionIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AuctionRepository auctionRepository;

    @Test
    public void findAll_requestAllAuctions_returnAllAuctions() throws Exception {

        Auction auction1 = new Auction().setCurrentPrice(350);
        Auction auction2 = new Auction().setCurrentPrice(275);

        auction1 = auctionRepository.save(auction1);
        auction2 = auctionRepository.save(auction2);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/auctions/")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(4)))
                //    .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(4))
                //    .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value(5))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].id", Matchers.containsInAnyOrder(auction1.getId(), auction2.getId(), 444, 555)))
        ;
    }


}







