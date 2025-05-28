package com.lucianoortizsilva.migration.jobs.netflix.steps.step03;

import static java.util.Objects.nonNull;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;
import java.util.List;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.support.ClassifierCompositeItemWriter;
import org.springframework.batch.item.support.builder.ClassifierCompositeItemWriterBuilder;
import org.springframework.classify.Classifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.CollectionUtils;
import com.lucianoortizsilva.migration.jobs.netflix.vo.NetflixCatalogVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class CompositeNetflixCatalogJdbcWriterConfig {
	
	@Bean
	ClassifierCompositeItemWriter<NetflixCatalogVO> compositeNetflixCatalogJdbcWriter(final JdbcBatchItemWriter<NetflixCatalogVO> netflixCatalogDocumentaryJdbcWriter, final JdbcBatchItemWriter<NetflixCatalogVO> netflixCatalogComedieJdbcWriter) {
		return new ClassifierCompositeItemWriterBuilder<NetflixCatalogVO>()//
				.classifier(writer(netflixCatalogDocumentaryJdbcWriter, netflixCatalogComedieJdbcWriter))//
				.build();//
	}
	
	private static Classifier<NetflixCatalogVO, ItemWriter<? super NetflixCatalogVO>> writer(final JdbcBatchItemWriter<NetflixCatalogVO> netflixCatalogDocumentaryJdbcWriter, final JdbcBatchItemWriter<NetflixCatalogVO> netflixCatalogComedieJdbcWriter) {
		return new Classifier<>() {
			
			private static final long serialVersionUID = 1L;
			
			@Override
			public ItemWriter<? super NetflixCatalogVO> classify(final NetflixCatalogVO vo) {
				final String[] listedIn = vo.listedIn().split(",");
				if (existsAllData(vo)) {
					if (isDocumentary(listedIn)) {
						return netflixCatalogDocumentaryJdbcWriter;
					} else if (isComedie(listedIn)) {
						return netflixCatalogComedieJdbcWriter;
					}
				}
				return dummyWriter;
			}
			
			private boolean existsAllData(final NetflixCatalogVO vo) {
				return isNotEmpty(vo.id())//
						&& isNotEmpty(vo.cast())//
						&& isNotEmpty(vo.country())//
						&& isNotEmpty(vo.duration())//
						&& isNotEmpty(vo.listedIn())//
						&& isNotEmpty(vo.title())//
						&& isNotEmpty(vo.releaseYear());//
			}
			
			@SuppressWarnings("unchecked")
			private boolean isDocumentary(final String[] listedIn) {
				if (nonNull(listedIn)) {
					final List<String> categories = (List<String>) CollectionUtils.arrayToList(listedIn);
					return categories.stream().anyMatch(category -> category.equalsIgnoreCase("Documentaries"));
				}
				return false;
			}
			
			@SuppressWarnings("unchecked")
			private boolean isComedie(final String[] listedIn) {
				if (nonNull(listedIn)) {
					final List<String> categories = (List<String>) CollectionUtils.arrayToList(listedIn);
					return categories.stream().anyMatch(category -> category.equalsIgnoreCase("Comedies"));
				}
				return false;
			}
			
			ItemWriter<NetflixCatalogVO> dummyWriter = items -> {
				log.info(items.toString());
			};
		};
	}
}