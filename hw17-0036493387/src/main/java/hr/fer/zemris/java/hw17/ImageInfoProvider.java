package hr.fer.zemris.java.hw17;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import org.json.JSONObject;

/**
 * This class contains methods that can be used to retrieve information about images.
 * 
 * @author Mihael Stočko
 *
 */
@Path("/imageinfo")
public class ImageInfoProvider {
	
	/**
	 * Context
	 */
	@Context
	private ServletContext context;
	
	/**
	 * Request
	 */
	@Context 
	private HttpServletRequest req;
	
	/**
	 * Returns a JSON file which contains all unique tags.
	 * 
	 * @return JSON with all tags
	 * @throws IOException
	 */
	@Path("tags")
	@GET
	@Produces("application/json")
	public Response getAllTags() throws IOException {
		java.nio.file.Path p = Paths.get(context.getRealPath("/WEB-INF/opisnik.txt"));
		List<String> lines = Files.readAllLines(p);
		
		JSONObject result = new JSONObject();
		
		Set<String> uniqueTags = new HashSet<>();
		
		for(int i = 2; i < lines.size(); i += 3) {
			String[] tags = lines.get(i).split(",");
			for(int j = 0; j < tags.length; j++) {
				uniqueTags.add(tags[j].trim());
			}
		}
		
		for(String s : uniqueTags) {
			result.append("tags", s);
		}
		
		return Response.status(Status.OK).entity(result.toString()).build();
	}
	
	/**
	 * This method returns the description for the image given by parameter.
	 * 
	 * @return Image description
	 * @throws IOException
	 */
	@Path("description")
	@GET
	public Response getImageDescription() throws IOException {
		List<Image> images = readImages();

		String title = req.getParameter("title");
		
		Image found = null;
		for(Image i : images) {
			if(i.getTitle().equals(title)) {
				found = i;
				break;
			}
		}
		
		if(found == null) {
			return Response.status(Status.OK).entity("Description not found.").build();
		}
		
		return Response.status(Status.OK).entity(found.getDescription()).build();
	}
	
	/**
	 * Returns a JSON file which maps a title to the list of tags for each image.
	 * 
	 * @throws IOException
	 */
	@Path("images")
	@GET
	public Response getImageTags() throws IOException {
		List<Image> images = readImages();

		JSONObject result = new JSONObject();
		
		for(Image i : images) {
			result.put(i.getTitle(), i.getTags());
		}
		
		return Response.status(Status.OK).entity(result.toString()).build();
	}
	
	/**
	 * This method reads all images and return a list of {@link Image} objects.
	 * @return List of images
	 * @throws IOException
	 */
	private List<Image> readImages() throws IOException {
		java.nio.file.Path p = Paths.get(context.getRealPath("/WEB-INF/opisnik.txt"));
		List<String> lines = Files.readAllLines(p);
		
		List<Image> images = new LinkedList<>();
		for(int i = 0; i < lines.size(); i += 3) {
			Image img = new Image();
			
			String imgName = lines.get(i).trim();
			img.setTitle(imgName);
			
			String description = lines.get(i+1).trim();
			img.setDescription(description);
			
			String[] tagsArray = lines.get(i+2).split(",");
			List<String> tags = new LinkedList<>();
			for(String s : tagsArray) {
				tags.add(s.trim());
			}
			img.setTags(tags);
			
			images.add(img);
		}
		
		return images;
	}
}