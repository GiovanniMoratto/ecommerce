package br.com.zupacademy.giovannimoratto.ecommerce.add_category;

import com.google.gson.Gson;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Optional;

/**
 * @Author giovanni.moratto
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class CategoryControllerTest {

    private final String urlTemplate = "/new_category";
    private final MockMvc mockMvc;
    private final Gson gson;
    private final CategoryRepository repository;

    @Autowired
    CategoryControllerTest(MockMvc mockMvc, Gson gson, CategoryRepository repository) {
        this.mockMvc = mockMvc;
        this.gson = gson;
        this.repository = repository;
    }

    @BeforeEach
    void setUp() {
        this.repository.deleteAll();
    }

    /* Methods */
    // POST Request
    @Test
    @DisplayName("400 Bad Request - When trying to POST with empty BODY")
    void emptyBodyStatus400() throws Exception {
        // Values to Fail Test
        mockMvc.perform(MockMvcRequestBuilders.post(urlTemplate)
                .characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    // POST Request
    @Test
    @DisplayName("400 Bad Request - When trying to POST with empty Object")
    void emptyObjectStatus400() throws Exception {
        // Values to Fail Test
        mockMvc.perform(MockMvcRequestBuilders.post(urlTemplate)
                .content("{ }")
                .characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    // POST Request
    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("400 Bad Request - When trying to POST with empty NAME")
    void nameEmptyStatus400(String name) throws Exception {
        // Values to Fail Test
        Long idParentCategory = null;
        String jsonRequest = gson.toJson(new CategoryRequest(name, idParentCategory));
        mockMvc.perform(MockMvcRequestBuilders.post(urlTemplate)
                .content(jsonRequest)
                .characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
        Assertions.assertTrue(repository.findByName(name).isEmpty());
    }

    // POST Request
    @Test
    @DisplayName("400 Bad Request - When trying to POST with duplicate NAME")
    void duplicateNameStatus400() throws Exception {
        // Values to Fail Test
        String name = "duplicate";
        Long idParentCategory = null;

        CategoryRequest request1 = new CategoryRequest(name, idParentCategory);
        String jsonRequest1 = gson.toJson(request1);

        CategoryRequest request2 = new CategoryRequest(name, idParentCategory);
        String jsonRequest2 = gson.toJson(request2);

        mockMvc.perform(MockMvcRequestBuilders.post(urlTemplate)
                .content(jsonRequest1)
                .characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON));

        mockMvc.perform(MockMvcRequestBuilders.post(urlTemplate)
                .content(jsonRequest2)
                .characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

        Assertions.assertEquals(1, repository.countByName(name));
    }

    // POST Request
    @Test
    @DisplayName("400 Bad Request - When trying to POST with invalid idParentCategory")
    void idParentCategoryInvalidStatus400() throws Exception {
        // Values to Fail Test
        String name = "test";
        Long idParentCategory = 99L;
        String jsonRequest = gson.toJson(new CategoryRequest(name, idParentCategory));
        mockMvc.perform(MockMvcRequestBuilders.post(urlTemplate)
                .content(jsonRequest)
                .characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
        Assertions.assertTrue(repository.findByName(name).isEmpty());
    }

    // POST Request
    @Test
    @DisplayName("200 OK - Succeed and persist a Category without parent")
    void createNewCategoryNoParentStatus200() throws Exception {
        // Values to Success Test
        String name = "test";
        Long idParentCategory = null;

        String jsonRequest = gson.toJson(new CategoryRequest(name, idParentCategory));
        mockMvc.perform(MockMvcRequestBuilders.post(urlTemplate)
                .content(jsonRequest)
                .characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
        Optional <CategoryModel> optionalCategory = repository.findByName(name);
        Assertions.assertTrue(optionalCategory.isPresent());
    }

    // POST Request
    @Test
    @DisplayName("200 OK - Succeed and persist a Category without parent")
    void createNewCategoryWithParentStatus200() throws Exception {
        // Values to Success Test
        String name1 = "test01";
        Long idParentCategory1 = null;

        CategoryRequest request1 = new CategoryRequest(name1, idParentCategory1);
        String jsonRequest1 = gson.toJson(request1);

        mockMvc.perform(MockMvcRequestBuilders.post(urlTemplate)
                .content(jsonRequest1)
                .characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON));

        String name2 = "test02";
        Long idParentCategory2 = repository.findByName(name1).orElseThrow().getId();

        CategoryRequest request2 = new CategoryRequest(name2, idParentCategory2);
        String jsonRequest2 = gson.toJson(request2);

        mockMvc.perform(MockMvcRequestBuilders.post(urlTemplate)
                .content(jsonRequest2)
                .characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
        Optional <CategoryModel> optionalCategory2 = repository.findByName(name2);
        Assertions.assertTrue(optionalCategory2.isPresent());
    }

}