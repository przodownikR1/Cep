package pl.java.scalatech.web.controller;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.google.common.base.Joiner;

@Controller
@Slf4j
public class FileUploadController {

    @RequestMapping(value = "/fileUpload", method = RequestMethod.GET)
    public String redirect() {
        return "fileUpload";
    }

    @RequestMapping(value = "/fileUpload", method = RequestMethod.POST)
    public String importParse(@RequestParam("myFile") MultipartFile myFile, RedirectAttributes ra) {
        ra.addFlashAttribute("message", "Successfully upload..");
        log.info("++++                  {}", myFile);
        
        log.info("++++ contentType                 {}", myFile.getContentType());
        log.info("++++ name                        {}", myFile.getName());
        log.info("++++ size                        {}", myFile.getSize());

        ra.addFlashAttribute("info", Joiner.on(",").join(new String[] { myFile.getContentType(), myFile.getName(), "" + myFile.getSize(),myFile.getOriginalFilename() }));
        return "redirect:uploadSuccess.html";
    }

    @RequestMapping(value = "uploadSuccess.html", method = RequestMethod.GET)
    @ResponseBody
    public String successMessage(Model model) {
        return model.asMap().get("info").toString();
    }
}
