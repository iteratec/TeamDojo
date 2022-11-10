/*
 SPDX-FileCopyrightText: the TeamDojo authors
 SPDX-License-Identifier: Apache-2.0
 */
package com.iteratec.teamdojo.domain.custom;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * Represents the over-all score of one team.
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@Accessors(chain = true)
public class TeamScore {}
