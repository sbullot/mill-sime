package fr.ippon.mill.farmer.infrastructure.primary;

import fr.ippon.mill.farmer.domain.Farmer;
import fr.ippon.mill.farmer.domain.FarmerAlreadyExistsException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

import static fr.ippon.mill.farmer.infrastructure.primary.CerealRessource.CEREALS_API_URI;

@RestController
@RequestMapping(CEREALS_API_URI)
@Validated
public class CerealRessource {
  public static final String CEREALS_API_URI = "/api/cereals";

  @PostMapping
  public ResponseEntity<Void> create(@RequestBody @Valid CerealRequest cereal) {
    Farmer farmer = Farmer.create(cereal.getFirstName(), cereal.getLastName(), cereal.getEmail(), cereal.getPhoneNumber());
    try {
      String reference = farmerService.register(farmer);
      String locationUri = String.format("%s/%s", FARMER_API_URI, reference);
      return ResponseEntity.created(URI.create(locationUri)).build();
    } catch (FarmerAlreadyExistsException farmerAlreadyExistsException) {
      return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }
  }
}
