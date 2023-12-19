package alotra.milktea;

import alotra.milktea.config.StorageProperties;
import alotra.milktea.service.IStorageService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import alotra.milktea.service.Email;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import alotra.milktea.service.CloudStorageServiceImpl;
@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
public class MilkTeaShopsChainManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(MilkTeaShopsChainManagementApplication.class, args);
	}
	@Bean
	CommandLineRunner init(IStorageService storageService) {
		return (args -> {
			storageService.init();
		});
	}
	@Bean
	IStorageService storageService() {
		return new CloudStorageServiceImpl();
	}
}
