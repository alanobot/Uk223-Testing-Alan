package ch.noseryoung.uk.businessObjects.auction;

import ch.noseryoung.uk.businessObjects.auction.dto.AuctionDTO;
import ch.noseryoung.uk.businessObjects.auction.dto.AuctionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController //das framework sucht durch alle controller ob die anfrage es passt
@RequestMapping("/auctions")

public class AuctionController {

    private AuctionService auctionService;
    private AuctionMapper auctionMapper;

    @Autowired
    public AuctionController(AuctionService auctionService, AuctionMapper auctionMapper) {
        this.auctionService = auctionService;
        this.auctionMapper = auctionMapper;
    }


    @PostMapping({"/", ""}) //macht neuen eintrag in die db
    public ResponseEntity<AuctionDTO> create(@RequestBody AuctionDTO auctionDTO) {
        return new ResponseEntity<>(auctionMapper.toDTO(auctionService.create(auctionMapper.fromDTO(auctionDTO))), HttpStatus.CREATED);
    }

    @GetMapping("/")
    public ResponseEntity<List<AuctionDTO>> getAll() {
        return new ResponseEntity<>(auctionMapper.toDTOs(auctionService.findAll()), HttpStatus.OK); //responsenetity ist die rückgabe als jason file
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuctionDTO> getById(@PathVariable int id) {
        return new ResponseEntity<>(auctionMapper.toDTO(auctionService.findById(id)), HttpStatus.OK);
    }





    @GetMapping("/between/{startPrice}/{endPrice}")
    public ResponseEntity<List<AuctionDTO>> getAuctionsByRange(@PathVariable int startPrice, @PathVariable int endPrice) {
        return new ResponseEntity<>(auctionMapper.toDTOs(auctionService.getAuctionsByRange(startPrice, endPrice)), HttpStatus.OK);
    }





    @PutMapping("/{id}")
    public ResponseEntity<AuctionDTO> updateById(@PathVariable int id, @RequestBody AuctionDTO auctionDTO) {
        return new ResponseEntity<>(auctionMapper.toDTO(auctionService.updateById(id, auctionMapper.fromDTO(auctionDTO))), HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable int id) {
        auctionService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
