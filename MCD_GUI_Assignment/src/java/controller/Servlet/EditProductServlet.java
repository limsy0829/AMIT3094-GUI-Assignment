// Author:Lim Sheng Yang
// Description:use to edit the detail of the product and save it into the database 
package controller.Servlet;

import controller.Servlet.AddFoodServlet;
import controller.Control.ProductController;
import model.da.ProductDA;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.domain.Product;
import model.domain.ProductCategory;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

@WebServlet(name = "editProductServlet", urlPatterns = {"/editProductServlet"})
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2,maxFileSize = 1024 * 1024 * 10,maxRequestSize = 1024 * 1024 * 50)
public class EditProductServlet extends HttpServlet {
   public static final String UPLOAD_DIR = "images";
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        String ProdID = "";
        String ProdName = "";
        double price = 0.0;
        String progCa = "";
        String uploadImagePath = "";
        boolean file=false;
        if (ServletFileUpload.isMultipartContent(request)) {

            try {
                response.setContentType("text/html;charset=UTF-8");
                

                String servletDirectory = getServletContext().getRealPath("/"); //Absolute Path (servlet dir)
                String uploadDirPath = servletDirectory + UPLOAD_DIR; // concat servlet dir path and the name of upload folder
                //Sample Path - NetbeanProjectAbsolutePath\\web\\images(UPLOAD_DIR)

                //Create a new directory for uploading product image
                File directory = new File(uploadDirPath);
                if (!directory.exists()) { //Create a new directory if not exists
                    directory.mkdir();
                }
                
                //org.apache.commons.fileupload Library
                //Upload Image file to upload dir
                ServletFileUpload servletFileUpload = new ServletFileUpload(new DiskFileItemFactory());
                List<FileItem> formItems = servletFileUpload.parseRequest(request);
                if (formItems != null && formItems.size() > 0) {
                    for (FileItem item : formItems) {
                        if (item.isFormField()) {
                            String name = item.getFieldName();
                            String value = item.getString();
                            if(name.equals("id")){
                                ProdID=value;
                            }
                            if(name.equals("name")){
                                ProdName=value;
                            }
                            if(name.equals("price")){
                                price=Double.valueOf(value);
                            }
                            if(name.equals("category")){
                                progCa=value;
                            }
                        } else {
                            //Absolute Path (image file)
                            file=true;
                           
                            uploadImagePath = uploadDirPath + "/" + item.getName();

                            File image = new File(uploadImagePath);

                            //check if image file exists in the upload dir
                            if (!image.exists()) {
                                item.write(image);
                            }
                        }
                    }
                }
                
                boolean bol = false;
                Product prod = new Product(ProdID, ProdName, price, bol, new ProductCategory(progCa));
                
                if(uploadImagePath.endsWith("/")){
                    ProductDA prodDA = new ProductDA();
                    prodDA.updateProductWithoutImage(prod);
                }else{
                    prod.setImageFile(uploadImagePath);
                    ProductDA prodDA = new ProductDA();
                    prodDA.updateProduct(prod);
                }
               
                RequestDispatcher dispatcher = request.getRequestDispatcher("AdminMenu.jsp");
                dispatcher.forward(request, response);

            } catch (FileUploadException ex) {
                out.println(file+"Err0");
                Logger.getLogger(AddFoodServlet.class.getName()).log(Level.SEVERE, null, ex);
                
            }  catch (SQLException ex) {
                out.println("SQL Exception");
            }catch (Exception ex) {
                out.println(file+"Error");
                out.print(ex.getMessage());
                out.print(ProdName);
                out.println(uploadImagePath);
                Logger.getLogger(AddFoodServlet.class.getName()).log(Level.SEVERE, null, ex);
               
            }
        }

    }


}
