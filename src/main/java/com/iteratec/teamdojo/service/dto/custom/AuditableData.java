/*
 SPDX-FileCopyrightText: the TeamDojo authors
 SPDX-License-Identifier: Apache-2.0
 */
package com.iteratec.teamdojo.service.dto.custom;

import java.time.Instant;

/**
 * Describes DTOs wich hold audit data
 * <p>
 * Audit data means that implementations hold information when such a DTO is created and updated.
 * </p>
 * <p>
 * The API methods define some requirements about nullability of method parameters. This can't be validated w/o changing
 * generated code. So this is not enforced and only specified as text.
 * </p>
 */
public interface AuditableData {
    /**
     * Get the primary key for that object
     *
     * @return may be {@code null}, if not persisted yet
     */
    Long getId();

    /**
     * Get time of creation
     *
     * @return may be {@code null}, if newly created
     */
    Instant getCreatedAt();

    /**
     * Set time of creation
     * <p>
     * Must not be altered after first set on creation!
     * </p>
     *
     * @param createdAt not {@code null}
     */
    void setCreatedAt(Instant createdAt);

    /**
     * Get time of last modification
     * <p>
     * Same value as {@link #getCreatedAt()} on first creation.
     * </p>
     *
     * @return may be {@code null}, if newly created
     */
    Instant getUpdatedAt();

    /**
     * Set time of modification
     *
     * @param updatedAt not {@code null}
     */
    void setUpdatedAt(Instant updatedAt);
}
