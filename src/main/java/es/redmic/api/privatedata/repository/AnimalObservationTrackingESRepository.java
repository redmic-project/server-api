package es.redmic.api.privatedata.repository;

import es.redmic.es.geodata.tracking.common.repository.TrackingBaseESRepository;

public class AnimalObservationTrackingESRepository extends TrackingBaseESRepository {

	protected static String[] INDEX = { "private-geodata" };
	protected static String TYPE = "_doc";

	public AnimalObservationTrackingESRepository() {
		super();
	}

	@Override
	public String[] getIndex() {
		return INDEX;
	}

	@Override
	public String getType() {
		return TYPE;
	}

	@Override
	protected String getMappingFilePath(String index, String type) {
		return MAPPING_BASE_PATH + "private/geodata" + MAPPING_FILE_EXTENSION;
	}
}
