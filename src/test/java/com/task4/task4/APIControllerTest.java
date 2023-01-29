package com.task4.task4;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.task4.task4.model.DTO.TextDocumentDto;
import com.task4.task4.repository.TextDocumentRepository;
import com.task4.task4.service.TextDocumentService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.thymeleaf.spring6.context.SpringContextUtils;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
public class APIControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private TextDocumentService textDocumentService;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private TextDocumentRepository textDocumentRepository;

    public APIControllerTest() {
    }

    @Before
    public void setup() {
        textDocumentService.deleteAll();
        textDocumentService.save(new TextDocumentDto("document 1", new Date()));
        textDocumentService.save(new TextDocumentDto("document 2 test", new Date()));
        textDocumentService.save(new TextDocumentDto("document 3 test", new Date()));
    }

    @Test
    public void postDocuments() throws Exception {
        TextDocumentDto dto = new TextDocumentDto("post document test", new Date());

        ResultActions response = mockMvc.perform(post("/api/v1/documents")
             .contentType(MediaType.APPLICATION_JSON)
             .content(objectMapper.writeValueAsString(dto)));

        response.andExpect(status().isCreated())
             .andDo(print())
             .andExpect(jsonPath("$.text", is("post document test")));
    }

    @Test
    public void getDocuments() throws Exception {
        ResultActions response = mockMvc.perform(get("/api/v1/documents")
             .contentType(MediaType.APPLICATION_JSON_VALUE));

        response.andExpect(status().isOk())
             .andDo(print())
             .andExpect(jsonPath("$", hasSize(3)))
             .andExpect(jsonPath("$[0].text", is("document 1")));
    }

    @Test
    public void deleteDocuments() throws Exception {
        String id = textDocumentRepository.findAll().iterator().next().getId();

        ResultActions response = mockMvc.perform(delete("/api/v1/documents/" + id)
             .contentType(MediaType.APPLICATION_JSON_VALUE));

        response.andExpect(status().isOk())
             .andDo(print())
             .andExpect(jsonPath("$.text", is("document 1")));
    }

    @Test
    public void deleteAll() throws Exception {
        ResultActions response = mockMvc.perform(delete("/api/v1/documents/all")
             .contentType(MediaType.APPLICATION_JSON_VALUE));

        response.andExpect(status().isOk())
             .andDo(print());
    }

    @Test
    public void findXmlDocuments() throws Exception {
        String xml = Files.readString(Paths.get("src/test/resources/static/textDocument.xml"));

        ResultActions response = mockMvc.perform(post("/api/v1/documents/xml")
             .contentType(MediaType.APPLICATION_XML)
             .accept(MediaType.APPLICATION_XML)
             .content(xml));

        response.andExpect(status().isOk())
             .andDo(print())
             .andExpect(xpath("List/*").nodeCount(is(2)))
             .andExpect(xpath("List/item[1]/text").string(is("document 2 test")));
    }
}
