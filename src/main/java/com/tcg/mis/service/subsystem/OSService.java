package com.tcg.mis.service.subsystem;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.tcg.mis.to.FileInfoTo;
import com.tcg.mis.to.OperatorInfo;

public interface OSService {

    OperatorInfo getCurrentUser(String token);

	public boolean hasPermission(String params, String token,Integer menuId,String merchantCode);

	public List<String> getMerchants(String token);
	
	FileInfoTo uploadFile(MultipartFile file, String moduleName) throws IOException;
	
}
