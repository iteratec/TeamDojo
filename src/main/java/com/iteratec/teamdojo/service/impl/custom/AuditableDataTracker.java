package com.iteratec.teamdojo.service.impl.custom;

import com.iteratec.teamdojo.service.dto.custom.AuditableData;
import com.iteratec.teamdojo.service.mapper.EntityMapper;
import java.time.Instant;
import java.util.Optional;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.Setter;

/**
 * Tracks the creation and modification on {@link AuditableData auditable data objects}
 *
 * <p>This helper class sets the {@link AuditableData#getCreatedAt() creation} and
 * {@link AuditableData#getUpdatedAt()} upodate}  time of a given object. This implementation is responsible to
 * maintain the creation time unchanged for already persisted data and therefore needs a reference to a repository of
 * this data. Here comes the functional interface {@link PersistedSupplier} into play. You cna use this tracker like
 * this in the services:</p>
 * <pre>
 *     {@code}
 *     private final ModificationTracker<DataDTO, Data> tracker = ModificationTracker<>(new DataMapper());
 *
 *     &#064;Override
 *     public ActivityDTO save(final DataDTO data) {
 *         tracker.modifyCreatedAndUpdatedAt(data, repository::findById);
 *         return super.save(data);
 *     }
 *     {@code}
 * </pre>
 */
final class AuditableDataTracker<D extends AuditableData, E> {

    @NonNull
    @Setter(AccessLevel.PROTECTED)
    private InstantProvider time = InstantProvider.DEFAULT;

    private final EntityMapper<D, E> mapper;
    private final PersistedSupplier<E> supplier;

    /**
     * Dedicated constructor
     *
     * <p>Use this constructor if you need a different implementation for {@code time}</p>
     *
     * @param mapper   not {@code null}
     * @param supplier not {@code null}
     */
    AuditableDataTracker(final @NonNull EntityMapper<D, E> mapper, final @NonNull PersistedSupplier<E> supplier) {
        super();
        this.mapper = mapper;
        this.supplier = supplier;
    }

    /**
     * Tracks the modification time for given data object
     * <p>
     * Modification here means that this method sets {@link AuditableData#setCreatedAt(Instant) creation} and
     * {@link AuditableData#setUpdatedAt(Instant) modification}.
     * </p>
     * <p>
     * The {@link PersistedSupplier} is needed to lookup if the data is already persisted by
     * {@link AuditableData#getId()} to maintain the creation time.
     * </p>
     *
     * @param dto not {@code null}
     */
    void modifyCreatedAndUpdatedAt(final @NonNull AuditableData dto) {
        final var now = time.now();
        // Here use one instant to avoid minimal drift of time,
        // if we would use a second Instant.now() invocation for the updated time.
        var creationTime = determineCreationTime(dto, now);

        dto.setUpdatedAt(now);
        dto.setCreatedAt(creationTime);
    }

    private Instant determineCreationTime(final AuditableData dto, final Instant fallback) {
        final var id = dto.getId();

        if (id == null) {
            return fallback;
        }

        return findPersistedDto(id).map(AuditableData::getCreatedAt).orElse(fallback);
    }

    private Optional<D> findPersistedDto(final Long id) {
        return supplier.get(id).map(mapper::toDto);
    }

    /**
     * Represents a functional  supplier of persisted data object
     *
     * <p>We use this to encapsulate lookup of data via repositories in a generic way. We can't use
     * {@link java.util.function.Supplier} because we need to pass an id to the invocation.</p>
     *
     * @param <E> type of entity to lookup
     */
    @FunctionalInterface
    interface PersistedSupplier<E> {
        /**
         * Returns the looked up entity
         *
         * @param id not {@code null}
         * @return never {@code null}
         */
        Optional<E> get(Long id);
    }
}
