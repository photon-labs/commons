/**
 * Phresco Commons
 *
 * Copyright (C) 1999-2013 Photon Infotech Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.photon.phresco.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;

import com.photon.phresco.commons.model.ApplicationInfo;
import com.photon.phresco.exception.PhrescoException;
import com.phresco.pom.exception.PhrescoPomException;

public final class FileUtil {
	
	private FileUtil(){
		
	}
	public static boolean delete(File file) {
		boolean deleteStatus = false;
		if (file.isDirectory()) {
			deleteStatus = deleteDir(file);
		} else {
			deleteStatus = file.delete();
		}

		return deleteStatus;
	}

	private static boolean deleteDir(File dir) {
		if (dir!= null && dir.isDirectory() && dir.exists()) {
			String[] children = dir.list();
			for (int i = 0; i < children.length; i++) {
				boolean success = deleteDir(new File(dir, children[i]));
				if (!success) {
					return false;
				}
			}
		}
		
		return dir.delete();
	}
	
	public static File writeFileFromInputStream(InputStream inputStream, String tempZip) throws IOException, FileNotFoundException {
		File tempZipFile = new File(tempZip);
		if (!tempZipFile.exists()) {
			tempZipFile.createNewFile();
		}
		OutputStream out = new FileOutputStream(new File(tempZip));
		int read = 0;
		byte[] bytes = new byte[1024];
		while ((read = inputStream.read(bytes)) != -1) {
			out.write(bytes, 0, read);
		}

		inputStream.close();
		out.flush();
		out.close();
		return tempZipFile;
	}
	
	public static boolean moveUploadedThemeBundle(File extractedFile, ApplicationInfo appInfo) throws PhrescoException {
		boolean flag = true;
		StringBuilder bundlePath = new StringBuilder(Utility.getProjectHome());
		try {
			String destFolder = getThemeBundleUploadDir(appInfo);
			bundlePath.append(appInfo.getAppDirName())
			.append(File.separator)
			.append(destFolder);
			File destination = new File(bundlePath.toString());
			
			if (StringUtils.isEmpty(destFolder) || !destination.exists()) {
				flag = false;
			} else if (destination.exists()) {
				FileUtils.copyDirectoryToDirectory(extractedFile, destination);
			} 
		} catch (Exception e) {
			flag = false;
			throw new PhrescoException(e);
		}
		
		return flag;
	}

	private static String getThemeBundleUploadDir(ApplicationInfo appinfo) throws PhrescoException, PhrescoPomException {
		return Utility.getPomProcessor(appinfo).getProperty(Constants.POM_PROP_KEY_THEME_BUNDLE_UPLOAD_DIR);
	}

}
