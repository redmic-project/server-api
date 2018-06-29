package es.redmic.api.utils.sitemap.service;

import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import es.redmic.api.utils.sitemap.dto.ModuleNamesDTO;
import es.redmic.api.utils.sitemap.dto.OpenModules;
import es.redmic.es.common.queryFactory.geodata.CitationQueryUtils;
import es.redmic.es.common.queryFactory.geodata.PlatformTrackingQueryUtils;
import es.redmic.es.common.queryFactory.geodata.TrackingQueryUtils;
import es.redmic.es.common.service.MetaDataESService;
import es.redmic.exception.utils.SitemapGenerateException;
import es.redmic.models.es.common.query.dto.DataQueryDTO;
import es.redmic.utils.httpclient.HttpClient;
import es.redmic.utils.sitemap.Sitemap;

@Service
public class GenerateSitemapService {

	HttpClient client = new HttpClient();

	@Value("${property.URL_OPEN_MODULES}")
	private String openModulesUrl;

	@Autowired
	ApplicationContext ctx;

	@Value("${property.SITEMAP_BASE_URL}")
	private String BASE_URL;

	@Value("${property.SITEMAP_DESTINATION_DIR}")
	private String DESTINATION_DIR;

	@Value("#{'${property.LANGS}'.split(',')}")
	private List<String> langs;

	@Value("${property.DEFAULT_LANG}")
	private String defaultLang = "es";

	// @formatter:off

	private final static String ID_PROPERTY = "id",
			ID_PATTERN = "{id}",
			SERVICE_SUFFIX = "ESService";

	// @formatter:on

	private OpenModules openModules;

	@PostConstruct
	private void init() {

		openModules = (OpenModules) client.get(openModulesUrl, OpenModules.class);
	}

	public void createSitemap() {

		Sitemap sitemap = new Sitemap(BASE_URL, DESTINATION_DIR, langs, defaultLang);

		for (ModuleNamesDTO moduleNames : openModules) {

			String parentName = moduleNames.getParentName(), moduleName = moduleNames.getModuleName();

			if (!moduleName.contains(ID_PATTERN)) {

				sitemap.addUrl(createUrl(parentName, moduleName));

			} else {

				String serviceName = getServiceName(moduleName);

				if (serviceName == null) {

					System.out.println("Nombre del servicio no soportado en el generador de sitemap " + serviceName);
					throw new SitemapGenerateException();
				}

				List<String> ids = getIds(serviceName, getQuery(moduleName));

				if (ids != null) {

					for (String id : ids) {

						String moduleNameWithId = moduleName.replace(ID_PATTERN, id);
						sitemap.addUrl(createUrl(parentName, moduleNameWithId));
					}
				}
			}
		}

		sitemap.writeSitemap();
	}

	private String createUrl(String parentName, String moduleName) {

		return parentName != null ? parentName + "/" + moduleName : moduleName;
	}

	private String getServiceName(String moduleName) {

		String[] moduleNameSplit = moduleName.split("-");

		String name = null;

		if (moduleNameSplit.length > 1) {

			if (moduleNameSplit[1].equals("ogc"))
				return "layer" + SERVICE_SUFFIX;

			return moduleNameSplit[0] + SERVICE_SUFFIX;
		}
		return name;

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private List<String> getIds(String serviceName, DataQueryDTO queryDTO) {

		MetaDataESService service = null;

		try {
			service = (MetaDataESService) ctx.getBean(serviceName);
		} catch (Exception e) {
			System.out.println("Nombre del servicio no soportado en el generador de sitemap " + serviceName);
			throw new SitemapGenerateException(e);
		}

		return service.getAllIds(queryDTO, ID_PROPERTY);
	}

	private DataQueryDTO getQuery(String moduleName) {

		DataQueryDTO queryDTO = new DataQueryDTO();
		queryDTO.setAccessibilityIds(Arrays.asList(2L));

		if (moduleName.contains("map")) {
			queryDTO.setTerms(CitationQueryUtils.getActivityCategoryTermQuery());
		} else if (moduleName.contains("tracking")) {
			queryDTO.setTerms(TrackingQueryUtils.getActivityCategoryTermQuery());
		} else if (moduleName.contains("infrastructure")) {
			queryDTO.setTerms(PlatformTrackingQueryUtils.getActivityCategoryTermQuery());
		}
		return queryDTO;
	}
}
