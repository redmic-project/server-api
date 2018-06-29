package es.redmic.api.geodata.citation.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vividsolutions.jts.geom.Geometry;

import es.redmic.db.geodata.common.service.GridService;
import es.redmic.es.geodata.common.service.GridServiceItfc;
import es.redmic.es.tools.distributions.species.repository.RWTaxonDistributionRepository;
import es.redmic.models.es.tools.distribution.model.Distribution;

@Service
public class CitationGridService implements GridServiceItfc {

	@Autowired
	GridService gridService;

	@Autowired
	protected ObjectMapper objectMapper;

	public Distribution getDistribution(Geometry geometry, RWTaxonDistributionRepository repo, int radius) {

		Map<String, Object> result = gridService.findGridByPointAndPrecision(repo.getGridSize(),
				geometry.getCoordinate().x, geometry.getCoordinate().y, radius);
		if (result != null)
			return objectMapper.convertValue(result, Distribution.class);
		return null;
	}
}
