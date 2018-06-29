package es.redmic.api.common.controller;

import javax.servlet.http.HttpServletResponse;

import es.redmic.models.es.common.dto.BaseDTO;
import es.redmic.models.es.common.dto.ElasticSearchDTO;
import es.redmic.models.es.common.model.BaseES;

public interface IRBaseController <TModel extends BaseES<?>, TDTO extends BaseDTO<?>, TQueryDTO> {
	
	public ElasticSearchDTO getFilterSchema(HttpServletResponse response);
}
