package xml.productshop.demo.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import xml.productshop.demo.utils.impls.ValidationUtilImpl;
import xml.productshop.demo.utils.impls.XmlParserImpl;
import xml.productshop.demo.utils.intefaces.ValidationUtil;
import xml.productshop.demo.utils.intefaces.XmlParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;

@Configuration
public class AppConfigurations {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public ValidationUtil validationUtil() {
        return new ValidationUtilImpl();
    }

    @Bean
    public BufferedReader bufferedReader() {
        return new BufferedReader(new InputStreamReader(System.in));
    }

    @Bean
    public XmlParser xmlParser() {
        return new XmlParserImpl();
    }
}
