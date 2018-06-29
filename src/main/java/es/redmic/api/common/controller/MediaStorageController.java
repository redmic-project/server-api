package es.redmic.api.common.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import es.redmic.exception.mediastorage.MSFileNotSupportedException;
import es.redmic.exception.mediastorage.MSFileUploadNotFoundException;
import es.redmic.mediastorage.service.MediaStorageServiceItfc;
import es.redmic.models.es.common.dto.BodyItemDTO;
import es.redmic.models.es.common.dto.SuperDTO;
import es.redmic.models.es.common.dto.UrlDTO;

@RestController
@RequestMapping(value = "${controller.mapping.MEDIASTORAGE}")
public class MediaStorageController {

	@SuppressWarnings("serial")
	private List<String> PHOTO_CONTENT_TYPES = new ArrayList<String>() {
		{
			add("image/jpg");
			add("image/jpeg");
			add("image/png");
		}
	};

	@SuppressWarnings("serial")
	private List<String> DOCUMENT_CONTENT_TYPES = new ArrayList<String>() {
		{
			add("application/pdf");
		}
	};

	@SuppressWarnings("serial")
	private List<String> CSV_CONTENT_TYPES = new ArrayList<String>() {
		{
			add("text/csv");
			add("application/vnd.ms-excel");
		}
	};

	@SuppressWarnings("serial")
	private List<String> COMPRESS_CONTENT_TYPES = new ArrayList<String>() {
		{
			add("application/zip");
			add("application/x-zip-compressed");
			add("application/octet-stream");
		}
	};

	@Value("${property.path.media_storage.SPECIES}")
	private String PATH_SPECIES;
	@Value("${property.URL_SPECIES_PHOTOBANK}")
	private String URL_PHOTO_SPECIES;

	@Value("${property.path.media_storage.CONTACTS}")
	private String PATH_CONTACTS;
	@Value("${property.URL_CONTACTS_PHOTOBANK}")
	private String URL_PHOTO_CONTACTS;

	@Value("${property.path.media_storage.ORGANISATIONS}")
	private String PATH_ORGANISATIONS;
	@Value("${property.URL_ORGANISATIONS_PHOTOBANK}")
	private String URL_PHOTO_ORGANISATIONS;

	@Value("${property.path.media_storage.USERS}")
	private String PATH_USERS;
	@Value("${property.URL_USERS_PHOTOBANK}")
	private String URL_PHOTO_USERS;

	@Value("${property.path.media_storage.PLATFORMS}")
	private String PATH_PLATFORMS;
	@Value("${property.URL_PLATFORMS_PHOTOBANK}")
	private String URL_PHOTO_PLATFORMS;

	@Value("${property.path.media_storage.ANIMALS}")
	private String PATH_ANIMALS;
	@Value("${property.URL_ANIMALS_PHOTOBANK}")
	private String URL_PHOTO_ANIMALS;

	@Value("${property.path.media_storage.DOCUMENTS}")
	private String PATH_DOCUMENTS;
	@Value("${property.URL_DOCUMENTS}")
	private String URL_DOCUMENTS;

	@Value("${property.path.media_storage.temp.INGEST_DATA}")
	private String PATH_SERIES_TEMP;

	@Autowired
	MediaStorageServiceItfc mediaStorageService;

	public MediaStorageController() {

	}

	@RequestMapping(value = "${controller.mapping.MEDIASTORAGE.UPLOADS_DOCUMENTS}", method = RequestMethod.POST, headers = "content-type=multipart/form-data")
	public SuperDTO uploadDocument(@RequestParam("file") MultipartFile file) {

		checkFile(file, DOCUMENT_CONTENT_TYPES);

		return new BodyItemDTO<UrlDTO>(mediaStorageService.uploadFile(file, PATH_DOCUMENTS, URL_DOCUMENTS));
	}

	@RequestMapping(value = "${controller.mapping.MEDIASTORAGE.UPLOADS_SPECIES}", method = RequestMethod.POST, headers = "content-type=multipart/form-data")
	public SuperDTO uploadSpeciesPhoto(@RequestParam("file") MultipartFile file) {

		checkFile(file, PHOTO_CONTENT_TYPES);

		return new BodyItemDTO<UrlDTO>(mediaStorageService.uploadFile(file, PATH_SPECIES, URL_PHOTO_SPECIES));
	}

