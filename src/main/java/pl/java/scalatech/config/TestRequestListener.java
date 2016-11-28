package pl.java.scalatech.config;

import java.util.UUID;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;

import org.slf4j.MDC;

import lombok.extern.slf4j.Slf4j;
@Slf4j
public class TestRequestListener implements ServletRequestListener {    


 @Override
public void requestInitialized(ServletRequestEvent arg0) {
    log.debug("++++++++++++ REQUEST INITIALIZED +++++++++++++++++");

    MDC.put("RequestId", UUID.randomUUID().toString());

 }

 @Override
public void requestDestroyed(ServletRequestEvent arg0) {
   log.debug("-------------REQUEST DESTROYED ------------");
    MDC.clear(); 
 }
}