package org.ktunaxa.referral.server.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.parsing.BeanComponentDefinition;
import org.springframework.beans.factory.parsing.ComponentDefinition;
import org.springframework.beans.factory.parsing.EmptyReaderEventListener;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.web.context.support.XmlWebApplicationContext;

public class LogXmlContext extends XmlWebApplicationContext {

    @Override
    protected void initBeanDefinitionReader(XmlBeanDefinitionReader reader) {
        super.initBeanDefinitionReader(reader);
        reader.setEventListener(new LogReaderEventListener());
    }
    
    class LogReaderEventListener extends EmptyReaderEventListener {

        private final Logger log = LoggerFactory.getLogger(LogReaderEventListener.class);

        @Override
        public void componentRegistered(ComponentDefinition componentDefinition) {

            log.info("Registered Component [" + componentDefinition.getName() + "]");
            for (BeanDefinition bd : componentDefinition.getBeanDefinitions()) {
                String name = bd.getBeanClassName();

                if (bd instanceof BeanComponentDefinition) {
                    name = ((BeanComponentDefinition) bd).getBeanName();
                }
                log.info("Registered bean definition: [" + name + "]" + 
                        " from " + bd.getResourceDescription());
            }
        }



    }


}
