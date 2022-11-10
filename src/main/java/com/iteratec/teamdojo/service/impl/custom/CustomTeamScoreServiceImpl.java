/*
 SPDX-FileCopyrightText: the TeamDojo authors
 SPDX-License-Identifier: Apache-2.0
 */
package com.iteratec.teamdojo.service.impl.custom;

import com.iteratec.teamdojo.service.custom.CustomTeamScoreService;
import com.iteratec.teamdojo.service.dto.TeamDTO;
import com.iteratec.teamdojo.service.dto.custom.TeamScoreDTO;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CustomTeamScoreServiceImpl implements CustomTeamScoreService {

    @Override
    public TeamScoreDTO calculateTeamScore(@NonNull TeamDTO t) {
        return TeamScoreDTO.NULL;
    }
}
