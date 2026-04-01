package fr.ippon.mill.farmer.infrastructure.primary;

import java.time.LocalDate;

public record CerealRequest(Cereal cereal, String farmerId, LocalDate deliveryDate) {
}
