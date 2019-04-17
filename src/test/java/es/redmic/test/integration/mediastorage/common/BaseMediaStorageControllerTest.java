package es.redmic.test.integration.mediastorage.common;

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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.databind.ObjectMapper;

import es.redmic.ApiApplication;
import es.redmic.exception.mediastorage.MSFileNotFoundException;
import es.redmic.mediastorage.service.MediaStorageServiceItfc;
import es.redmic.models.es.common.dto.BodyItemDTO;
import es.redmic.models.es.common.dto.UrlDTO;
import es.redmic.test.integration.ApiApplicationTest;
import es.redmic.test.integration.common.IntegrationTestBase;

@SpringBootTest(classes = { ApiApplication.class,
		ApiApplicationTest.class }, webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public abstract class BaseMediaStorageControllerTest extends IntegrationTestBase {

	@Autowired
	protected MediaStorageServiceItfc mediaStorage;

	@Autowired
	protected ObjectMapper objectMapper;

	final protected String PHOTO_RESOURCES = "/mediastorage/photo/";
	final protected String FILENAME_PHOTO = "test.jpg";
	final protected String PHOTO_CONTENT_TYPE = "image/jpg";

	final protected String DOCUMENT_RESOURCES = "/mediastorage/document/";
	final protected String FILENAME_DOCUMENT = "test.pdf";
	final protected String DOCUMENT_CONTENT_TYPE = "application/pdf";

	final protected String INGEST_DATA_RESOURCES = "/mediastorage/ingestdata/";
	final protected String FILENAME_CSV = "test.csv";
	final protected String CSV_CONTENT_TYPE = "text/csv";

	final protected String FILENAME_COMPRESS = "test.zip";
	final protected String COMPRESS_CONTENT_TYPE = "application/zip";

	protected UrlDTO uploadMultipartFileToMediaStorage(String sourcePath, String fileName, String urlResult,
			String targetPath, String contentType) {

		MockMultipartFile multipartFile = getFile(sourcePath, fileName, contentType);

		return mediaStorage.upload(multipartFile, targetPath, urlResult, fileName);
	}

	protected MockMultipartFile getFile(String sourcePath, String fileName, String contentType) {

		URI url = null;
		try {
			url = this.getClass().getResource(sourcePath + fileName).toURI();
		} catch (URISyntaxException e1) {
			e1.printStackTrace();
		}

		File file = new File(url);
		try {
			FileInputStream input = new FileInputStream(file);
			return new MockMultipartFile("file", file.getName(), contentType, IOUtils.toByteArray(input));
		} catch (IOException e) {
			throw new MSFileNotFoundException(file.getName(), sourcePath, e);
		}
	}

	protected void checkResult(ResultActions result, String filePath, String urlBase) throws Exception {

		UrlDTO urlDTO = getResult(result);

		String newName = getNameFromUrl(urlDTO.getUrl());

		assertEquals(urlDTO.getUrl(), urlBase + newName);

		assertTrue(mediaStorage.checkFileExist(filePath, newName));
		mediaStorage.deleteFile(filePath, newName);
	}

	protected void checkTempResult(ResultActions result, String filePath, String urlBase) throws Exception {

		UrlDTO urlDTO = getResult(result);

		String newName = getNameFromUrl(urlDTO.getUrl());

		assertEquals(urlDTO.getUrl(), urlBase + newName);

		assertTrue(mediaStorage.checkTempFileExist(filePath, newName));
		mediaStorage.deleteTempFile(filePath, newName);
	}

	@SuppressWarnings("unchecked")
	private UrlDTO getResult(ResultActions result) throws Exception {

		BodyItemDTO<UrlDTO> resultDTO = objectMapper.readValue(result.andReturn().getResponse().getContentAsString(),
				BodyItemDTO.class);

		return objectMapper.convertValue(resultDTO.getBody(), UrlDTO.class);
	}

	protected String getNameFromUrl(String url) {

		String[] urlSplit = url.split("/");

		return urlSplit[urlSplit.length - 1];
	}
}
