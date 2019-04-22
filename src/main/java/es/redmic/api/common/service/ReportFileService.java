package es.redmic.api.common.service;

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
