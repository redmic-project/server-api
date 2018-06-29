package es.redmic.api.presence.biological.common;

import es.redmic.api.presence.geodata.common.GeoDataPresenceController;
import es.redmic.es.geodata.common.service.GeoPresenceESService;
import es.redmic.models.es.common.query.dto.SimpleQueryDTO;
import es.redmic.models.es.geojson.common.dto.MetaFeatureDTO;
import es.redmic.models.es.geojson.common.model.Feature;
import es.redmic.models.es.geojson.properties.model.GeoDataProperties;

public abstract class GeoBiologicalPresenceController<TModel extends Feature<GeoDataProperties, ?>, TDTO extends MetaFeatureDTO<?, ?>, TQueryDTO extends SimpleQueryDTO>
		extends GeoDataPresenceController<TModel, TDTO, TQueryDTO> {

	public GeoBiologicalPresenceController(GeoPresenceESService<TDTO, TModel> serviceES) {
		super(serviceES);
	}
}