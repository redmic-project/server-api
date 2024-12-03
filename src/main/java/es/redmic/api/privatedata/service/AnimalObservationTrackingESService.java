package es.redmic.api.privatedata.service;

import org.springframework.beans.factory.annotation.Autowired;

import es.redmic.api.privatedata.repository.AnimalObservationTrackingESRepository;
import es.redmic.es.geodata.tracking.common.service.TrackingBaseESService;

public class AnimalObservationTrackingESService extends TrackingBaseESService {

    @Autowired
    public AnimalObservationTrackingESService(AnimalObservationTrackingESRepository repository){
        super(repository);
    }
}
