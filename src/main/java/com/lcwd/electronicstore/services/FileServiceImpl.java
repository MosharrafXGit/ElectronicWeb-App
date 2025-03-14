package com.lcwd.electronicstore.services;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.lcwd.electronicstore.exception.BadApiRequest;

@Service
public class FileServiceImpl implements FileService {

	@Override
	public String uploadFile(MultipartFile file, String path) throws IOException {
		
		String originalFileName = file.getOriginalFilename();
		
		String fileName = UUID.randomUUID().toString();
		
		String extension= originalFileName.substring(originalFileName.lastIndexOf("."));
		
		String fileNameWithExtension = fileName + extension;
		
		String fullPathWithFileName = path+ File.separator+fileNameWithExtension;
		
		if(extension.equalsIgnoreCase(".png") || extension.equalsIgnoreCase(".jpeg") || extension.equalsIgnoreCase(".jpg"))
		{
			//file Save
			
			File folder = new File(path);
			
			if(!folder.exists())
			{
				folder.mkdirs();
			}
			
			//upload
			
			Files.copy(file.getInputStream(), Paths.get(fullPathWithFileName));
			
			return fileNameWithExtension;
		}
		else
		{
			throw new BadApiRequest("File with this "+extension+"not allowed");
		}
		
	
	}

	@Override
	public InputStream getResource(String path, String name) throws FileNotFoundException {

		String fullPath = path+name;
		
		InputStream inputStream = new FileInputStream(fullPath);
		
		return inputStream;
		
	}

	


}
