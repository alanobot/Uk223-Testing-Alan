package ch.noseryoung.uk.businessObjects.auction.unitTests;


import ch.noseryoung.uk.businessObjects.auction.Auction;
import ch.noseryoung.uk.businessObjects.auction.AuctionService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class AuctionControllerTest {

    //Dummy AuctionDTO to use as return value
    Auction auctionToBeTestedAgainst;
    Auction auctionToBeTestedAgainst2;
    Auction auctionToBeTestedAgainst3;
    List<Auction> auctionListToBeTestedAgainst = new ArrayList<>();
    List<Auction> auctionListToBeTestedAgainstRange = new ArrayList<>();

    @Autowired
    private MockMvc mvc; //gebraucht wenn man den weblayer künstlich testen möchte


    @MockBean
    private AuctionService auctionService; //Immitation vom Servicelayer


    //vor jedem unittest wird diese methode ausgefürt
    @BeforeEach
    void setUp() {
        auctionToBeTestedAgainst = new Auction(10, 400f);
        auctionToBeTestedAgainst2 = new Auction(10, 600f);
        auctionToBeTestedAgainst3 = new Auction(10, 900f);


        auctionListToBeTestedAgainst.add(auctionToBeTestedAgainst);
        auctionListToBeTestedAgainst.add(auctionToBeTestedAgainst2);

        auctionListToBeTestedAgainstRange.add(auctionToBeTestedAgainst);
        auctionListToBeTestedAgainstRange.add(auctionToBeTestedAgainst2);
        //auctionListToBeTestedAgainstRange.add(auctionToBeTestedAgainst3);
    }


    //hier wird aufgeräumt
    @AfterEach
    void tearDown() {
    }


    //***arrange
    //configuration vom perfekten servicelayer
    @Test
    // @WithMockUser(authorities = "USER_SEE_GLOBAL") //falls ein user gebraucht wird
    public void findById_requestAuctionById_returnAuction() throws Exception {

        given(auctionService.findById(anyInt())).will(invocation -> {
            if ("non-existent".equals(invocation.getArgument(0))) throw new Exception();
            return (auctionToBeTestedAgainst);
        });

//***act
        //Entspricht das json mit dem erwartetem produkt
        mvc.perform(
                MockMvcRequestBuilders.get("/auctions/{id}", auctionToBeTestedAgainst.getId()) //führt eigentlich einen get aus
                        .accept(MediaType.APPLICATION_JSON)) //sagt, dass es ein json will
                //***assert
                .andExpect(MockMvcResultMatchers.status().isOk()) //überprüft den http status "ok"
                .andExpect(MockMvcResultMatchers.jsonPath("$.currentPrice").value(auctionToBeTestedAgainst.getCurrentPrice()));//überprüft beim current preis ob es sich um den wert handelt

        ArgumentCaptor<Integer> integerArgumentCaptor = ArgumentCaptor.forClass(Integer.class);             //eingegebenes argument abfangen
        verify(auctionService, times(1)).findById(integerArgumentCaptor.capture()); //schaut, dass die methode nur 1 mal aufgerufen wurde
        Assertions.assertThat(integerArgumentCaptor.getValue()).isEqualTo(auctionToBeTestedAgainst.getId()); //argument mit dem gewünschtem wert vergleichen

    }


    @Test
    public void getAll_requestAllAuctions_returnAllAuctions() throws Exception {
        given(auctionService.findAll()).willReturn(auctionListToBeTestedAgainst);


        mvc.perform(
                MockMvcRequestBuilders.get("/auctions/")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].currentPrice").value(auctionListToBeTestedAgainst.get(0).getCurrentPrice()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].currentPrice").value(auctionListToBeTestedAgainst.get(1).getCurrentPrice()));

        verify(auctionService, times(1)).findAll();
    }


    @Test
    public void getAuctionsByRange_requestAuctions_returnAuctionsByRange() throws Exception {
        given(auctionService.getAuctionsByRange(anyInt(), anyInt())).will(invocation -> {
            if ("non-existent".equals(invocation.getArgument(0))) throw new Exception();
            return (auctionListToBeTestedAgainstRange);
        });

        mvc.perform(
                MockMvcRequestBuilders.get("/auctions/between/{startPrice}/{endPrice}", 300, 700)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].currentPrice").value(auctionListToBeTestedAgainstRange.get(0).getCurrentPrice()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].currentPrice").value(auctionListToBeTestedAgainstRange.get(1).getCurrentPrice()));

        ArgumentCaptor<Integer> integerArgumentCaptor = ArgumentCaptor.forClass(Integer.class);
        ArgumentCaptor<Integer> integerArgumentCaptor2 = ArgumentCaptor.forClass(Integer.class);
       verify(auctionService, times(1)).getAuctionsByRange(integerArgumentCaptor.capture(),integerArgumentCaptor2.capture());

        Assertions.assertThat(integerArgumentCaptor.getValue()).isEqualTo(300);
        Assertions.assertThat(integerArgumentCaptor2.getValue()).isEqualTo(700);


        // Assertions.assertThat(integerArgumentCaptor.getValue().equals(300));
       // Assertions.assertThat(integerArgumentCaptor2.getValue().equals(900));

    }


}