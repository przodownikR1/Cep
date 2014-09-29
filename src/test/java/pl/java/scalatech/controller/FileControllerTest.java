package pl.java.scalatech.controller;

import lombok.extern.slf4j.Slf4j;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import pl.java.scalatech.config.AppConfig;
import pl.java.scalatech.repository.UserRepository;
import pl.java.scalatech.repository.UserResourceRepository;
import pl.java.scalatech.service.filestorage.FileService;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.fileUpload;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { AppConfig.class })
@WebAppConfiguration
@Slf4j
public class FileControllerTest {

    private MockMvc mockMvc;

    @Mock
    UserRepository userRepository;
    @Mock
    UserResourceRepository userResourceRepository;
    @Autowired
    @InjectMocks
    private FileService fileService;

    @Autowired
    private FileService fileServiceMock;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        // Mockito.reset(fileServiceMock);

        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void shouldContextStart() {
        log.info("{}", fileServiceMock);
    }

    @Test
    public void shouldHealthControllerWorks() throws Exception {
        mockMvc.perform(get("/api/appContext")).andExpect(content().contentType("application/json;charset=UTF-8")).andExpect(status().isOk());

    }

    @Test
    public void shouldFileUploadWork() throws Exception {
        mockMvc.perform(get("/fileUpload")).andExpect(status().isOk()).andExpect(view().name("fileUpload"));
    }

    @Test
    public void shouldCarWork() throws Exception {
        this.mockMvc.perform(get("/api/car/name/{name}", "opel")).andDo(print()).andExpect(status().isOk());
    }

    
    @Test
    public void shouldFileUploadPostWork() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", "orig", null, "bar".getBytes());
        mockMvc.perform(fileUpload("/fileUpload").file(file).param("myFile", "test")).andDo(print());

    }

}