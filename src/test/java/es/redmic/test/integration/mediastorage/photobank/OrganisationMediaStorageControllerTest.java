package es.redmic.test.integration.mediastorage.photobank;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.fileUpload;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import es.redmic.test.integration.mediastorage.common.BaseMediaStorageControllerTest;

@RunWith(SpringJUnit4ClassRunner.class)
public class OrganisationMediaStorageControllerTest extends BaseMediaStorageControllerTest {

	@Value("${property.path.media_storage.ORGANISATIONS}")
	private String PATH_ORGANISATIONS;

	@Value("${controller.mapping.MEDIASTORAGE.UPLOADS_ORGANISATIONS}")
	private String CONTROLLER_UPLOAD_FILE;

	@Value("${property.URL_ORGANISATIONS_PHOTOBANK}")
	private String URL_PHOTO_ORGANISATIONS;

	@Value("${controller.mapping.MEDIASTORAGE}")
	private String BASE_MAPPING;

	MockMultipartFile multipartFile = getFile(PHOTO_RESOURCES, FILENAME_PHOTO, PHOTO_CONTENT_TYPE);

	@Test
	public void checkSecurity_Return200_IfUploadPhotoAsOAGUser() throws Exception {

		MockHttpServletRequestBuilder fileUpload = fileUpload(BASE_MAPPING + CONTROLLER_UPLOAD_FILE).file(multipartFile)
				.contentType(MediaType.MULTIPART_FORM_DATA).header("Authorization", "Bearer " + getTokenOAGUser());

		ResultActions result = mockMvc.perform(fileUpload);

		result.andExpect(status().isOk());

		checkResult(result, PATH_ORGANISATIONS, URL_PHOTO_ORGANISATIONS);
	}

	@Test
	public void uploadFile_Return400_IfNotUploadImage() throws Exception {

		MockHttpServletRequestBuilder fileUpload = fileUpload(BASE_MAPPING + CONTROLLER_UPLOAD_FILE)
				.file(getFile(DOCUMENT_RESOURCES, FILENAME_DOCUMENT, DOCUMENT_CONTENT_TYPE))
				.contentType(MediaType.MULTIPART_FORM_DATA).header("Authorization", "Bearer " + getTokenOAGUser());

		ResultActions result = mockMvc.perform(fileUpload);

		result.andExpect(status().isBadRequest());
	}

	@Test
	public void checkSecurity_Return401_IfUploadPhotoAsGuest() throws Exception {

		MockHttpServletRequestBuilder fileUpload = fileUpload(BASE_MAPPING + CONTROLLER_UPLOAD_FILE)
				.file(multipartFile);

		mockMvc.perform(fileUpload).andExpect(status().isUnauthorized());
	}
}
