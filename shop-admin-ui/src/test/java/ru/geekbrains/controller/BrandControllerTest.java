package ru.geekbrains.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import ru.geekbrains.controllers.NotFoundException;
import ru.geekbrains.model.Brand;
import ru.geekbrains.repo.BrandRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@TestPropertySource(locations = "classpath:application-text.properties")
@AutoConfigureMockMvc
@SpringBootTest
public class BrandControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private BrandRepository brandRepository;

    @WithMockUser(value = "admin", password = "admin", roles = {"ADMIN"})
    @Test
    public void newBrandTest() throws Exception {
        mvc.perform(post("/brand")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id","-1")
                .param("name", "New Brand")
                .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/brands"));

        Brand brand = new Brand();
        brand.setName("New Brand");
        Optional<Brand> actualBrand = brandRepository.findOne(Example.of(brand));
        assertTrue(actualBrand.isPresent());
        assertEquals("New Brand", actualBrand.get().getName());
    }

    @WithMockUser(value = "admin", password = "admin", roles = {"ADMIN"})
    @Test
    public void brandsListText() throws Exception {
        mvc.perform(get("/brands"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attribute("activePage", "Brands"))
                .andExpect(model().attribute("brands", brandRepository.findAll()))
                .andExpect(view().name("brands"));
    }

    @WithMockUser(value = "admin", password = "admin", roles = {"ADMIN"})
    @Test
    public void brandsCreateTest() throws Exception {
        mvc.perform(get("/brand/create"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attribute("create", true))
                .andExpect(model().attribute("activePage", "Brands"))
                .andExpect(model().attributeExists("brand"))
                .andExpect(view().name("brand_form"));
    }

    @WithMockUser(value = "admin", password = "admin", roles = {"ADMIN"})
    @Test
    public void brandsEditTest() throws Exception {
        mvc.perform(get("/brand/1/edit"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attribute("edit", true))
                .andExpect(model().attribute("activePage", "Brands"))
                .andExpect(model().attribute("brand",brandRepository.findById(1L).orElseThrow(NotFoundException::new)))
                .andExpect(view().name("brand_form"));
    }

    @WithMockUser(value = "admin", password = "admin", roles = {"ADMIN"})
    @Test
    public void brandsDeleteTest() throws Exception {
        mvc.perform(delete("/brand/1/delete")
                .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/brands"));
    }
}
