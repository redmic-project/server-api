package es.redmic.api.utils.geo.controller;

/*-
 * #%L
 * API
 * %%
 * Copyright (C) 2019 REDMIC Project / Server
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import java.io.File;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import es.redmic.exception.mediastorage.MSFileNotSupportedException;
import es.redmic.mediastorage.service.MediaStorageServiceItfc;
import es.redmic.models.es.common.dto.BodyItemDTO;
import es.redmic.models.es.geojson.common.dto.GeoJSONFeatureCollectionDTO;
import es.redmic.utils.geo.convert.GeoUtils;

@Controller
public class GeoConvertController {

	@Value("${property.path.media_storage.TEMP_PUBLIC}")
	private String PATH_TEMP_PUBLIC;

	@Autowired
	MediaStorageServiceItfc mediaStorageService;

	@Autowired
	GeoUtils geoutils;

	private final String EXTENSION_SHAPE_FILE = "shp";

	public GeoConvertController() {
	}

	@RequestMapping(value = "${controller.mapping.utils.geo.CONVERT_TO_GEOJSON}", method = RequestMethod.POST, headers = "content-type=multipart/form-data")
	@ResponseBody
	public BodyItemDTO<GeoJSONFeatureCollectionDTO> uploadGeoFile(
			@RequestParam(value = "file", required = true) MultipartFile file) {

		String extension = FilenameUtils.getExtension(file.getOriginalFilename());

		if (!extension.equals(EXTENSION_SHAPE_FILE))
			throw new MSFileNotSupportedException(EXTENSION_SHAPE_FILE, extension);

		File fileOut = mediaStorageService.uploadTempFile(file, PATH_TEMP_PUBLIC);

		GeoJSONFeatureCollectionDTO geoJson = new GeoJSONFeatureCollectionDTO();
		geoJson = geoutils.convertShpToGeoJSON(fileOut);

		BodyItemDTO<GeoJSONFeatureCollectionDTO> dto = new BodyItemDTO<GeoJSONFeatureCollectionDTO>();
		dto.setBody(geoJson);

		return dto;
	}
}
