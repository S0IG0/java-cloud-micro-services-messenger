package ru.soigo.auth.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for setting up the ModelMapper bean.
 * <p>
 * This class provides a configuration for the ModelMapper used
 * within the application, ensuring that the mapping strategy is set
 * to strict to avoid unintended matches.
 * </p>
 * <p>
 * The ModelMapper is a popular library used to map objects of one type to another.
 * By setting the matching strategy to {@link org.modelmapper.convention.MatchingStrategies#STRICT},
 * we ensure that properties between source and destination objects must match exactly
 * for mapping to occur.
 * </p>
 *
 * <p><b>Usage:</b></p>
 * <pre>
 * {@code
 *  @Autowired
 *  private ModelMapper modelMapper;
 * }
 * </pre>
 *
 * @see org.modelmapper.ModelMapper
 * @see org.modelmapper.convention.MatchingStrategies
 */
@Configuration
public class MapperConfig {

    /**
     * Creates and configures a {@link ModelMapper} bean.
     * <p>
     * The {@link ModelMapper} is configured with a strict matching strategy to
     * ensure that only exact matches between source and destination properties
     * are mapped. This reduces the risk of incorrect mappings and ensures
     * greater control over the mapping process.
     * </p>
     *
     * @return a configured {@link ModelMapper} instance
     */
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper mapper = new ModelMapper();
        mapper
                .getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT);
        return mapper;
    }
}
