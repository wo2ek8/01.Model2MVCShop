package com.model2.mvc.view.product;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.DiskFileUpload;
import org.apache.commons.fileupload.FileUpload;
import org.apache.tomcat.util.http.fileupload.FileItem;

import com.model2.mvc.common.SearchVO;
import com.model2.mvc.framework.Action;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.product.vo.ProductVO;
import com.model2.mvc.service.user.vo.UserVO;

public class AddProductAction extends Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		if(FileUpload.isMultipartContent(request)) {
			//==> Eclipse workspace / Project ����� ������ ��
			//String temDir = "D:\\workspace03(Project&analysis)\\9941.1.Model2MVCShop(ins)\\WebContent\\images\\uploadFiles\\";
			String temDir = "C:\\workspace\\01.Model2MVCShop\\WebContent\\images\\uploadFiles\\";
			//String temDir - ".";
			//String temDir2 = "/uploadFiles/";
			
			DiskFileUpload fileUpload = new DiskFileUpload();
			fileUpload.setRepositoryPath(temDir);
			//setSizeThreshold�� ũ�⸦ ����� �Ǹ� ������ ��ġ�� �ӽ÷� �����Ѵ�.
			fileUpload.setSizeMax(1024 * 1024 * 100);
			//�ִ� 1�ް����� ���ε� ����(1024*1024*100) <- 100MB
			fileUpload.setSizeThreshold(1024 * 100); //�ѹ��� 100k������ �޸𸮿� ����
			
			if(request.getContentLength() < fileUpload.getSizeMax()) {
				ProductVO productVO = new ProductVO();
				
				StringTokenizer token = null;
				
				//parseRequest()�� FileItem�� �����ϰ� �ִ� ListŸ���� �����Ѵ�.
				List fileItemList = fileUpload.parseRequest(request);
				int Size = fileItemList.size(); //html page���� ���� ������ ������ ���Ѵ�.
				for(int i = 0; i < Size; i++) {
					org.apache.commons.fileupload.FileItem fileItem = (org.apache.commons.fileupload.FileItem)fileItemList.get(i);
					//isFormField()�� ���ؼ� ������������ �Ķ�������� �����Ѵ�. �Ķ���Ͷ�� true
					if(fileItem.isFormField()) {
						if(fileItem.getFieldName().equals("manuDate")) {
							token = new StringTokenizer(fileItem.getString("euc-kr"), "-");
							String manuDate = token.nextToken() + token.nextToken() + token.nextToken();
							productVO.setManuDate(manuDate);
						}
						else if(fileItem.getFieldName().equals("prodName"))
							productVO.setProdName(fileItem.getString("euc-kr"));
						else if(fileItem.getFieldName().equals("prodDetail"))
							productVO.setProdDetail(fileItem.getString("euc-kr"));
						else if(fileItem.getFieldName().equals("price"))
							productVO.setPrice(Integer.parseInt(fileItem.getString("euc-kr")));
					} else { //���������̸�
						//out.print("���� : " + fileItem.getFieldName() + " = " + fileItem.getName());
						//out.print("(" + fileItem.getSize() + " byte)<br>");
						
						if(fileItem.getSize() > 0) { //������ �����ϴ� if
							int idx = fileItem.getName().lastIndexOf("\\");
							//getName()�� ��θ� �� �������� ������ lastIndexOf�� �߶󳽴�
							if(idx == -1) {
								idx = fileItem.getName().lastIndexOf("/");
							}
							String fileName = fileItem.getName().substring(idx + 1);
							productVO.setFileName(fileName);
							try {
								java.io.File uploadedFile = new java.io.File(temDir, fileName);
								fileItem.write(uploadedFile);
							} catch(IOException e) {
								System.out.println(e);
							}
						} else {
							productVO.setFileName("../../images/empty.GIF");
						}
						} //else
					} //for
				
				ProductServiceImpl service = new ProductServiceImpl();
				service.addProduct(productVO);
				
				request.setAttribute("productVO", productVO);
				} else { //���ε��ϴ� ������ setSizeMax���� ū���
					int overSize = (request.getContentLength() / 1000000);
					System.out.println("<script>alert('������ ũ��� 1MB���� �Դϴ�. �ø��� ���� �뷮�� " + overSize + "MB�Դϴ�.');");
					System.out.println("history.back();</script>");
				}
			} else {
				System.out.println("���ڵ� Ÿ���� multipart/form-data�� �ƴմϴ�.");
			}
		
		return "forward:/product/readProduct.jsp";
		}
		
		
	}

