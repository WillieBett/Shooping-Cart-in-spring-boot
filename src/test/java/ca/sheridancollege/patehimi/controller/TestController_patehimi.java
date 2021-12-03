package ca.sheridancollege.patehimi.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;

import ca.sheridancollege.patehimi.beans.ShoppingItem;
import ca.sheridancollege.patehimi.database.DatabaseAccess;

@SpringBootTest
@AutoConfigureMockMvc
public class TestController_patehimi {

	@Autowired
	private MockMvc mockMvc;
	
	@Test
	 public void testLoadingViewItemsPage() throws Exception {
	 this.mockMvc.perform(get("/"))
	 .andExpect(status().isOk())
	 .andExpect(view().name("view"));
	 }
	
//	@Test
//	 public void testAddItem() throws Exception {
//	 this.mockMvc.perform(get("/additem")) 
//	 .andExpect(status().isFound());
//	 }
	
	@Test
	 public void testAddItem() throws Exception {
	 this.mockMvc.perform(get("/additem")) 
	 .andExpect(status().isBadRequest());
	 }
	
//	@Test
//	public void testAddItem() throws Exception {
//	LinkedMultiValueMap<String, String> requestParams = 
//	new LinkedMultiValueMap<>();
//	requestParams.add("name", "TV");
//	requestParams.add("Description", "TV");
//	requestParams.add("price", "234.5");
//	requestParams.add("link", "abcd");
//	this.mockMvc.perform(get("/additem").params(requestParams))
//	.andExpect(status().isFound());
//	}

	@Autowired
    private DatabaseAccess da;
    
    @Test
    public void testAddItemToDatabase() {
   ShoppingItem shoppingitem = new ShoppingItem();
    int origSize = da.getAllmy_purchases().size();

    da.addItem(shoppingitem);
    int newSize = da.getAllmy_purchases().size();

    assertThat(newSize).isEqualTo(origSize + 1);

    }
	
    @Test
    public void testAddItemWithRedirect() throws Exception {
    LinkedMultiValueMap<String, String> requestParams =
    new LinkedMultiValueMap<>();
    requestParams.add("name", "himil");
    requestParams.add("description", "abc");
    requestParams.add("price", "289.5");
    requestParams.add("link", "xyz");
    this.mockMvc.perform(get("/additem").params(requestParams))
    .andExpect(status().isFound())
    .andExpect(redirectedUrl("/"));
    }
    
    @Test
    public void testDoEdit() throws Exception {
    Long id = da.getAllmy_purchases().get(0).getId();
    ShoppingItem a = new  ShoppingItem(0L, "food","tasty", 14000.0,"link");
    this.mockMvc.perform(post("/doEdit").flashAttr("shoppingitem", a))
    .andExpect(status().isFound())
    .andExpect(redirectedUrl("/"));
    }
    
    @Test
    public void testDeleteItem() throws Exception {
    this.mockMvc.perform(get("/delete/{id}", 0))
    .andExpect(status().isFound())
    .andExpect(redirectedUrl("/"));
    }
}

