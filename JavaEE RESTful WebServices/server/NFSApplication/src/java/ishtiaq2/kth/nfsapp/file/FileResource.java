/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ishtiaq2.kth.nfsapp.file;

/**
 *
 * @author ishtiaq
 */
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Stateless
@Path("/file")
public class FileResource {
    @Inject FileService fileService;
    
    @POST
    @Path("/createFile")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public FFile createFile(FFile file, @QueryParam ("uid") String uid) {
        String result = "";
        try {
            System.out.println("Create File Request: " + file.getTitle() + " uid=> " + uid);
            result = fileService.createFile(file, uid);
            if (result.equalsIgnoreCase("fileCreated")) {
                return new FFile(0, file.getTitle(), "Created");
            }
        } catch(Exception e) {
            System.out.println("Error in Post: " + e);
        }
        return new FFile(0, result, result);   
    }

    //@Produces({"application/xml", "application/json"})
    @Produces("application/json")
    @Path("/getFileList")
    @GET
    public FFile[] getFileList() {
        System.out.println("Get Request (File List): ");
        FFile[] fileList = fileService.getFileList();
        return fileList;
    }
    
    @Produces("application/json")
    @Path("/getFile")
    @GET
    public FFile getFile(@QueryParam ("id") String id, @QueryParam ("uid") String uid) {
        System.out.println("Get Request (File contents): " + id + " uid=> " + uid);
        
        return fileService.getFile(Integer.parseInt(id));
    }
   
    @PUT
    @Path("/updateFile")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public FFile updateFile(FFile file, @QueryParam ("uid") String uid) {
        System.out.println("Update Request (File): " + file.getTitle() + " uid=> " + uid);
        String res = fileService.updateFile(file, uid);
        if (res.equalsIgnoreCase("fileupdated")) {
            return new FFile(0, "File Updated", "File Updated");
        }
        return new FFile(0, res, res);
    }
    
    @PUT
    @Path("/commentFile/{id}/{comment}/{uid}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public FFile commentFile(@PathParam("id") String id, @PathParam("comment") String comment, @PathParam ("uid") String uid ) {
        System.out.println("Comment Request (File): " + id + " uid=> " + uid);
        String res = fileService.commentFile(id, comment, uid);
        return new FFile(0, res, res);
        
    }
    
    @DELETE
    @Path("/deleteFile/{title}")  //@Path("users/{username: [a-zA-Z][a-zA-Z_0-9]*}")
    @Produces(MediaType.APPLICATION_JSON)
    public FFile deleteFile(@PathParam("title") String title, @QueryParam("uid") String uid) {
        System.out.println("Delete Request (File): " + title + " uid=> " + uid);
        String res = fileService.deleteFile(title, uid);
        return new FFile(0, res, res);
        
    }
}