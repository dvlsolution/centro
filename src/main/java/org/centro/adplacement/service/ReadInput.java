package org.centro.adplacement.service;

import org.centro.adplacement.exeption.BrokenInputFileException;
import org.centro.adplacement.model.Delivery;
import org.centro.adplacement.model.Placement;

import java.util.List;
import java.util.Map;

public interface ReadInput
{
    Map<Long, Placement> readPlacementsInput() throws BrokenInputFileException;
    List<Delivery> readDeliveryInput(Map<Long, Placement> placements) throws BrokenInputFileException;
}
