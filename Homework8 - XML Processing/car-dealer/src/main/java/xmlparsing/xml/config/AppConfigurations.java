package xmlparsing.xml.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import xmlparsing.xml.utils.impls.ValidationUtilImpl;
import xmlparsing.xml.utils.impls.XmlParserImpl;
import xmlparsing.xml.utils.interfaces.ValidationUtil;
import xmlparsing.xml.utils.interfaces.XmlParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;

@Configuration
public class AppConfigurations {
    @Bean
    public BufferedReader bufferedReader() {
        return new BufferedReader(new InputStreamReader(System.in));
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public ValidationUtil validationUtil() {
        return new ValidationUtilImpl();
    }

    @Bean
    public XmlParser xmlParser() {
        return new XmlParserImpl();
    }
}
