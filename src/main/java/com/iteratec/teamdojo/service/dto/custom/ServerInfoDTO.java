/*
 SPDX-FileCopyrightText: the TeamDojo authors
 SPDX-License-Identifier: Apache-2.0
 */
package com.iteratec.teamdojo.service.dto.custom;

import java.time.Instant;
import lombok.*;

/**
 * Hand crafted DTO because JHipster has no ability to just generate a DTO w/o entity
 */
@ToString
@EqualsAndHashCode
@NoArgsConstructor // Needed for Jackson.
@AllArgsConstructor
public final class ServerInfoDTO {

    @Getter
    Instant time;
}
