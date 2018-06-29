package es.redmic.api.common.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import es.redmic.exception.custom.ResourceNotFoundException;

@Service
public class ReportFileService {

	@Value("${property.path.temp.report}")
	private String PATH_BASE;

	public ReportFileService() {
	}

	public void returnReportFile(String name, HttpServletResponse response) {

		response.reset();

		String[] nameSplit = name.split("\\.");
		if (nameSplit.length < 2)
			return;

		String extension = nameSplit[1];
		if (extension.equals("pdf"))
			response.setContentType("application/pdf");
		else if (extension.equals("html"))
			response.setContentType("text/html");
		else if (extension.equals("odt"))
			response.setContentType("application/vnd.oasis.opendocument.text");
		else
			return;

		response.setHeader("Content-Disposition", "attachment;filename=\"" + name + "\"");

		File file = new File(PATH_BASE, name);
		try {
			IOUtils.copy(new FileInputStream(file), response.getOutputStream());
		} catch (IOException e) {
			throw new ResourceNotFoundException(e);
		} finally {
			// file.delete(); // TODO: (script borrado para que si nunca accede,
			// borre al tiempo)
		}
	}
}
