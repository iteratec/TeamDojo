package com.iteratec.teamdojo.service.impl.ext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

import com.iteratec.teamdojo.repository.ext.ExtendedImageRepository;
import com.iteratec.teamdojo.service.mapper.ImageMapper;
import java.awt.image.BufferedImage;
import org.junit.jupiter.api.Test;

class ExtendedImageServiceImplTest {

    @Test
    void resize_ifNullPassedInReturnNull() {
        final ExtendedImageRepository repo = mock(ExtendedImageRepository.class);
        final ImageMapper mapper = mock(ImageMapper.class);
        final ExtendedImageServiceImpl sut = new ExtendedImageServiceImpl(repo, mapper);

        final BufferedImage result = sut.resize(null, 0);

        assertThat(result).isNull();
    }
}
