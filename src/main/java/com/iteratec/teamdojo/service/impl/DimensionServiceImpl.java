package com.iteratec.teamdojo.service.impl;

import com.iteratec.teamdojo.GeneratedByJHipster;
import com.iteratec.teamdojo.domain.Dimension;
import com.iteratec.teamdojo.repository.DimensionRepository;
import com.iteratec.teamdojo.service.DimensionService;
import com.iteratec.teamdojo.service.dto.DimensionDTO;
import com.iteratec.teamdojo.service.mapper.DimensionMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.iteratec.teamdojo.domain.Dimension}.
 */
@Service
@Transactional
@GeneratedByJHipster
public class DimensionServiceImpl implements DimensionService {

    private final Logger log = LoggerFactory.getLogger(DimensionServiceImpl.class);

    private final DimensionRepository dimensionRepository;

    private final DimensionMapper dimensionMapper;

    public DimensionServiceImpl(DimensionRepository dimensionRepository, DimensionMapper dimensionMapper) {
        this.dimensionRepository = dimensionRepository;
        this.dimensionMapper = dimensionMapper;
    }

    @Override
    public DimensionDTO save(DimensionDTO dimensionDTO) {
        log.debug("Request to save Dimension : {}", dimensionDTO);
        Dimension dimension = dimensionMapper.toEntity(dimensionDTO);
        dimension = dimensionRepository.save(dimension);
        return dimensionMapper.toDto(dimension);
    }

    @Override
    public DimensionDTO update(DimensionDTO dimensionDTO) {
        log.debug("Request to update Dimension : {}", dimensionDTO);
        Dimension dimension = dimensionMapper.toEntity(dimensionDTO);
        dimension = dimensionRepository.save(dimension);
        return dimensionMapper.toDto(dimension);
    }

    @Override
    public Optional<DimensionDTO> partialUpdate(DimensionDTO dimensionDTO) {
        log.debug("Request to partially update Dimension : {}", dimensionDTO);

        return dimensionRepository
            .findById(dimensionDTO.getId())
            .map(existingDimension -> {
                dimensionMapper.partialUpdate(existingDimension, dimensionDTO);

                return existingDimension;
            })
            .map(dimensionRepository::save)
            .map(dimensionMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DimensionDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Dimensions");
        return dimensionRepository.findAll(pageable).map(dimensionMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<DimensionDTO> findOne(Long id) {
        log.debug("Request to get Dimension : {}", id);
        return dimensionRepository.findById(id).map(dimensionMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Dimension : {}", id);
        dimensionRepository.deleteById(id);
    }
}
