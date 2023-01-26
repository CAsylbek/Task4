package com.task4.task4;

import com.task4.task4.model.DTO.TextDocumentDto;
import com.task4.task4.service.TextDocumentService;
import jakarta.xml.bind.JAXBException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.internal.matchers.apachecommons.ReflectionEquals;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
public class ServiceTests {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private TextDocumentService textDocumentService;

    private List<TextDocumentDto> dtoList = new ArrayList<>(
        List.of(new TextDocumentDto("document 1", new Date()),
             new TextDocumentDto("document 2 test", new Date()),
             new TextDocumentDto("document 3 test", new Date())));

    @Before
    public void setup() {
        textDocumentService.deleteAll();
        dtoList.stream().forEachOrdered(n -> textDocumentService.save(n));
    }

    @Test
    public void findByMessageXmlPositive() throws Exception {
        XmlValidator xmlValidator = new XmlValidator(
             "src/test/resources/static/textDocument.xml",
             "src/test/resources/static/Schema.xsd");
        TextDocumentDto dto = xmlValidator.createDtoFromXmlFile();

        List<TextDocumentDto> list = dtoList.subList(1, 3);
        List<TextDocumentDto> responseList = textDocumentService.findByMessageXml(dto);

        if (list.size() == responseList.size()) {
            for (int i = 0; i < list.size(); i++) {
                assertEquals(list.get(i).getText(), responseList.get(i).getText());
                assertEquals(list.get(i).getDate(), responseList.get(i).getDate());
            }
        }
    }

    @Test
    public void findByMessageXmlNegative() throws Exception{
        XmlValidator xmlValidator = new XmlValidator(
             "src/test/resources/static/textDocumentNegative.xml",
             "src/test/resources/static/Schema.xsd");
        TextDocumentDto dto = xmlValidator.createDtoFromXmlFile();

        List<TextDocumentDto> list = dtoList.subList(1, 3);
        List<TextDocumentDto> responseList = textDocumentService.findByMessageXml(dto);

        if (list.size() == responseList.size()) {
            for (int i = 0; i < list.size(); i++) {
                assertEquals(list.get(i).getText(), responseList.get(i).getText());
                assertEquals(list.get(i).getDate(), responseList.get(i).getDate());
            }
        }    }
}
