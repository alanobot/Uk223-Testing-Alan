package ch.noseryoung.uk.businessObjects.auction.dto;

import ch.noseryoung.uk.businessObjects.auction.Auction;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import java.util.List;
import java.util.Set;


@Mapper(componentModel="spring", unmappedTargetPolicy= ReportingPolicy.IGNORE) //
public interface AuctionMapper {

    Auction fromDTO(AuctionDTO dto); //dto zu businessobj

    List<Auction> fromDTOs(List<AuctionDTO> dtos); //mehrere gleichzeitig

    Set<Auction> fromDTOs(Set<AuctionDTO> dtos);

    AuctionDTO toDTO(Auction dm); //bo zu dto

    List<AuctionDTO> toDTOs(List<Auction> dms); //mehrere bo zu dto

    Set<AuctionDTO> toDTOs(Set<Auction> dms); // andere art von listen bo zu dto

}
