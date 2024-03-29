package es.redmic.test.integration.mediastorage.documents;

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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import es.redmic.models.es.common.dto.UrlDTO;
import es.redmic.test.integration.mediastorage.common.BaseMediaStorageControllerTest;

@RunWith(SpringJUnit4ClassRunner.class)
public class DocumentMediaStorageControllerTest extends BaseMediaStorageControllerTest {

	@Value("${property.path.media_storage.DOCUMENTS}")
	private String PATH_DOCUMENTS;

	@Value("${controller.mapping.MEDIASTORAGE.UPLOADS_DOCUMENTS}")
	private String CONTROLLER_UPLOAD_FILE;

	@Value("${property.URL_DOCUMENTS}")
	private String URL_DOCUMENTS;

	@Value("${controller.mapping.MEDIASTORAGE}")
	private String BASE_MAPPING;

	MockMultipartFile multipartFile = getFile(DOCUMENT_RESOURCES, FILENAME_DOCUMENT, DOCUMENT_CONTENT_TYPE);

	@Test
	public void checkSecurity_Return200_IfUploadDocumentAsOAGUser() throws Exception {

		MockHttpServletRequestBuilder fileUpload = multipart(BASE_MAPPING + CONTROLLER_UPLOAD_FILE).file(multipartFile)
				.contentType(MediaType.MULTIPART_FORM_DATA).header("Authorization", "Bearer " + getTokenOAGUser());

		ResultActions result = mockMvc.perform(fileUpload);

		result.andExpect(status().isOk());

		checkResult(result, PATH_DOCUMENTS, URL_DOCUMENTS);
	}

	@Test
	public void uploadFile_Return400_IfNotUploadDocument() throws Exception {

		MockHttpServletRequestBuilder fileUpload = multipart(BASE_MAPPING + CONTROLLER_UPLOAD_FILE)
				.file(getFile(PHOTO_RESOURCES, FILENAME_PHOTO, PHOTO_CONTENT_TYPE))
				.contentType(MediaType.MULTIPART_FORM_DATA).header("Authorization", "Bearer " + getTokenOAGUser());

		ResultActions result = mockMvc.perform(fileUpload);

		result.andExpect(status().isBadRequest());
	}

	@Test
	public void checkSecurity_Return401_IfUploadDocumentAsGuest() throws Exception {

		MockHttpServletRequestBuilder fileUpload = multipart(BASE_MAPPING + CONTROLLER_UPLOAD_FILE)
				.file(multipartFile);

		mockMvc.perform(fileUpload).andExpect(status().isUnauthorized());
	}

	@Test
	public void getFile_Return401_IfGetDocumentAsGuest() throws Exception {

		UrlDTO urlDTO = uploadMultipartFileToMediaStorage(DOCUMENT_RESOURCES, FILENAME_DOCUMENT, URL_DOCUMENTS,
				PATH_DOCUMENTS, DOCUMENT_CONTENT_TYPE);

		String newName = getNameFromUrl(urlDTO.getUrl());

		mediaStorage.checkFileExist(PATH_DOCUMENTS, newName);

		mockMvc.perform(get(urlDTO.getUrl().replace("/api", ""))).andExpect(status().isUnauthorized());

		mediaStorage.deleteFile(PATH_DOCUMENTS, newName);
	}

	@Test
	public void getFile_Return200_IfGetDocumentAsOAGUser() throws Exception {

		UrlDTO urlDTO = uploadMultipartFileToMediaStorage(DOCUMENT_RESOURCES, FILENAME_DOCUMENT, URL_DOCUMENTS,
				PATH_DOCUMENTS, DOCUMENT_CONTENT_TYPE);

		String newName = getNameFromUrl(urlDTO.getUrl());

		mediaStorage.checkFileExist(PATH_DOCUMENTS, newName);

		mockMvc.perform(get(urlDTO.getUrl().replace("/api", "")).header("Authorization", "Bearer " + getTokenOAGUser()))
				.andExpect(status().isOk());

		mediaStorage.deleteFile(PATH_DOCUMENTS, newName);
		mediaStorage.deleteTempFile("./", newName);
	}
}
