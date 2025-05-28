package com.lucianoortizsilva.migration.jobs.netflix.steps.step02;

import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import com.lucianoortizsilva.migration.jobs.netflix.vo.NetflixCatalogVO;

@Configuration
public class NetflixCatalogFileReaderConfig {
	
	@Bean
	FlatFileItemReader<NetflixCatalogVO> netflixCatalogFileReader() {
		return new FlatFileItemReaderBuilder<NetflixCatalogVO>()//
				.name("netflixCatalogFileReader")//
				.resource(new FileSystemResource("files/netflix.csv"))//
				.delimited().delimiter(";")//
				.names(getNames())//
				.fieldSetMapper(fieldSet -> create(fieldSet))//
				.build();//
	}
	
	private static String[] getNames() {
		return new String[] { //
				"id", //
				"title", //
				"cast", //
				"country", //
				"releaseYear", //
				"duration", //
				"listedIn"//
		};
	}
	
	private static NetflixCatalogVO create(final FieldSet fieldSet) {
		final String id = fieldSet.readString("id");
		final String title = fieldSet.readString("title");
		final String cast = fieldSet.readString("cast");
		final String country = fieldSet.readString("country");
		final String releaseYear = fieldSet.readString("releaseYear");
		final String duration = fieldSet.readString("duration");
		final String listedIn = fieldSet.readString("listedIn");
		return new NetflixCatalogVO(id, title, cast, country, releaseYear, duration, listedIn);
	}
}