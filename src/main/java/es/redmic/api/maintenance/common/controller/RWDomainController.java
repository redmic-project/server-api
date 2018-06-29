package es.redmic.api.maintenance.common.controller;

import es.redmic.api.common.controller.RWController;
import es.redmic.databaselib.common.model.LongModel;
import es.redmic.db.common.service.IServiceRW;
import es.redmic.es.data.common.service.RWDataESService;
import es.redmic.models.es.common.dto.DTO;
import es.redmic.models.es.common.model.BaseAbstractES;
import es.redmic.models.es.common.query.dto.SimpleQueryDTO;

public abstract class RWDomainController<TModel extends LongModel, TESModel extends BaseAbstractES, TDTO extends DTO, TQueryDTO extends SimpleQueryDTO>
		extends RWController<TModel, TESModel, TDTO, TQueryDTO> {

	public RWDomainController(IServiceRW<TModel, TDTO> service, RWDataESService<TESModel, TDTO> serviceES) {
		super(service, serviceES);
	}
}