	@RequestMapping(value = "${controller.mapping.MEDIASTORAGE.UPLOADS_CONTACTS}", method = RequestMethod.POST, headers = "content-type=multipart/form-data")
	public SuperDTO uploadContactPhoto(@RequestParam("file") MultipartFile file) {

		checkFile(file, PHOTO_CONTENT_TYPES);

		return new BodyItemDTO<UrlDTO>(mediaStorageService.uploadFile(file, PATH_CONTACTS, URL_PHOTO_CONTACTS));
	}

	@RequestMapping(value = "${controller.mapping.MEDIASTORAGE.UPLOADS_ORGANISATIONS}", method = RequestMethod.POST, headers = "content-type=multipart/form-data")
	public SuperDTO uploadOrganisationPhoto(@RequestParam("file") MultipartFile file) {

		checkFile(file, PHOTO_CONTENT_TYPES);

		return new BodyItemDTO<UrlDTO>(
				mediaStorageService.uploadFile(file, PATH_ORGANISATIONS, URL_PHOTO_ORGANISATIONS));
	}

	@RequestMapping(value = "${controller.mapping.MEDIASTORAGE.UPLOADS_USERS}", method = RequestMethod.POST, headers = "content-type=multipart/form-data")
	public SuperDTO uploadUserPhoto(@RequestParam("file") MultipartFile file) {

		checkFile(file, PHOTO_CONTENT_TYPES);

		return new BodyItemDTO<UrlDTO>(mediaStorageService.uploadFile(file, PATH_USERS, URL_PHOTO_USERS));
	}

	@RequestMapping(value = "${controller.mapping.MEDIASTORAGE.UPLOADS_PLATFORMS}", method = RequestMethod.POST, headers = "content-type=multipart/form-data")
	public SuperDTO uploadPlatformPhoto(@RequestParam("file") MultipartFile file) {

		checkFile(file, PHOTO_CONTENT_TYPES);

		return new BodyItemDTO<UrlDTO>(mediaStorageService.uploadFile(file, PATH_PLATFORMS, URL_PHOTO_PLATFORMS));
	}

	@RequestMapping(value = "${controller.mapping.MEDIASTORAGE.UPLOADS_ANIMALS}", method = RequestMethod.POST, headers = "content-type=multipart/form-data")
	public SuperDTO uploadAnimalPhoto(@RequestParam("file") MultipartFile file) {

		checkFile(file, PHOTO_CONTENT_TYPES);

		return new BodyItemDTO<UrlDTO>(mediaStorageService.uploadFile(file, PATH_ANIMALS, URL_PHOTO_ANIMALS));
	}

	@RequestMapping(value = "${controller.mapping.MEDIASTORAGE.UPLOADS_DATA}", method = RequestMethod.POST, headers = "content-type=multipart/form-data")
	@ResponseBody
	public SuperDTO uploadDataFile(@RequestParam("file") MultipartFile file) {

		List<String> contenTypeList = new ArrayList<>();
		contenTypeList.addAll(CSV_CONTENT_TYPES);
		contenTypeList.addAll(COMPRESS_CONTENT_TYPES);

		checkFile(file, contenTypeList);

		File seriesFile = mediaStorageService.uploadTempFile(file, PATH_SERIES_TEMP);
		UrlDTO dto = new UrlDTO();
		dto.setUrl(seriesFile.getName());
		return new BodyItemDTO<UrlDTO>(dto);
	}

	/* Retornar el documento pdf */

	@RequestMapping(value = "/documents/{name:.+}", method = RequestMethod.GET, produces = { "text/plain",
			"application/*" })
	public void download(@PathVariable("name") String name, HttpServletResponse response) {
		mediaStorageService.openDocumentPDF(name, response, PATH_DOCUMENTS);
	}

	/* Retornar imagenes de contacts */

	@RequestMapping(value = "/photobank/contacts/{name:.+}", method = RequestMethod.GET, produces = {
			"image/*" }, headers = "content-type=*")
	public void openImageContacts(@PathVariable("name") String name, HttpServletResponse response) {
		mediaStorageService.openImage(name, response, PATH_CONTACTS);
	}

	/* Retornar imagenes de users */

	@RequestMapping(value = "/photobank/users/{name:.+}", method = RequestMethod.GET, produces = {
			"image/*" }, headers = "content-type=*")
	public void openImageUsers(@PathVariable("name") String name, HttpServletResponse response) {
		mediaStorageService.openImage(name, response, PATH_USERS);
	}

	private void checkFile(MultipartFile file, List<String> contentTypeList) {

		if (file == null)
			throw new MSFileUploadNotFoundException();

		String contentType = file.getContentType().toLowerCase();

		if (!contentTypeList.contains(contentType))
			throw new MSFileNotSupportedException(StringUtils.join(contentTypeList, ", "), contentType);
	}
}
